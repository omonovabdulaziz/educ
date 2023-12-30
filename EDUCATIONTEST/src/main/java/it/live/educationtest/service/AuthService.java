package it.live.educationtest.service;

import it.live.educationtest.payload.ApiResponse;
import it.live.educationtest.payload.ForgetPasswordDTO;
import it.live.educationtest.payload.LoginDTO;
import it.live.educationtest.payload.RegisterDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponse> registerUser(RegisterDTO registerUserDTO);

    ResponseEntity<ApiResponse> loginUser(LoginDTO loginDTO);

    ResponseEntity<ApiResponse> forgetPassword(ForgetPasswordDTO forgetPasswordDTO);


    ResponseEntity<ApiResponse> confirmForgetPassword(ForgetPasswordDTO forgetPasswordDTO, Integer code);

}
