package com.mybank.favoriteaccount.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank")
public class Bank {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer bankCode;
	private String bankName;
	public Integer getBankCode() {
		return bankCode;
	}
	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
}
