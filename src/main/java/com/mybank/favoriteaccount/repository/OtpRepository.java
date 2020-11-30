package com.mybank.favoriteaccount.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mybank.favoriteaccount.entity.OtpDetails;

@Repository
public interface OtpRepository extends JpaRepository<OtpDetails, Integer> {

	@Query("select o from OtpDetails o where o.otp=:otp and o.customerId=:customerId")
	Optional<OtpDetails> findByOtp(@Param("otp") Integer otp, @Param("customerId") int customerId);

}
