$(document).ready(function ($) {
	var removeFirstOptionFlag = true;
	$( "#operationDate" ).datepicker();
	$('#cosmeticShop').change(removeFirstOptionFlag,getShopAllClientsList);
	$('#client').change(removeFirstOptionFlag,getClientAllSalesList);
	$('form[id="operation_new_item"]').validate(operationItemValidator);
	
});
