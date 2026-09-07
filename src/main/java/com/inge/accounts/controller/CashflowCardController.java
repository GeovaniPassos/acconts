package com.inge.accounts.controller;

import com.inge.accounts.domain.dto.CashflowCardDto;
import com.inge.accounts.domain.dto.CashflowCardPatchDto;
import com.inge.accounts.domain.validations.OnCreate;
import com.inge.accounts.response.ApiResponse;
import com.inge.accounts.services.CashflowCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cashflow-cards")
public class CashflowCardController {
    private final CashflowCardService service;

    public CashflowCardController(CashflowCardService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CashflowCardDto>>> findAll(Authentication authentication) {
        return ResponseEntity.ok(
            ApiResponse.success(
                "Cards de fluxo carregados", 
                service.findAllByUser(authentication.getName())));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CashflowCardDto>> create(@Validated(OnCreate.class) @RequestBody CashflowCardDto dto,
                                                                 Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    ApiResponse.success(
                        "Card criado", 
                        service.createByUser(dto, authentication.getName())));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> patch(@PathVariable Long id, @RequestBody CashflowCardPatchDto dto,
                                                    Authentication authentication) {
        service.patchByUser(id, dto, authentication.getName());
        return ResponseEntity.ok(
            ApiResponse.success(
                "Card atualizado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id, Authentication authentication) {
        service.deleteByUser(id, authentication.getName());
        return ResponseEntity.ok(
            ApiResponse.success(
                "Card removido"));
    }
}
