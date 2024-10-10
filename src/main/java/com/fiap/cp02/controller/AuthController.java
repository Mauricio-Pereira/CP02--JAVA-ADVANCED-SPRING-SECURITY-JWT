package com.fiap.cp02.controller;
import com.fiap.cp02.dto.AuthDTO;
import com.fiap.cp02.dto.LoginResponseDTO;
import com.fiap.cp02.dto.RegisterDTO;
import com.fiap.cp02.model.Usuario;
import com.fiap.cp02.repository.UsuarioRepository;
import com.fiap.cp02.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthDTO authDTO) {
        // Gera um token do usuário e senha
        var usuarioSenha = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.senha());
        // Autentica esse token
        var auth = this.authenticationManager.authenticate(usuarioSenha);
        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO registerDTO) {
        if (usuarioRepository.findByLogin(registerDTO.login()) != null) {
            return ResponseEntity.badRequest().build();
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.senha());
        Usuario novoUsuario = new Usuario(registerDTO.login(), encryptedPassword, registerDTO.role());
        usuarioRepository.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
