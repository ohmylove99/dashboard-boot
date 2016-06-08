<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>Task Manage</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/task/${action}" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${task.id}" />
		<fieldset>
			<legend>
				<small>Manage Task</small>
			</legend>
			<div class="control-group">
				<label for="task_title" class="control-label">Name:</label>
				<div class="controls">
					<input type="text" id="task_title" name="title"
						value="${task.title}" class="input-large required" minlength="3" />
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">Description</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large">${task.description}</textarea>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="Submit" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="Return" onclick="history.back()" />
			</div>
		</fieldset>
	</form>
	<script>
		$(document).ready(function() {
			$("#task_title").focus();
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
