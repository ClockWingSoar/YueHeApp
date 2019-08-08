$(function ($) {
	var sortProperty = $('#sortProperty').val(); 
	var sortDirectionFlag = $('#sortDirectionFlag').val(); //Here it returns a String type of "true" or "false", it's different with boolean true or false
	var currentPage = $('#operationPageNumber').val(); 
	var pageSize =$('#operationPageSize').val();

	$(document).ready(function(){
		//show up/down arrows
		$("table#operationRecordList thead th").each(function(){
		var head = $(this);
		if(head.attr("data-sort-prop") != null){
			if(head.attr('data-sort-prop')==sortProperty){
				head.append((sortDirectionFlag=="true")?'▾':'▴');
			}
		}
		});

		//set click action, reload page on clicking with all query params
		$("table#operationRecordList thead th").click(function() {
		var headerSortPropName = $(this).attr("data-sort-prop");
		if(headerSortPropName != null){
		if(headerSortPropName==sortProperty){
			// window.location.href = window.location.pathname+
			// '/getOperationList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
			window.location.href = '/getOperationList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
			((sortDirectionFlag=="true")?'asc':'desc');
		}else{
				window.location.href = '/getOperationList?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+',asc';
		}
		}
		
		});
		$(".clickable-row").click(function() {
			
		window.location.href = "/operation/edit/"+$(this).find("td:eq(0)").text();
		console.log(window.location);
		});


		$('#operation_csv_export').click(
			function() {
				var tableName = "操作详情-"; 
				window.location.href = 'operationCsvDownload'+
				'?sort='+ sortProperty+','+ ((sortDirectionFlag=="false")?'asc':'desc')+'&tableName='+ tableName;
			});

		
		$('.yuehe-filter-Input').keyup( function(event) {
			var filter = event.target.value.toLowerCase();
			var index = $('.yuehe-filter-Input').index(this); 
			var operationTable = $('table#operationRecordList');
			filterTable(operationTable, filter, index);
		});

		$('.yuehe-filter-Input').keypress( function(event) {
			 var searchParameters = "";
			if(event.which == 13) {
				searchParameters = getCurrentFilterArray();
				window.location.href = '/operations/search'+'?page='+currentPage+'&size='+pageSize+
					'&sort='+ sortProperty+','+ ((sortDirectionFlag=="false")?'asc':'desc')+'&searchParameters='+ searchParameters;
			}
		});

		function getCurrentFilterArray(){
			var searchParameters = "";
			var id = $('#id').val();
			var saleId = $('#sale_id').val();
			var operationDate = $('#operation_date').val();
			var operatorName = $('#operator_name').val();
			var toolName = $('#tool_name').val();
			var description = $('#description').val();
			var regex = new RegExp('[:><!~$-]');
			if(!isEmpty(id)){
				if(regex.test(id)){

					searchParameters ="id"+id+",";
				}else{
					searchParameters ="id;"+id+",";//default to equality
				}
			}
			if(!isEmpty(saleId)){
				if(regex.test(saleId)){

					searchParameters +="sale.id"+saleId+",";
				}else{
					searchParameters +="sale.id;"+saleId+",";//default to equality
				}
			}
			if(!isEmpty(operationDate)){
				if(regex.test(operationDate)){

					searchParameters +="operationDate"+operationDate+",";
				}else{
					searchParameters +="operationDate;"+operationDate+",";//default to equality
				}
				
			}
			if(!isEmpty(operatorName)){
				if(regex.test(operatorName)){

					searchParameters +="employee.name"+operatorName+",";
				}else{
					searchParameters +="employee.name;"+operatorName+",";//default to equality
				}
			}
			if(!isEmpty(toolName)){
				if(regex.test(toolName)){

					searchParameters +="tool.name"+toolName+",";
				}else{
					searchParameters +="tool.name;"+toolName+",";//default to equality
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
			return searchParameters;
		}

	});
});