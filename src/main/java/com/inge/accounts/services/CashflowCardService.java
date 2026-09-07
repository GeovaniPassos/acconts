package com.inge.accounts.services;

import com.inge.accounts.domain.dto.CashflowCardDto;
import com.inge.accounts.domain.dto.CashflowCardPatchDto;
import com.inge.accounts.domain.entity.CashflowCard;
import com.inge.accounts.domain.entity.User;
import com.inge.accounts.exceptions.BusinessException;
import com.inge.accounts.repository.CashflowCardRepository;
import com.inge.accounts.repository.ExpensesRepository;
import com.inge.accounts.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CashflowCardService {
    private final CashflowCardRepository cardRepository;
    private final ExpensesRepository expensesRepository;
    private final UserRepository userRepository;

    public CashflowCardService(CashflowCardRepository cardRepository, ExpensesRepository expensesRepository,
                               UserRepository userRepository) {
        this.cardRepository = cardRepository;
        this.expensesRepository = expensesRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CashflowCardDto> findAllByUser(String username) {
        User user = findUser(username);
        return cardRepository.findAllByUserIdOrderByIdAsc(user.getId()).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public CashflowCardDto createByUser(CashflowCardDto dto, String username) {
        CashflowCard card = new CashflowCard();
        card.setName(dto.name().trim());
        card.setUser(findUser(username));
        return toDto(cardRepository.save(card));
    }

    @Transactional
    public void patchByUser(Long id, CashflowCardPatchDto dto, String username) {
        CashflowCard card = findCard(id, username);
        if (dto.name() != null && !dto.name().isBlank()) card.setName(dto.name().trim());
    }

    @Transactional
    public void deleteByUser(Long id, String username) {
        CashflowCard card = findCard(id, username);
        if (expensesRepository.existsByCashflowCardId(id)) {
            throw new BusinessException("Não é possível excluir um card que possui despesas associadas.");
        }
        cardRepository.delete(card);
    }

    private CashflowCard findCard(Long id, String username) {
        User user = findUser(username);
        return cardRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new BusinessException("Card de fluxo não encontrado."));
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));
    }

    private CashflowCardDto toDto(CashflowCard card) {
        return new CashflowCardDto(card.getId(), card.getName());
    }
}
