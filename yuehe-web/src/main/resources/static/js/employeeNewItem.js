
$(function ($) {
$(document).ready(function($) {
	$( "#birthday" ).datepicker();
 	$('form[id="employee_new_item"]').validate(employeeItemValidator);
  });
});