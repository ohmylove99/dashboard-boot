<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Spring MVC Multiple File Upload</title>

<script>
$(document).ready(function() {
    //add more file components if Add is clicked
    $('#addFile').click(function() {
        var fileIndex = $('#fileTable tr').children().length - 1;
        $('#fileTable').append(
                '<tr><td>'+
                '   <input type="file" name="files['+ fileIndex +']" />'+
                '</td></tr>');
    });
     
});
</script>
</head>
<body>

 
<form method="post" action="uploadResume.html"
        modelAttribute="uploadResumeForm" enctype="multipart/form-data">
 	<input type="hidden" name="id" value="${id}" />
 	<input type="hidden" name="type" value="${type}" />
    <p>Select Resume to upload.</p>

    <table id="fileTable">
        <tr>
            <td><input name="files[0]" type="file" /></td>
        </tr>
    </table>
    <br/><input type="submit" value="Upload" />
</form>
</body>
</html>