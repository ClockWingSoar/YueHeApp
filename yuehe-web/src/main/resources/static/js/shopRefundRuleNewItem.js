
$(function ($) {
	$(document).ready(function($) {
		$( "#adjustDate" ).datepicker();
		$('form[id="shopRefundRule_new_item"]').validate(shopRefundRuleValidator);
	  });
	});