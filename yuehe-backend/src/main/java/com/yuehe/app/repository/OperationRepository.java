/*
    Copyright (C) 2019 Yi Xiang Zhong

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.yuehe.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuehe.app.dto.OperationDetailDTO;
import com.yuehe.app.dto.OperationOperatorToolForDBDTO;
import com.yuehe.app.entity.Operation;

/**
 * @author Soveran Zhong
 */
public interface OperationRepository  extends JpaRepository<Operation, Long> {
//	private String operationId;//操作ID
//	private String saleId;//销售卡ID
//	private String createCardDate;//开卡日期
//	private String operationDate;//操作日期
//	private String clientName;//客户名
//	private String cosmeticShopName;//美容院名===================
//	private String cosmeticShopDiscount;//美容院名===================
//	private String beautifySkinItemName;//美肤项目
//	private long createCardTotalAmount;//开卡金额
//	private long earnedTotalAmount;//回款金额------------------------------------------
//	private int totalItemNumber;//开卡次数-疗程总次数
//	private int restItemNumber;//剩余次数--------------------------------------------------
//	private long consumedTotalAmount;//已消耗总数----------------------------------------
//	private long consumedEarnedTotalAmount;//已消耗回款------------------------------
//	private long advancedEarnedTotalAmount;//预付款总数----------------------------------
//	private String operatorName;//操作人
//	private String toolName;//操作仪器=======================
//	private int operateExpense;//操作费用+++++++++++++++++++++
//	private String description;//备注
//	this.id = id;
//	this.saleId = saleId;
//	this.operatorId = operatorId;
//	this.toolId = toolId;
//	this.operationDate = operationDate;
//	this.description = description;
	@Query("select count(o) from Operation o where o.sale.id = ?1")
    int findOperationNumBySaleId(String saleId);
	
	
	
	@Query("select new com.yuehe.app.dto.OperationDetailDTO(o.sale.id,o.id,o.operationDate, "
			+ "e.name,t.name,t.operateExpense,o.description) "
			+ "FROM Operation o INNER JOIN o.sale s "
//			+ "INNER JOIN s.beautifySkinItem b "
+ "INNER JOIN o.employee e "
//			+ "INNER JOIN s.client c "
//			+ "INNER JOIN c.cosmeticShop p "
+ "INNER JOIN o.tool t"
+ " where o.sale.id = ?1")
	List<OperationDetailDTO> findBySaleId(String saleId);
	
	@Query("select new com.yuehe.app.dto.OperationDetailDTO(o.sale.id,o.id,o.operationDate, "
			+ "e.name,t.name,t.operateExpense,o.description) "
			+ "FROM Operation o INNER JOIN o.sale s "
			+ "INNER JOIN o.employee e "
			+ "INNER JOIN o.tool t")
	List<OperationDetailDTO> findAllOperationList();
	

	
    @Query("SELECT new com.yuehe.app.dto.OperationOperatorToolForDBDTO(o.id,s.id, s.createCardDate, o.operationDate, "
			+ "c.id,c.name,p.id,p.name,p.discount,b.name,s.createCardTotalAmount,s.itemNumber,"
			+ "e.name,t.name,t.operateExpense,"
			+ "o.description) "
			+ "FROM Operation o INNER JOIN o.sale s "
			+ "INNER JOIN s.beautifySkinItem b "
			+ "INNER JOIN o.employee e "
			+ "INNER JOIN s.client c "
			+ "INNER JOIN c.cosmeticShop p "
			+ "INNER JOIN o.tool t"
			)
    List<OperationOperatorToolForDBDTO> fetchOperationOpertatorToolData();
    
    Operation findById(String id);
}
