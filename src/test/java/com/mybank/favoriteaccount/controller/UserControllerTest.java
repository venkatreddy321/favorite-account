package com.mybank.favoriteaccount.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mybank.favoriteaccount.dto.LoginRequest;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.CustomerNotFoundException;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.service.CustomerService;
import com.mybank.favoriteaccount.util.CustomerConstants;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
 class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;
	
	@Mock
	CustomerService customerService;
	
	ResponseDto responseDto;
	
	LoginRequest loginRequest;
	
	@BeforeAll
	public void setUp() {
		responseDto=new ResponseDto();
		responseDto.setMessage(CustomerConstants.Customer_LOGIN_SUCCESS);
		responseDto.setStatus(HttpStatus.OK.value());
		loginRequest=new LoginRequest();
		loginRequest.setCustomerId(100);
	}
	
	@Test
	void CustomerLoginTest() throws CustomerNotFoundException, InvalidCustomerException {
		//GIVEN
		
		when(customerService.loginUser(100)).thenReturn(Optional.of(responseDto));
		
		ResponseEntity<Optional<ResponseDto>> actual=customerController.userLogin(loginRequest);
		
		//THEN
		assertEquals(responseDto.getMessage(),actual.getBody().get().getMessage());
		
		//WHEN

	}
}
