package com.mindhub.homebancking.controllers;

import com.mindhub.homebancking.dtos.AccountDTO;
import com.mindhub.homebancking.dtos.ClientDTO;
import com.mindhub.homebancking.models.Account;
import com.mindhub.homebancking.models.AccountStatus;
import com.mindhub.homebancking.models.AccountType;
import com.mindhub.homebancking.models.Client;
import com.mindhub.homebancking.repositories.AccountRepository;
import com.mindhub.homebancking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccount(){
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam AccountType accountType){

        Client client = clientRepository.findByEmail(authentication.getName());

        List<Account> accountList = client.getAccounts().stream().filter(account -> { return account.isActive(); }).collect(toList());

        if(accountList.size() >= 3){
            return new ResponseEntity<>("No se pueden crear mas cuentas", HttpStatus.FORBIDDEN);
        }

        Account account1 = new Account("VIN-" + ((int) ((Math.random() * (99999999 - 0)) + 0)), AccountStatus.CPG, LocalDateTime.now(), 0.0, client, accountType);
        accountRepository.save(account1);

        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts/delete/{id}")
    public ResponseEntity<Object> accountDelete(@PathVariable Long id) {
        Account account = accountRepository.findById(id).orElse(null);

        if (account.getBalance() > 0) {
            return new ResponseEntity<>("No se puede borrar una cuenta con saldo en la cuenta", HttpStatus.FORBIDDEN);
        }

        account.setActive(false);
        accountRepository.save(account);

        return new ResponseEntity<>("Cuenta eliminada correctamente", HttpStatus.CREATED);
    }


}
