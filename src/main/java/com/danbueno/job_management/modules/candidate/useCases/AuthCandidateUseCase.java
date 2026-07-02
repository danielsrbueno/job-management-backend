package com.danbueno.job_management.modules.candidate.useCases;

import javax.naming.AuthenticationException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.danbueno.job_management.modules.candidate.CandidateEntity;
import com.danbueno.job_management.modules.candidate.CandidateRepository;
import com.danbueno.job_management.modules.candidate.dto.AuthCandidateRequestDTO;
import com.danbueno.job_management.modules.candidate.dto.AuthCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute (AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    CandidateEntity candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("Username/password incorrect");
        });

    boolean passwordMatches = passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches)
      throw new AuthenticationException();

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    Instant expiresIn = Instant.now().plus(Duration.ofMinutes(10));

    String token = JWT.create()
        .withIssuer("jobManagement")
        .withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("CANDIDATE"))
        .withSubject(candidate.getId().toString())
        .sign(algorithm);

    AuthCandidateResponseDTO candidateResponse = AuthCandidateResponseDTO.builder()
        .access_token(token)
        .expiresIn(expiresIn.toEpochMilli())
        .build();

    return candidateResponse;

  }
}
