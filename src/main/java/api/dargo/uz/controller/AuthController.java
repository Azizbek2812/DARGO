package api.dargo.uz.controller;

import api.dargo.uz.dto.AppResponse;
import api.dargo.uz.dto.AuthDTO;
import api.dargo.uz.dto.ProfileDTO;
import api.dargo.uz.dto.RegistrationDTO;
import api.dargo.uz.dto.sms.SmsVerificationDTO;
import api.dargo.uz.enums.AppLanguage;
import api.dargo.uz.service.AuthService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.parser.AcceptLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<AppResponse<String>> registration(@Valid @RequestBody RegistrationDTO dto,
                                                            @RequestHeader(value = "Accept-Language" , defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(authService.registration(dto, lang));
    }
    @GetMapping("/registration/email-verification/{token}")
    public ResponseEntity<AppResponse<String>> regVerification(@PathVariable("token") String token,
                                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") String lang) {
        return ResponseEntity.ok().body(authService.registrationEmailVerification(token, lang));
    }

    @PostMapping("/login")
    public ResponseEntity<AppResponse<ProfileDTO>> registration(@Valid @RequestBody AuthDTO dto,
                                                                @RequestHeader(value = "Accept-Language" , defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(authService.login(dto, lang));
    }
    @PostMapping("/registration/sms-verification")
    public ResponseEntity<String> smsVerification(@RequestBody SmsVerificationDTO dto ,
                                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") String lang) {
        return ResponseEntity.ok().body(authService.registrationSmsVerification(dto, lang));
    }
}
