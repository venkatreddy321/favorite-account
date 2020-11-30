package com.mybank.favoriteaccount.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mybank.favoriteaccount.entity.Bank;

@Repository
public interface BankRepository extends JpaRepository<Bank, Integer>{

}
