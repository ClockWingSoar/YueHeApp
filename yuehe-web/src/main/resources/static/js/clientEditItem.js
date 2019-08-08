$(function ($) {
	$(document).ready(function(){
		var shopList = Array.from(document.getElementById("cosmeticShop").querySelectorAll("option"));
		//remove the first option if it's for Sale or Client Update Item page to avoid duplicatation
		shopList[0].remove();

		$('#client_delete_btn').click(function(){
			var actionPage = "/client/delete/"+$('#clientId').val();
			// alert(actionPage);
			$('#client_edit_item').attr('method', 'get');
			$('#client_edit_item').attr('action', actionPage);
		});
		
		$('form[id="client_edit_item"]').validate(clientItemValidtor);


	});

	
});
