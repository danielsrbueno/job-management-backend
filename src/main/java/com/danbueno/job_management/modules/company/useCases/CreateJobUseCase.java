package com.danbueno.job_management.modules.company.useCases;

import com.danbueno.job_management.modules.company.entities.JobEntity;
import com.danbueno.job_management.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

  @Autowired
  private JobRepository jobRepository;

  public JobEntity execute (JobEntity jobEntity) {
    return jobRepository.save(jobEntity);
  }
}
