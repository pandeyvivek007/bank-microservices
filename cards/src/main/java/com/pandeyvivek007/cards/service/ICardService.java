package com.pandeyvivek007.cards.service;

import com.pandeyvivek007.cards.dto.CardDto;

public interface ICardService {

    public void createCard(String mobileNumber);

    public CardDto getCardDetails(String mobileNumber);

   public void updateCardDetails(CardDto cardDto);

   public void deleteCard(String mobileNumber);
}
