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
	                $('#client').html(html);
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
	$('#sale').change(
			function() {
				$.getJSON("http://localhost:9090/getSaleAllOperations", {
					saleId : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '';
					var len = data.operationDetailDtos.length; 
					for ( var i = 0; i < len; i++) {
						html = html+'<tr><td>' +data.operationDetailDtos[i].operationId+'</td>'
//						+'<td>'+data["operationList"][i].saleId+'</td>'
//						+'<td>'+data["operationList"][i].createCardDate+'</td>'
						+'<td>'+data.operationDetailDtos[i].operationDate+'</td>'
//						+'<td>'+data["operationList"][i].clientName+'</td>'
//						+'<td>'+data["operationList"][i].cosmeticShopName+'</td>'
//						+'<td>'+data["operationList"][i].beautifySkinItemName+'</td>'
//						+'<td>'+data["operationList"][i].createCardTotalAmount+'</td>'
//						+'<td>'+data["operationList"][i].earnedTotalAmount+'</td>'
//						+'<td>'+data["operationList"][i].totalItemNumber+'</td>'
//						+'<td>'+data["operationList"][i].restItemNumber+'</td>'
//						+'<td>'+data["operationList"][i].consumedTotalAmount+'</td>'
//						+'<td>'+data["operationList"][i].consumedEarnedTotalAmount+'</td>'
//						+'<td>'+data["operationList"][i].advancedEarnedTotalAmount+'</td>'
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
//					$('#operation_detail_total_advanced_earned_amount').html(data["totalAdvancedEarnedAmount"]);
					$("#operation_detail").removeClass('hidden')
				});
			});
	});
	
	
});
