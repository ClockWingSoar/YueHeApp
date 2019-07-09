$(function ($) {
	$( "#createCardDate" ).datepicker();
	$('#cosmeticShop').change(getShopAllClients);
	$('#client').change(getClientAllSales);
	

	
});
