$(function() {
	search();

	$("#add_agency").click(function() {
		checkSn();
	});
	$("#userPlatformId").blur(function(){
		if($("#userPlatformId").val() == ""){
			return;
		}else{
			var header = $("meta[name='_csrf_header']").attr("content");
			var token = $("meta[name='_csrf']").attr("content");
			var data = {
				"data" : {
					"userPlatformId" : $("#userPlatformId").val()
					
				}
			};
			$.ajax({
				url : "/admin/recharge/admin/check",
				type : 'post',
				contentType : 'application/json',
				data : JSON.stringify(data),
				dataType : 'json',
				timeout : 1000,
				beforeSend : function(xhr) {
					xhr.setRequestHeader(header, token);
				},
				success : function(data, status) {

					if (data.status = 10200) {
						if (data.data) {
							
						     $("#wxNickname").val(data.data.wxNickname)
						}

					}else{
						alert(data.msg);
					}

				},
				fail : function(err, status) {
					alert("系统错误，请稍后");
				}
			});	
		}
		
		
	});

});
function checkSn() {
	if ($("#userPlatformId").val() == "") {
		$("#userPlatformId").focus();
		return;
	}
	if ($("#wxNickname").val() == "") {
		$("#wxNickname").focus();
		return;
	}
	if ($("#rechargeAmount").val() == "") {
		$("#rechargeAmount").focus();
		return;
	}
	if ($("#rechargeScore").val() == "") {
		$("#rechargeScore").focus();
		return;
	}
	
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	var data = {
		"data" : {
			"userPlatformId" : $("#userPlatformId").val()
			
		}
	};
	$.ajax({
		url : "/admin/recharge/admin/check",
		type : 'post',
		contentType : 'application/json',
		data : JSON.stringify(data),
		dataType : 'json',
		timeout : 1000,
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(data, status) {

			if (data.status = 10200) {
				if (data.data) {
					addAgency()
				}

			}

		},
		fail : function(err, status) {
			alert("系统错误，请稍后");
		}
	});

}

function addAgency() {

	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	var data = {
			"data" : {
				"userPlatformId" : $("#userPlatformId").val(),
				"rechargeAmount" : $("#rechargeAmount").val(),
				"rechargeScore" : $("#rechargeScore").val()
			}};
	$.ajax({
		url : "/admin/recharge/admin/manager",
		type : 'post',
		contentType : 'application/json',
		data : JSON.stringify(data),
		dataType : 'json',
		timeout : 1000,
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {

			if (data.status = 10200) {
				alert("充值成功");
				$("#add_slide_div").hide();
				$(".deleted_tipsBox,.success_tipsBox,.add_slide_img,.add_user")
						.fadeOut("fast");
				$("#mask,#top_mask").fadeOut("fast");
				search();
			}else{
				alert(data.msg)
			}

		}
	});

}

function search() {

	$('#pager').sjAjaxPager({
		url : "/admin/recharge/admin/list",
		pageSize : 10,
		searchParam : {
			/*
			 * 如果有其他的查询条件，直接在这里传入即可
			 */
			userPlatformId : $("#userPlatformId").val(),
			wxNickname : $("#wxNickname").val()
			
		},
		beforeSend : function() {

		},
		success : function(data) {

			creatSlideList(data);
		},
		complete : function() {
		}
	});

}
function creatSlideList(data) {
	var html = "";

	if (data.items.length > 0) {
		var list = data.items;
		for (var i = 0; i < list.length; i++) {
			html += "<tr><td>"
			html += (i + 1)
			html += "</td><td>" + list[i].player.userPlatformId + "</td>"
			html += "<td>" + list[i].player.wxNickname + "</td>"
			if(list[i].rechargeSource==1){
				html += "<td>app</td>"
			}else{
				html += "<td>后台</td>"
			}
			
			html += "<td>" + list[i].rechargeAmount + "</td>"
			html += "<td>" + list[i].rechargeScore + "</td>"
			if(list[i].operateType==1){
				html += "<td>新建</td>"
					html += "<td>" + list[i].createDate + "</td>"
					html += "<td><a href='#' class='on_delete' onclick='manageRecharge("
							+ list[i].id + ",2)'>充值</a><a href='#' class='on_delete' onclick='manageRecharge("
							+ list[i].id + ",3)'>失效</a><a href='#' class='on_delete' onclick='manageRecharge("
							+ list[i].id + ",4)'>过期</a></td>"
			}else if(list[i].operateType==2){
				html += "<td>已充值</td>"
				html += "<td>" + list[i].createDate + "</td>"
				html += "<td><a href='#' class='on_delete' onclick='del("
							+ list[i].id + ")'>删除</a></td>"
			}else if(list[i].operateType==3){
				html += "<td>已失效</td>"
				html += "<td>" + list[i].createDate + "</td>"
				html += "<td><a href='#' class='on_delete' onclick='del("
								+ list[i].id + ")'>删除</a></td>"
			}else{
				html += "<td>已过期</td>"
				html += "<td>" + list[i].createDate + "</td>"
				html += "<td><a href='#' class='on_delete' onclick='del("
								+ list[i].id + ")'>删除</a></td>"
			}
			
			
			html += "</tr>"
		}

	}
	$("#slide_data").html("");
	$("#slide_data").html(html);

}
function manageRecharge(id,status) {
	
	if(confirm("确定要操作这条数据？")==true){
		var header = $("meta[name='_csrf_header']").attr("content");
		var token = $("meta[name='_csrf']").attr("content");
		var data = {
				"data" : {
					"rechargeId" : id,
					"status":status
				}
			};
		
		$.ajax({
			url : "/admin/recharge/api/manager",
			type : 'post',
			contentType : 'application/json',
			data : JSON.stringify(data),
			dataType : 'json',
			timeout : 1000,
			beforeSend : function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {

				if (data.status=10200) {
                    alert("操作成功");
					search();
				}else{
					alert(data.msg);
				}

			}
		});
		
		
	}
	
}
function del(id) {
	
}
