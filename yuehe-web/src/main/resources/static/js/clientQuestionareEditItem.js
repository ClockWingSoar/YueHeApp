
$(function ($) {
$(document).ready(function($) {
  $('#client_questionare_delete_btn').click(function(){
    var actionPage = "/clientQuestionare/delete/"+$('#clientId').val();
    // alert(actionPage);
    $('#client_questionare_edit_item').attr('method', 'get');
    $('#client_questionare_edit_item').attr('action', actionPage);
  });
  
	// $('form[id="client_questionare_edit_item"]').validate(clientQuestionareItemValidator);
  });
});