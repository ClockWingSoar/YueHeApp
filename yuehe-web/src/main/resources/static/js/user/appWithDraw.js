$(function() {
	search();



});



function search() {

	$('#pager').sjAjaxPager({
		url : "/admin/withdraw/list",
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
			html += "</td><td>" + list[i].benefactor.userPlatformId + "</td>"
			html += "<td>" + list[i].benefactor.wxNickname + "</td>"
			
			
			html += "<td>" + list[i].receiver.wxNickname + "</td>"
			html += "<td>" + list[i].score + "</td>"
			if(list[i].status==1){
				    html += "<td>新建</td>"
					html += "<td>" + list[i].crateTime + "</td>"
					html += "<td><a href='#' class='on_delete' onclick='manageWithDraw("
							+ list[i].id + ",2)'>处理</a><a href='#' class='on_delete' onclick='manageWithDraw("
							+ list[i].id + ",3)'>拒绝</a></td>"
			}else if(list[i].status==2){
				html += "<td>已充值</td>"
				html += "<td>" + list[i].crateTime + "</td>"
				html += "<td><a href='#' class='on_delete' onclick='del("
							+ list[i].id + ")'>删除</a></td>"
			}else if(list[i].status==3){
				html += "<td>已失效</td>"
				html += "<td>" + list[i].crateTime + "</td>"
				html += "<td><a href='#' class='on_delete' onclick='del("
								+ list[i].id + ")'>删除</a></td>"
			}else{
				html += "<td>已过期</td>"
				html += "<td>" + list[i].crateTime + "</td>"
				html += "<td><a href='#' class='on_delete' onclick='del("
								+ list[i].id + ")'>删除</a></td>"
			}
			
			
			html += "</tr>"
		}

	}
	$("#slide_data").html("");
	$("#slide_data").html(html);

}

function manageWithDraw(id,status) {
	
	if(confirm("确定要操作这条数据？")==true){
		var header = $("meta[name='_csrf_header']").attr("content");
		var token = $("meta[name='_csrf']").attr("content");
		var data = {
				"data" : {
					"withDrawId" : id,
					"status":status
				}
			};
		
		$.ajax({
			url : "/admin/withdraw/api/manager",
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

function del(id){}
