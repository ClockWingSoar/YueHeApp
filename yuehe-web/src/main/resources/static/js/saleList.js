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
				window.location.href = window.location.pathname+
				'?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
				((sortDirectionFlag=="true")?'asc':'desc');
			}else{
				 window.location.href = window.location.pathname+
				'?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+',asc';
			}
		  }
	      
		  });
		  $(".clickable-row").click(function() {
			  
			window.location = "sale/edit/"+$(this).find("td:eq(0)").text();
			console.log(window.location);
			});


			$('#sale_csv_export').click(
				function() {
					var tableName = "销售业绩-"; 
					window.location.href = 'saleCsvDownload'+
					'?sort='+ sortProperty+','+ ((sortDirectionFlag=="false")?'asc':'desc')+'&tableName='+ tableName;
				});


			// function performReset() {
			// // document.getElementById("inputName").value = "";
			// // document.getElementById("inputCity").value = "";
			// // document.getElementById("inputCountry").value = "";
			// filterTable(event, 0);
			// }
			
			// function filterTable() {
			// var filter = event.target.value.toUpperCase();
			// alert(filter)
			// // var rows = document.querySelector("table#saleRecordList tbody").rows;
			// $('table#saleRecordList tbody tr').each(function() {
			// 	var row = this.cells;alert(row)
			// 	$.each(this.cells, function(){
			// 		var col = this.textContent.toUpperCase();alert(col);
			// 		if(col.indexOf(filter) != -1){
			// 			this.closest('tr').hide();
			// 		}else{
			// 			this.closest('tr').show();
			// 		}
			// 	});
			
			// });
			// }
			
			$('.yuehe-filter-Input').keyup( function(event) {
				var filter = event.target.value.toLowerCase();
				var index = $('.yuehe-filter-Input').index(this); 
				var saleTable = $('table#saleRecordList');
				filterTable(saleTable, filter, index);
			});
		});
		
	
});
