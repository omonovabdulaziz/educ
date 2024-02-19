package it.live.educationtest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.ForgetPasswordDTO;
import it.live.educationtest.payload.LoginDTO;
import it.live.educationtest.payload.RegisterDTO;
import it.live.educationtest.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.loginUser(loginDTO);
    }


    @PutMapping("/forgetPassword")
    public ResponseEntity<ApiResponse> forgetPassword(@Valid @RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        return authService.forgetPassword(forgetPasswordDTO);
    }



    @PutMapping("/confirmPassword")
    public ResponseEntity<ApiResponse> confirmForgetPassword(@RequestParam(value = "code") Integer code, @RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        return authService.confirmForgetPassword(forgetPasswordDTO, code);
    }
}