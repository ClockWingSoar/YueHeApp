
$(function ($) {
$(document).ready(function($) {
	$( "#buyDate" ).datepicker();
	$('form[id="tool_new_item"]').validate(toolItemValidator);
  });
});