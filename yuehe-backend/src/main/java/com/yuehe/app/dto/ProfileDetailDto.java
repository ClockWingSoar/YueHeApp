package com.yuehe.app.dto;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class ProfileDetailDTO {
	public ProfileDetailDTO() {
	}
	private ClientDetailDTO clientDetailDTO;//该顾客的个人销售卡操作信息
	private ClientShopDTO clientShopDTO;//该顾客的个人信息
	private ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO;//该顾客的个人销售卡信息
	public ClientDetailDTO getClientDetailDTO() {
		return clientDetailDTO;
	}
	public void setClientDetailDTO(ClientDetailDTO clientDetailDTO) {
		this.clientDetailDTO = clientDetailDTO;
	}
	public ClientShopDTO getClientShopDTO() {
		return clientShopDTO;
	}
	public void setClientShopDTO(ClientShopDTO clientShopDTO) {
		this.clientShopDTO = clientShopDTO;
	}
	public ClientAllSalesPerformanceDetailDTO getClientAllSalesPerformanceDetailDTO() {
		return clientAllSalesPerformanceDetailDTO;
	}
	public void setClientAllSalesPerformanceDetailDTO(
			ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO) {
		this.clientAllSalesPerformanceDetailDTO = clientAllSalesPerformanceDetailDTO;
	}
	public ProfileDetailDTO(ClientDetailDTO clientDetailDTO, ClientShopDTO clientShopDTO,
			ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO) {
		super();
		this.clientDetailDTO = clientDetailDTO;
		this.clientShopDTO = clientShopDTO;
		this.clientAllSalesPerformanceDetailDTO = clientAllSalesPerformanceDetailDTO;
	}
	@Override
	public String toString() {
		return "ProfileDetailDTO [clientDetailDTO=" + clientDetailDTO + ", clientShopDTO=" + clientShopDTO
				+ ", clientAllSalesPerformanceDetailDTO=" + clientAllSalesPerformanceDetailDTO + "]";
	}
	
	
}