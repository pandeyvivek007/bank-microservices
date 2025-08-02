package com.pandeyvivek007.cards.service.impl;

import com.pandeyvivek007.cards.constants.CardsConstants;
import com.pandeyvivek007.cards.dto.CardDto;
import com.pandeyvivek007.cards.entity.Cards;
import com.pandeyvivek007.cards.exception.CardAlreadyExistsException;
import com.pandeyvivek007.cards.exception.ResourceNotFoundException;
import com.pandeyvivek007.cards.repository.ICardsRepository;
import com.pandeyvivek007.cards.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class CardsServiceImpl implements ICardService {

    @Autowired
    private ICardsRepository iCardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCard = iCardsRepository.findCardsByMobileNumber(mobileNumber);

        if (optionalCard.isPresent()) {
            throw new CardAlreadyExistsException("Card already exists for the given mobile number: " + mobileNumber);
        }

        Cards cards = createNewCard(mobileNumber);
        iCardsRepository.save(cards);
    }



    public Cards createNewCard(String mobileNumber) {
        Cards cards = new Cards();

        Long randomAccountNumber = (Long)(100000000000L + new Random().nextInt(900000000));
        String accountNumber = randomAccountNumber.toString();

        cards.setCardNumber(accountNumber);
        cards.setMobileNumber(mobileNumber);
        cards.setCardType(CardsConstants.CREDIT_CARD);
        cards.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        cards.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        cards.setAmountUsed(0);
        cards.setCreatedAt(LocalDateTime.now());
        cards.setCreatedBy(mobileNumber);
        System.out.println("Card created with account number: " + accountNumber);

        return cards;
    }

    @Override
    public CardDto getCardDetails(String mobileNumber) {
        Optional<Cards> optionalCard = iCardsRepository.findCardsByMobileNumber(mobileNumber);
        if (optionalCard.isPresent()) {
            Cards card = optionalCard.get();
            return new CardDto(card.getCardNumber(), card.getMobileNumber(), card.getCardType(),
                    card.getAvailableAmount(), card.getTotalLimit(), card.getAmountUsed()
                    );
        } else {
            throw new ResourceNotFoundException("Cards", "Mobile Number", mobileNumber);// or throw an exception if preferred
        }
    }

    @Override
    public void updateCardDetails(CardDto cardDto) {
        Optional<Cards> optionalCard = iCardsRepository.findCardsByMobileNumber(cardDto.getMobileNumber());
        if (optionalCard.isPresent()) {
            Cards card = optionalCard.get();
            card.setCardType(cardDto.getCardType());
            card.setAvailableAmount(cardDto.getAvailableAmount());
            card.setTotalLimit(cardDto.getTotalLimit());
            card.setAmountUsed(cardDto.getAmountUsed());
            card.setUpdatedAt(LocalDateTime.now());
            card.setUpdatedBy(cardDto.getMobileNumber());
            iCardsRepository.save(card);
        } else {
            throw new ResourceNotFoundException("Cards", "Mobile Number", cardDto.getMobileNumber());
        }
    }

    @Override
    public void deleteCard(String mobileNumber) {
        Optional<Cards> optionalCard = iCardsRepository.findCardsByMobileNumber(mobileNumber);

        if (optionalCard.isPresent()) {
            iCardsRepository.delete(optionalCard.get());
        } else {
            throw new ResourceNotFoundException("Cards", "Mobile Number", mobileNumber);
        }
    }
}
