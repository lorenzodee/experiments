package com.orangeandbronze;

public class Car {

	// @javax.validation.constraints.NotNull
	private String manufacturer;
	// @javax.validation.constraints.NotNull
	// @javax.validation.constraints.Size(min = 2, max = 14)
	private String licensePlate;
	// @javax.validation.constraints.Min(2)
	private int seatCount;

	public Car(String manufacturer, String licensePlate, int seatCount) {
		super();
		this.manufacturer = manufacturer;
		this.licensePlate = licensePlate;
		this.seatCount = seatCount;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public int getSeatCount() {
		return seatCount;
	}

	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}

}
