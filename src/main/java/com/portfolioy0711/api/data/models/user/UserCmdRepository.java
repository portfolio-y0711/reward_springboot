package com.portfolioy0711.api.data.models.user;

import com.portfolioy0711.api.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCmdRepository extends JpaRepository<User, String> { }
