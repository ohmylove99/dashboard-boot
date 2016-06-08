<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="reqUrl"
	value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}" />

<html>
<head>
<title>Restful API</title>
</head>

<body>

	<h3>Restful API</h3>
	<h4>API</h4>
	<ul>
		<li>Task List: <a href="${reqUrl}/api/v1/task">${reqUrl}/api/v1/task</a></li>
		<li>Task Get(id=1) : <a href="${reqUrl}/api/v1/task/1">${reqUrl}/api/v1/task/1</a></li>
		
		<li>Project List: <a href="${reqUrl}/api/v1/project">${reqUrl}/api/v1/project</a></li>
		<li>Project Get(id=1) : <a href="${reqUrl}/api/v1/project/1">${reqUrl}/api/v1/project/1</a></li>
		
		<li>Resume List: <a href="${reqUrl}/api/v1/resume">${reqUrl}//api/v1/resume</a></li>
		<li>Resume Get(id=1) : <a href="${reqUrl}/api/v1/resume/1">${reqUrl}/api/v1/resume/1</a></li>
		
		<li>Job List: <a href="${reqUrl}/api/v1/job">${reqUrl}//api/v1/job</a></li>
		<li>Job Get(id=1) : <a href="${reqUrl}/api/v1/job/1">${reqUrl}/api/v1/job/1</a></li>
		
		<li>Type - EmpType List: <a href="${reqUrl}/api/v1/type/emptype">${reqUrl}/api/v1/type/emptype</a></li>
		<li>Type - EmpType Get(id=1) : <a href="${reqUrl}/api/v1/type/emptype/1">${reqUrl}/api/v1/type/emptype/1</a></li>
		
		<li>Type - InterviewRound List: <a href="${reqUrl}/api/v1/type/interviewround">${reqUrl}/api/v1/type/interviewround</a></li>
		<li>Type - InterviewRound Get(id=1) : <a href="${reqUrl}/api/v1/type/interviewround/1">${reqUrl}/api/v1/type/interviewround/1</a></li>
		
		<li>Type - InterviewStatus List: <a href="${reqUrl}/api/v1/type/interviewstatus">${reqUrl}/api/v1/type/interviewstatus</a></li>
		<li>Type - InterviewStatus Get(id=1) : <a href="${reqUrl}/api/v1/type/interviewstatus/1">${reqUrl}/api/v1/type/interviewstatus/1</a></li>
		
		<li>Type - JobBiz List: <a href="${reqUrl}/api/v1/type/jobbiz">${reqUrl}/api/v1/type/jobbiz</a></li>
		<li>Type - JobBiz Get(id=1) : <a href="${reqUrl}/api/v1/type/jobbiz/1">${reqUrl}/api/v1/type/jobbiz/1</a></li>
		
		<li>Type - JobGrade List: <a href="${reqUrl}/api/v1/type/jobgrade">${reqUrl}/api/v1/type/jobgrade</a></li>
		<li>Type - JobGrade Get(id=1) : <a href="${reqUrl}/api/v1/type/jobgrade/1">${reqUrl}/api/v1/type/jobgrade/1</a></li>
		
		<li>Type - JobStatus List: <a href="${reqUrl}/api/v1/type/jobstatus">${reqUrl}/api/v1/type/jobstatus</a></li>
		<li>Type - JobStatus Get(id=1) : <a href="${reqUrl}/api/v1/type/jobstatus/1">${reqUrl}/api/v1/type/jobstatus/1</a></li>
		
		<li>Type - ResumeStatus List: <a href="${reqUrl}/api/v1/type/resumestatus">${reqUrl}/api/v1/type/resumestatus</a></li>
		<li>Type - ResumeStatus Get(id=1) : <a href="${reqUrl}/api/v1/type/resumestatus/1">${reqUrl}/api/v1/type/resumestatus/1</a></li>
		<br>
		<li>Status - Status List: <a href="${reqUrl}/api/v1/status">${reqUrl}/api/v1/status</a></li>
		<li>Status - Status Get(id=1) : <a href="${reqUrl}/api/v1/status/1">${reqUrl}/api/v1/status/1</a></li>
		<li>Status - Status List: <a href="${reqUrl}/api/v1/status/type/EMPTYPE">${reqUrl}/api/v1/status/type/EMPTYPE</a></li>
		<li>Status - Status Get(id=1) : <a href="${reqUrl}/api/v1/status/type/JOBGRADE">${reqUrl}/api/v1/type/JOBGRADE</a></li>
	</ul>

	<h4>Update API</h4>
	<ul>
		<li>Create task: ${reqUrl}/api/v1/task method=Post, consumes=JSON</li>
		<li>Update task: (id=1) ：${reqUrl}/api/v1/task/1 method=Put,
			consumes=JSON</li>
	</ul>
</body>
</html>
