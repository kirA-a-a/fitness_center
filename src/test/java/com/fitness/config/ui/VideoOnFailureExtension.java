package com.fitness.config.ui;

import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.LogEvent;
import com.codeborne.selenide.logevents.LogEventListener;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Allure;
import org.jcodec.api.awt.AWTSequenceEncoder;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.io.SeekableByteChannel;
import org.jcodec.common.model.Rational;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * JUnit 5 Extension для записи видео (MP4) при падении теста.
 * Использует Jcodec для создания видео из скриншотов.
 */
public class VideoOnFailureExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger log = LoggerFactory.getLogger(VideoOnFailureExtension.class);
    
    private static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create(VideoOnFailureExtension.class);
    private static final String FRAMES_KEY = "frames";
    private static final String LISTENER_KEY = "listener";
    private static final String LAST_SCREENSHOT_KEY = "lastScreenshot";
    
    private static final int VIDEO_WIDTH = 1280;
    private static final int VIDEO_HEIGHT = 720;
    private static final int FRAME_RATE = 2; // 2 FPS
    private static final int PAUSE_FRAMES = 4; // 2 секунды паузы на последнем кадре (4 кадра при 2 FPS)

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        List<BufferedImage> frames = Collections.synchronizedList(new ArrayList<>());
        AtomicReference<BufferedImage> lastFrame = new AtomicReference<>();
        
        String listenerId = "VideoRecorder-" + context.getUniqueId();
        
        // Listener для захвата скриншотов после каждого действия Selenide
        LogEventListener listener = new LogEventListener() {
            @Override
            public void afterEvent(LogEvent event) {
                captureFrame(frames, lastFrame);
            }
            
            @Override
            public void beforeEvent(LogEvent event) {
                // Захватываем начальное состояние
                if (frames.isEmpty()) {
                    captureFrame(frames, lastFrame);
                }
            }
        };
        
        SelenideLogger.addListener(listenerId, listener);

        context.getStore(NAMESPACE).put(FRAMES_KEY, frames);
        context.getStore(NAMESPACE).put(LISTENER_KEY, listenerId);
        context.getStore(NAMESPACE).put(LAST_SCREENSHOT_KEY, lastFrame);

        log.info("Starting video recording for test: {}", context.getDisplayName());
    }

    private void captureFrame(List<BufferedImage> frames, AtomicReference<BufferedImage> lastFrame) {
        try {
            if (!WebDriverRunner.hasWebDriverStarted()) {
                return;
            }

            WebDriver driver = WebDriverRunner.getWebDriver();
            if (driver instanceof TakesScreenshot) {
                byte[] data = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                if (data != null && data.length > 0) {
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(data));
                    if (img != null) {
                        BufferedImage scaled = scaleAndPadImage(img, VIDEO_WIDTH, VIDEO_HEIGHT);
                        synchronized (frames) {
                            frames.add(scaled);
                            lastFrame.set(scaled);
                        }
                    }
                }
            }
        } catch (Exception e) {
            // ignore
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void afterTestExecution(ExtensionContext context) {
        List<BufferedImage> frames = (List<BufferedImage>) context.getStore(NAMESPACE).get(FRAMES_KEY);
        String listenerId = context.getStore(NAMESPACE).get(LISTENER_KEY, String.class);
        AtomicReference<BufferedImage> lastFrame = 
                (AtomicReference<BufferedImage>) context.getStore(NAMESPACE).get(LAST_SCREENSHOT_KEY);
        
        // Удаляем listener
        if (listenerId != null) {
            SelenideLogger.removeListener(listenerId);
        }
        
        if (frames == null) {
            return;
        }
        
        // Делаем финальные скриншоты
        for (int i = 0; i < 3; i++) {
            captureFrame(frames, lastFrame);
            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        boolean testFailed = context.getExecutionException().isPresent();

        log.info("Test {} finished. Failed: {}. Frames captured: {}", 
                context.getDisplayName(), testFailed, frames.size());

        if (!testFailed || frames.isEmpty()) {
            return;
        }

        try {
            // Дублируем последний кадр для паузы на ошибке
            if (lastFrame.get() != null) {
                for (int i = 0; i < PAUSE_FRAMES; i++) {
                    frames.add(lastFrame.get());
                }
            }
            
            // Создаём видео
            Path videoPath = createVideo(new ArrayList<>(frames));
            
            // Всегда прикрепляем скриншот с ошибкой
            attachLastScreenshot(lastFrame, context.getDisplayName());
            
            if (videoPath != null && Files.exists(videoPath)) {
                long size = Files.size(videoPath);
                log.info("Video created: {} bytes", size);
                
                // Прикрепляем видео к Allure
                try (InputStream is = Files.newInputStream(videoPath)) {
                    Allure.addAttachment(
                            "Video: " + context.getDisplayName(),
                            "video/mp4",
                            is,
                            ".mp4"
                    );
                }
                
                Files.deleteIfExists(videoPath);
            } else {
                log.warn("Video creation failed");
            }
            
        } catch (Exception e) {
            log.error("Failed to create video: {}", e.getMessage(), e);
            attachLastScreenshot(lastFrame, context.getDisplayName());
        }
    }
    
    private Path createVideo(List<BufferedImage> frames) {
        if (frames.isEmpty()) {
            return null;
        }
        
        Path videoPath = null;
        SeekableByteChannel channel = null;
        
        try {
            videoPath = Files.createTempFile("test-video-", ".mp4");
            channel = NIOUtils.writableChannel(videoPath.toFile());
            
            // Создаём encoder с заданным frame rate
            AWTSequenceEncoder encoder = new AWTSequenceEncoder(channel, Rational.R(FRAME_RATE, 1));
            
            for (BufferedImage frame : frames) {
                // Jcodec требует TYPE_3BYTE_BGR
                BufferedImage converted = convertToRGB(frame);
                encoder.encodeImage(converted);
            }
            
            encoder.finish();
            
            return videoPath;
            
        } catch (Exception e) {
            log.error("Jcodec error: {}", e.getMessage(), e);
            if (videoPath != null) {
                try {
                    Files.deleteIfExists(videoPath);
                } catch (IOException ignored) {}
            }
            return null;
        } finally {
            if (channel != null) {
                NIOUtils.closeQuietly(channel);
            }
        }
    }
    
    private BufferedImage convertToRGB(BufferedImage img) {
        if (img.getType() == BufferedImage.TYPE_3BYTE_BGR) {
            return img;
        }
        BufferedImage converted = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = converted.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.dispose();
        return converted;
    }
    
    private void attachLastScreenshot(AtomicReference<BufferedImage> lastFrame, String testName) {
        try {
            if (lastFrame == null || lastFrame.get() == null) return;
            
            BufferedImage img = lastFrame.get();
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            
            Allure.addAttachment(
                    "Screenshot: " + testName,
                    "image/png",
                    new ByteArrayInputStream(baos.toByteArray()),
                    ".png"
            );
            log.info("Screenshot attached");
        } catch (Exception e) {
            log.error("Failed to attach screenshot: {}", e.getMessage());
        }
    }

    private BufferedImage scaleAndPadImage(BufferedImage original, int targetWidth, int targetHeight) {
        int width = original.getWidth();
        int height = original.getHeight();

        double scale = Math.min((double) targetWidth / width, (double) targetHeight / height);
        
        int newWidth = (int) (width * scale);
        int newHeight = (int) (height * scale);

        BufferedImage scaled = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = scaled.createGraphics();
        
        // Заливаем фон чёрным
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, targetWidth, targetHeight);
        
        // Центрируем изображение
        int x = (targetWidth - newWidth) / 2;
        int y = (targetHeight - newHeight) / 2;
        
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(original, x, y, newWidth, newHeight, null);
        g.dispose();
        
        return scaled;
    }
}
