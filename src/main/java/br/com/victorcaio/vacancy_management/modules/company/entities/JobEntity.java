package br.com.victorcaio.vacancy_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity(name = "jobs")
public class JobEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String description;

  @NotBlank(message = "Campo obrigatório")
  private String level;
  private String benefits;

  @ManyToOne()
  @JoinColumn(name = "id_company", insertable = false, updatable = false)
  private CompanyEntity companyEntity;

  @NotNull
  private UUID id_company;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
