package br.com.victorcaio.vacancy_management.modules.candidate.controllers;

import java.util.List;
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
import br.com.victorcaio.vacancy_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.victorcaio.vacancy_management.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.victorcaio.vacancy_management.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/candidate")
public class CandidateController {
  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

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

  @GetMapping("/jobs")
  @PreAuthorize("hasRole('CANDIDATE')")
  @Tag(name = "Candidate", description = "Informações do candidato")
  @Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Rota responsável por listar todas as vagas disponíveis para o candidato a parir de um filtro")
  @ApiResponses(@ApiResponse(responseCode = "200", content = {
      @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
  }))
  @SecurityRequirement(name = "jwt_auth")
  public List<JobEntity> findJobByFilter(@RequestParam String param) {
    return this.listAllJobsByFilterUseCase.execute(param);
  }
}