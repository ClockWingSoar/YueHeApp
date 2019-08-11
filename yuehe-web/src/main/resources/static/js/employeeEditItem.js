
$(function ($) {
$(document).ready(function($) {
	$( "#birthday" ).datepicker();
	$('form[id="employee_edit_item"]').validate(employeeItemValidator);
	$('#employee_delete_btn').click(function(){
		var actionPage = "/employee/delete/"+$('#employeeId').val();
		// alert(actionPage);
		$('#employee_edit_item').attr('method', 'get');
		$('#employee_edit_item').attr('action', actionPage);
	});
  });
});