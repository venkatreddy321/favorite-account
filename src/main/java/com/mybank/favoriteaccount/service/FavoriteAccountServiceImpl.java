package com.mybank.favoriteaccount.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.entity.FavoriteAccount;
import com.mybank.favoriteaccount.exception.AccountNotFoundException;
import com.mybank.favoriteaccount.repository.FavoriteAccountRepository;
import com.mybank.favoriteaccount.util.AccountConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation of FavoriteAccountService will delete favorite accounts
 * related operations.
 * 
 * @author Venkat Reddy Singireddy
 * @since 2020/11/30
 */
@Service
@Slf4j
public class FavoriteAccountServiceImpl implements FavoriteAccountService {
	@Autowired
	FavoriteAccountRepository favoriteAccountRepository;
	private static final Logger logger = LoggerFactory.getLogger(FavoriteAccountServiceImpl.class);

	/**
	 * Method to call service method to delete the favorite accounts for the given
	 * customer id.
	 * 
	 * @param customerId id of the customer who logged in .
	 * @param account    number of the customer who is having favorite account .
	 * @return ResponseDto which consist the message ,status code is the return type
	 *         of this method
	 * 
	 * 
	 */

	@Override
	public ResponseDto deleteAccount(Integer customerId, String accNumber) {
		logger.info("deleteAccount in service started");

		Optional<FavoriteAccount> account = favoriteAccountRepository.findByCustomerIdAndAccNumber(customerId,
				accNumber);

		if (!account.isPresent()) {
			throw new AccountNotFoundException(AccountConstants.ACCOUNT_NOT_FOUND);
		}
		favoriteAccountRepository.deleteById(account.get().getFavAccountId());

		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(AccountConstants.ACCOUNT_DELETED);
		responseDto.setStatus(AccountConstants.STATUS);
		return responseDto;
	}
}
