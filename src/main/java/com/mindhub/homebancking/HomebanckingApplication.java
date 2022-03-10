package com.mindhub.homebancking;

import com.mindhub.homebancking.models.*;
import com.mindhub.homebancking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebanckingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebanckingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository,
									  AccountRepository accountRepository,
									  TransactionRepository transactionRepository,
									  LoanRepository loanRepository,
									  ClientLoanRepository clientLoanRepository,
									  CardRepository cardRepository) {
		return (args) -> {

			//para manipular el tiempo que se muestra
			LocalDateTime date = LocalDateTime.now();
			LocalDateTime newDate = date.plusDays(1);


			// clients
			Client c1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba"));
			clientRepository.save(c1);

			Client c2 = new Client("Claudio", "Pactat", "claudio.pactat@gmail.com", passwordEncoder.encode("claudio"));
			clientRepository.save(c2);


			// accounts
			Account a1 = new Account("VIN001", AccountStatus.CPG, date, 5000.0, c1, AccountType.SAVING);
			accountRepository.save(a1);

			Account a2 = new Account("VIN002", AccountStatus.CANP, newDate, 7500.0, c1, AccountType.SAVING);
			accountRepository.save(a2);

			Account a3 = new Account("VIN003", AccountStatus.CRNCF, date, 3000.0, c1, AccountType.SAVING);
			accountRepository.save(a3);

			Account a4 = new Account("VIN004", AccountStatus.CPG, newDate, 12800.0, c2, AccountType.SAVING);
			accountRepository.save(a4);

			Account a5 = new Account("VIN005", AccountStatus.CANP, date, 9500.0, c2, AccountType.SAVING);
			accountRepository.save(a5);

			Account a6 = new Account("VIN006", AccountStatus.CRNCF, newDate, 2100.0, c2, AccountType.SAVING);
			accountRepository.save(a6);


			// transactions
			Transaction t1 = new Transaction(TransactionType.DEBIT, -2500.0, "TIENDA UNION", date, a1, (a1.getBalance() - 2500));
			transactionRepository.save(t1);

			Transaction t2 = new Transaction(TransactionType.CREDIT, 1000.0, "CR CREDIN TRANSF.", date, a1, (a1.getBalance() -1500));
			transactionRepository.save(t2);

			Transaction t3 = new Transaction(TransactionType.CREDIT, 1500.0, "PAGO CT PLUSPAGOS", date, a1, a1.getBalance());
			transactionRepository.save(t3);

			Transaction t4 = new Transaction(TransactionType.DEBIT, -5000.0, "MERPAGO", date, a2, (a2.getBalance() - 5000));
			transactionRepository.save(t4);

			Transaction t5 = new Transaction(TransactionType.CREDIT, 3500.0, "CR CREDIN TRANSF.", date, a2, (a2.getBalance() + 1500));
			transactionRepository.save(t5);

			Transaction t6 = new Transaction(TransactionType.CREDIT, 1500.0, "PAGO CT PLUSPAGOS", date, a2, a2.getBalance());
			transactionRepository.save(t6);

			Transaction t7 = new Transaction(TransactionType.DEBIT, -5000.0, "MERPAGO", date, a4, (a4.getBalance() - 5000));
			transactionRepository.save(t7);

			Transaction t8 = new Transaction(TransactionType.CREDIT, 3500.0, "CR CREDIN TRANSF.", date, a4, (a4.getBalance() -1500));
			transactionRepository.save(t8);

			Transaction t9 = new Transaction(TransactionType.DEBIT, 1500.0, "PAGO CT PLUSPAGOS", date, a4, a4.getBalance());
			transactionRepository.save(t9);


			// loans
			Loan l1 = new Loan("Hipotecario", 500000.0, List.of(12,24,36,48,60), 60.0);
			loanRepository.save(l1);

			Loan l2 = new Loan("Personal", 100000.0, List.of(6,12,24), 20.0);
			loanRepository.save(l2);

			Loan l3 = new Loan("Automotriz", 300000.0, List.of(6,12,24,36), 40.0);
			loanRepository.save(l3);

			Loan l4 = new Loan("Admin", 200000.0, List.of(6,12), 10.0);
			loanRepository.save(l4);


			// clients-loans
			ClientLoan cl1 = new ClientLoan("Hipotecario",400000.0,60,c1);
			clientLoanRepository.save(cl1);

			ClientLoan cl2 = new ClientLoan("Personal",50000.0,12,c1);
			clientLoanRepository.save(cl2);

			ClientLoan cl3 = new ClientLoan("Personal",100000.0,24,c2);
			clientLoanRepository.save(cl3);

			ClientLoan cl4 = new ClientLoan("Automotriz",200000.0,36,c2);
			clientLoanRepository.save(cl4);


			// cards
			Card card1 = new Card(c1.getFirstName() + " " + c1.getLastName(), CardType.DEBIT, CardColor.SILVER, "8965-4651-8314-6666", 320, date, date.plusYears(5), c1);
			cardRepository.save(card1);

			Card card2 = new Card(c1.getFirstName() + " " + c1.getLastName(), CardType.DEBIT, CardColor.GOLD, "3325-6745-7876-4445", 990, date, date.plusYears(-1), c1);
			cardRepository.save(card2);

			Card card8 = new Card(c1.getFirstName() + " " + c1.getLastName(), CardType.DEBIT, CardColor.GOLD, "3325-6745-7876-4445", 990, date, date.plusYears(5), c1);
			cardRepository.save(card8);

			Card card3 = new Card(c1.getFirstName() + " " + c1.getLastName(), CardType.CREDIT, CardColor.SILVER, "5204-6005-5752-1828", 533, date, date.plusYears(5), c1);
			cardRepository.save(card3);

			Card card4 = new Card(c1.getFirstName() + " " + c1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "2234-6745-552-7888", 750, date, date.plusYears(-1) , c1);
			cardRepository.save(card4);

			Card card5 = new Card(c1.getFirstName() + " " + c1.getLastName(), CardType.DEBIT, CardColor.SILVER, "2204-6105-5252-1828", 423, date, date.plusYears(5), c2);
			cardRepository.save(card5);

			Card card6 = new Card(c1.getFirstName() + " " + c1.getLastName(), CardType.CREDIT, CardColor.TITANIUM, "7234-6545-552-7484", 560, date, date.plusYears(-1) , c2);
			cardRepository.save(card6);



		};
	}
}
