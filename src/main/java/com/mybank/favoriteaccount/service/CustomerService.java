package com.mybank.favoriteaccount.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;

@Service
public interface CustomerService {

	public Optional<ResponseDto> loginUser(Integer customerId, String pwd) throws InvalidCustomerException;
}
