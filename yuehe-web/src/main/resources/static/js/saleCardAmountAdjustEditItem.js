
$(function ($) {
	$(document).ready(function($) {
		$('form[id="saleCardAmountAdjust_edit_item"]').validate(saleCardAmountAdjustValidator);
		$('#saleCardAmountAdjust_delete_btn').click(function(){
			var actionPage = "/saleCardAmountAdjust/delete/"+$('#id').val();
			$('#saleCardAmountAdjust_edit_item').attr('method', 'get');
			$('#saleCardAmountAdjust_edit_item').attr('action', actionPage);
		});
	  });
	});