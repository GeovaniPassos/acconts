package com.inge.accounts.domain.dto;

import jakarta.validation.constraints.Size;

public record CashflowCardPatchDto(
        @Size(max = 40, message = "O nome do card deve ter no máximo 40 caracteres.")
        String name
) {}
