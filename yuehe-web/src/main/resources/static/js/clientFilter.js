$(document).ready(function($) {
	var filterNeedComesFrom = $('#filterNeedComesFrom').val();
	var id;
	if(filterNeedComesFrom == "clientQuestionare"){

		$('#client_filter_btn').html("新建客户问卷");
		$('#client_filter_btn').addClass("btn-primary");
	}
	jQuery(function() { 
		$('#cosmeticShop').change(getShopAllClientsList);
		if(filterNeedComesFrom != "clientBasic")
			$('#client').change(checkClientHasQuestionare);//don't add() after checkClientHasQuestionare since it doesn't accept any parameter
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
				window.location.href = '/clientQuestionare/edit/'+id;
			}
		});

		function checkClientHasQuestionare(){
			$.getJSON("http://"+window.location.host+"/checkClientHasQuestionare", {
				clientId:$('#client').val(),
				ajax : 'true'
			}, function(data) {
				if(data.id != null && data.id !=undefined && data.id !=0){
					id = data.id;
					$('#client_filter_btn').html("编辑客户问卷");
					$('#filterNeedComesFrom').val("clientQuestionareEditItem");
				}
				// else{
				// 	window.location.href = '/getClientQuestionare?clientId='+$('#client').val();
				// 	//  $('#client_filter_btn').html("新建客户问卷");
				// 	//  $('#filterNeedComesFrom').val("clientQuestionare");
				// 	//  $('#clientId').val($('#client').val());
				// }
					
			});
		}
});
	

	
