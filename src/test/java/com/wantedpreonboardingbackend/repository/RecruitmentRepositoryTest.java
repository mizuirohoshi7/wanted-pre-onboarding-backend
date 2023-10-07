package com.wantedpreonboardingbackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RecruitmentRepositoryTest {

    @Autowired
    RecruitmentRepository recruitmentRepository;

}