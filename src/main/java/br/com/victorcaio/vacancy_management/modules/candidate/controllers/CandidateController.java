package br.com.victorcaio.vacancy_management.modules.candidate.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victorcaio.vacancy_management.modules.candidate.CandidateEntity;
import br.com.victorcaio.vacancy_management.modules.candidate.repositories.CandidateRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
  @Autowired
  private CandidateRepository candidateRepository;

  @PostMapping()
  public CandidateEntity create(@Valid @RequestBody CandidateEntity candidateEntity) {
    System.out.println("TESTE");
    System.out.println(candidateEntity.getUsername());

    return this.candidateRepository.save(candidateEntity);

    // System.out.println("Candidate");
    // System.out.println(candidateEntity);
  }
}