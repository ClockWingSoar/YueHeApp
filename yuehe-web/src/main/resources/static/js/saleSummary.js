$(function ($) {	
	jQuery(function() { 
		$('#cosmeticShop').change(getShopAllClients);
		$('#client').change(getClientAllSales);
		});
	$('#sale_summary_lookup').click(
			function() {
				var shopId = $('#cosmeticShop').val();
				var clientId = $('#client').val();
				var saleId = $('#sale').val();
				if(shopId == "all"){
					$.getJSON("http://localhost:9090/getYueHeAllShopsSales", {
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.shopAllSalesPerformanceDetailDtos.length; 
						for ( var i = 0; i < len; i++) {
							html = html+'<tr><td>' +data.shopAllSalesPerformanceDetailDtos[i].cosmeticShopName+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesCreateCardTotalAmount+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesReceivedAmount+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesDebtAmount+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesEarnedAmount+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesReceivedEarnedAmount+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesDebtEarnedAmount+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesEmployeePremium+'</td>'
							+'<td>'+data.shopAllSalesPerformanceDetailDtos[i].allClientsSalesShopPremium+'</td></tr>';
							
						}
						$('#shop_detail_table_body').html(html);
						$('#yuehe_overall_company_name').html(data.companyName);
						$('#yuehe_overall_create_card_amount').html(data.allShopsSalesCreateCardTotalAmount);
						$('#yuehe_overall_received_amount').html(data.allShopsSalesReceivedAmount);
						$('#yuehe_overall_debt_amount').html(data.allShopsSalesDebtAmount);
						$('#yuehe_overall_earned_amount').html(data.allShopsSalesEarnedAmount);
						$('#yuehe_overall_received_earned_amount').html(data.allShopsSalesReceivedEarnedAmount);
						$('#yuehe_overall_debt_earned_amount').html(data.allShopsSalesDebtEarnedAmount);
						$('#yuehe_overall_employee_premium').html(data.allShopsSalesEmployeePremium);
						$('#yuehe_overall_shop_premium').html(data.allShopsSalesShopPremium);
						$("#shop_detail").removeClass('hidden');
						$("#client_detail").addClass('hidden');
						$("#sale_detail").addClass('hidden');
						$("#sale_item").addClass('hidden');
					});
				}else if(clientId == "all"){
					$.getJSON("http://localhost:9090/getShopAllClientsSales", {
						shopId:shopId,
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.clientAllSalesPerformanceDetailDtos.length;
						for ( var i = 0; i < len; i++) {
							html = html+'<tr><td>' +data.clientAllSalesPerformanceDetailDtos[i].clientName+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesCreateCardTotalAmount+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesReceivedAmount+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesDebtAmount+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesEarnedAmount+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesReceivedEarnedAmount+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesDebtEarnedAmount+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesEmployeePremium+'</td>'
							+'<td>'+data.clientAllSalesPerformanceDetailDtos[i].allSalesShopPremium+'</td></tr>';
							
						}
						$('#client_detail_table_body').html(html);
						$('#shop_overall_shop_name').html(data.cosmeticShopName);
						$('#shop_overall_create_card_amount').html(data.allClientsSalesCreateCardTotalAmount);
						$('#shop_overall_received_amount').html(data.allClientsSalesReceivedAmount);
						$('#shop_overall_debt_amount').html(data.allClientsSalesDebtAmount);
						$('#shop_overall_earned_amount').html(data.allClientsSalesEarnedAmount);
						$('#shop_overall_received_earned_amount').html(data.allClientsSalesReceivedEarnedAmount);
						$('#shop_overall_debt_earned_amount').html(data.allClientsSalesDebtEarnedAmount);
						$('#shop_overall_employee_premium').html(data.allClientsSalesEmployeePremium);
						$('#shop_overall_shop_premium').html(data.allClientsSalesShopPremium);
						$("#shop_detail").addClass('hidden');
						$("#client_detail").removeClass('hidden');
						$("#sale_detail").addClass('hidden');
						$("#sale_item").addClass('hidden');
					});
				}else if(saleId =="all"){
					$.getJSON("http://localhost:9090/getClientAllSales", {
						clientId:clientId,
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.salePerformanceDetailDtos.length; 
						for ( var i = 0; i < len; i++) {
							html = html+'<tr><td>' +data.salePerformanceDetailDtos[i].saleId+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].createCardTotalAmount+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].receivedAmount+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].debtAmount+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].earnedAmount+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].receivedEarnedAmount+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].debtEarnedAmount+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].employeePremium+'</td>'
							+'<td>'+data.salePerformanceDetailDtos[i].shopPremium+'</td></tr>';
							
						}
						$('#sale_detail_table_body').html(html);
						$('#client_overall_client_name').html(data.clientName);
						$('#client_overall_create_card_amount').html(data.allSalesCreateCardTotalAmount);
						$('#client_overall_received_amount').html(data.allSalesReceivedAmount);
						$('#client_overall_debt_amount').html(data.allSalesDebtAmount);
						$('#client_overall_earned_amount').html(data.allSalesEarnedAmount);
						$('#client_overall_received_earned_amount').html(data.allSalesReceivedEarnedAmount);
						$('#client_overall_debt_earned_amount').html(data.allSalesDebtEarnedAmount);
						$('#client_overall_employee_premium').html(data.allSalesEmployeePremium);
						$('#client_overall_shop_premium').html(data.allSalesShopPremium);
						$("#shop_detail").addClass('hidden');
						$("#client_detail").addClass('hidden');
						$("#sale_detail").removeClass('hidden');
						$("#sale_item").addClass('hidden');
					});
				}else{
					$.getJSON("http://localhost:9090/getSalePerformanceDetail", {
						saleId : saleId,
						ajax : 'true'
					}, function(data) {
						$('#sale_sale_id').html(data.saleId);
						$('#sale_create_card_amount').html(data.createCardTotalAmount);
						$('#sale_received_amount').html(data.receivedAmount);
						$('#sale_debt_amount').html(data.debtAmount);
						$('#sale_earned_amount').html(data.earnedAmount);
						$('#sale_received_earned_amount').html(data.receivedEarnedAmount);
						$('#sale_debt_earned_amount').html(data.debtEarnedAmount);
						$('#sale_employee_premium').html(data.employeePremium);
						$('#sale_shop_premium').html(data.shopPremium);
						$("#sale_detail").addClass('hidden')
						$("#shop_detail").addClass('hidden');
						$("#client_detail").addClass('hidden');
						$("#sale_item").removeClass('hidden');
					});
				}
			});

	
});
