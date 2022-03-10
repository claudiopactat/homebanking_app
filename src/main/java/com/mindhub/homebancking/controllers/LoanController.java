package com.mindhub.homebancking.controllers;

import com.mindhub.homebancking.dtos.ClientDTO;
import com.mindhub.homebancking.dtos.LoanApplicationDTO;
import com.mindhub.homebancking.dtos.LoanDTO;
import com.mindhub.homebancking.models.*;
import com.mindhub.homebancking.repositories.AccountRepository;
import com.mindhub.homebancking.repositories.ClientLoanRepository;
import com.mindhub.homebancking.repositories.ClientRepository;
import com.mindhub.homebancking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoan(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<String> newLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO){

        Client client = clientRepository.findByEmail(authentication.getName());
        Loan loanCurrent = loanRepository.findByName(loanApplicationDTO.getName());
        Account accountCurrent = accountRepository.findByNumber(loanApplicationDTO.getNumber());


        // Verificamos que los datos no esten vacios
        if (loanApplicationDTO.getNumber().isEmpty() || loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("403 prohibido, campos imcompletos o datos erroneos", HttpStatus.FORBIDDEN);
        }

        // Verificamos que el prestamo exista:
        if (loanCurrent == null){
            return new ResponseEntity<>("403 prohibido, el prestamo no existe", HttpStatus.FORBIDDEN);
        }

        // Verificamos que el monto solicitado no exceda el monto maximo del prestamo
        if (loanApplicationDTO.getAmount() > loanCurrent.getMaxAmount()){
            return new ResponseEntity<>("403 prohibido, el monto solicitado excede el monto maximo del prestamo", HttpStatus.FORBIDDEN);
        }

        // Verificamos que las cantidades de cuotas se encuentren entre las disponibles del prestamo
        if (!loanCurrent.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("403 prohibido, la cantidad de cuotas no es correcta", HttpStatus.FORBIDDEN);
        }

        // Verificamos que la cuenta de destino exista
        if (accountCurrent == null){
            return new ResponseEntity<>("403 prohibido, la cuenta de destino no existe", HttpStatus.FORBIDDEN);
        }

        // Verificamos que la cuenta de destino pertenezca al cliente autenticado
        if (!client.getAccounts().contains(accountCurrent)){
            return new ResponseEntity<>("403 prohibido, la cuenta de destino no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        // Creamos una solicitud de préstamo con el monto solicitado sumando el 20% del mismo
        Double loanInterest = loanApplicationDTO.getAmount() * 1.2;

        ClientLoan newClientLoan = new ClientLoan(loanCurrent.getName(), loanApplicationDTO.getAmount(), loanApplicationDTO.getPayments(), client);
        clientLoanRepository.save(newClientLoan);




        // Actualizamos el monto de la cuenta
        accountCurrent.setBalance(accountCurrent.getBalance() + loanApplicationDTO.getAmount());

        // Creamos la nueva transaccion
        Transaction newTransaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(),
                loanApplicationDTO.getNumber() + " loan approved", LocalDateTime.now(), accountCurrent, accountCurrent.getBalance());


        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }



}
