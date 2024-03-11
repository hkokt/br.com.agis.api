package com.br.fatec.AGIS.controller;

import com.br.fatec.AGIS.dto.AuthenticationDto;
import com.br.fatec.AGIS.dto.LoginResponseDto;
import com.br.fatec.AGIS.dto.RegisterDto;
import com.br.fatec.AGIS.model.User;
import com.br.fatec.AGIS.repository.UserRepository;
import com.br.fatec.AGIS.service.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenService tokenService;


    public AuthenticationController(AuthenticationManager authManager, UserRepository userRepo,TokenService tokenServ) {
        authenticationManager = authManager;
        userRepository = userRepo;
        tokenService = tokenServ;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token  = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDto data) {

        if (userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User user = new User(data.login(), encryptedPassword, data.role());

        userRepository.save(user);

        return ResponseEntity.ok("enum:"  + user.getRole());
    }

}
