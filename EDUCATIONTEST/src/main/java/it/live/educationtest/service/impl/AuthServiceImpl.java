package it.live.educationtest.service.impl;

import it.live.educationtest.entity.User;
import it.live.educationtest.entity.enums.SystemRoleName;
import it.live.educationtest.exception.ForbiddenException;
import it.live.educationtest.exception.MainException;
import it.live.educationtest.exception.NotFoundException;
import it.live.educationtest.jwt.JwtProvider;
import it.live.educationtest.mapper.UserMapper;
import it.live.educationtest.payload.*;
import it.live.educationtest.repository.UserRepository;
import it.live.educationtest.service.AuthService;
import it.live.educationtest.util.SmsServiceUtil;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseEntity<ApiResponse> registerUser(RegisterDTO registerUserDTO) {
        if (userRepository.existsByPhoneNumber(registerUserDTO.getPhoneNumber()))
            throw new MainException("Bunday telefon raqamli user mavjud");
        int code = new Random().nextInt(9999);
        if (code < 1000)
            code += 1000;
        userRepository.save(userMapper.toEntity(registerUserDTO, code));
        SmsServiceUtil.sendSMS(registerUserDTO.getPhoneNumber(), "Code for EducationTest : " + code);
        return ResponseEntity.ok(ApiResponse.builder().message("Telefon raqamingizga tasdiqlash kodi yuborildi").status(201).build());
    }

    @Override
    public ResponseEntity<ApiResponse> loginUser(LoginDTO loginDTO) {
        User user = userRepository.findByPhoneNumber(loginDTO.getPhoneNumber()).orElseThrow(() -> new ForbiddenException("Login yoki parol xato"));
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new ForbiddenException("Login yoki parol xato");
        return ResponseEntity.ok(new ApiResponse("Xush kelibsiz" , RoleAndToken.builder().token(jwtProvider.generateToken(user)).systemRoleName(user.getSystemRoleName()).build() , 200));
    }

    @Override
    public ResponseEntity<ApiResponse> forgetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        User user = userRepository.findByPhoneNumber(forgetPasswordDTO.getPhoneNumber()).orElseThrow(() -> new NotFoundException("Bunday telefon raqamli foydalanuvchi mavjud emas"));
        int code = new Random().nextInt(9999);
        if (code < 1000)
            code += 1000;
        user.setPhoneCode(code);
        userRepository.save(user);
        SmsServiceUtil.sendSMS(user.getPhoneNumber(), String.valueOf(code));
        return ResponseEntity.ok(ApiResponse.builder().message("Telefon raqamingizga tasdiqlash kodi yuborildi").status(200).build());
    }

    @Override
    public ResponseEntity<ApiResponse> confirmForgetPassword(ForgetPasswordDTO forgetPasswordDTO, Integer code) {
        User user = userRepository.findByPhoneNumberAndPhoneCode(forgetPasswordDTO.getPhoneNumber(), code).orElseThrow(() -> new ForbiddenException("Tasdiqlash kodi xato"));
        if (forgetPasswordDTO.getNewPassword() != null) {
            user.setPassword(passwordEncoder.encode(forgetPasswordDTO.getNewPassword()));
        }
        user.setPhoneCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return ResponseEntity.ok(ApiResponse.builder().message("Muvafaqiyatli").status(200).object(jwtProvider.generateToken(user)).build());
    }
}



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
class RoleAndToken {
    private SystemRoleName systemRoleName;
    private String token;
}

