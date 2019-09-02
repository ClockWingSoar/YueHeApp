
$(document).ready(function($) {
	var removeFirstOptionFlag = true;
	$( "#createCardDate" ).datepicker();
	$('#cosmeticShop').change(removeFirstOptionFlag,getShopAllClientsList);
	$('#client').change(removeFirstOptionFlag,getClientAllSalesList);
	$('#receivedAmount').change(function(){
		var receivedAmount = $('#receivedAmount').val();
		var shopDiscount = $('#shopDiscount').val();
		$('#receivedEarnedAmount').val(receivedAmount * shopDiscount);
	});

	$('form[id="sale_new_item"]').validate(saleItemValidator);
  });