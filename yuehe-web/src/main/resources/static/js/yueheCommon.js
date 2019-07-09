function getShopAllClients() {
    $.getJSON("http://localhost:9090/getShopAllClientsForFiltering", {
        cosmeticShopId : $(this).val(),
        ajax : 'true'
    }, function(data) {
        var html = '<option value="all">--所有客户--</option>';
        var len = data.length; 
        for ( var i = 0; i < len; i++) {
            html += '<option value="' + data[i].id + '">'
                    + data[i].name + '</option>';
        }
        html += '</option>';
        htmlForSale = '<option value="all">--所有美肤卡--</option>';
        $('#client').html(html);
        $('#sale').html(htmlForSale);
    });
}

function getClientAllSales() {
	$.getJSON("http://localhost:9090/getClientAllSalesForFiltering", {
		clientId : $(this).val(),
		ajax : 'true'
	}, function(data) {
		var html = '<option value="all">--所有美肤卡--</option>';
		var len = data.length;
		for ( var i = 0; i < len; i++) {
			//alert(data[i].saleId);
			html += '<option value="' + data[i].saleId + '">'
			+ data[i].beautifySkinItemName + '-' + data[i].createCardDate+ '</option>';
		}
		html += '</option>';
		$('#sale').html(html);
	});
}
