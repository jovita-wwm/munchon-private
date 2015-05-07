package com.simelabs.kmb.domain;

import com.google.android.gms.maps.model.LatLng;

public class LocationDetails {
	
	String name,venueimageurl,floorname,details;
	int id;
	LatLng latlng;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVenueimageurl() {
		return venueimageurl;
	}
	public void setVenueimageurl(String venueimageurl) {
		this.venueimageurl = venueimageurl;
	}
	public String getFloorname() {
		return floorname;
	}
	public void setFloorname(String floorname) {
		this.floorname = floorname;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LatLng getLatlng() {
		return latlng;
	}
	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}



}
