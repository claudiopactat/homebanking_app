package com.mindhub.homebancking.repositories;

import com.mindhub.homebancking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {

    Loan findByName (String name);

}
