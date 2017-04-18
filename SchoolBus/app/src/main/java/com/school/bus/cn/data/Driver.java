package com.school.bus.cn.data;


public class Driver {
	private int id;
	private String name;
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	private String telephone;
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Driver(int id, String telephone,String name, float radius, double latitude,
			double longitude) {
		super();
		this.id = id;
		this.telephone=telephone;
		this.name = name;
		this.radius = radius;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	private float radius;
    private double latitude;
    private double longitude;

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
