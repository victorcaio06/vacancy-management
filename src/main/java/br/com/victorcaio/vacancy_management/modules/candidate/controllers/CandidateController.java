package br.com.victorcaio.vacancy_management.modules.candidate.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victorcaio.vacancy_management.modules.candidate.CandidateEntity;
import br.com.victorcaio.vacancy_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.victorcaio.vacancy_management.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @PostMapping("/")
  public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
    try {
      var createCandidate = this.createCandidateUseCase.execute(candidateEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(createCandidate);

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(e.getMessage());
    }

  }

  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<Object> profile(HttpServletRequest request) {
    var idCandidate = request.getAttribute("id_candidate");

    try {
      var candidateProfile = profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));

      return ResponseEntity.status(HttpStatus.OK).body(candidateProfile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

}