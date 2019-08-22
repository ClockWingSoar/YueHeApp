function getClientProfile(clientId){
	$.getJSON("http://"+window.location.host+"/getProfileDetail", {
		clientId:clientId,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].saleId+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].beautifySkinItemName+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].createCardDate+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].createCardTotalAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].receivedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].debtAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].earnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].receivedEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].debtEarnedAmount+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].itemNumber+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].restItemNumber+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].consumedAmount+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].consumedEarnedAmount+'</td>'
			+'<td>'+data.clientDetailDTO.saleDetailDTOs[i].advancedEarnedAmount+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].employeePremium+'</td>'
			+'<td>'+data.clientAllSalesPerformanceDetailDTO.salePerformanceDetailDTOs[i].shopPremium+'</td></tr>';
			
		}
		$('#sale_detail_table_body').html(html);
		$('#client_name').html(data.clientAllSalesPerformanceDetailDTO.clientName);
		$('#create_card_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesCreateCardTotalAmount);
		$('#received_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesReceivedAmount);
		$('#debt_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesDebtAmount);
		$('#earned_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesEarnedAmount);
		$('#received_earned_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesReceivedEarnedAmount);
		$('#debt_earned_amount').html(data.clientAllSalesPerformanceDetailDTO.allSalesDebtEarnedAmount);
		$('#consumed_amount').html(data.clientDetailDTO.allSalesConsumedAmount);
		$('#consumed_earned_amount').html(data.clientDetailDTO.allSalesConsumedEarnedAmount);
		$('#advanced_earned_amount').html(data.clientDetailDTO.allSalesAdvancedEarnedAmount);
		$('#employee_premium').html(data.clientAllSalesPerformanceDetailDTO.allSalesEmployeePremium);
		$('#shop_premium').html(data.clientAllSalesPerformanceDetailDTO.allSalesShopPremium);
		$("#sale_detail").removeClass('hidden');
		
	});
}