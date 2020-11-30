package com.mybank.favoriteaccount.dto;

import java.util.List;

public class FavoriteAccountsResponse {

	private List<FavoriteAccountDto> favoriteAccount;
	private String message;
	private Integer statusCode;
	public List<FavoriteAccountDto> getFavoriteAccount() {
		return favoriteAccount;
	}
	public void setFavoriteAccount(List<FavoriteAccountDto> favoriteAccount) {
		this.favoriteAccount = favoriteAccount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	
}
