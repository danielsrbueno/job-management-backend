package com.danbueno.job_management.modules.candidate.useCases;

import com.danbueno.job_management.exceptions.UserFoundException;
import com.danbueno.job_management.modules.candidate.CandidateEntity;
import com.danbueno.job_management.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

  @Autowired // spring vai ser responsável por instânciar
  private CandidateRepository candidateRepository;

  public CandidateEntity execute (CandidateEntity candidateEntity) {
    this.candidateRepository
        .findByUsernameOrEmail(
            candidateEntity.getUsername(),
            candidateEntity.getEmail()
        )
        .ifPresent((user) -> {
          throw new UserFoundException();
        });

    return this.candidateRepository.save(candidateEntity);
  }

}
