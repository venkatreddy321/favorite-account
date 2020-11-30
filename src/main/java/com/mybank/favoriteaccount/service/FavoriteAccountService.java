package com.mybank.favoriteaccount.service;

import org.springframework.stereotype.Service;

import com.mybank.favoriteaccount.dto.ResponseDto;



@Service
public interface FavoriteAccountService {

	public ResponseDto deleteAccount(Integer customerId, String accNumber);

	
}
