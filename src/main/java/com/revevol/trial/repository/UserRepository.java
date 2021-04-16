package com.revevol.trial.repository;

import com.revevol.trial.model.User;
import com.revevol.trial.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, UserId> {

    List<User> findByFileRef(String lastName);


}