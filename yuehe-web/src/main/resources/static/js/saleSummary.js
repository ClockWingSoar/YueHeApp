// $(function ($) {
// 	jQuery(function() { 
// 		$('#cosmeticShop').change(getShopAllClientsList);
// 		$('#client').change(getClientAllSalesList);
// 		});
// 	$('#sale_summary_lookup').click(
// 		function() {
// 			var shopId = $('#cosmeticShop').val();
// 			var clientId = $('#client').val();
// 			var saleId = $('#sale').val();
// 			if(shopId == "all"){
// 				getYueHeAllShopsSales();
			
// 			}else if(clientId == "all"){
// 				getShopAllClientsSales(shopId);
// 			}else if(saleId =="all"){
// 				getClientAllSales(clientId);
// 			}else{
// 				getSalePerformanceDetail(saleId);
// 			}
// 		});
// });
	

function getYueHeAllShopsSales(startDate,endDate){

	$.getJSON("http://"+window.location.host+"/getYueHeAllShopsSales", {
		startDate:startDate,
		endDate:endDate,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.shopAllSalesPerformanceDetailDTOs.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.shopAllSalesPerformanceDetailDTOs[i].cosmeticShopName+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesCreateCardTotalAmount+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesReceivedAmount+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesDebtAmount+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesEarnedAmount+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesCurrentEarnedAmount+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesDebtEarnedAmount+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesEmployeePremium+'</td>'
			+'<td>'+data.shopAllSalesPerformanceDetailDTOs[i].allClientsSalesShopPremium+'</td></tr>';
			
		}
		$('#shop_detail_table_body').html(html);
		$('#yuehe_overall_company_name').html(data.companyName);
		$('#yuehe_overall_create_card_amount').html(data.allShopsSalesCreateCardTotalAmount);
		$('#yuehe_overall_received_amount').html(data.allShopsSalesReceivedAmount);
		$('#yuehe_overall_debt_amount').html(data.allShopsSalesDebtAmount);
		$('#yuehe_overall_earned_amount').html(data.allShopsSalesEarnedAmount);
		$('#yuehe_overall_received_earned_amount').html(data.allShopsSalesCurrentEarnedAmount);
		$('#yuehe_overall_debt_earned_amount').html(data.allShopsSalesDebtEarnedAmount);
		$('#yuehe_overall_employee_premium').html(data.allShopsSalesEmployeePremium);
		$('#yuehe_overall_shop_premium').html(data.allShopsSalesShopPremium);
		$("#shop_detail").removeClass('hidden');
		$("#client_detail").addClass('hidden');
		$("#sale_detail").addClass('hidden');
		$("#sale_item").addClass('hidden');
	});
}
function getShopAllClientsSales(shopId,startDate,endDate){
	$.getJSON("http://"+window.location.host+"/getShopAllClientsSales", {
		startDate:startDate,
		endDate:endDate,
		shopId:shopId,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.clientAllSalesPerformanceDetailDTOs.length;
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.clientAllSalesPerformanceDetailDTOs[i].clientName+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesCreateCardTotalAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesReceivedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesDebtAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesCurrentEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesDebtEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesEmployeePremium+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTOs[i].allSalesShopPremium+'</td></tr>';
			
		}
		$('#client_detail_table_body').html(html);
		$('#shop_overall_shop_name').html(data.cosmeticShopName);
		$('#shop_overall_create_card_amount').html(data.allClientsSalesCreateCardTotalAmount);
		$('#shop_overall_received_amount').html(data.allClientsSalesReceivedAmount);
		$('#shop_overall_debt_amount').html(data.allClientsSalesDebtAmount);
		$('#shop_overall_earned_amount').html(data.allClientsSalesEarnedAmount);
		$('#shop_overall_received_earned_amount').html(data.allClientsSalesCurrentEarnedAmount);
		$('#shop_overall_debt_earned_amount').html(data.allClientsSalesDebtEarnedAmount);
		$('#shop_overall_employee_premium').html(data.allClientsSalesEmployeePremium);
		$('#shop_overall_shop_premium').html(data.allClientsSalesShopPremium);
		$("#shop_detail").addClass('hidden');
		$("#client_detail").removeClass('hidden');
		$("#sale_detail").addClass('hidden');
		$("#sale_item").addClass('hidden');
	});
}
function getClientAllSales(clientId,startDate,endDate){
	$.getJSON("http://"+window.location.host+"/getClientAllSales", {
		startDate:startDate,
		endDate:endDate,
		clientId:clientId,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.salePerformanceDetailDTOs.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.salePerformanceDetailDTOs[i].saleId+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].createCardTotalAmount+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].receivedAmount+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].debtAmount+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].earnedAmount+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].currentEarnedAmount+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].debtEarnedAmount+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].employeePremium+'</td>'
			+'<td>'+data.salePerformanceDetailDTOs[i].shopPremium+'</td></tr>';
			
		}
		$('#sale_detail_table_body').html(html);
		$('#client_overall_client_name').html(data.clientName);
		$('#client_overall_create_card_amount').html(data.allSalesCreateCardTotalAmount);
		$('#client_overall_received_amount').html(data.allSalesReceivedAmount);
		$('#client_overall_debt_amount').html(data.allSalesDebtAmount);
		$('#client_overall_earned_amount').html(data.allSalesEarnedAmount);
		$('#client_overall_received_earned_amount').html(data.allSalesCurrentEarnedAmount);
		$('#client_overall_debt_earned_amount').html(data.allSalesDebtEarnedAmount);
		$('#client_overall_employee_premium').html(data.allSalesEmployeePremium);
		$('#client_overall_shop_premium').html(data.allSalesShopPremium);
		$("#shop_detail").addClass('hidden');
		$("#client_detail").addClass('hidden');
		$("#sale_detail").removeClass('hidden');
		$("#sale_item").addClass('hidden');
	});

}
function getSalePerformanceDetail(saleId){

	$.getJSON("http://"+window.location.host+"/getSalePerformanceDetail", {
		saleId : saleId,
		ajax : 'true'
	}, function(data) {
		$('#sale_sale_id').html(data.saleId);
		$('#sale_create_card_amount').html(data.createCardTotalAmount);
		$('#sale_received_amount').html(data.receivedAmount);
		$('#sale_debt_amount').html(data.debtAmount);
		$('#sale_earned_amount').html(data.earnedAmount);
		$('#sale_received_earned_amount').html(data.currentEarnedAmount);
		$('#sale_debt_earned_amount').html(data.debtEarnedAmount);
		$('#sale_employee_premium').html(data.employeePremium);
		$('#sale_shop_premium').html(data.shopPremium);
		$("#sale_detail").addClass('hidden')
		$("#shop_detail").addClass('hidden');
		$("#client_detail").addClass('hidden');
		$("#sale_item").removeClass('hidden');
	});
}
	
