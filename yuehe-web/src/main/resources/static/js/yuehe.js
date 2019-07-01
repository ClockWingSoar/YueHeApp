$(function ($) {
	jQuery(function() { 
	$('#cosmeticShop').change(
	        function() {
	            $.getJSON("http://localhost:9090/getShopAllClients", {
	                cosmeticShopId : $(this).val(),
	                ajax : 'true'
	            }, function(data) {
	                var html = '<option value="">--所有客户--</option>';
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
				$.getJSON("http://localhost:9090/getClientAllSales", {
					clientId : $(this).val(),
					ajax : 'true'
				}, function(data) {
					var html = '<option value="">--所有美肤卡--</option>';
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
					var len = data["operationList"].length; 
					for ( var i = 0; i < len; i++) {
						html = html+'<tr><td>' +data["operationList"][i].operationId+'</td>'
						+'<td>'+data["operationList"][i].saleId+'</td>'
						+'<td>'+data["operationList"][i].createCardDate+'</td>'
						+'<td>'+data["operationList"][i].operationDate+'</td>'
						+'<td>'+data["operationList"][i].clientName+'</td>'
						+'<td>'+data["operationList"][i].cosmeticShopName+'</td>'
						+'<td>'+data["operationList"][i].beautifySkinItemName+'</td>'
						+'<td>'+data["operationList"][i].createCardTotalAmount+'</td>'
						+'<td>'+data["operationList"][i].earnedTotalAmount+'</td>'
						+'<td>'+data["operationList"][i].totalItemNumber+'</td>'
						+'<td>'+data["operationList"][i].restItemNumber+'</td>'
						+'<td>'+data["operationList"][i].consumedTotalAmount+'</td>'
						+'<td>'+data["operationList"][i].consumedEarnedTotalAmount+'</td>'
						+'<td>'+data["operationList"][i].advancedEarnedTotalAmount+'</td>'
						+'<td>'+data["operationList"][i].operatorName+'</td>'
						+'<td>'+data["operationList"][i].toolName+'</td>'
						+'<td>'+data["operationList"][i].operateExpense+'</td>'
						+'<td>'+data["operationList"][i].description+'</td></tr>';
						
					}
					//alert(html);
					$('#operation_detail_table_body').html(html);
					$('#operation_detail_total_consumed_amount').html(data["totalConsumedAmount"]);
					$('#operation_detail_total_advanced_earned_amount').html(data["totalAdvancedEarnedAmount"]);
					
				});
			});
	});
	
	
});
