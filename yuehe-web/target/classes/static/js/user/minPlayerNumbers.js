$(function() {
	search();
});




function search() {

	$('#pager').sjAjaxPager({
		url : "/admin/game/rule/list",
		pageSize : 10,
		searchParam : {
			/*
			 * 如果有其他的查询条件，直接在这里传入即可
			 */
                agencyName : $("#agencyName").val(),
				agencyCode : $("#agencyCode").val()
			
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
			html += "</td><td>" + list[i].playerNum + "</td>"
			if(list[i].status==1){
				html += "<td>运行</td>"
			}else{
				html += "<td>暂停</td>"
			}
			
			html += "<td><a href='#' class='on_delete' onclick='delAgency("
					+ list[i].agencyId + ")'>暂停</a><a href='#' class='on_delete' onclick='delAgency("
					+ list[i].agencyId + ")'>删除</a></td>"
			html += "</tr>"
		}

	}
	$("#slide_data").html("");
	$("#slide_data").html(html);

}

function delAgency(id) {}


function edit(){
	if ($("#min_num").val() == "") {
		alert("请输出玩家最小开奖值");
		$("#min_num").focus();
		return;
	}
	
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	var data = {
		"data" : {
			"minNum" : $("#min_num").val()
		}
	};
	$.ajax({
		url : "/admin/game/rule/update",
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
				
				search();
			}

		},
		fail : function(err, status) {
			alert("系统错误，请稍后");
		}
	});
	
}
