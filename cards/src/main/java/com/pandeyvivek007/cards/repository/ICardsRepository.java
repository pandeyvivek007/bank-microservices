package com.pandeyvivek007.cards.repository;

import com.pandeyvivek007.cards.entity.Cards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.smartcardio.Card;
import java.util.Optional;

@Repository
public interface ICardsRepository extends JpaRepository<Cards, Long> {
    Optional<Cards> findCardsByMobileNumber(String mobileNumber);
}
