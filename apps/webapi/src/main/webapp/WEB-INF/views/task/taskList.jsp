<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Task List</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>Name:</label> <input type="text" name="search_LIKE_title"
					class="input-medium" value="${param.search_LIKE_title}">
				<button type="submit" class="btn" id="search_btn">Search</button>
			</form>
		</div>
		<tags:sort />
	</div>
	<legend>
				<small>Manage Task</small>
			</legend>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>Task</th>
				<th>Manage</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${tasks.content}" var="task">
				<tr>
					<td><a href="${ctx}/task/update/${task.id}">${task.title}</a></td>
					<td><a href="${ctx}/task/delete/${task.id}">Delete</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<tags:pagination page="${tasks}" paginationSize="5" />

	<div>
		<a class="btn" href="${ctx}/task/create">Create Task</a>
	</div>
</body>
</html>
