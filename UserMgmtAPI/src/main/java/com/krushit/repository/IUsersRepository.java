package com.krushit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.krushit.entity.UserMaster;

public interface IUsersRepository extends JpaRepository<UserMaster, Integer> {

}
