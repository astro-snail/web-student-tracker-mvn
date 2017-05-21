<!DOCTYPE html>

<html>

<head>
	<title>Delete Student</title>
	
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/form-style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>FooBar University</h2>
		</div>
		
		<div id="container">
			<h3>Delete Student</h3>
			
			<form action="StudentControllerServlet" method="POST">
			
				<input type="hidden" name="command" value="DELETE"/>
				
				<input type="hidden" name="studentId" value="${THE_STUDENT.id}"/>
				
				<table>
					<tbody>
						<tr>
							<td><label>First name:</label></td>
							<td><input type="text" name="firstName" value="${THE_STUDENT.firstName}" disabled="disabled"/></td>
						</tr>
						<tr>
							<td><label>Last name:</label></td>
							<td><input type="text" name="lastName" value="${THE_STUDENT.lastName}" disabled="disabled"/></td>
						</tr>
						<tr>
							<td><label>E-Mail:</label></td>
							<td><input type="text" name="email" value="${THE_STUDENT.email}" disabled="disabled"/></td>
						</tr>
						<tr>
							<td><label>&nbsp;</label></td>
							<td><input type="submit" value="Delete" class="confirm"/></td>
						</tr>
					</tbody>
				</table>
				
			</form>
			<div style="clear: both;"></div>
			
			<p class="message"><b>${message}</b></p>
			
			<p>
				<a href="StudentControllerServlet">Back to List</a>
			</p>
			
		</div>
	</div>

</body>

</html>