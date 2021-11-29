<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
	<title>Student Tracker</title>
	
	<link type="text/css" rel="stylesheet" href="css/style.css" >
</head>

<body>
	
	<div id="container">
        
        <div id="header">
			<h2>FooBar University</h2>
		</div>
        
		<div id="content">
			<!-- Button: Add student -->
			<input type="button" value="Add Student"
				   onclick="window.location.href='add-student-form.jsp'; return false;"
				   class="button"/>
				   
			<!-- Search form -->
			<form action="/" method="GET">
				<input type="hidden" name="command" value="SEARCH"/>
				Search student: <input type="text" name="theSearchName" placeholder="Student's name"/>
				<input type="submit" value="Search" class="button"/>
			</form>	   
			
			<!-- List of students -->
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>E-Mail Address</th>
					<th colspan="2">Action</th>
				</tr>
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					
					<!-- Set up an update link -->
					<c:url var="updateLink" value="StudentControllerServlet">
						<c:param name="command" value="UPDATE"></c:param>
						<c:param name="studentId" value="${tempStudent.id}"></c:param>
					</c:url>
					
					<!-- Set up an update link -->
					<c:url var="deleteLink" value="StudentControllerServlet">
						<c:param name="command" value="DELETE"></c:param>
						<c:param name="studentId" value="${tempStudent.id}"></c:param>
					</c:url>
					
					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td><a href="${updateLink}">Update</a></td>
						<td><a href="${deleteLink}">Delete</a></td>
						<!-- onclick="if (!confirm('Delete this student?')) return false;" -->
					</tr>
				
                </c:forEach>
			
            </table>
		</div>
	
    </div>

</body>

</html>
