package com.app.emailserver.infrastructure.controller;

import com.app.emailserver.domain.model.data.users.User;
import com.app.emailserver.domain.model.data.users.UserDataAuth;
import com.app.emailserver.application.security.DataJWTtoken;
import com.app.emailserver.application.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DataJWTtoken> login(@RequestBody @Valid UserDataAuth datos) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(datos.login(), datos.password());
        Authentication authentication = manager.authenticate(authenticationToken);
        String tokenJWT = tokenService.generarToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(new DataJWTtoken(tokenJWT));
    }
}
