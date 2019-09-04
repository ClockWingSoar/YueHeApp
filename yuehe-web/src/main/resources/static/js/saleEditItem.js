$(function ($) {
	var removeFirstOptionFlag = true;
	$( "#createCardDate" ).datepicker();
	$('#cosmeticShop').change(removeFirstOptionFlag,getShopAllClientsList);
	$('#client').change(removeFirstOptionFlag,getClientAllSalesList);
	$(document).ready(function(){
		var shopList = Array.from(document.getElementById("cosmeticShop").querySelectorAll("option"));
		var beautifySkinItemList = Array.from(document.getElementById("beautifySkinItem").querySelectorAll("option"));
		var sellerList = Array.from(document.getElementById("seller").querySelectorAll("option"));
		for(var i = 0; i < shopList.length; i++)
		{
			console.log(shopList[i]);
		}
		//remove the first option if it's for Sale or Operation Update Item page to avoid duplicatation
		shopList[0].remove();
		beautifySkinItemList[0].remove();
		sellerList[0].remove();
		var createCardAmount = $('#createCaredAmount').val();
		var receivedAmount = $('#receivedAmount').val();
		$('#receivedAmount').change(function(){
			var receivedAmount = $('#receivedAmount').val();
			var shopDiscount = $('#shopDiscount').val();
			$('#receivedEarnedAmount').val(receivedAmount * shopDiscount);
		});
		if(createCardAmount != receivedAmount){
			$('#sale_add_amount_btn').removeClass("hidden");
		}else{

			$('#sale_add_amount_btn').addClass("hidden");
		}
		$('#sale_delete_btn').click(function(){
			var actionPage = "/sale/delete/"+$('#saleId').val();
			$('#sale_edit_item').attr('method', 'get');
			$('#sale_edit_item').attr('action', actionPage);
		});
		$('#sale_copy_btn').click(function(){
			var actionPage = "/sale/copy/"+$('#saleId').val();
			$('#sale_edit_item').attr('method', 'post');
			$('#sale_edit_item').attr('action', actionPage);
		});
		$('#sale_card_amount_adjust_btn').click(function(){
			var actionPage = "/getSaleCardAmountAdjustNewItem/"+$('#saleId').val();
			$('#sale_edit_item').attr('method', 'get');
			$('#sale_edit_item').attr('action', actionPage);
		});
		
		$('form[id="sale_edit_item"]').validate(saleItemValidator);
		

	});

	
});
