function getShopAllClientsList(removeFirstOptionFlag) {
    $.getJSON("http://"+window.location.host+"/getShopAllClientsForFiltering", {
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
        var clientSelector = document.getElementById("client");
        $('#client').removeAttr("data-disabled");
        if(removeFirstOptionFlag.data){
            var clientList = Array.from(document.getElementById("client").querySelectorAll("option"));
            for(var i = 0; i < clientList.length; i++)
            {
                console.log(clientList[i]);
            }
            clientList[0].remove();//remove the first option if it's for Sale or Operation New Item page
            //change the first option if it's for Sale or Operation New Item page
            var opt = document.createElement("option");
            opt.value = "";
            opt.text = "--输入客户名搜索客户--";
            opt.selected = true;
            clientSelector.add(opt,0);
        }
        clientSelector.fstdropdown.rebind();//To populate the generated div dropdown list after selecting cosmeticshop
        clientSelector.fstdropdown.activated();//To enable the dropdown list after selecting cosmeticshop
        $('#sale').html(htmlForSale);
    });
}

function getClientAllSalesList() {
	$.getJSON("http://"+window.location.host+"/getClientAllSalesForFiltering", {
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
        document.getElementById("sale").fstdropdown.rebind();
	});
}
function filterTable(table, filter, index) {
		// var index = null;
		// this.find("thead > tr:first > th").each(function(i) {
		// 	if ($.trim($(this).text()) == columnname) {
		// 		index = i;
		// 		return false;
		// 	}
		// });
		// if (index == null)
		// 	throw ("filter columnname: " + columnname + " not found");

		table.find("tbody:first > tr").each(function() {
            var row = $(this);
            var rowIndex =  $(this).index();
            if(rowIndex > 0){

                if (filter == "") {
                    row.show();
                }
                else {
                    var cellText = row.find("td:eq(" + index + ")").find('option:selected').text();
                    if (cellText == "") {
                        cellText = $(row.find(("td:eq(" + index + ")"))).text();
                    }
                    if (cellText.indexOf(filter) == -1) {
                        row.hide();
                    }
                    else {
                        row.show();
                    }
                }
            }
		});
		return this;
    }
    function isEmpty(value) {
        return typeof value == 'string' && !value.trim() || typeof value == 'undefined' || value === null;
      }