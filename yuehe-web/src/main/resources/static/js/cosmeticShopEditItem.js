
$(function ($) {
$(document).ready(function($) {
	$('form[id="cosmeticShop_edit_item"]').validate(cosmeticShopItemValidator);
  });
  $('#shop_refund_rule_btn').click(function(){
    var actionPage = "/getShopRefundRuleNewItem/"+$('#cosmeticShopId').val();
    $('#cosmeticShop_edit_item').attr('method', 'get');
    $('#cosmeticShop_edit_item').attr('action', actionPage);
  });
});