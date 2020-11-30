package com.mybank.favoriteaccount.dto;

public class FavoriteAccountRequest {

	private Integer favAccountId;
	private String accNumber;
	private String accName;

	public Integer getFavAccountId() {
		return favAccountId;
	}

	public void setFavAccountId(Integer favAccountId) {
		this.favAccountId = favAccountId;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

}
