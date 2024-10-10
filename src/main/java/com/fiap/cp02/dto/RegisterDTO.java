package com.fiap.cp02.dto;


import com.fiap.cp02.model.UserRole;

public record RegisterDTO(String login, String senha, UserRole role) {}
