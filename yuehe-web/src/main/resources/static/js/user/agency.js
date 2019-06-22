$(function() {
	search();

	$("#add_agency").click(function() {
		checkSn();
	});

});
function checkSn() {
	if ($("#a_name").val() == "") {
		$("#a_name").focus();
		return;
	}
	
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	var data = {
		"data" : {
			"agencyName" : $("#a_name").val()
		}
	};
	$.ajax({
		url : "/admin/agency/check",
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
			"agencyName" : $("#a_name").val()
		}
	};
	$.ajax({
		url : "/admin/agency/save",
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
				$("#add_slide_div").hide();
				$(".deleted_tipsBox,.success_tipsBox,.add_slide_img,.add_user")
						.fadeOut("fast");
				$("#mask,#top_mask").fadeOut("fast");
				search();
			}

		}
	});

}

function search() {

	$('#pager').sjAjaxPager({
		url : "/admin/agency/list",
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
			html += "</td><td>" + list[i].agencyName + "</td>"
			html += "<td>" + list[i].agencyUnionCode + "</td>"
			html += "<td>" + list[i].createTime + "</td>"
			html += "<td><a href='#' class='on_delete' onclick='delAgency("
					+ list[i].agencyId + ")'>删除</a></td>"
			html += "</tr>"
		}

	}
	$("#slide_data").html("");
	$("#slide_data").html(html);

}

function delAgency(id) {
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	$.ajax({
		url : "/admin/agency/del?id="+id,
		type : "get",
		cache : false,
		contentType : false,
		processData : false,
		beforeSend : function(xhr) {
			xhr.setRequestHeader(header, token);
		},
		success : function(data) {

			if (data.status=10200) {

				alert("刪除成功");
				search();
			}else{
				alert(data.msg);
			}

		}
	});
}
