package api.dargo.uz.controller;

import api.dargo.uz.dto.AppResponse;
import api.dargo.uz.dto.AuthDTO;
import api.dargo.uz.dto.ProfileDTO;
import api.dargo.uz.dto.RegistrationDTO;
import api.dargo.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private  AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<AppResponse<String>> registration(@Valid @RequestBody RegistrationDTO dto){
        return ResponseEntity.ok().body(authService.registration(dto));
    }

    @GetMapping("/registration/verification/{token}")
    public ResponseEntity<String> regVerification(@PathVariable("token") String token){
        return ResponseEntity.ok().body(authService.regVerification(token));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> registration(@Valid @RequestBody AuthDTO dto){
        return ResponseEntity.ok().body(authService.login(dto));
    }
}
