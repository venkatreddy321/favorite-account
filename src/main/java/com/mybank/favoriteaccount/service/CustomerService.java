package com.mybank.favoriteaccount.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.FavoriteAccountRequest;
import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.exception.FavoriteIdNotFoundException;
import com.mybank.favoriteaccount.exception.IncorrectBankCodeException;
import com.mybank.favoriteaccount.exception.InvalidAccountNumberException;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.util.FavoriteAccountConstants;

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

	public ResponseDto updateFavoriteAccount(Integer customerId, FavoriteAccountRequest favoriteAccountRequest)
			throws IncorrectBankCodeException, FavoriteIdNotFoundException, InvalidAccountNumberException;

	
	default void validateAccountNumber(String accountNumber) throws InvalidAccountNumberException {

		accountNumber = accountNumber.replaceAll("\\s+", "");

		Pattern pattern = Pattern.compile("^[a-zA-Z]{1,2}[0-9]{3,17}$");
		if (!pattern.matcher(accountNumber).matches()) {
			throw new InvalidAccountNumberException(FavoriteAccountConstants.INVALID_ACCOUNT_NUMBER);
		}
	}
}
