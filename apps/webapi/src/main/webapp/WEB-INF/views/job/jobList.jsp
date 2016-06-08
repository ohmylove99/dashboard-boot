<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Job List</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<table id="tree"></table>
	<div id="pager"></div>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="#">
				<label>Name:</label> <input type="text" name="search_LIKE_name"
					class="input-medium" value="${param.search_LIKE_name}">
				<button type="submit" class="btn" id="search_btn">Search</button>
			</form>
		</div>
		<tags:sort />
	</div>
	<legend>
		<small>Manage Job</small>
	</legend>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>OpenBy</th>
				<th>OpenByBiz</th>
				<th>Status</th>
				<th>Manage</th>
				<th>Assigned Resumes</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${jobs.content}" var="Job">
				<tr>
					<td><a href="${ctx}/job/update/${Job.id}">${Job.name}</a></td>
					<td>${Job.description}</td>
					<td>${Job.openBy}</td>
					<td>${Job.openByBiz}</td>
					<td>${Job.status}</td>
					<td><a href="${ctx}/job/update/${Job.id}">update</a>|<a
						href="${ctx}/job/delete/${Job.id}">Delete</a></td>
					<td><a href="${ctx}/resume/jobresumes?jobId=${Job.id}">Resumes</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<tags:pagination page="${jobs}" paginationSize="5" />

	<div>
		<a class="btn" href="${ctx}/job/create">Create Job</a>
	</div>


</body>
</html>
