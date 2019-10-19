
$(function ($) {
	$(document).ready(function($) {
		$( "#adjustDate" ).datepicker();
		$('form[id="shopRefundRule_edit_item"]').validate(shopRefundRuleValidator);
		$('#shopRefundRule_delete_btn').click(function(){
			var actionPage = "/shopRefundRule/delete/"+$('#id').val();
			$('#shopRefundRule_edit_item').attr('method', 'get');
			$('#shopRefundRule_edit_item').attr('action', actionPage);
		});
	  });
	});