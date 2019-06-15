<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>美肤项目详情表</title>
</head>
<body>
    
	<div>
		<div>以下为现有的美肤项目:</div>
		<table>
			<tr>
				<td>名称</td>
				<td>单价</td>
				<td>简介</td>
			</tr>
			<c:forEach var="beautifyskinitem" items="${beautifySkinItemList}">
				<tr>
					<td>${beautifyskinitem.name}</td>
					<td>${beautifyskinitem.price}</td>
					<td>${beautifyskinitem.description}</td>
				</tr>
			</c:forEach>
		</table>
	</div>

</body>


</html>