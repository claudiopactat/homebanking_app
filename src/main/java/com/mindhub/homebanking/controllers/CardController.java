package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CardRepository cardRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication,
                                          @RequestParam CardType cardType,
                                          @RequestParam CardColor cardColor){

        Client client = clientRepository.findByEmail(authentication.getName());

        // filtramos todas las tarjetas de un tipo.. si ya hay 3 no permitimos crear mas
        List<Card> listCard = client.getCards().stream().filter(card -> {
            return card.getType() == cardType;
        }).collect(Collectors.toList());

        // ahora filtramos solo las que estan activas
        List<Card> listCardActives = listCard.stream().filter(card -> {
            return card.isActive();
        }).collect(Collectors.toList());

        if(listCardActives.size() == 3){
            return new ResponseEntity<>("403 prohibida", HttpStatus.FORBIDDEN);
        }

        String cardNumber = CardUtils.getCardNumber();
        int cvv = CardUtils.getCVV();

        Card card1 = new Card(client.getFirstName() + " " + client.getLastName(), cardType, cardColor, cardNumber, cvv,
                LocalDateTime.now(), LocalDateTime.now().plusYears(5), client);

        cardRepository.save(card1);

        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/cards/delete/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id){
        Card card = cardRepository.findById(id).orElse(null);
        card.setActive(false);
        cardRepository.save(card);

        return  new ResponseEntity<>("201 eliminado", HttpStatus.CREATED);
    }
}
