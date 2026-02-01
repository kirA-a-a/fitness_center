package com.fitness.controller.api;

import com.fitness.entity.ContactMessage;
import com.fitness.repository.ContactMessageRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/messages")
public class ContactMessageApiController {

    private final ContactMessageRepository contactMessageRepository;

    public ContactMessageApiController(ContactMessageRepository contactMessageRepository) {
        this.contactMessageRepository = contactMessageRepository;
    }

    @GetMapping
    public ResponseEntity<List<ContactMessage>> getAll() {
        List<ContactMessage> messages = contactMessageRepository.findAll();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactMessage> getById(@PathVariable Long id) {
        Optional<ContactMessage> message = contactMessageRepository.findById(id);
        return message.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ContactMessage> create(@Valid @RequestBody ContactMessage contactMessage) {
        ContactMessage saved = contactMessageRepository.save(contactMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactMessage> update(@PathVariable Long id, @Valid @RequestBody ContactMessage contactMessage) {
        if (!contactMessageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contactMessage.setId(id);
        ContactMessage updated = contactMessageRepository.save(contactMessage);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!contactMessageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contactMessageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
