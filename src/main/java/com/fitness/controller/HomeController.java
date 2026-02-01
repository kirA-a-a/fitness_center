package com.fitness.controller;

import com.fitness.entity.ContactMessage;
import com.fitness.repository.ContactMessageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {

    @Autowired
    private ContactMessageRepository contactMessageRepository;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        model.addAttribute("contactMessage", new ContactMessage());
        return "contacts";
    }

    @PostMapping("/contacts")
    public String submitContact(@Valid ContactMessage contactMessage, 
                                 BindingResult bindingResult,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("contactMessage", contactMessage);
            return "contacts";
        }
        
        contactMessageRepository.save(contactMessage);
        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Спасибо! Ваше сообщение успешно отправлено.");
        
        return "redirect:/contacts";
    }

    @PostMapping("/api/contacts")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> submitContactAjax(@Valid @RequestBody ContactMessage contactMessage,
                                                                  BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            response.put("success", false);
            response.put("errors", errors);
            return ResponseEntity.badRequest().body(response);
        }
        
        contactMessageRepository.save(contactMessage);
        response.put("success", true);
        response.put("message", "Спасибо! Ваше сообщение успешно отправлено.");
        
        return ResponseEntity.ok(response);
    }
}
