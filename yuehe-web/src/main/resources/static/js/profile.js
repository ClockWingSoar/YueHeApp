$(function ($) {
	$('#cosmeticShop').change(getShopAllClientsList);
//	$('#client').change(getClientAllSales);
	
	$('#profile_search').click(
			function() {
				var clientId = $('#client').val();
				getClientProfile(clientId);
			});
	
});
function getClientProfile(clientId){
	$.getJSON("http://localhost:9090/getProfileDetail", {
		clientId:clientId,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].saleId+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].createCardTotalAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].receivedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].debtAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].earnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].receivedEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].debtEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].employeePremium+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].shopPremium+'</td></tr>';
			
		}
		$('#sale_detail_table_body').html(html);
		$('#sale_client_overall_client_name').html(data.clientAllSalesPerformanceDetailDTO.clientName);
		$('#sale_client_overall_create_card_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesCreateCardTotalAmount);
		$('#sale_client_overall_received_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesReceivedAmount);
		$('#sale_client_overall_debt_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesDebtAmount);
		$('#sale_client_overall_earned_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesEarnedAmount);
		$('#sale_client_overall_received_earned_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesReceivedEarnedAmount);
		$('#sale_client_overall_debt_earned_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesDebtEarnedAmount);
		$('#sale_client_overall_employee_premium').html(data.clientAllSalesPerformanceDetailDTO.allSalesEmployeePremium);
		$('#sale_client_overall_shop_premium').html(data.clientAllSalesPerformanceDetailDTO.allSalesShopPremium);
		$("#sale_detail").removeClass('hidden');
		
		
		var len = data.clientDetailDTO.saleDetailDTOs.length;
		html = " ";
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.clientDetailDTO.saleDetailDTOs[i].saleId+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].createCardDate+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].beautifySkinItemName+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].createCardTotalAmount+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].earnedAmount+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].itemNumber+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].restItemNumber+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].consumedAmount+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].consumedEarnedAmount+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].advancedEarnedAmount+'</td></tr>'
//			+'<td>'+data.saleDetailDTOs[i].description+'</td></tr>';
			
		}
		$('#sale_operation_detail_table_body').html(html);
		$('#client_overall_client_name').html(data.clientDetailDTO.clientName);
		$('#client_overall_create_card_amount').html(data.clientDetailDTO.allSalesCreateCardAmount);
		$('#client_overall_earned_amount').html(data.clientDetailDTO.allSalesEarnedAmount);
		$('#client_overall_consumed_amount').html(data.clientDetailDTO.allSalesConsumedAmount);
		$('#client_overall_consumed_earned_amount').html(data.clientDetailDTO.allSalesConsumedEarnedAmount);
		$('#client_overall_advanced_earned_amount').html(data.clientDetailDTO.allSalesAdvancedEarnedAmount);
		$("#sale_detail_operation").removeClass('hidden')
	});
}