package br.com.victorcaio.vacancy_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "companys")
public class CompanyEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;
  private String cnpj;

  @Pattern(regexp = "^\\S+", message = "O campo username não deve conter espaço")
  private String username;

  @Email(message = "O campo deve conter um email válido")
  private String email;

  @Length(min = 8, max = 25)
  private String password;
  private String website;
  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
