package com.mybank.favoriteaccount.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.InvalidAccountNumberException;
import com.mybank.favoriteaccount.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
/**
 * Controller for handling the requests and responses related with favorite
 * accounts.
 * 
 * @author Venkat Reddy Singireddy
 * @since 2020/11/30
 *
 */
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class FavoriteAccountController {
	
	@Autowired
	CustomerService customerService;
	
	private static final Logger logger = LoggerFactory.getLogger(FavoriteAccountController.class);
	/**
	 * Method to call service method to delete the favorite accounts for the given customer id.
	 * 
	 * @param customerId id of the customer who logged in .
	 * @param accountNumber of the customer which he added  as favorite account.
	 * @return ResponseDto which consist the message ,status code
	 * @throws InvalidAccountNumberException 
	 * 
	 */
	@DeleteMapping("/customers/{customerId}/accounts/{accNumber}")
	public ResponseEntity<ResponseDto> deleteFavAccount(@PathVariable Integer customerId,
			@PathVariable String accNumber) throws InvalidAccountNumberException{
		logger.info("deleteFavAccount inside FavoriteAccountController started");
		return new ResponseEntity<>(customerService.deleteAccount(customerId,accNumber),HttpStatus.OK);

	}

}
