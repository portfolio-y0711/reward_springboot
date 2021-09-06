package com.portfolioy0711.api.data;

import com.portfolioy0711.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> { }
