package com.mybank.favoriteaccount.service;

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
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.entity.Customer;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.repository.CustomerRepository;
import com.mybank.favoriteaccount.util.CustomerConstants;
@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class UserServiceImplTest {
	
	@InjectMocks
	CustomerServiceImpl customerServiceImpl;
	
	@Mock
	CustomerRepository customerRepository;
	
	ResponseDto responseDto;
	
	Customer customer;
	
	@BeforeAll
	 private void setup() {
		responseDto=new ResponseDto();
		responseDto.setMessage(CustomerConstants.Customer_LOGIN_SUCCESS);
		responseDto.setStatus(HttpStatus.OK.value());
		customer =new Customer();
		customer.setCustomerId(1000);
	}

	@Test
	void loginUserTest(String userId, String pwd) throws InvalidCustomerException
	{
		when(customerRepository.findById(1000)).thenReturn(Optional.of(customer));
		
		Optional<ResponseDto> responsedto=customerServiceImpl.loginUser(1000) ;
		
		assertEquals(1000 ,customer.getCustomerId());
		
	
	}	
	

	
	
}
