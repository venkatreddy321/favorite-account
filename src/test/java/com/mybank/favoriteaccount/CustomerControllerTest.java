package com.mybank.favoriteaccount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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

import com.mybank.favoriteaccount.controller.CustomerController;
import com.mybank.favoriteaccount.dto.FavoriteAccountDto;
import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.dto.LoginRequest;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.CustomerNotFoundException;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.service.CustomerService;
import com.mybank.favoriteaccount.util.FavoriteAccountConstants;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerControllerTest {

	@InjectMocks
	CustomerController customerController;

	@Mock
	CustomerService customerService;

	FavoriteAccountsResponse favoriteAccountsResponse;
	FavoriteAccountDto favoriteAccount;
	List<FavoriteAccountDto> favoriteAccounts;
	ResponseDto responseDto;

	LoginRequest loginRequest;

	@BeforeAll
	public void setUp() {
		responseDto = new ResponseDto();
		responseDto.setMessage(FavoriteAccountConstants.CUSTOMER_LOGIN_SUCCESS);
		responseDto.setStatus(HttpStatus.OK.value());
		loginRequest = new LoginRequest();
		loginRequest.setCustomerId(100);
		loginRequest.setPassword("siva");

		favoriteAccount = new FavoriteAccountDto();
		favoriteAccount.setAccName("Savings");
		favoriteAccount.setAccNumber("ES21 1234 0000 00 00000");
		favoriteAccount.setBankName("My bank");

		favoriteAccounts = new ArrayList<>();
		favoriteAccounts.add(favoriteAccount);

		favoriteAccountsResponse = new FavoriteAccountsResponse();
		favoriteAccountsResponse.setFavoriteAccount(favoriteAccounts);
		favoriteAccountsResponse.setMessage(FavoriteAccountConstants.FAVORITE_ACCOUNT_RESPONSE);
		favoriteAccountsResponse.setStatusCode(HttpStatus.OK.value());
	}

	@Test
	public void favoriteAccountsTest() {

		// GIVEN
		when(customerService.favoriteAccounts(1, 1)).thenReturn(favoriteAccountsResponse);

		// WHEN
		ResponseEntity<FavoriteAccountsResponse> actual = customerController.favoriteAccounts(1, 1);

		// THEN
		assertEquals(favoriteAccountsResponse.getMessage(), actual.getBody().getMessage());
	}

	@Test
	void customerLoginTest() throws CustomerNotFoundException, InvalidCustomerException {
		// GIVEN
		when(customerService.loginUser(100,"siva")).thenReturn(Optional.of(responseDto));
		// WHEN
		ResponseEntity<Optional<ResponseDto>> actual = customerController.userLogin(loginRequest);

		// THEN
		assertEquals(responseDto.getMessage(), actual.getBody().get().getMessage());

	}

}
