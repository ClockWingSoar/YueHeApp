
$(document).ready(function($) {
	var removeFirstOptionFlag = true;
	$( "#createCardDate" ).datepicker();
	$('#cosmeticShop').change(removeFirstOptionFlag,getShopAllClientsList);
	$('#client').change(getClientAllSalesList);

	$('form[id="sale_new_item"]').validate({
	  rules: {
		// cosmeticShop : {
		//   required: true,
		// //   minlength: 3
		// },
		// client: {
		//   required: {
		// 	depends: function(elem) {
		// 	  return $("#cosmeticShop") != null
		// 	}
		//   },
		// //   number: true,
		// //   min: 18
		// },
		// beautifySkinitem: {
		//   required: true,
		// //   email: true
		// },
		// createCardDate: {
		//   required: true,
		// //   number: true,
		// //   min: 0
		// },
		// itemNumber: {
		//   required: true,
		//   number: true,
		//   min: 1,
		//   max: 36
		// },
		// createCardTotalAmount: {
		//   required: true,
		//   number: true,
		//   min: 100
		// },
		// receivedAmount: {
		//   required: true,
		//   number: true,
		//   min: 0
		// },
		// receivedEarnedAmount: {
		// 	required: true,
		// 	number: true,
		// 	min: 0
		// },
		employeePremium: {
			number: true,
			min: 0
		},
		// shopPremium: {
		// 	required: true,
		// 	number: true,
		// 	min: 0
		// },
		sellerId: {
			required: true
		},
		description: {
			maxlength: 20
		}
	  },
	  messages : {
		// cosmeticShop: {
		//   required: "请选择美容院",
		// },
		// client: {
		//   required: "请选择客户姓名",
		// },
		// beautifySkinitem: {
		//   required: "请选择美肤套餐",
		// },
		// createCardDate: {
		//   required: "请选择开卡日期",
		// },
		// itemNumber: {
		//   required: "请选择开卡次数",
		//   min:"最小开卡次数为1",
		//   max:"最大开卡次数为36"
		// },
		// createCardTotalAmount: {
		//   required: "请输入开卡金额",
		//   number: "请输入一个数字字符",
		//   min: "最小金额不能低于100"
		// },
		// receivedAmount: {
		// 	required: "请输入美容院实际收到的金额",
		// 	number: "请输入一个数字字符"
		// },
		// receivedEarnedAmount: {
		//   required: "请输入悦和实际收到的回款",
		//   number: "请输入一个数字字符"
		// },
		employeePremium: {
		  min: "奖励应该至少为0",
		  number: "请输入一个数字字符"
		},
		// shopPremium: {
		//   required: "请输入该销售卡返还给店家的回扣",
		//   number: "请输入一个数字字符"
		// },
		sellerId: {
		  required: "请选择销售专家的名字",
		},
		description: {
		  maxlength: "备注不能超过20个字符"
		}
	  }, 
	//   submitHandler: function(form) {
	// 	form.submit();
	//   }
	});
  });