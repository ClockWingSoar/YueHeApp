$(document).ready(function($) {
	jQuery(function() { 
		$('#cosmeticShop').change(getShopAllClientsList);
		});
		var filterNeedComesFrom = $('#filterNeedComesFrom').val();
		if(filterNeedComesFrom == "clientQuestionare"){

			$('#client_filter_btn').html("新建客户问卷");
			$('#client_filter_btn').addClass("btn-primary");
		}
	$('#client_filter_btn').click(
		function() {
			var clientId = $('#client').val();
			if(filterNeedComesFrom == "clientBasic"){
				getClientProfile(clientId);
			}else if(filterNeedComesFrom == "clientQuestionare"){
				// createClientQuetionare(clientId)
			}
		});
});
	

	
