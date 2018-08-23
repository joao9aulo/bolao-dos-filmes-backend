package com.example.movies.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movies.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
