<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>Settings</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/profile" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}" />
		<fieldset>
			<legend>
				<small>Update Settings</small>
			</legend>
			<div class="control-group">
				<label for="name" class="control-label">Username:</label>
				<div class="controls">
					<input type="text" id="name" name="name" value="${user.name}"
						class="input-large required" />
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">Password:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword"
						class="input-large" placeholder="...Leave it blank if no change" />
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">Confirm
					Password</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="input-large" equalTo="#plainPassword" />
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit"
					value="submit" />&nbsp; <input id="cancel_btn" class="btn"
					type="button" value="return" onclick="history.back()" />
			</div>
		</fieldset>
	</form>

	<script>
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
