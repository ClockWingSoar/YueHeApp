function getClientQuestionareNewItem(clientId){
	$.getJSON("http://"+window.location.host+"/getClientQuestionareNewItem", {
		clientId:clientId,
		ajax : 'true'
	}, function(data) {
		$('#clientName').html(data.name);
		var genderInDB = data.gender;
		var gender ="";
		if(genderInDB =="f")
			gender = "女性";
		else
			gender = "男性";
		$('#clientGender').html(gender);
		$('#clientAge').html(data.age);
		$("#client_questionare").removeClass('hidden');
		
	});
}