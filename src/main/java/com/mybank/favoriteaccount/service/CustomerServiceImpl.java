package com.mybank.favoriteaccount.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.FavoriteAccountDto;
import com.mybank.favoriteaccount.dto.FavoriteAccountRequest;
import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;
import com.mybank.favoriteaccount.dto.ResponseDto;
import com.mybank.favoriteaccount.entity.Bank;
import com.mybank.favoriteaccount.entity.Customer;
import com.mybank.favoriteaccount.entity.FavoriteAccount;
import com.mybank.favoriteaccount.exception.AccountNotFoundException;
import com.mybank.favoriteaccount.exception.FavoriteIdNotFoundException;
import com.mybank.favoriteaccount.exception.IncorrectBankCodeException;
import com.mybank.favoriteaccount.exception.InvalidAccountNumberException;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.repository.BankRepository;
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

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	FavoriteAccountRepository favoriteAccountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	BankRepository bankRepository;

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

		} else {
			accountsResponse.setMessage(FavoriteAccountConstants.FAVORITE_ACCOUNT_EMPTY_RESPONSE);
		}
		accountsResponse.setFavoriteAccount(favoriteAccountDtos);
		accountsResponse.setStatusCode(HttpStatus.OK.value());
		return accountsResponse;

	}

	@Override
	public ResponseDto updateFavoriteAccount(Integer customerId, FavoriteAccountRequest favoriteAccountRequest)
			throws IncorrectBankCodeException, FavoriteIdNotFoundException, InvalidAccountNumberException {

		validateAccountNumber(favoriteAccountRequest.getAccNumber());
		String accNumber = favoriteAccountRequest.getAccNumber();
		String accNumberWithoutSpace = accNumber.replaceAll("\\s+", "");
		Integer bankCode = Integer.valueOf(accNumberWithoutSpace.substring(4, 8));

		Optional<Bank> bankDetails = bankRepository.findById(bankCode);
		if (!bankDetails.isPresent()) {
			throw new IncorrectBankCodeException(FavoriteAccountConstants.BANK_CODE_DOES_NOT_EXIST);
		}

		if (favoriteAccountRequest.getFavAccountId() > 0) {
			return editFavoriteAccount(favoriteAccountRequest, bankDetails);

		} else {
			return addFavoriteAccount(customerId, favoriteAccountRequest, bankDetails);
		}
	}

	public ResponseDto addFavoriteAccount(Integer customerId, FavoriteAccountRequest favoriteAccountRequest,
			Optional<Bank> bankDetails) {

		ResponseDto responseDto = new ResponseDto();
		FavoriteAccount favoriteAccount = new FavoriteAccount();
		favoriteAccount.setAccName(favoriteAccountRequest.getAccName());
		favoriteAccount.setAccNumber(favoriteAccountRequest.getAccNumber());
		favoriteAccount.setBankName(bankDetails.get().getBankName());
		favoriteAccount.setCustomerId(customerId);
		favoriteAccount.setUpdatedDate(LocalDateTime.now());

		favoriteAccountRepository.save(favoriteAccount);
		responseDto.setMessage(FavoriteAccountConstants.FAVORITE_ACCOUNT_ADDED);
		responseDto.setStatus(HttpStatus.OK.value());
		return responseDto;
	}

	public ResponseDto editFavoriteAccount(FavoriteAccountRequest favoriteAccountRequest, Optional<Bank> bankDetails)
			throws FavoriteIdNotFoundException {

		ResponseDto responseDto = new ResponseDto();
		Optional<FavoriteAccount> favAccount = favoriteAccountRepository
				.findById(favoriteAccountRequest.getFavAccountId());

		if (!favAccount.isPresent()) {
			throw new FavoriteIdNotFoundException(FavoriteAccountConstants.FAVORITE_ACCOUNT_DOES_NOT_EXIST);
		}
		favAccount.get().setAccName(favoriteAccountRequest.getAccName());
		favAccount.get().setAccNumber(favoriteAccountRequest.getAccNumber());
		favAccount.get().setBankName(bankDetails.get().getBankName());
		favAccount.get().setUpdatedDate(LocalDateTime.now());

		favoriteAccountRepository.save(favAccount.get());
		responseDto.setMessage(FavoriteAccountConstants.FAVORITE_ACCOUNT_UPDATED);
		responseDto.setStatus(HttpStatus.OK.value());
		return responseDto;
	}

	@Override
	public Optional<ResponseDto> loginUser(int customerId) throws InvalidCustomerException {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (!customer.isPresent()) {
			throw new InvalidCustomerException(FavoriteAccountConstants.CUSTOMER_DOES_NOT_EXISTS);
		}

		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(FavoriteAccountConstants.CUSTOMER_LOGIN_SUCCESS);
		responseDto.setStatus(HttpStatus.OK.value());
		return Optional.of(responseDto);
	}

	/**
	 * Method to call service method to delete the favorite accounts for the given
	 * customer id.
	 * 
	 * @param customerId id of the customer who logged in .
	 * @param account    number of the customer who is having favorite account .
	 * @return ResponseDto which consist the message ,status code is the return type
	 *         of this method
	 * @throws InvalidAccountNumberException
	 * 
	 * 
	 */

	@Override
	public ResponseDto deleteAccount(Integer customerId, String accNumber) throws InvalidAccountNumberException {
		logger.info("deleteAccount in service started");
		validateAccountNumber(accNumber);
		Optional<FavoriteAccount> account = favoriteAccountRepository.findByCustomerIdAndAccNumber(customerId,
				accNumber);

		if (!account.isPresent()) {
			throw new AccountNotFoundException(FavoriteAccountConstants.ACCOUNT_NOT_FOUND);
		}
		favoriteAccountRepository.deleteById(account.get().getFavAccountId());

		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage(FavoriteAccountConstants.ACCOUNT_DELETED);
		responseDto.setStatus(HttpStatus.OK.value());
		return responseDto;
	}
}
