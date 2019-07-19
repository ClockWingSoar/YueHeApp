$(function ($) {
	    var sortProperty = $('#sortProperty').val(); 
	    var sortDirection = $('#sortDirection').val(); //Here it returns a String type of "true" or "false", it's different with boolean true or false
	    var currentPage = $('#salePageNumber').val(); 
	    var pageSize =$('#salePageSize').val();

	    $(document).ready(function(){
	     //show up/down arrows
	     $("table#saleRecordList thead th").each(function(){
	        var head = $(this);
	        if(head.attr('data-sort-prop')==sortProperty){
	            head.append((sortDirection=="true")?'▾':'▴');
	        }
	        });

	     //set click action, reload page on clicking with all query params
	     $("table#saleRecordList thead th").click(function() {
		  var headerSortPropName = $(this).attr("data-sort-prop");
		  if(headerSortPropName != null){
			if(headerSortPropName==sortProperty){
				window.location.href = window.location.pathname+
				'?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+','+
				((sortDirection=="true")?'asc':'desc');
			}else{
				 window.location.href = window.location.pathname+
				'?page='+currentPage+'&size='+pageSize+'&sort='+ headerSortPropName+',asc';
			}
		  }
	      
	      });
	    });
	
});
