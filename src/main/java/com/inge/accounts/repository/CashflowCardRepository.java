package com.inge.accounts.repository;

import com.inge.accounts.domain.entity.CashflowCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CashflowCardRepository extends JpaRepository<CashflowCard, Long> {
    List<CashflowCard> findAllByUserIdOrderByIdAsc(Long userId);
    Optional<CashflowCard> findByIdAndUserId(Long id, Long userId);
}
