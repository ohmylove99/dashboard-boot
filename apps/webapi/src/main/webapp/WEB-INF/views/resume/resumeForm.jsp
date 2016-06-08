<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>Resume Manage</title>
</head>

<body>
	<form id="inputForm" action="${ctx}/resume/${action}" method="post"
		class="form-horizontal">
		<input type="hidden" name="id" value="${resume.id}" />
		<input type="hidden" name="jobId" id="jobId" />
		<fieldset>
			<legend>
				<small>Manage Resume</small>
			</legend>

			<div class="control-group">
				<label for="job_name" class="control-label">Name:</label>
				<div class="controls">
					<input type="text" id="name" name="name" value="${resume.name}"
						class="input-large required" minlength="3" />
				</div>
			</div>
			
			<c:if test="${empty resume.id}">
                <div class="control-group">
				<label for="description" class="control-label">Status</label>
				<div class="controls">
					<input type="text" id="status" name="status" class="input-large" value="Open"></input>
				</div>
				</div>
            </c:if>
            <c:if test="${not empty resume.id}">
                <div class="control-group">
				<label for="description" class="control-label">Status</label>
				<div class="controls">
					<input type="text" id="status" name="status" class="input-large" value="${resume.status}"></input>
				</div>
				</div>
            </c:if>
            
			<div class="control-group">
				<label for="description" class="control-label">Skills</label>
				<div class="controls">
					<textarea id="description" name="description" class="input-large" >${resume.skills}</textarea>
				</div>
			</div>
			
			<div class="control-group">
				<label for="description" class="control-label">Assign to Job</label>
				<div class="controls">
					<input type="text" id="positiondesc" name="positiondesc" class="input-large" value="${resume.job.name}"/>
				</div>
			</div>
			
			<!-- <div class="control-group">
				<label for="description" class="control-label">Job</label>
				<div class="controls">
					<input type="text" id="job" name="job" class="input-large"></input>
				</div>
			</div> -->
				
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
			$('#status').autocomplete({
				serviceUrl: '${pageContext.request.contextPath}/api/v1/type/resumestatus',
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
			$('#positiondesc').autocomplete({
				serviceUrl: '${pageContext.request.contextPath}/api/v1/job',
				paramName: "name",
				delimiter: ",",
				onSelect: function (suggestion) {  
			        //alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
			        $('#positiondesc').val(suggestion.value);
			        $('#jobId').val(suggestion.data);
			        //alert($('#position').val());
			    },  
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
