package br.com.victorcaio.vacancy_management.exceptions;

public class CandidateNotFoundException extends RuntimeException {
  public CandidateNotFoundException() {
    super("Candidato não existe");
  }
}
