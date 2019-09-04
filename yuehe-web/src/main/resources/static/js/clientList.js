$(function ($) {
	var sortProperty = $('#sortProperty').val(); 
	var sortDirectionFlag = $('#sortDirectionFlag').val(); //Here it returns a String type of "true" or "false", it's different with boolean true or false
	var currentPage = parseInt($('#clientPageNumber').val()); 
	var pageSize =$('#clientPageSize').val();
	var clientTotalPages = parseInt($('#clientTotalPages').val());
	var searchParameters =$('#searchParameters').val();	
	var sortParam = $('#sortParam').val(); 



	$(document).ready(function(){
		var firstPageClick = true;
		$('.sync-pagination').twbsPagination({
			totalPages: clientTotalPages,
			visiblePages:10,
			first:'首页',
			prev:'上一页',
			next:'下一页',
			last:'末页',
			startPage:++currentPage,
			onPageClick: function (evt, page) {
				if(firstPageClick) {
					firstPageClick = false;
					return;
				}else{
					if(isEmpty(searchParameters)){
						window.location.href = '/getClientList?page='+page+'&size='+pageSize+'&sort='+ sortParam;
					}else{
						window.location.href = '/clients/search?page='+page+'&size='+pageSize+'&sort='+ sortParam+'&searchParameters='+searchParameters;
					}

				}
			}
		});
		//show up/down arrows
		$("table#clientRecordList thead th").each(function(){
		var head = $(this);
		if(head.attr("data-sort-prop") != null){
			if(head.attr('data-sort-prop')==sortProperty){
				head.append((sortDirectionFlag=="true")?'▾':'▴');
			}
		}
		});

		//set click action, reload page on clicking with all query params
		$("table#clientRecordList thead th").click(function() {
		var headerSortPropName = $(this).attr("data-sort-prop");
		if(headerSortPropName != null){
		if(headerSortPropName==sortProperty){
			// window.location.href = window.location.pathname+
			// '/getClientList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
			window.location.href = '/getClientList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
			((sortDirectionFlag=="true")?'asc':'desc');
		}else{
				window.location.href = '/getClientList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+',asc';
		}
		}
		
		});
		$(".clickable-row").click(function() {
			
		window.location.href = "/client/edit/"+$(this).find("td:eq(0)").text();
		console.log(window.location);
		});


		$('#client_csv_export').click(
			function() {
				var tableName = "客户列表-"; 
				// window.location.href = 'clientCsvDownload'+
				window.location.href = "http://"+window.location.host+'/clientCsvDownload'+
				'?sort='+ sortProperty+','+ ((sortDirectionFlag=="false")?'asc':'desc')+'&tableName='+ tableName;
			});

		
		$('.yuehe-filter-Input').keyup( function(event) {
			var filter = event.target.value.toLowerCase();
			var index = $('.yuehe-filter-Input').index(this); 
			var clientTable = $('table#clientRecordList');
			filterTable(clientTable, filter, index);
		});

		$('.yuehe-filter-Input').keypress( function(event) {
			 var searchParameters = "";
			if(event.which == 13) {
				searchParameters = getCurrentFilterArray();
				window.location.href = '/clients/search'+'?page=0&size='+pageSize+
					'&sort='+ sortProperty+','+ ((sortDirectionFlag=="false")?'asc':'desc')+'&searchParameters='+ searchParameters;
			}
		});

		function getCurrentFilterArray(){
			var searchParameters = "";
			var id = $('#id').val();
			var name = $('#name').val();
			var cosmeticShopName = $('#cosmeticShop_name').val();
			var age = $('#age').val();
			var gender = $('#gender').val();
			var symptom = $('#symptom').val();
			var regex = new RegExp('[:><!~$_]');
			if(!isEmpty(id)){
				if(regex.test(id)){

					searchParameters ="id"+id+",";
				}else{
					searchParameters ="id;"+id+",";//default to equality
				}
			}
			if(!isEmpty(name)){
				if(regex.test(name)){

					searchParameters +="name"+name+",";
				}else{
					searchParameters +="name;"+name+",";//default to equality
				}
			}
			if(!isEmpty(cosmeticShopName)){
				if(regex.test(cosmeticShopName)){

					searchParameters +="cosmeticShop.name"+cosmeticShopName+",";
				}else{
					searchParameters +="cosmeticShop.name;"+cosmeticShopName+",";//default to equality
				}
				
			}
			if(!isEmpty(age)){
				if(regex.test(age)){

					searchParameters +="age"+age+",";
				}else{
					searchParameters +="age:"+age+",";//default to equality
				}
				
			}
			if(!isEmpty(gender)){
				if (gender.includes("男")){
					gender = "m";
				}
				if (gender.includes("女")){
					gender = "f";
				}
				if(regex.test(gender)){

					searchParameters +="gender"+gender+",";
				}else{
					searchParameters +="gender:"+gender+",";//default to equality
				}
			}
			if(!isEmpty(symptom)){
				if(regex.test(symptom)){

					searchParameters +="symptom"+symptom+",";
				}else{
					searchParameters +="symptom;"+symptom+",";//default to equality
				}
			}
			return searchParameters;
		}

	});
});
