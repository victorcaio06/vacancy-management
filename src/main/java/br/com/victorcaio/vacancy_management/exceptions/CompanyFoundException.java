package br.com.victorcaio.vacancy_management.exceptions;

public class CompanyFoundException extends RuntimeException {
  public CompanyFoundException() {
    super("Empresa já existe");
  }
}
