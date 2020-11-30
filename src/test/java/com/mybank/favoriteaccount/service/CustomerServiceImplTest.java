package com.mybank.favoriteaccount.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;

import com.mybank.favoriteaccount.dto.FavoriteAccountDto;
import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.entity.Customer;
import com.mybank.favoriteaccount.entity.FavoriteAccount;
import com.mybank.favoriteaccount.exception.InvalidAccountNumberException;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.repository.CustomerRepository;
import com.mybank.favoriteaccount.repository.FavoriteAccountRepository;
import com.mybank.favoriteaccount.util.FavoriteAccountConstants;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CustomerServiceImplTest {

	@InjectMocks
	CustomerServiceImpl customerServiceImpl;

	@Mock
	CustomerRepository customerRepository;

	@Mock
	FavoriteAccountRepository favoriteAccountRepository;

	FavoriteAccountsResponse favoriteAccountsResponse;
	FavoriteAccountDto favoriteAccount;
	List<FavoriteAccountDto> favoriteAccounts;
	ResponseDto responseDto;
	List<FavoriteAccount> accounts;
	FavoriteAccount account1;
	FavoriteAccount account2;

	Customer customer;
	private Integer customerId = 1;
	private String accNumber = "ES21123500000000000";
	ResponseDto responseDto1;
	FavoriteAccount favoriteAccount1;

	@BeforeAll
	private void setup() {
		
		responseDto1 = new ResponseDto();
		responseDto1.setMessage("Account number deleted successfully");
		responseDto1.setStatus(200);
		
		favoriteAccount1 = new FavoriteAccount();
		favoriteAccount1.setAccName("vodafonespain");
		favoriteAccount1.setAccNumber("ES21123500000000000");
		favoriteAccount1.setBankName("bbva");
		favoriteAccount1.setCustomerId(1);
		favoriteAccount1.setFavAccountId(3);
		favoriteAccount1.setUpdatedDate(LocalDateTime.now());
		
		account1 = new FavoriteAccount();
		account1.setCustomerId(1);
		account1.setBankName("My bank");
		account2 = new FavoriteAccount();
		account2.setCustomerId(2);
		accounts = new ArrayList<>();
		accounts.add(account1);
		accounts.add(account2);

		responseDto = new ResponseDto();
		responseDto.setMessage(FavoriteAccountConstants.CUSTOMER_LOGIN_SUCCESS);
		responseDto.setStatus(HttpStatus.OK.value());
		customer = new Customer();
		customer.setCustomerId(1000);
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
	public void favoriteAccountRepositoryTest() {

		int pageNumber = 1;
		int pageSize = 5;
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("accName"));

		when(favoriteAccountRepository.findByCustomerId(1, pageRequest)).thenReturn(Optional.of(accounts));

		FavoriteAccountsResponse actual = customerServiceImpl.favoriteAccounts(1, 1);

		assertEquals(favoriteAccountsResponse.getFavoriteAccount().get(0).getBankName(),
				actual.getFavoriteAccount().get(0).getBankName());

	}

	@Test
	void loginUserTest() throws InvalidCustomerException {
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));

		Optional<ResponseDto> actual = customerServiceImpl.loginUser(1000);

		assertEquals(responseDto.getMessage(), actual.get().getMessage());

	}
	@Test
	public void deleteAccountTest() throws InvalidAccountNumberException {
		
		//Given
		when(favoriteAccountRepository.findByCustomerIdAndAccNumber(customerId, accNumber))
		.thenReturn(Optional.of(favoriteAccount1));
		
		//when
		ResponseDto actual = customerServiceImpl.deleteAccount(customerId, accNumber);
		
		//then
		assertEquals("Account number deleted successfully",actual.getMessage());
	
	}

}