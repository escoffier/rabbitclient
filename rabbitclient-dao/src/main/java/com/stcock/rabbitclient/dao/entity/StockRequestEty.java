package com.stcock.rabbitclient.dao.entity;
public class StockRequestEty {
	private String ticker;
	private Long quantity;
	private Double price;
	private String orderType;
	private String accountName;
	private Integer buyRequest;
	private String userName;
	private String requestId;
	private Long id;
	public String getTicker() {
		return this.ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public Long getQuantity() {
		return this.quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return this.price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}

	public String getOrderType() {
		return this.orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getAccountName() {
		return this.accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public Integer getBuyRequest() {
		return this.buyRequest;
	}
	public void setBuyRequest(Integer buyRequest) {
		this.buyRequest = buyRequest;
	}

	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRequestId() {
		return this.requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}

}