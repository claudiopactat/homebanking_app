package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountStatus;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AccountRepository accountRepository;



    @GetMapping("/clients")
    public List<ClientDTO> getClient(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    /*
    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        //return repo.findById(id).stream().map(ClientDTO::new).collect(toList());
        //return new ClientDTO(repo.findById(id).get());
        return repo.findById(id).map(ClientDTO::new).orElse(null);
    }
    */

    @GetMapping("/clients/current")
    public ClientDTO getClient(Authentication authentication) {
        ClientDTO client = new ClientDTO(clientRepository.findByEmail(authentication.getName()));
        return client;
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Faltan datos, revise espacios vacios o incompletos", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) !=  null) {
            return new ResponseEntity<>("El usuario ya esta en uso", HttpStatus.FORBIDDEN);
        }


        Client client1 = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client1);
        Account account1 = new Account("VIN-" + ((int) ((Math.random() * (99999999 - 0)) + 0)), AccountStatus.CPG, LocalDateTime.now(), 0.0, client1, AccountType.SAVING);
        accountRepository.save(account1);

        return new ResponseEntity<>("Se ha registrado con exito", HttpStatus.CREATED);
    }

}
