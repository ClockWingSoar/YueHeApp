$(document).ready(function($) {
	var filterNeedComesFrom = $('#filterNeedComesFrom').val();
	if(filterNeedComesFrom == "clientQuestionare"){

		$('#client_filter_btn').html("新建客户问卷");
		$('#client_filter_btn').addClass("btn-primary");
	}
	jQuery(function() { 
		$('#cosmeticShop').change(getShopAllClientsList);
		$('#client').change(checkClientHasQuestionare);
		});
		
	$('#client_filter_btn').click(
		function() {
			filterNeedComesFrom = $('#filterNeedComesFrom').val();
			var clientId = $('#client').val();
			if(filterNeedComesFrom == "clientBasic"){
				getClientProfile(clientId);
			}else if(filterNeedComesFrom == "clientQuestionare"){
				getClientQuestionareNewItem(clientId);
			}else{
				window.location.href = '/clientQuestionare/edit/'+clientId;
			}
		});

		function checkClientHasQuestionare(){
			$.getJSON("http://"+window.location.host+"/checkClientHasQuestionare", {
				clientId:$('#client').val(),
				ajax : 'true'
			}, function(data) {
				if(data.id != null && data.id !=undefined && data.id !=0){
					$('#client_filter_btn').html("编辑客户问卷");
					$('#filterNeedComesFrom').val("clientQuestionareEditItem");
				}else{
					
					$('#client_filter_btn').html("新建客户问卷");
				}
					
			});
		}
});
	

	
