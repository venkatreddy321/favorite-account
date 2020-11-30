package com.mybank.favoriteaccount.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.codehaus.plexus.util.StringUtils;
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
import com.mybank.favoriteaccount.entity.OtpDetails;
import com.mybank.favoriteaccount.exception.InvalidCustomerException;
import com.mybank.favoriteaccount.repository.CustomerRepository;
import com.mybank.favoriteaccount.repository.FavoriteAccountRepository;
import com.mybank.favoriteaccount.repository.OtpRepository;
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
	OtpRepository otpRepository;
	
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

	/**
	 * Method to call service method to check the customer validation for the given
	 * customer id and password
	 * 
	 * @param customerId id of the customer who wants to logged in .
	 * @return responsedto which consist the message ,status code 
	 */
	@Override
	public Optional<ResponseDto> loginUser(int customerId,String password, Integer otp) throws InvalidCustomerException {
		Optional<Customer> customer = customerRepository.findById(customerId);

		if (!customer.isPresent()) {
			throw new InvalidCustomerException(FavoriteAccountConstants.CUSTOMER_DOES_NOT_EXISTS);
		}
		ResponseDto responseDto= new ResponseDto();
		if(StringUtils.isBlank(password) && otp==null){
			throw new InvalidCustomerException(FavoriteAccountConstants.MISSING_CREDENTIALS);
		}else if(StringUtils.isNotBlank(password)) {
			if (!customer.get().getPassword().equals(password)) {
				throw new InvalidCustomerException(FavoriteAccountConstants.INVALID_CREDENTIALS);
			}
			responseDto.setMessage(FavoriteAccountConstants.CUSTOMER_LOGIN_SUCCESS);
		}else {
			validteOtp(customerId, otp, customer, responseDto);
		}

		responseDto.setStatus(HttpStatus.OK.value());
		return Optional.of(responseDto);
	}
	private void validteOtp(int customerId, Integer otp, Optional<Customer> customer, ResponseDto responseDto) throws InvalidCustomerException {
		if (null != otp && otp != 0) {
			Optional<OtpDetails> otpDetail = otpRepository.findByOtp(otp, customerId);
			if (!otpDetail.isPresent()) {
				throw new InvalidCustomerException(FavoriteAccountConstants.INVALID_OTP);
			}
			if (customerId == otpDetail.get().getCustomerId()) {
				responseDto.setMessage(FavoriteAccountConstants.CUSTOMER_LOGIN_SUCCESS);
			}
		} else {
			generateOtp(customer.get().getCustomerId());
			responseDto.setMessage(FavoriteAccountConstants.OTP_SENT);
		}
	}

	private void generateOtp(Integer customerId) {
		String values = "0123456789";
		int otpLength = 5;
		Random rndm_method = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < otpLength; i++) {
			sb.append(values.charAt(rndm_method.nextInt(values.length())));
		}
		OtpDetails otpDetails = new OtpDetails();
		otpDetails.setCreatedOn(LocalDateTime.now());
		otpDetails.setOtp(Integer.parseInt(sb.toString()));
		otpDetails.setCustomerId(customerId);
		otpRepository.save(otpDetails);
		// sendOtp(sb.toString());//TO DO with SMS integration here
	}
}
