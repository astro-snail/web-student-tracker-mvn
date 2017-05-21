<!DOCTYPE html>

<html>

<head>
	<title>Add Student</title>
	
	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/form-style.css">
</head>

<body>

	<div id="wrapper">
		<div id="header">
			<h2>FooBar University</h2>
		</div>
		
		<div id="container">
			<h3>Add Student</h3>
			
			<form action="StudentControllerServlet" method="POST">
			
				<input type="hidden" name="command" value="ADD"/>
				
				<table>
					<tbody>
						<tr>
							<td><label>First name:</label></td>
							<td><input type="text" name="firstName"/></td>
						</tr>
						<tr>
							<td><label>Last name:</label></td>
							<td><input type="text" name="lastName"/></td>
						</tr>
						<tr>
							<td><label>E-Mail:</label></td>
							<td><input type="text" name="email"/></td>
						</tr>
						<tr>
							<td><label>&nbsp;</label></td>
							<td><input type="submit" value="Save" class="confirm"/></td>
						</tr>
					</tbody>
				</table>
				
			</form>
			<div style="clear: both;"></div>
			
			<p>
				<a href="StudentControllerServlet">Back to List</a>
			</p>
			
		</div>
	</div>

</body>

</html>