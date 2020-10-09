package com.commscenter.topsecret.location;

public class RebelSatellite implements Satellite {

	private SecretPoint location;
	private String name;
	private Integer id;

	public RebelSatellite(Integer id, String name, SecretPoint location) {
		super();
		this.setLocation(location);
		this.name = name;
		this.id = id;
	}

	public RebelSatellite() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SecretPoint getLocation() {
		return location;
	}

	public void setLocation(SecretPoint location) {
		this.location = location;
	}

}
