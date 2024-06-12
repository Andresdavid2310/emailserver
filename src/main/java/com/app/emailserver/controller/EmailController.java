package com.app.emailserver.controller;

import com.app.emailserver.domain.model.dto.input.EmailDto;
import com.app.emailserver.domain.model.dto.input.Emails;
import com.app.emailserver.domain.model.dto.output.EmailResponse;
import com.app.emailserver.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @GetMapping
    public ResponseEntity<Emails> getAllEmails() {
        return emailService.getAllEmails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailDto> getEmailById(@PathVariable Long id){
       return emailService.getEmailById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailResponse>  updateEmail(@PathVariable Long id, @RequestBody EmailDto email){
        return emailService.updateEmail(id, email);
    }

    @PostMapping
    public ResponseEntity<Emails> createEmail(@RequestBody Emails emails) {
        return emailService.saveEmails(emails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmailResponse> deleteEmail(@PathVariable Long id) {
        return emailService.deleteEmail(id);
    }
}
