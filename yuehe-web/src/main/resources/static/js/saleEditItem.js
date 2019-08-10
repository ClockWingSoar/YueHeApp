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

		$('#sale_delete_btn').click(function(){
			var actionPage = "/sale/delete/"+$('#saleId').val();
			$('#sale_edit_item').attr('method', 'get');
			$('#sale_edit_item').attr('action', actionPage);
		});
		$('form[id="sale_edit_item"]').validate(saleItemValidator);


	});

	
});
