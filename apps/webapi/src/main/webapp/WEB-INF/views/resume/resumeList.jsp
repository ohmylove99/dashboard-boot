<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Resume List</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
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
				<small>Manage Resume<c:if test="${not empty jobName}">
		 By Job - <a href="${ctx}/job">${jobName}</a>
</c:if></small>
			</legend>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>Name</th>
				<th>Description</th>
				<th>Status</th>
				<th>Manage</th>
				<th>Resume</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${resumes.content}" var="Resume">
				<tr>
					<td><a href="${ctx}/resume/update/${Resume.id}">${Resume.name}</a></td>
					<td>${Resume.description}</td>
					<td>${Resume.status}</td>
					<td><a href="${ctx}/resume/update/${Resume.id}">Update</a>|<a href="${ctx}/resume/delete/${Resume.id}">Delete</a></td>
					<td><a href="${ctx}/uploadResumeForm?id=${Resume.id}">Upload</a> | <a href="<c:out value="${Resume.uploadFileLink}" escapeXml="false"/>"><c:out value="${Resume.uploadFileName}"/></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<tags:pagination page="${resumes}" paginationSize="5" />

	<div>
		<a class="btn" href="${ctx}/resume/create">Create Resume</a>
	</div>
</body>
</html>
