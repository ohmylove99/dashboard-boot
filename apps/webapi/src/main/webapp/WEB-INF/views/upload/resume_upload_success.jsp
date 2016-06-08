<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Resume Upload</title>
</head>
<body>
	<h1>Resume Upload:</h1>
	<p>Following files are uploaded successfully.</p>
	<ol>
		<c:forEach items="${files}" var="file">
			<li>${file}</li>
		</c:forEach>

		<li>${friendlyName}</li>
		<li>${fileName}</li>
		<li>${size}</li>
		<li>${location}</li>
		<li>${link}</li>
		
		<table class="table-striped" cellpadding="5">
        <tr>
            <th>Friendly Name:</th>
            <td><c:out value="${friendlyName}"/></td>
        </tr>
        <tr>
            <th>Filename:</th>
            <td><c:out value="${fileName}"/></td>
        </tr>
        <tr>
            <th>File content type:</th>
            <td><c:out value="${contentType}"/></td>
        </tr>
        <tr>
            <th>File size:</th>
            <td><c:out value="${size}"/></td>
        </tr>
        <tr>
            <th class="tallCell">File Location:</th>
            <td>The file has been written to: <br />
                <a href="<c:out value="${link}"/>"><c:out value="${link}" escapeXml="false"/></a>
            </td>
        </tr>
    </table>
	</ol>
</body>
</html>