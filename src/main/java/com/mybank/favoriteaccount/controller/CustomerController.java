package com.mybank.favoriteaccount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.service.CustomerService;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("/customers/{customerId}/accounts")
	public ResponseEntity<FavoriteAccountsResponse> favoriteAccounts(Integer customerId, Integer pageNumber) {
		
		FavoriteAccountsResponse favoriteAccountsResponse=customerService.favoriteAccounts(customerId, pageNumber);
		
		return new ResponseEntity<>(favoriteAccountsResponse,HttpStatus.OK);
		
	}

}

