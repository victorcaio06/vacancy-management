package br.com.victorcaio.vacancy_management.modules.candidate;

import java.time.LocalDateTime;
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
@Entity(name = "candidates")
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  private String name;

  @Pattern(regexp = "^\\S+", message = "O campo username não deve conter espaço")
  private String username;

  @Email(message = "O campo deve conter um email válido")
  private String email;

  @Length(min = 8, max = 25)
  private String password;
  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;

}
