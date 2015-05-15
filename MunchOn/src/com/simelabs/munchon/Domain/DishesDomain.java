package com.simelabs.munchon.Domain;

import java.util.Date;

public class DishesDomain {

	String DishName, DishDescription, DishImage, CategoryName, Unit,ParameterName,ParameterTwoName,
	ParameterFourName,ParameterThreeName,ParameterFiveName;
	int DishID, CategoryId, RestaurantId, CalorieAmount, CookingTime, Status,
			DishPropertyChilly, DishPropertyVegan, DishPropertyglutenFree,
			DishPropertySeaFood, DishPropertyVeg, DishPropertyNutFree,
			DishParameterCount;
	Double Price, Discount;
	String Rating;
	Date AvailableFrom,AvailableTo;

	public String getDishName() {
		return DishName;
	}

	public void setDishName(String DishName) {
		this.DishName = DishName;
	}

	public String getDishDescription() {
		return DishDescription;
	}

	public void setDishDescription(String DishDescription) {
		this.DishDescription = DishDescription;
	}

	public String getDishImage() {
		return DishImage;
	}

	public void setDishImage(String DishImage) {
		this.DishImage = DishImage;
	}

	public String getCategoryName() {
		return CategoryName;
	}

	public void setCategoryName(String CategoryName) {
		this.CategoryName = CategoryName;
	}

	public String getParameterName() {
		return ParameterName;
	}
	
	public void setParameterName(String ParameterName) {
		this.ParameterName = ParameterName;
	}
	public String getParameterTwoName() {
		return ParameterTwoName;
	}
	
	public void setParameterTwoName(String ParameterTwoName) {
		this.ParameterTwoName = ParameterTwoName;
	}
	public String getParameterThreeName() {
		return ParameterThreeName;
	}
	
	public void setParameterThreeName(String ParameterThreeName) {
		this.ParameterThreeName = ParameterThreeName;
	}
	public String getParameterFourName() {
		return ParameterFourName;
	}
	
	public void setParameterFourName(String ParameterFourName) {
		this.ParameterFourName = ParameterFourName;
	}
	public String getParameterFiveName() {
		return ParameterFiveName;
	}
	
	public void setParameterFiveName(String ParameterFiveName) {
		this.ParameterFiveName = ParameterFiveName;
	}
	
	public String getUnit() {
		return Unit;
	}
	
	public void setUnit(String Unit) {
		this.Unit = Unit;
	}

	public int getDishID() {
		return DishID;
	}

	public void setDishID(int DishID) {
		this.DishID = DishID;
	}

	public int getCategoryId() {
		return CategoryId;
	}

	public void setCategoryId(int CategoryId) {
		this.CategoryId = CategoryId;
	}

	public int getRestaurantId() {
		return RestaurantId;
	}

	public void setRestaurantId(int RestaurantId) {
		this.RestaurantId = RestaurantId;
	}

	public int getCalorieAmount() {
		return CalorieAmount;
	}

	public void setCalorieAmount(int CalorieAmount) {
		this.CalorieAmount = CalorieAmount;
	}

	public int getCookingTime() {
		return CalorieAmount;
	}

	public void setCookingTime(int CookingTime) {
		this.CookingTime = CookingTime;
	}

	public int getStatus() {
		return Status;
	}

	public void setStatus(int Status) {
		this.Status = Status;
	}

	public int getDishPropertyChilly() {
		return DishPropertyChilly;
	}

	public void setDishPropertyChilly(int DishPropertyChilly) {
		this.DishPropertyChilly = DishPropertyChilly;
	}

	public int getDishPropertyVegan() {
		return DishPropertyVegan;
	}

	public void setDishPropertyVegan(int DishPropertyVegan) {
		this.DishPropertyVegan = DishPropertyVegan;
	}

	public int getDishPropertyglutenFree() {
		return DishPropertyglutenFree;
	}

	public void setDishPropertyglutenFree(int DishPropertyglutenFree) {
		this.DishPropertyglutenFree = DishPropertyglutenFree;
	}

	public int getDishPropertyVeg() {
		return DishPropertyVeg;
	}

	public void setDishPropertyVeg(int DishPropertyVeg) {
		this.DishPropertyVeg = DishPropertyVeg;
	}

	public int getDishPropertyNutFree() {
		return DishPropertyNutFree;
	}

	public void setDishPropertyNutFree(int DishPropertyNutFree) {
		this.DishPropertyNutFree = DishPropertyNutFree;
	}

	public int getDishPropertySeaFood() {
		return DishPropertySeaFood;
	}

	public void setDishPropertySeaFood(int DishPropertySeaFood) {
		this.DishPropertySeaFood = DishPropertySeaFood;
	}

	public int getDishParameterCount() {
		return DishParameterCount;
	}

	public void setDishParameterCount(int DishParameterCount) {
		this.DishParameterCount = DishParameterCount;
	}
	
	public Double getPrice() {
		return Price;
	}

	public void setPrice(Double Price) {
		this.Price = Price;
	}
	
	public Double getDiscount() {
		return Discount;
	}

	public void setDiscount(Double Discount) {
		this.Discount = Discount;
	}
	
	public String getRating() {
		return Rating;
	}

	public void setRating(String Rating) {
		this.Rating = Rating;
	}
	
	
	public Date getAvailableFrom() {
		return AvailableFrom;
	}

	public void setAvailableFrom(Date AvailableFrom) {
		this.AvailableFrom = AvailableFrom;
	}
	public Date getAvailableTo(){
		return AvailableTo;
	}

	public void setAvailableFromAvailableTo(Date AvailableTo) {
		this.AvailableTo = AvailableTo;
	}
	
	
}