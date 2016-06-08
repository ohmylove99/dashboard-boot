<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>Job Manage</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/job/${action}" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${job.id}" />
		<fieldset>
			<legend>
				<small>Manage Job</small>
			</legend>

			<div class="control-group">
				<label for="job_name" class="control-label">Name:</label>
				<div class="controls">
					<input type="text" id="name" name="name" value="${job.name}"
						class="input-large required" minlength="3" />
				</div>
			</div>
			
			<div class="control-group">
				<label for="description" class="control-label">Reference Id</label>
				<div class="controls">
					<input type="text" id="referenceid" name="referenceid" class="input-large" value="${job.referenceid}">
				</div>
			</div>
			
			<div class="control-group">
				<label for="description" class="control-label">Status</label>
				<div class="controls">
					<input type="text" id="status" name="status" class="input-large" value="${job.status}">
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">Open By</label>
				<div class="controls">
					<input type="text" id="openBy" name="openBy" class="input-large" value="${job.openBy}">
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">Open By Biz</label>
				<div class="controls">
					<input type="text" id="openByBiz" name="openByBiz" class="input-large" value="${job.openByBiz}">
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">Interviewer</label>
				<div class="controls">
					<input type="text" id="interviewer" name="interviewer" class="input-large" value="${job.interviewer}">
				</div>
			</div>
			
			<div class="control-group">
				<label for="description" class="control-label">Description</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large" >${job.description}</textarea>
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
			$("#job_name").focus();
			$("#inputForm").validate();
			$('#grade').autocomplete({
				serviceUrl: '${pageContext.request.contextPath}/api/v1/type/jobgrade',
				paramName: "name",
				delimiter: ",",
			   	transformResult: function(response) {
				return {      	
				  suggestions: $.map($.parseJSON(response), function(item) {      	
				      return { value: item.name, data: item.id };
				   })        
				 };
		        }    
			 });
			$('#status').autocomplete({
				serviceUrl: '${pageContext.request.contextPath}/api/v1/type/jobstatus',
				paramName: "name",
				delimiter: ",",
			   	transformResult: function(response) {
				return {      	
				  suggestions: $.map($.parseJSON(response), function(item) {      	
				      return { value: item.name, data: item.id };
				   })        
				 };
		        }    
			 });
			$('#status').autocomplete({
				serviceUrl: '${pageContext.request.contextPath}/api/v1/type/jobstatus',
				paramName: "name",
				delimiter: ",",
			   	transformResult: function(response) {
				return {      	
				  suggestions: $.map($.parseJSON(response), function(item) {      	
				      return { value: item.name, data: item.id };
				   })        
				 };
		        }    
			 });
			$('#openByBiz').autocomplete({
				serviceUrl: '${pageContext.request.contextPath}/api/v1/type/jobbiz',
				paramName: "name",
				delimiter: ",",
			   	transformResult: function(response) {
				return {      	
				  suggestions: $.map($.parseJSON(response), function(item) {      	
				      return { value: item.name, data: item.id };
				   })        
				 };
		        }    
			 });
		});
	</script>
</body>
</html>
