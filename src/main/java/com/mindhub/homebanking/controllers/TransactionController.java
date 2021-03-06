package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> newTransaction(Authentication authentication,
                                         @RequestParam Double amount,
                                         @RequestParam String description,
                                         @RequestParam String numberOrigin,
                                         @RequestParam String numberDestiny) {

        Client client = clientRepository.findByEmail(authentication.getName());

        Account accountOrigin = accountRepository.findByNumber(numberOrigin);
        Account accountDestiny = accountRepository.findByNumber(numberDestiny);
        Set<Account> setNumberOrigin = client.getAccounts();


        if(accountOrigin.isActive() == false){
            return new ResponseEntity<>("La cuenta de origen no esta activa", HttpStatus.FORBIDDEN);
        }

        if(accountDestiny.isActive() == false){
            return new ResponseEntity<>("La cuenta de destino no esta activa", HttpStatus.FORBIDDEN);
        }


        //Verificar que los par??metros no est??n vac??os
        if ((amount == null) || (description == null) || (numberDestiny == null) || (numberOrigin == null)) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        //Verificar que los n??meros de cuenta no sean iguales
        if (numberOrigin.equals(numberDestiny)) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        //Verificar que exista la cuenta de origen
        if (accountOrigin == null) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        // Verificar que la cuenta de origen pertenezca al cliente autenticado
        if(!setNumberOrigin.contains(accountOrigin)) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        //Verificar que exista la cuenta de destino
        if (accountDestiny == null) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        //Verificar que la cuenta de origen tenga el monto disponible.
        if(accountOrigin.getBalance() < amount){
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }



        //Descontamos de las cuentas correspondientes
        Double auxOrigin = accountOrigin.getBalance() - amount;
        Double auxDestiny = accountDestiny.getBalance() + amount;

        //Actualizamos los montos en las cuentas
        accountOrigin.setBalance(auxOrigin);
        accountDestiny.setBalance(auxDestiny);

        //Creamos las transacciones
        Transaction transactionOrigin = new Transaction(TransactionType.DEBIT, amount, accountOrigin.getNumber() + description, LocalDateTime.now(), accountOrigin, auxOrigin);
        Transaction transactionDestiny = new Transaction(TransactionType.CREDIT, amount, accountDestiny.getNumber() + description, LocalDateTime.now(), accountDestiny, auxDestiny);

        transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionDestiny);


        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);

    }


}
