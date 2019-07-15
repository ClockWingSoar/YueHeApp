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
		var len = data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].saleId+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].createCardTotalAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].receivedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].debtAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].earnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].receivedEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].debtEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].employeePremium+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDto.salePerformanceDetailDtos[i].shopPremium+'</td></tr>';
			
		}
		$('#sale_detail_table_body').html(html);
		$('#sale_client_overall_client_name').html(data.clientAllSalesPerformanceDetailDto.clientName);
		$('#sale_client_overall_create_card_amount').html(data.clientAllSalesPerformanceDetailDto.allSalesCreateCardTotalAmount);
		$('#sale_client_overall_received_amount').html(data.clientAllSalesPerformanceDetailDto.allSalesReceivedAmount);
		$('#sale_client_overall_debt_amount').html(data.clientAllSalesPerformanceDetailDto.allSalesDebtAmount);
		$('#sale_client_overall_earned_amount').html(data.clientAllSalesPerformanceDetailDto.allSalesEarnedAmount);
		$('#sale_client_overall_received_earned_amount').html(data.clientAllSalesPerformanceDetailDto.allSalesReceivedEarnedAmount);
		$('#sale_client_overall_debt_earned_amount').html(data.clientAllSalesPerformanceDetailDto.allSalesDebtEarnedAmount);
		$('#sale_client_overall_employee_premium').html(data.clientAllSalesPerformanceDetailDto.allSalesEmployeePremium);
		$('#sale_client_overall_shop_premium').html(data.clientAllSalesPerformanceDetailDto.allSalesShopPremium);
		$("#sale_detail").removeClass('hidden');
		
		
		var len = data.clientDetailDto.saleDetailDtos.length;
		html = " ";
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.clientDetailDto.saleDetailDtos[i].saleId+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].createCardDate+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].beautifySkinItemName+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].createCardTotalAmount+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].earnedAmount+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].itemNumber+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].restItemNumber+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].consumedAmount+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].consumedEarnedAmount+'</td>'
			+'<td>'+data.clientDetailDto.saleDetailDtos[i].advancedEarnedAmount+'</td></tr>'
//			+'<td>'+data.saleDetailDtos[i].description+'</td></tr>';
			
		}
		$('#sale_operation_detail_table_body').html(html);
		$('#client_overall_client_name').html(data.clientDetailDto.clientName);
		$('#client_overall_create_card_amount').html(data.clientDetailDto.allSalesCreateCardAmount);
		$('#client_overall_earned_amount').html(data.clientDetailDto.allSalesEarnedAmount);
		$('#client_overall_consumed_amount').html(data.clientDetailDto.allSalesConsumedAmount);
		$('#client_overall_consumed_earned_amount').html(data.clientDetailDto.allSalesConsumedEarnedAmount);
		$('#client_overall_advanced_earned_amount').html(data.clientDetailDto.allSalesAdvancedEarnedAmount);
		$("#sale_detail_operation").removeClass('hidden')
	});
}