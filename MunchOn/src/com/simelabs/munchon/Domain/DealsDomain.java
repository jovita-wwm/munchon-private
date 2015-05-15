package com.simelabs.munchon.Domain;

public class DealsDomain {

	String image, restaurantName, couponCode, dealValidityDate;
	int dealID, restaurantID, userID;
	Double minimumPurchase, discount;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	
	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	
	public String getDealValidityDate() {
		return dealValidityDate;
	}

	public void setDealValidityDate(String dealValidityDate) {
		this.dealValidityDate = dealValidityDate;
	}
	
	public void setDealID(int dealID) {
		this.dealID = dealID;
	}
	
	public int getDealID() {
		return dealID;
	}

	
	public void setRestaurantID(int restaurantID) {
		this.restaurantID = restaurantID;
	}
	
	public int getRestaurantID() {
		return restaurantID;
	}

	public void setUserID(int userID) {
		this. userID =  userID;
	}
	
	public int getUserID() {
		return  userID;
	}
	
	public void setMinimumPurchase(Double minimumPurchase) {
		this. minimumPurchase =  minimumPurchase;
	}
	
	public Double getMinimumPurchase() {
		return  minimumPurchase;
	}
	
	public void setDiscount(Double discount) {
		this. discount =  discount;
	}
	
	public Double getDiscount() {
		return  discount;
	}
}
