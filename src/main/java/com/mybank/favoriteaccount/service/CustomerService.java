package com.mybank.favoriteaccount.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;

/**
 * 
 * Service interface will have the customer favorite accounts related
 * operations. The preferred implementation is {@code CustomerServiceImpl}.
 * 
 * @author Kiruthika & prem
 * @since 2020/11/30
 *
 */
@Service
public interface CustomerService {

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
	public FavoriteAccountsResponse favoriteAccounts(Integer customerId, Integer pageNumber);

	public Optional<ResponseDto> loginUser(int customerId) throws InvalidCustomerException;
}
