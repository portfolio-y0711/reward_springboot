package com.portfolioy0711.api.data.models.user;

import com.portfolioy0711.api.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCmdRepository extends JpaRepository<User, String> {
    @Query(value = "select count(*) as count from user", nativeQuery = true)
    List<Object> getUserCount();
}
