package com.example.blog.controller;

import static com.example.blog.utils.AppConstants.BASE_AUTH_URL;

import com.example.blog.payload.JWTAuthResponse;
import com.example.blog.payload.LoginDto;
import com.example.blog.payload.RegisterDto;
import com.example.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BASE_AUTH_URL)
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(value = {"/login", "/signin"})
  public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
    String token = authService.login(loginDto);
    JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
    jwtAuthResponse.setAccessToken(token);

    return ResponseEntity.ok(jwtAuthResponse);
  }

  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
    String response = authService.register(registerDto);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }
}
