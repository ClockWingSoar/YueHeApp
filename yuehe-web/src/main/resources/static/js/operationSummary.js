// $(function ($) {jQuery(function() { 
// 	$('#cosmeticShop').change(getShopAllClientsList);
// 	$('#client').change(getClientAllSalesList);
// 	});
// 	$('#operation_summary_lookup').click(
// 			function() {
// 				var shopId = $('#cosmeticShop').val();
// 				var clientId = $('#client').val();
// 				var saleId = $('#sale').val();
// 				if(shopId == "all"){
// 					getYueHeAllShopsOperations(shopId);
// 				}else if(clientId == "all"){
// 					getShopAllClientsOperations(shopId);
// 				}else if(saleId =="all"){
// 					getClientAllSalesOperations(clientId);
// 				}else{
// 					 getSaleAllOperations(saleId);
// 				}
// 			});

	
// });

function getClientAllSalesOperations(clientId,startDate,endDate){
	$.getJSON("http://"+window.location.host+"/getClientAllSalesOperations", {
		clientId:clientId,
		startDate:startDate,
		endDate:endDate,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.saleDetailDTOs.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.saleDetailDTOs[i].saleId+'</td>'
			+'<td>'+data.saleDetailDTOs[i].createCardDate+'</td>'
			+'<td>'+data.saleDetailDTOs[i].beautifySkinItemName+'</td>'
			+'<td>'+data.saleDetailDTOs[i].createCardTotalAmount+'</td>'
			+'<td>'+data.saleDetailDTOs[i].earnedAmount+'</td>'
			+'<td>'+data.saleDetailDTOs[i].itemNumber+'</td>'
			+'<td>'+data.saleDetailDTOs[i].restItemNumber+'</td>'
			+'<td>'+data.saleDetailDTOs[i].consumedAmount+'</td>'
			+'<td>'+data.saleDetailDTOs[i].consumedEarnedAmount+'</td>'
			+'<td>'+data.saleDetailDTOs[i].advancedEarnedAmount+'</td></tr>'
//			+'<td>'+data.saleDetailDTOs[i].description+'</td></tr>';
			
		}
		$('#sale_detail_table_body').html(html);
		$('#client_overall_client_name').html(data.clientName);
		$('#client_overall_create_card_amount').html(data.allSalesCreateCardAmount);
		$('#client_overall_earned_amount').html(data.allSalesEarnedAmount);
		$('#client_overall_consumed_amount').html(data.allSalesConsumedAmount);
		$('#client_overall_consumed_earned_amount').html(data.allSalesConsumedEarnedAmount);
		$('#client_overall_advanced_earned_amount').html(data.allSalesAdvancedEarnedAmount);
		$("#sale_detail").removeClass('hidden')
		$("#shop_detail").addClass('hidden');
		$("#client_detail").addClass('hidden');
		$("#operation_detail").addClass('hidden');
	});
}
function getSaleAllOperations(saleId,startDate,endDate){

	$.getJSON("http://"+window.location.host+"/getSaleAllOperations", {
		saleId : saleId,
		startDate:startDate,
		endDate:endDate,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.operationDetailDTOs.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.operationDetailDTOs[i].operationId+'</td>'
			+'<td>'+data.operationDetailDTOs[i].operationDate+'</td>'
			+'<td>'+data.operationDetailDTOs[i].operatorName+'</td>'
			+'<td>'+data.operationDetailDTOs[i].toolName+'</td>'
			+'<td>'+data.operationDetailDTOs[i].operateExpense+'</td>'
			+'<td>'+data.operationDetailDTOs[i].description+'</td></tr>';
			
		}
		//alert(html);
		$('#operation_detail_table_body').html(html);
		$('#sale_overall_sale_id').html(data.saleId);
		$('#sale_overall_create_card_date').html(data.createCardDate);
		$('#sale_overall_beautify_skin_item_name').html(data.beautifySkinItemName);
		$('#sale_overall_create_card_amount').html(data.createCardTotalAmount);
		$('#sale_overall_earned_amount').html(data.earnedAmount);
		$('#sale_overall_item_number').html(data.itemNumber);
		$('#sale_overall_rest_item_number').html(data.restItemNumber);
		$('#sale_overall_consumed_amount').html(data.consumedAmount);
		$('#sale_overall_consumed_earned_amount').html(data.consumedEarnedAmount);
		$('#sale_overall_advanced_earned_amount').html(data.advancedEarnedAmount);
//		$('#operation_detail_total_advanced_earned_amount').html(data["totalAdvancedEarnedAmount"]);
		$("#operation_detail").removeClass('hidden')
		$("#shop_detail").addClass('hidden');
		$("#client_detail").addClass('hidden');
		$("#sale_detail").addClass('hidden');
	});
}

