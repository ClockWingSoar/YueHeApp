$(function ($) {
	jQuery(function() { 
		$( "#startDate" ).datepicker();
		$( "#endDate" ).datepicker();
		$('#cosmeticShop').change(getShopAllClientsList);
		$('#client').change(getClientAllSalesList);
		});
	$('#yuehe_filter_lookup').click(
		function() {
			var shopId = $('#cosmeticShop').val();
			var clientId = $('#client').val();
			var saleId = $('#sale').val();
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var filterNeedComesFrom = $('#filterNeedComesFrom').val();
			if(filterNeedComesFrom == "sale"){

				if(shopId == "all"){
					
					getYueHeAllShopsSales(startDate,endDate);
				
				}else if(clientId == "all"){
					getShopAllClientsSales(shopId,startDate,endDate);
				}else if(saleId =="all"){
					getClientAllSales(clientId,startDate,endDate);
				}else{
					getSalePerformanceDetail(saleId);
				}
			}else if(filterNeedComesFrom == "operation"){
				if(shopId == "all"){
					getYueHeAllShopsOperations(startDate,endDate);
				}else if(clientId == "all"){
					getShopAllClientsOperations(shopId,startDate,endDate);
				}else if(saleId =="all"){
					getClientAllSalesOperations(clientId,startDate,endDate);
				}else{
					 getSaleAllOperations(saleId,startDate,endDate);
				}
			}
		});
});
	

	
