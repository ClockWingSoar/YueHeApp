$(function ($) {
	$( "#createCardDate" ).datepicker();
	$('#cosmeticShop').change(getShopAllClientsList);
	$('#client').change(getClientAllSalesList);
	

	
});
