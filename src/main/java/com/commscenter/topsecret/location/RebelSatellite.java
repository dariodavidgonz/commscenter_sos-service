package com.commscenter.topsecret.location;

public class RebelSatellite implements Satellite {

	private SecretPoint position;
	private String name;
	private Integer id;

	public RebelSatellite(Integer id, String name, SecretPoint position) {
		super();
		this.position = position;
		this.name = name;
		this.id = id;
	}

	public RebelSatellite() {
		super();
	}

	public SecretPoint getPosition() {
		return position;
	}

	public void setPosition(SecretPoint position) {
		this.position = position;
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

}
