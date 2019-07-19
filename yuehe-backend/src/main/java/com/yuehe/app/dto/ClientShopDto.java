package com.yuehe.app.dto;

/**
 * 
 * This is DTO class which is to render the view of client.html,using when it needs to join other tables
 * to get cosmetic shop name by it's shop id, it's different with the entity
 * class {@link com.yuehe.app.entity.Client} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class ClientShopDTO {
	public ClientShopDTO() {
		//super();
	}
	public ClientShopDTO(String clientId, String clientName, String cosmeticShopName, int clientAge,
			String clientGender, String clientSymptom) {
		super();
		this.clientId = clientId;
		this.clientName = clientName;
		this.cosmeticShopName = cosmeticShopName;
		this.clientAge = clientAge;
		this.clientGender = clientGender;
		this.clientSymptom = clientSymptom;
	}
	private String clientId;
	private String clientName;
	private String cosmeticShopName;
	private int clientAge;
	private String clientGender;
	private String clientSymptom;
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCosmeticShopName() {
		return cosmeticShopName;
	}
	public void setCosmeticShopName(String cosmeticShopName) {
		this.cosmeticShopName = cosmeticShopName;
	}
	public int getClientAge() {
		return clientAge;
	}
	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}
	public String getClientGender() {
		return clientGender;
	}
	public void setClientGender(String clientGender) {
		this.clientGender = clientGender;
	}
	public String getClientSymptom() {
		return clientSymptom;
	}
	public void setClientSymptom(String clientSymptom) {
		this.clientSymptom = clientSymptom;
	}
	@Override
	public String toString() {
		return "ClientShopDTO [clientId=" + clientId + ", clientName=" + clientName + ", cosmeticShopName="
				+ cosmeticShopName + ", clientAge=" + clientAge + ", clientGender=" + clientGender + ", clientSymptom="
				+ clientSymptom + "]";
	}

}