function getShopAllClientsOperations(shopId,startDate,endDate){
	$.getJSON("http://"+window.location.host+"/getShopAllClientsOperations", {
		shopId:shopId,
		startDate:startDate,
		endDate:endDate,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.clientDetailDTOs.length;
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.clientDetailDTOs[i].clientName+'</td>'
			+'<td>'+data.clientDetailDTOs[i].allSalesCreateCardAmount+'</td>'
			+'<td>'+data.clientDetailDTOs[i].allSalesEarnedAmount+'</td>'
			+'<td>'+data.clientDetailDTOs[i].allSalesConsumedAmount+'</td>'
			+'<td>'+data.clientDetailDTOs[i].allSalesConsumedEarnedAmount+'</td>'
			+'<td>'+data.clientDetailDTOs[i].allSalesAdvancedEarnedAmount+'</td></tr>';
			
		}
		$('#client_detail_table_body').html(html);
		$('#shop_overall_shop_name').html(data.cosmeticShopName);
		$('#shop_overall_create_card_amount').html(data.allClientsCreateCardAmount);
		$('#shop_overall_earned_amount').html(data.allClientsEarnedAmount);
		$('#shop_overall_consumed_amount').html(data.allClientsConsumedAmount);
		$('#shop_overall_consumed_earned_amount').html(data.allClientsConsumedEarnedAmount);
		$('#shop_overall_advanced_earned_amount').html(data.allClientsAdvancedEarnedAmount);
		$("#client_detail").removeClass('hidden')
		$("#shop_detail").addClass('hidden');
		$("#sale_detail").addClass('hidden');
		$("#operation_detail").addClass('hidden');
	});
}

function getYueHeAllShopsOperations(startDate,endDate){
	$.getJSON("http://"+window.location.host+"/getYueHeAllShopsOperations", {
		startDate:startDate,
		endDate:endDate,
		ajax : 'true'
	}, function(data) {
		var html = '';
		var len = data.shopDetailDTOs.length; 
		for ( var i = 0; i < len; i++) {
			html = html+'<tr><td>' +data.shopDetailDTOs[i].cosmeticShopName+'</td>'
			+'<td>'+data.shopDetailDTOs[i].allClientsCreateCardAmount+'</td>'
			+'<td>'+data.shopDetailDTOs[i].allClientsEarnedAmount+'</td>'
			+'<td>'+data.shopDetailDTOs[i].allClientsConsumedAmount+'</td>'
			+'<td>'+data.shopDetailDTOs[i].allClientsConsumedEarnedAmount+'</td>'
			+'<td>'+data.shopDetailDTOs[i].allClientsAdvancedEarnedAmount+'</td></tr>';
			
		}
		$('#shop_detail_table_body').html(html);
		$('#yuehe_overall_company_name').html(data.yueheCompanyName);
		$('#yuehe_overall_create_card_amount').html(data.allShopsCreateCardTotalAmount);
		$('#yuehe_overall_earned_amount').html(data.allShopsEarnedAmount);
		$('#yuehe_overall_consumed_amount').html(data.allShopsConsumedAmount);
		$('#yuehe_overall_consumed_earned_amount').html(data.allShopsConsumedEarnedAmount);
		$('#yuehe_overall_advanced_earned_amount').html(data.allShopsAdvancedEarnedAmount);
		$("#shop_detail").removeClass('hidden');
		$("#client_detail").addClass('hidden');
		$("#sale_detail").addClass('hidden');
		$("#operation_detail").addClass('hidden');
	});
}