package com.mybank.favoriteaccount.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.entity.FavoriteAccount;
import com.mybank.favoriteaccount.repository.FavoriteAccountRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class FavoriteAccountServiceImplTest {

	@Mock
	FavoriteAccountRepository favoriteAccountRepository;
	@InjectMocks
	FavoriteAccountServiceImpl favoriteAccountService;
	
	private Integer customerId = 1;
	private String accNumber = "ES21123500000000000";
	ResponseDto responseDto;
	FavoriteAccount favoriteAccount;
	@BeforeAll
	public void setUp() {
		responseDto = new ResponseDto();
		responseDto.setMessage("Account number deleted successfully");
		responseDto.setStatus(200);
		
		favoriteAccount = new FavoriteAccount();
		favoriteAccount.setAccName("vodafonespain");
		favoriteAccount.setAccNumber("ES21123500000000000");
		favoriteAccount.setBankName("bbva");
		favoriteAccount.setCustomerId(1);
		favoriteAccount.setFavAccountId(3);
		favoriteAccount.setUpdatedDate(LocalDateTime.now());
	}
	@Test
	public void deleteAccountTest() {
		
		//Given
		when(favoriteAccountRepository.findByCustomerIdAndAccNumber(customerId, accNumber))
		.thenReturn(Optional.of(favoriteAccount));
		
		//when
		ResponseDto actual = favoriteAccountService.deleteAccount(customerId, accNumber);
		
		//then
		assertEquals("Account number deleted successfully",actual.getMessage());
	
	}
}
