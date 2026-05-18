package com.example.iotrfidbe.repository;

import com.example.iotrfidbe.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    // Query Method / Derived Query Methods
    // phải bắt đầu bằng findBy, countBy, existsBy để JPA nhận dạng
    Optional<Users> findByUsername(String userName);
    Optional<Users> findByEmail(String email);
}
