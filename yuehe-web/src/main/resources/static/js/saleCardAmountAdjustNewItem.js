
$(function ($) {
	$(document).ready(function($) {
		$( "#adjustDate" ).datepicker();
		$('form[id="saleCardAmountAdjust_new_item"]').validate(saleCardAmountAdjustValidator);
	  });
	});