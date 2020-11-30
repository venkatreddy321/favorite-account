package com.mybank.favoriteaccount.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.service.FavoriteAccountService;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class FavoriteAccountControllerTest {

	@Mock
	FavoriteAccountService favoriteAccountService;
	@InjectMocks
	FavoriteAccountController favoriteAccountController;
	
	private Integer customerId = 1;
	private String accNumber = "ES21123500000000000";
	ResponseDto responseDto;
	@BeforeAll
	public void setUp() {
		responseDto = new ResponseDto();
		
		responseDto.setMessage("successfully deleted");
		responseDto.setStatus(200);
	}
	@Test
	public void deleteFavAccountTest() {
	
		 //Given
		when(favoriteAccountService.deleteAccount(customerId, accNumber)).thenReturn(responseDto);
		//when
		ResponseEntity<ResponseDto> actual = favoriteAccountController.deleteFavAccount(customerId, accNumber);
	   //then
		assertEquals(responseDto.getMessage(),actual.getBody().getMessage());
	}
}
