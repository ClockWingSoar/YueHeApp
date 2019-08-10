
$(function ($) {
$(document).ready(function($) {
	$( "#buyDate" ).datepicker();
	$('form[id="tool_edit_item"]').validate(toolItemValidtor);
	$('#tool_delete_btn').click(function(){
		var actionPage = "/tool/delete/"+$('#toolId').val();
		// alert(actionPage);
		$('#tool_edit_item').attr('method', 'get');
		$('#tool_edit_item').attr('action', actionPage);
	});
  });
});