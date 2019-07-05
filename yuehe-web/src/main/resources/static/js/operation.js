$(function ($) {
	jQuery(function() { 
	$('#cosmeticShop').change(
	        function() {
	            $.getJSON("http://localhost:9090/getShopAllClientsForFiltering", {
	                cosmeticShopId : $(this).val(),
	                ajax : 'true'
	            }, function(data) {
	                var html = '<option value="all">--所有客户--</option>';
	                var len = data.length;
	                for ( var i = 0; i < len; i++) {
	                    html += '<option value="' + data[i].id + '">'
	                            + data[i].name + '</option>';
	                }
	                html += '</option>';
	                htmlForSale = '<option value="all">--所有美肤卡--</option>';
	                $('#client').html(html);
	                $('#sale').html(htmlForSale);
	            });
	        });
	$('#client').change(
			function() {
				$.getJSON("http://localhost:9090/getClientAllSalesForFiltering", {
					clientId : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="all">--所有美肤卡--</option>';
					var len = data.length;
					for ( var i = 0; i < len; i++) {
						//alert(data[i].saleId);
						html += '<option value="' + data[i].saleId + '">'
						+ data[i].beautifySkinItemName + '-' + data[i].createCardDate+ '</option>';
					}
					html += '</option>';
					$('#sale').html(html);
				});
			});
//	$('#sale').change(
//			function() {
//				$.getJSON("http://localhost:9090/getSaleAllOperations", {
//					saleId : $(this).val(),
//					ajax : 'true'
//				}, function(data) {
//					var html = '';
//					var len = data.operationDetailDtos.length; 
//					for ( var i = 0; i < len; i++) {
//						html = html+'<tr><td>' +data.operationDetailDtos[i].operationId+'</td>'
//						+'<td>'+data.operationDetailDtos[i].operationDate+'</td>'
//						+'<td>'+data.operationDetailDtos[i].operatorName+'</td>'
//						+'<td>'+data.operationDetailDtos[i].toolName+'</td>'
//						+'<td>'+data.operationDetailDtos[i].operateExpense+'</td>'
//						+'<td>'+data.operationDetailDtos[i].description+'</td></tr>';
//						
//					}
//					//alert(html);
//					$('#operation_detail_table_body').html(html);
//					$('#sale_overall_sale_id').html(data.saleId);
//					$('#sale_overall_create_card_date').html(data.createCardDate);
//					$('#sale_overall_beautify_skin_item_name').html(data.beautifySkinItemName);
//					$('#sale_overall_create_card_amount').html(data.createCardTotalAmount);
//					$('#sale_overall_earned_amount').html(data.earnedAmount);
//					$('#sale_overall_item_number').html(data.itemNumber);
//					$('#sale_overall_rest_item_number').html(data.restItemNumber);
//					$('#sale_overall_consumed_amount').html(data.consumedAmount);
//					$('#sale_overall_consumed_earned_amount').html(data.consumedEarnedAmount);
//					$('#sale_overall_advanced_earned_amount').html(data.advancedEarnedAmount);
////					$('#operation_detail_total_advanced_earned_amount').html(data["totalAdvancedEarnedAmount"]);
//					$("#operation_detail").removeClass('hidden')
//					$("#shop_detail").addClass('hidden');
//					$("#client_detail").addClass('hidden');
//					$("#sale_detail").addClass('hidden');
//				});
//			});
	});
	
	$('#operation_summary_lookup').click(
			function() {
				var shopId = $('#cosmeticShop').val();
				var clientId = $('#client').val();
				var saleId = $('#sale').val();
				if(shopId == "all"){
					$.getJSON("http://localhost:9090/getYueHeAllShopsOperations", {
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.shopDetailDtos.length; 
						for ( var i = 0; i < len; i++) {
							html = html+'<tr><td>' +data.shopDetailDtos[i].cosmeticShopName+'</td>'
							+'<td>'+data.shopDetailDtos[i].allClientsCreateCardAmount+'</td>'
							+'<td>'+data.shopDetailDtos[i].allClientsEarnedAmount+'</td>'
							+'<td>'+data.shopDetailDtos[i].allClientsConsumedAmount+'</td>'
							+'<td>'+data.shopDetailDtos[i].allClientsConsumedEarnedAmount+'</td>'
							+'<td>'+data.shopDetailDtos[i].allClientsAdvancedEarnedAmount+'</td></tr>';
							
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
				}else if(clientId == "all"){
					$.getJSON("http://localhost:9090/getShopAllClientsOperations", {
						shopId:shopId,
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.clientDetailDtos.length;
						for ( var i = 0; i < len; i++) {
							html = html+'<tr><td>' +data.clientDetailDtos[i].clientName+'</td>'
							+'<td>'+data.clientDetailDtos[i].allSalesCreateCardAmount+'</td>'
							+'<td>'+data.clientDetailDtos[i].allSalesEarnedAmount+'</td>'
							+'<td>'+data.clientDetailDtos[i].allSalesConsumedAmount+'</td>'
							+'<td>'+data.clientDetailDtos[i].allSalesConsumedEarnedAmount+'</td>'
							+'<td>'+data.clientDetailDtos[i].allSalesAdvancedEarnedAmount+'</td></tr>';
							
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
				}else if(saleId =="all"){
					$.getJSON("http://localhost:9090/getClientAllSalesOperations", {
						clientId:clientId,
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.saleDetailDtos.length; 
						for ( var i = 0; i < len; i++) {
							html = html+'<tr><td>' +data.saleDetailDtos[i].saleId+'</td>'
							+'<td>'+data.saleDetailDtos[i].createCardDate+'</td>'
							+'<td>'+data.saleDetailDtos[i].beautifySkinItemName+'</td>'
							+'<td>'+data.saleDetailDtos[i].createCardTotalAmount+'</td>'
							+'<td>'+data.saleDetailDtos[i].earnedAmount+'</td>'
							+'<td>'+data.saleDetailDtos[i].itemNumber+'</td>'
							+'<td>'+data.saleDetailDtos[i].restItemNumber+'</td>'
							+'<td>'+data.saleDetailDtos[i].consumedAmount+'</td>'
							+'<td>'+data.saleDetailDtos[i].consumedEarnedAmount+'</td>'
							+'<td>'+data.saleDetailDtos[i].advancedEarnedAmount+'</td>'
//							+'<td>'+data.saleDetailDtos[i].description+'</td></tr>';
							
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
				}else{
					$.getJSON("http://localhost:9090/getSaleAllOperations", {
						saleId : saleId,
						ajax : 'true'
					}, function(data) {
						var html = '';
						var len = data.operationDetailDtos.length; 
						for ( var i = 0; i < len; i++) {
							html = html+'<tr><td>' +data.operationDetailDtos[i].operationId+'</td>'
							+'<td>'+data.operationDetailDtos[i].operationDate+'</td>'
							+'<td>'+data.operationDetailDtos[i].operatorName+'</td>'
							+'<td>'+data.operationDetailDtos[i].toolName+'</td>'
							+'<td>'+data.operationDetailDtos[i].operateExpense+'</td>'
							+'<td>'+data.operationDetailDtos[i].description+'</td></tr>';
							
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
//						$('#operation_detail_total_advanced_earned_amount').html(data["totalAdvancedEarnedAmount"]);
						$("#operation_detail").removeClass('hidden')
						$("#shop_detail").addClass('hidden');
						$("#client_detail").addClass('hidden');
						$("#sale_detail").addClass('hidden');
					});
				}
			});

	
});
