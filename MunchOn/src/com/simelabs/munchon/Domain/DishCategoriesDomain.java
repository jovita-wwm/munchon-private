package com.simelabs.munchon.Domain;

import java.util.Date;

public class DishCategoriesDomain {

	String CategoryName;
	int ID;
	

	public String getCategoryName() {
		return CategoryName;
	}

	public void setRestaurantName(String CategoryName) {
		this.CategoryName = CategoryName;
	}

	
	public void setID(int ID) {
		this.ID = ID;

	}

	public int getID() {
		return ID;

	}
}
