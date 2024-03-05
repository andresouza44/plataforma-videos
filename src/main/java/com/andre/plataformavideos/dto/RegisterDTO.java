package com.andre.plataformavideos.dto;

import com.andre.plataformavideos.entity.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
