package com.mybank.favoriteaccount.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.favoriteaccount.dto.LoginRequest;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.service.CustomerService;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/login")
	public ResponseEntity<Optional<ResponseDto>> userLogin(@RequestBody LoginRequest customer)
			throws InvalidCustomerException {

		return new ResponseEntity<>(customerService.loginUser(customer.getCustomerId(), customer.getPassword()),
				HttpStatus.OK);

	}
}
