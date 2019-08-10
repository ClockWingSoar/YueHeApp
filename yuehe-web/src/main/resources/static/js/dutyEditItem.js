
$(function ($) {
$(document).ready(function($) {
	$('form[id="duty_edit_item"]').validate(dutyItemValidator);
	$('#duty_delete_btn').click(function(){
		var actionPage = "/duty/delete/"+$('#dutyId').val();
		// alert(actionPage);
		$('#duty_edit_item').attr('method', 'get');
		$('#duty_edit_item').attr('action', actionPage);
	});
  });
});