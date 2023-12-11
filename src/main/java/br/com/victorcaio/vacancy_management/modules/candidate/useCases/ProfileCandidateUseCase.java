package br.com.victorcaio.vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.victorcaio.vacancy_management.exceptions.CandidateNotFoundException;
import br.com.victorcaio.vacancy_management.modules.candidate.CandidateEntity;
import br.com.victorcaio.vacancy_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.victorcaio.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate).orElseThrow(() -> {
      throw new CandidateNotFoundException();
    });

    var candidateResponseDTO = ProfileCandidateResponseDTO.builder()
        .description(candidate.getDescription())
        .email(candidate.getEmail())
        .id(idCandidate)
        .name(candidate.getName()).username(candidate.getUsername())
        .build();

    return candidateResponseDTO;

  }
}
