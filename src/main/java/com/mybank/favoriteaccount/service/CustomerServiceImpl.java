package com.mybank.favoriteaccount.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.entity.Customer;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	@Autowired
	private CustomerRepository customerRepository;

	ResponseDto responseDto;

	@Override
	public Optional<ResponseDto> loginUser(Integer customerId, String pwd) throws InvalidCustomerException {
		Optional<Customer> customer = customerRepository.findByCustomerId(customerId);

		if (!customer.isPresent()) {
			throw new InvalidCustomerException("Customer does't exist");
		}

		if (!customer.get().getPassword().equals(pwd))
		{
			throw new InvalidCustomerException("Invalid credentials");
		}

		responseDto = new ResponseDto();
		responseDto.setMessage("Login Success");
		responseDto.setStatus(HttpStatus.OK.value());
		return Optional.of(responseDto);
	}

}
