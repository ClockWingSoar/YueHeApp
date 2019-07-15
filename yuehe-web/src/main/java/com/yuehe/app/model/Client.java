package com.yuehe.app.model;

/**
 * 
 * This is Model class which is to render the view of client.html, it's different with the same name entity
 * class {@link com.yuehe.app.entity.Client} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */

public class Client {

	//Fields
	
	
	/**
	 * 
	 */
	private String id;
	private String name;
	private String cosmeticShopName;
	private int age;
	private String gender;
	private String symptom;
	
	// Constructors

	/** default constructor */
	public Client() {
		
	}
	public Client(String id, String name, String cosmeticShopName, int age, String gender, String symptom) {
		super();
		this.id = id;
		this.name = name;
		this.cosmeticShopName = cosmeticShopName;
		this.age = age;
		this.gender = gender;
		this.symptom = symptom;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCosmeticShopName() {
		return cosmeticShopName;
	}

	public void setCosmeticShopName(String cosmeticShopName) {
		this.cosmeticShopName = cosmeticShopName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", cosmeticShopName=" + cosmeticShopName + ", age=" + age
				+ ", gender=" + gender + ", symptom=" + symptom + "]";
	}


}
