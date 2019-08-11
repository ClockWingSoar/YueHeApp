
$(function ($) {
$(document).ready(function($) {
	$('form[id="role_edit_item"]').validate(roleItemValidator);
	$('#role_delete_btn').click(function(){
		var actionPage = "/role/delete/"+$('#roleId').val();
		// alert(actionPage);
		$('#role_edit_item').attr('method', 'get');
		$('#role_edit_item').attr('action', actionPage);
	});
  });
});