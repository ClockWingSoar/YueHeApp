$(function ($) {
	var sortProperty = $('#sortProperty').val(); 
	var sortDirectionFlag = $('#sortDirectionFlag').val(); //Here it returns a String type of "true" or "false", it's different with boolean true or false
	var currentPage = $('#salePageNumber').val(); 
	var pageSize =$('#salePageSize').val();

	$(document).ready(function(){
		//show up/down arrows
		$("table#saleRecordList thead th").each(function(){
		var head = $(this);
		if(head.attr("data-sort-prop") != null){
			if(head.attr('data-sort-prop')==sortProperty){
				head.append((sortDirectionFlag=="true")?'▾':'▴');
			}
		}
		});

		//set click action, reload page on clicking with all query params
		$("table#saleRecordList thead th").click(function() {
		var headerSortPropName = $(this).attr("data-sort-prop");
		if(headerSortPropName != null){
		if(headerSortPropName==sortProperty){
			// window.location.href = window.location.pathname+
			// '/getSaleList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
			window.location.href = '/getSaleList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
			((sortDirectionFlag=="true")?'asc':'desc');
		}else{
				window.location.href = '/getSaleList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+',asc';
		}
		}
		
		});
		$(".clickable-row").click(function() {
			
		window.location.href = "/sale/edit/"+$(this).find("td:eq(0)").text();
		console.log(window.location);
		});


		$('#sale_csv_export').click(
			function() {
				var tableName = "销售业绩-"; 
				window.location.href = 'saleCsvDownload'+
				'?sort='+ sortProperty+','+ ((sortDirectionFlag=="false")?'asc':'desc')+'&tableName='+ tableName;
			});

		
		$('.yuehe-filter-Input').keyup( function(event) {
			var filter = event.target.value.toLowerCase();
			var index = $('.yuehe-filter-Input').index(this); 
			var saleTable = $('table#saleRecordList');
			filterTable(saleTable, filter, index);
		});

		$('.yuehe-filter-Input').keypress( function(event) {
			 var searchParameters = "";
			if(event.which == 13) {
				searchParameters = getCurrentFilterArray();
				window.location.href = '/sales/search'+'?page='+currentPage+'&size='+pageSize+
					'&sort='+ sortProperty+','+ ((sortDirectionFlag=="false")?'asc':'desc')+'&searchParameters='+ searchParameters;
			}
		});

		function getCurrentFilterArray(){
			var searchParameters = "";
			var saleId = $('#sale_id').val();
			var sellerName = $('#seller_name').val();
			var cosmeticShopName = $('#cosmeticshop_name').val();
			var clientName = $('#client_name').val();
			var beautifySkinItemName = $('#beautifyskinitem_name').val();
			var createCardDate = $('#create_card_date').val();
			var itemNumber = $('#item_number').val();
			var createCardTotalAmount = $('#create_card_total_amount').val();
			var receivedAmount = $('#received_amount').val();
			var discount = $('#discount').val();
			var unpaidAmount = $('#unpaid_amount').val();
			var earnedAmount = $('#earned_amount').val();
			var receivedEarnedAmount = $('#received_earned_amount').val();
			var unpaidEarnedAmount = $('#unpaid_earned_amount').val();
			var employeePremium = $('#employee_premium').val();
			var shopPremium = $('#shop_premium').val();
			var description = $('#description').val();
			var regex = new RegExp('[:><!~$-]');
			if(!isEmpty(saleId)){
				if(regex.test(saleId)){

					searchParameters ="id"+saleId+",";
				}else{
					searchParameters ="id;"+saleId+",";//default to equality
				}
			}
			if(!isEmpty(sellerName)){
				if(regex.test(sellerName)){

					searchParameters +="employee.name"+sellerName+",";
				}else{
					searchParameters +="employee.name;"+sellerName+",";//default to equality
				}
			}
			if(!isEmpty(cosmeticShopName)){
				if(regex.test(cosmeticShopName)){

					searchParameters +="client.cosmeticShop.name"+cosmeticShopName+",";
				}else{
					searchParameters +="client.cosmeticShop.name;"+cosmeticShopName+",";//default to equality
				}
				
			}
			if(!isEmpty(clientName)){
				if(regex.test(clientName)){

					searchParameters +="client.name"+clientName+",";
				}else{
					searchParameters +="client.name;"+clientName+",";//default to equality
				}
			}
			if(!isEmpty(beautifySkinItemName)){
				if(regex.test(beautifySkinItemName)){

					searchParameters +="beautifySkinItem.name"+beautifySkinItemName+",";
				}else{
					searchParameters +="beautifySkinItem.name;"+beautifySkinItemName+",";//default to equality
				}
			}
			if(!isEmpty(createCardDate)){
				if(regex.test(createCardDate)){

					searchParameters +="createCardDate"+createCardDate+",";
				}else{
					searchParameters +="createCardDate;"+createCardDate+",";//default to equality
				}
			}
			if(!isEmpty(itemNumber)){
				if(regex.test(itemNumber)){

					searchParameters +="itemNumber"+itemNumber+",";
				}else{
					searchParameters +="itemNumber;"+itemNumber+",";//default to equality
				}
			}
			if(!isEmpty(createCardTotalAmount)){
				if(regex.test(createCardTotalAmount)){

					searchParameters +="createCardTotalAmount"+createCardTotalAmount+",";
				}else{
					searchParameters +="createCardTotalAmount;"+createCardTotalAmount+",";//default to equality
				}
			}
			if(!isEmpty(receivedAmount)){
				if(regex.test(receivedAmount)){

					searchParameters +="receivedAmount"+receivedAmount+",";
				}else{
					searchParameters +="receivedAmount;"+receivedAmount+",";//default to equality
				}
			}
			if(!isEmpty(receivedEarnedAmount)){
				if(regex.test(receivedEarnedAmount)){

					searchParameters +="receivedEarnedAmount"+receivedEarnedAmount+",";
				}else{
					searchParameters +="receivedEarnedAmount;"+receivedEarnedAmount+",";//default to equality
				}
			}
			if(!isEmpty(employeePremium)){
				if(regex.test(employeePremium)){

					searchParameters +="employeePremium"+employeePremium+",";
				}else{
					searchParameters +="employeePremium;"+saleemployeePremiumId+",";//default to equality
				}
			}
			if(!isEmpty(shopPremium)){
				if(regex.test(shopPremium)){

					searchParameters +="shopPremium"+shopPremium+",";
				}else{
					searchParameters +="shopPremium;"+shopPremium+",";//default to equality
				}
			}
			if(!isEmpty(description)){
				if(regex.test(description)){

					searchParameters +="description"+description+",";
				}else{
					searchParameters +="description;"+description+",";//default to equality
				}
				alert(searchParameters);
			}
			if(!isEmpty(discount)){
				if(regex.test(discount)){

					searchParameters +="discount"+discount+",";
				}else{
					searchParameters +="discount;"+discount+",";//default to equality
				}
			}
			if(!isEmpty(unpaidAmount)){
				if(regex.test(unpaidAmount)){

					searchParameters +="unpaidAmount"+unpaidAmount+",";
				}else{
					searchParameters +="unpaidAmount;"+unpaidAmount+",";//default to equality
				}
			}
			if(!isEmpty(earnedAmount)){
				if(regex.test(earnedAmount)){

					searchParameters +="earnedAmount"+earnedAmount+",";
				}else{
					searchParameters +="earnedAmount;"+earnedAmount+",";//default to equality
				}
			}
			if(!isEmpty(unpaidEarnedAmount)){
				if(regex.test(unpaidEarnedAmount)){

					searchParameters +="unpaidEarnedAmount"+unpaidEarnedAmount+",";
				}else{
					searchParameters +="unpaidEarnedAmount;"+unpaidEarnedAmount+",";//default to equality
				}
			}
			// alert(searchParameters);
			return searchParameters;
		}

	});
});
