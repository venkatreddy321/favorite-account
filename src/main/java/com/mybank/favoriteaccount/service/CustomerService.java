package com.mybank.favoriteaccount.service;

import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.FavoriteAccountsResponse;

@Service
public interface CustomerService {

	public FavoriteAccountsResponse favoriteAccounts(Integer customerId, Integer pageNumber);
}
