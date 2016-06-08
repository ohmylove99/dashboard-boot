<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>User Manage</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/admin/user/update" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${user.id}" />
		<fieldset>
			<legend>
				<small>User Manage</small>
			</legend>
			<div class="control-group">
				<label class="control-label">Username:</label>
				<div class="controls">
					<input type="text" value="${user.loginName}" class="input-large"
						disabled="" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Username:</label>
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
					Password:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword"
						class="input-large" equalTo="#plainPassword" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">Register Time:</label>
				<div class="controls">
					<span class="help-inline" style="padding: 5px 0px"><fmt:formatDate
							value="${user.registerDate}" pattern="yyyyMMdd  HHmmss" /></span>
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
			$("#name").focus();
			$("#inputForm").validate();
		});
	</script>
</body>
</html>
