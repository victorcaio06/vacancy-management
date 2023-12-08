package br.com.victorcaio.vacancy_management.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victorcaio.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.victorcaio.vacancy_management.modules.company.useCases.CreateCompanyUseCase;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class CompanyController {
  @Autowired
  private CreateCompanyUseCase companyUseCase;

  @PostMapping()
  public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
    try {
      var createCompany = this.companyUseCase.execute(companyEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(createCompany);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}
