package com.danbueno.job_management.modules.candidate.useCases;

import com.danbueno.job_management.modules.candidate.CandidateRepository;
import com.danbueno.job_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("User not found");
        });

    ProfileCandidateResponseDTO profileCandidateResponseDTO = ProfileCandidateResponseDTO.builder()
        .name(candidate.getName())
        .email(candidate.getEmail())
        .description(candidate.getDescription())
        .username(candidate.getUsername())
        .id(candidate.getId())
        .build();

    return profileCandidateResponseDTO;
  }

}
