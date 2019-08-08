
$(document).ready(function($) {
	var removeFirstOptionFlag = true;
	$( "#createCardDate" ).datepicker();
	$('#cosmeticShop').change(removeFirstOptionFlag,getShopAllClientsList);
	$('#client').change(removeFirstOptionFlag,getClientAllSalesList);

	$('form[id="sale_new_item"]').validate(saleItemValidtor);
  });