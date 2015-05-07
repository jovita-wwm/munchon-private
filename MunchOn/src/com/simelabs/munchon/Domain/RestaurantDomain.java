package com.simelabs.munchon.Domain;

import java.util.Date;


public class RestaurantDomain {

	int Id,Nooftable,countrycode,rate;
	String Name,shortAddress,LongAddress,location,MailId,RestaurantImage,active,status,updateduser,country,description,city,restaurantlogo;
	Long contacts;
	double createdon;
	Date activefrom,activetill;
	double updatedon;
	Long latitude,longitude;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getNooftable() {
		return Nooftable;
	}
	public void setNooftable(int nooftable) {
		Nooftable = nooftable;
	}
	public int getCountrycode() {
		return countrycode;
	}
	public void setCountrycode(int countrycode) {
		this.countrycode = countrycode;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getShortAddress() {
		return shortAddress;
	}
	public void setShortAddress(String shortAddress) {
		this.shortAddress = shortAddress;
	}
	public String getLongAddress() {
		return LongAddress;
	}
	public void setLongAddress(String longAddress) {
		LongAddress = longAddress;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getMailId() {
		return MailId;
	}
	public void setMailId(String mailId) {
		MailId = mailId;
	}
	public String getRestaurantImage() {
		return RestaurantImage;
	}
	public void setRestaurantImage(String restaurantImage) {
		RestaurantImage = restaurantImage;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdateduser() {
		return updateduser;
	}
	public void setUpdateduser(String updateduser) {
		this.updateduser = updateduser;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRestaurantlogo() {
		return restaurantlogo;
	}
	public void setRestaurantlogo(String restaurantlogo) {
		this.restaurantlogo = restaurantlogo;
	}
	public Long getContacts() {
		return contacts;
	}
	public void setContacts(Long contacts) {
		this.contacts = contacts;
	}
	public double getCreatedon() {
		return createdon;
	}
	public void setCreatedon(double d) {
		this.createdon = d;
	}
	public double getUpdatedon() {
		return updatedon;
	}
	public void setUpdatedon(double d) {
		this.updatedon = d;
	}
	public Date getActivefrom() {
		return activefrom;
	}
	public void setActivefrom(Date activefrom) {
		this.activefrom = activefrom;
	}
	public Date getActivetill() {
		return activetill;
	}
	public void setActivetill(Date activetill) {
		this.activetill = activetill;
	}
	public Long getLatitude() {
		return latitude;
	}
	public void setLatitude(Long d) {
		this.latitude = d;
	}
	public Long getLongitude() {
		return longitude;
	}
	public void setLongitude(Long d) {
		this.longitude = d;
	}
	
	
	
}
