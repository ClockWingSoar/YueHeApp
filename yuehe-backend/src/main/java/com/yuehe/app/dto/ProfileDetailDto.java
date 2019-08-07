package com.yuehe.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * This is DTO class which is to render the view of operationSummary.html,to
 * show the basic operation table view class
 * {@link com.yuehe.app.entity.Operation} which is aim to symplify the data
 * store volume
 * 
 * @author YIXIANGZhong
 * @since 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDetailDTO {
	private ClientDetailDTO clientDetailDTO;//该顾客的个人销售卡操作信息
	private ClientShopDTO clientShopDTO;//该顾客的个人信息
	private ClientAllSalesPerformanceDetailDTO clientAllSalesPerformanceDetailDTO;//该顾客的个人销售卡信息
	
}