package br.com.victorcaio.vacancy_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.victorcaio.vacancy_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.victorcaio.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.victorcaio.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class AuthCandidateUseCase {
  @Value("${security.token.secret.candidate}")
  private String secretKey;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
      throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username()).orElseThrow(() -> {
      throw new UsernameNotFoundException("Username ou senha incorreto");
    });

    var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException("Username ou senha incorreto");
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expires_in = Instant.now().plus(Duration.ofMinutes(10));

    var token = JWT.create()
        .withIssuer("javagas")
        .withSubject(candidate.getId().toString())
        .withClaim("roles", Arrays.asList("CANDIDATE"))
        .withExpiresAt(expires_in)
        .sign(algorithm);

    var authCandidateResponseDTO = AuthCandidateResponseDTO.builder()
        .access_token(token)
        .expires_in(expires_in.toEpochMilli())
        .build();

    return authCandidateResponseDTO;
  }
}
