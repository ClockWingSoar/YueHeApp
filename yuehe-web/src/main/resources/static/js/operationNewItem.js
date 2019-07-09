$(function ($) {
	$( "#operationDate" ).datepicker();
	$('#cosmeticShop').change(getShopAllClients);
	$('#client').change(getClientAllSales);

	
});
