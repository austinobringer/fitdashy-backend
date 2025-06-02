package com.fitdashy.fitdashy_backend.controllers;

import com.fitdashy.fitdashy_backend.payload.responses.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("")
    public ResponseEntity<?> test(HttpServletRequest request) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new MessageResponse("Success"));
    }
}
