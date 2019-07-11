$(function ($) {
	$( "#operationDate" ).datepicker();
	$('#cosmeticShop').change(getShopAllClientsList);
	$('#client').change(getClientAllSalesList);

	
});
