package com.fiap.cp02.repository;

import com.fiap.cp02.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public Usuario findByLogin(String login);

}
