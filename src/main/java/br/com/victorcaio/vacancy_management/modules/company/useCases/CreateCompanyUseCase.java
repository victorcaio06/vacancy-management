package br.com.victorcaio.vacancy_management.modules.company.useCases;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.victorcaio.vacancy_management.exceptions.CompanyFoundException;
import br.com.victorcaio.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.victorcaio.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {
  @Autowired
  private CompanyRepository companyRepository;

  public CompanyEntity execute(CompanyEntity companyEntity) {
    this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
        .ifPresent((company) -> {
          throw new CompanyFoundException();
        });

    return this.companyRepository.save(companyEntity);
  }
}
