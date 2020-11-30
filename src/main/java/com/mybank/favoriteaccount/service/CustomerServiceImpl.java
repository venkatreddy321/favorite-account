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
import com.mybank.favoriteaccount.entity.FavoriteAccount;
import com.mybank.favoriteaccount.repository.FavoriteAccountRepository;
import com.mybank.favoriteaccount.util.FavoriteAccountConstants;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	FavoriteAccountRepository favoriteAccountRepository;

	public FavoriteAccountsResponse favoriteAccounts(Integer customerId, Integer pageNumber) {

		Integer pageSize = 5;
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by("accName"));

		Optional<List<FavoriteAccount>> favoriteAccounts = favoriteAccountRepository.findByCustomerId(customerId,
				pageRequest);

		FavoriteAccountsResponse accountsResponse = new FavoriteAccountsResponse();
		List<FavoriteAccountDto> favoriteAccountDtos=new ArrayList<>();
		
		if(favoriteAccounts.isPresent()) {
			
		FavoriteAccountDto favoriteAccountDto = new FavoriteAccountDto();
		
		favoriteAccountDtos = favoriteAccounts.get().stream().map(favAccount -> {
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
}
