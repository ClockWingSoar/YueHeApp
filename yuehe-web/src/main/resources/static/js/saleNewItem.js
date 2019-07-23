$(function ($) {
	var removeFirstOptionFlag = true;
	$( "#createCardDate" ).datepicker();
	$('#cosmeticShop').change(removeFirstOptionFlag,getShopAllClientsList);
	$('#client').change(getClientAllSalesList);
	

	
});
