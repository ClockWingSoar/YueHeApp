
$(function ($) {
$(document).ready(function($) {

	var employeeList = Array.from(document.getElementById("employee").querySelectorAll("option"));
	var roleList = Array.from(document.getElementById("role").querySelectorAll("option"));
	//remove the first option if it's for Sale or Operation Update Item page to avoid duplicatation
	for(var i = 0; i < employeeList.length; i++)
	{
		console.log(employeeList[i]);
	}
	for(var i = 0; i < roleList.length; i++)
	{
		console.log(roleList[i]);
	}
	employeeList[0].remove();
	roleList[0].remove();
	var employeeSelector =  document.getElementById("employee");
	var roleSelector =  document.getElementById("role");
	employeeSelector.fstdropdown.rebind();
	roleSelector.fstdropdown.rebind();
	$('form[id="duty_edit_item"]').validate(dutyItemValidator);
	$('#duty_delete_btn').click(function(){
		var actionPage = "/duty/delete/"+$('#dutyId').val();
		// alert(actionPage);
		$('#duty_edit_item').attr('method', 'get');
		$('#duty_edit_item').attr('action', actionPage);
	});
  });
});