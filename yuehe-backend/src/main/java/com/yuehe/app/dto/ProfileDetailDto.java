package com.yuehe.app.dto;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
public class ProfileDetailDto {
	public ProfileDetailDto() {
	}
	private ClientDetailDto clientDetailDto;//该顾客的个人销售卡操作信息
	private ClientShopDto clientShopDto;//该顾客的个人信息
	private ClientAllSalesPerformanceDetailDto clientAllSalesPerformanceDetailDto;//该顾客的个人销售卡信息
	public ClientDetailDto getClientDetailDto() {
		return clientDetailDto;
	}
	public void setClientDetailDto(ClientDetailDto clientDetailDto) {
		this.clientDetailDto = clientDetailDto;
	}
	public ClientShopDto getClientShopDto() {
		return clientShopDto;
	}
	public void setClientShopDto(ClientShopDto clientShopDto) {
		this.clientShopDto = clientShopDto;
	}
	public ClientAllSalesPerformanceDetailDto getClientAllSalesPerformanceDetailDto() {
		return clientAllSalesPerformanceDetailDto;
	}
	public void setClientAllSalesPerformanceDetailDto(
			ClientAllSalesPerformanceDetailDto clientAllSalesPerformanceDetailDto) {
		this.clientAllSalesPerformanceDetailDto = clientAllSalesPerformanceDetailDto;
	}
	public ProfileDetailDto(ClientDetailDto clientDetailDto, ClientShopDto clientShopDto,
			ClientAllSalesPerformanceDetailDto clientAllSalesPerformanceDetailDto) {
		super();
		this.clientDetailDto = clientDetailDto;
		this.clientShopDto = clientShopDto;
		this.clientAllSalesPerformanceDetailDto = clientAllSalesPerformanceDetailDto;
	}
	@Override
	public String toString() {
		return "ProfileDetailDto [clientDetailDto=" + clientDetailDto + ", clientShopDto=" + clientShopDto
				+ ", clientAllSalesPerformanceDetailDto=" + clientAllSalesPerformanceDetailDto + "]";
	}
	
	
}