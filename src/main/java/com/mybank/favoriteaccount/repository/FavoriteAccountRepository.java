package com.mybank.favoriteaccount.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mybank.favoriteaccount.entity.FavoriteAccount;

@Repository
public interface FavoriteAccountRepository extends JpaRepository<FavoriteAccount, Integer>{

	Optional<List<FavoriteAccount>> findByCustomerId(Integer customerId, Pageable pageRequest);

	Optional<FavoriteAccount> findByCustomerIdAndAccNumber(Integer customerId, String accNumber);

}
