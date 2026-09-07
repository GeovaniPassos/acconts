package com.inge.accounts.domain.dto;

import com.inge.accounts.domain.validations.OnCreate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CashflowCardDto(
        Long id,
        @NotBlank(groups = OnCreate.class, message = "O nome do card é obrigatório.")
        @Size(max = 40, message = "O nome do card deve ter no máximo 40 caracteres.")
        String name
) {}
