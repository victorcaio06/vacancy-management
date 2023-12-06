package br.com.victorcaio.vacancy_management.modules.candidate.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.victorcaio.vacancy_management.modules.candidate.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
}
