package com.simelabs.munchon.Domain;

import java.util.ArrayList;



public class PublicValues {

	
/*Beacon*/
	public static int BeaconRefreshInterval; 
	public static String oldbeacons;
	public static ArrayList<BeaconDomain> allbeacons=new ArrayList<>();
	public static String beaconresponce;
	
/*Location*/
	public static String defaultlocation="No location Selected";
	
	
/*Account Details*/
	public static String AccountFirstName="jovita";
	public static String AccountLastName="pj";
	public static String Email="jovita.pj@simelabs.com";
	public static String Password="jovita";
	
	
	/*Beacon*/
	public static String tableno;
	public static int restaurantid=0;
	
	/*Restuarent Details*/
	
	public static ArrayList<RestaurantDomain> allnearbyRestaurants=new ArrayList<>();
	public static String restaurantresponce;
	
	/* Dish Categories*/
	public static ArrayList<DishCategoriesDomain> allDishCategories=new ArrayList<>();
	public static String dishResponse;
	
	/* Dish list*/
	public static ArrayList<DishesDomain> allDishes=new ArrayList<>();
	public static String dishListResponse;
	
	/*Deals list*/
	public static ArrayList<DealsDomain> userDeals=new ArrayList<>();
	public static String userDealsListResponse;
	public static ArrayList<DealsDomain> allDeals=new ArrayList<>();
	public static String allDealsListResponse;	
	
}
