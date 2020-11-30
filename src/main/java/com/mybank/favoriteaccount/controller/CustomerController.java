package com.mybank.favoriteaccount.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.dto.LoginRequest;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.service.CustomerService;

/**
 * Controller for handling the requests and responses related with favorite
 * accounts.
 * 
 * @author squad 2
 * @since 2020/11/30
 *
 */
@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	/**
	 * Method to call service method to get the favorite accounts for the given customer id.
	 * 
	 * @param customerId id of the customer who logged in .
	 * @param pageNumber page number for navigate the pages .
	 * @return FavoriteAccountsResponse which consist the message ,status code with
	 *         list of favorite accounts.
	 * 
	 */
	@GetMapping("/customers/{customerId}/accounts")
	public ResponseEntity<FavoriteAccountsResponse> favoriteAccounts(@PathVariable Integer customerId,
			@RequestParam Integer pageNumber) {

		FavoriteAccountsResponse favoriteAccountsResponse = customerService.favoriteAccounts(customerId, pageNumber);

		return new ResponseEntity<>(favoriteAccountsResponse, HttpStatus.OK);

	}
	
	/**
	 * Method to call service method to get login details for the given customer id and password.
	 * 
	 * @param customerId id of the customer who is going to log in .
	 * @return response dto which consist the message ,status code 
	 * 
	 */
	
	@PostMapping("/login")
	public ResponseEntity<Optional<ResponseDto>> userLogin(@RequestBody LoginRequest customer) throws InvalidCustomerException {

		return new ResponseEntity<>(customerService.loginUser(customer.getCustomerId(),customer.getPassword()), HttpStatus.OK);

	}

}
