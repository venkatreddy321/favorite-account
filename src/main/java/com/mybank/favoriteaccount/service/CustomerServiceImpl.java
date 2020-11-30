package com.mybank.favoriteaccount.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.FavoriteAccountDto;
import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.entity.Customer;
import com.mybank.favoriteaccount.entity.FavoriteAccount;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.repository.CustomerRepository;
import com.mybank.favoriteaccount.repository.FavoriteAccountRepository;
import com.mybank.favoriteaccount.util.FavoriteAccountConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of CustomerService will have the customer favorite accounts
 * related operations.
 * 
 * @author Kiruthika && prem
 * @since 2020/11/30
 */
@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	FavoriteAccountRepository favoriteAccountRepository;
	
	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Method to call service method to get the favorite accounts for the given
	 * customer id.
	 * 
	 * @param customerId id of the customer who logged in .
	 * @param pageNumber page number for navigate the pages .
	 * @return FavoriteAccountsResponse which consist the message ,status code with
	 *         list of favorite accounts.
	 * 
	 */
	public FavoriteAccountsResponse favoriteAccounts(Integer customerId, Integer pageNumber) {
		
		Integer pageSize = 5;
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("accName"));

		Optional<List<FavoriteAccount>> favoriteAccounts = favoriteAccountRepository.findByCustomerId(customerId,
				pageRequest);

		FavoriteAccountsResponse accountsResponse = new FavoriteAccountsResponse();
		List<FavoriteAccountDto> favoriteAccountDtos = new ArrayList<>();

		if (favoriteAccounts.isPresent()) {

			favoriteAccountDtos = favoriteAccounts.get().stream().map(favAccount -> {
				FavoriteAccountDto favoriteAccountDto = new FavoriteAccountDto();
				BeanUtils.copyProperties(favAccount, favoriteAccountDto);
				return favoriteAccountDto;
			}).collect(Collectors.toList());

			accountsResponse.setMessage(FavoriteAccountConstants.FAVORITE_ACCOUNT_RESPONSE);

		}

		accountsResponse.setFavoriteAccount(favoriteAccountDtos);
		accountsResponse.setMessage(FavoriteAccountConstants.FAVORITE_ACCOUNT_EMPTY_RESPONSE);
		accountsResponse.setStatusCode(HttpStatus.OK.value());
		return accountsResponse;

	}


	@Override
	public Optional<ResponseDto> loginUser(int customerId) throws InvalidCustomerException {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (!customer.isPresent()) {
			throw new InvalidCustomerException(FavoriteAccountConstants.CUSTOMER_DOES_NOT_EXISTS);
		} 
		

		ResponseDto responseDto= new ResponseDto();
		responseDto.setMessage(FavoriteAccountConstants.CUSTOMER_LOGIN_SUCCESS);
		responseDto.setStatus(HttpStatus.OK.value());
		return Optional.of(responseDto);
	}
}
