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
    </ol>
</body>
</html>