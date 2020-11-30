package com.mybank.favoriteaccount;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

	@BeforeAll
	public void setUp() {
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

}
