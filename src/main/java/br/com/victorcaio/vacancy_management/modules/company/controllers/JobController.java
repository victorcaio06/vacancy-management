package br.com.victorcaio.vacancy_management.modules.company.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.victorcaio.vacancy_management.modules.company.dto.CreateJobDTO;
import br.com.victorcaio.vacancy_management.modules.company.entities.JobEntity;
import br.com.victorcaio.vacancy_management.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company/job")
public class JobController {
  @Autowired
  private CreateJobUseCase createJobUseCase;

  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO createJobDTO,
      HttpServletRequest httpServletRequest) {
    var companyId = httpServletRequest.getAttribute("id_company");

    var jobEntity = JobEntity.builder().description(createJobDTO.getDescription())
        .level(createJobDTO.getLevel()).benefits(createJobDTO.getBenefits())
        .id_company(UUID.fromString(companyId.toString()))
        .build();

    try {
      var createJob = this.createJobUseCase.execute(jobEntity);

      return ResponseEntity.status(HttpStatus.CREATED).body(createJob);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}
