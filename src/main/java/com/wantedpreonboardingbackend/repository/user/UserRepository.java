package com.wantedpreonboardingbackend.repository.user;

import com.wantedpreonboardingbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
