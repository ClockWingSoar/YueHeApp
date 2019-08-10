$(function ($) {
	var removeFirstOptionFlag = true;//to indicate whether to delete all items option if it's for sale or operation edit page since for a single item, it won't be proper to have a all items option
	$("#operationDate" ).datepicker();
	$('#cosmeticShop').change(removeFirstOptionFlag,getShopAllClientsList);
	$('#client').change(removeFirstOptionFlag,getClientAllSalesList);
	$(document).ready(function(){
		var shopList = Array.from(document.getElementById("cosmeticShop").querySelectorAll("option"));
		var operatorList = Array.from(document.getElementById("operator").querySelectorAll("option"));
		for(var i = 0; i < shopList.length; i++)
		{
			console.log(shopList[i]);
		}
		//remove the first option if it's for Sale or Operation Update Item page to avoid duplicatation
		shopList[0].remove();
		operatorList[0].remove();

		$('#operation_delete_btn').click(function(){
			var actionPage = "/operation/delete/"+$('#operationId').val();
			// alert(actionPage);
			$('#operation_edit_item').attr('method', 'get');
			$('#operation_edit_item').attr('action', actionPage);
		});
		
		$('form[id="operation_edit_item"]').validate(operationItemValidator);


	});

	
});
