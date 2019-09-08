package com.yuehe.app.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to show the basic operation table view
 * class {@link com.yuehe.app.entity.Operation} which is aim to symplify the data store volume
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopDetailDTO {
	private String cosmeticShopName;//美容院名
	private long allClientsReceivedAmount;//单店所有顾客的卡的总实收金额
	private long allClientsEarnedAmount;//单店所有顾客的总应回款金额
	private long allClientsReceivedEarnedAmount;//单店所有顾客的总实际回款金额
	private long allClientsConsumedAmount;//单店所有顾客的总消耗款
	private long allClientsConsumedEarnedAmount;//单店所有顾客的总消耗回款
	private long allClientsAdvancedEarnedAmount;//单店所有顾客的总预付款
	private List<ClientDetailDTO> clientDetailDTOs;//单店下面所有的客户的信息
	
}