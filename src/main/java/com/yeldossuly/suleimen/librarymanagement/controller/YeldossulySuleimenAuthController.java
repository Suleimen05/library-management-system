package com.yeldossuly.suleimen.librarymanagement.controller;

import com.yeldossuly.suleimen.librarymanagement.dto.AuthRegisterRequestDto;
import com.yeldossuly.suleimen.librarymanagement.dto.UserResponseDto;
import com.yeldossuly.suleimen.librarymanagement.service.YeldossulySuleimenAuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class YeldossulySuleimenAuthController {

    private final YeldossulySuleimenAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody AuthRegisterRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }
}
