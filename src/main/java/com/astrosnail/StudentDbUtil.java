package com.astrosnail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

public class StudentDbUtil {
	
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws SQLException {
		
		List<Student> students = new ArrayList<>();
		
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			String sql = "SELECT * FROM student ORDER BY last_name";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
			
			while (resultSet.next()) {
				
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				
				Student tempStudent = new Student(id, firstName, lastName, email);
				students.add(tempStudent);
			}
			return students;	
		} finally {
			close(connection, statement, resultSet);
		}
	}
	
	public List<Student> searchStudents(String theSearchName) throws SQLException {
		
		if ((theSearchName == null) || (theSearchName.length() == 0)) {
			return getStudents();
		}
		
		List<Student> students = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "SELECT * FROM student " + "\n" + 
			             "WHERE lower(first_name) LIKE ? OR lower(last_name) LIKE ? " + "\n" +
					     "ORDER BY last_name";
			statement = connection.prepareStatement(sql);
			
			String theSearchNamePattern = "%" + theSearchName.toLowerCase() + "%";
			statement.setString(1, theSearchNamePattern);
			statement.setString(2, theSearchNamePattern);
						
			resultSet = statement.executeQuery();
			
			while (resultSet.next()) {
				
				int id = resultSet.getInt("id");
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				
				Student tempStudent = new Student(id, firstName, lastName, email);
				students.add(tempStudent);
			}
			return students;	
		} finally {
			close(connection, statement, resultSet);
		}
	}

	private void close(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addStudent(Student theStudent) throws SQLException {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "INSERT INTO student \n" +
			             "(first_name, last_name, email) \n" +
			             "VALUES (?, ?, ?)";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, theStudent.getFirstName());
			statement.setString(2, theStudent.getLastName());
			statement.setString(3, theStudent.getEmail());
			
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
	}
	
	public boolean isExists(int id) {
		try {
			getStudent(id);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

	public Student getStudent(int id) throws SQLException {
		Student theStudent = null;
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "SELECT * FROM student \n" +
			             "WHERE id = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
						
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				
				String firstName = resultSet.getString("first_name");
				String lastName = resultSet.getString("last_name");
				String email = resultSet.getString("email");
				
				theStudent = new Student(id, firstName, lastName, email);
			} else {
				throw new SQLException("Could not find student ID: " + id);
			}
			return theStudent;	
			
		} finally {
			close(connection, statement, resultSet);
		}
	}

	public void updateStudent(Student theStudent) throws SQLException {

		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "UPDATE student "
					   + "SET first_name = ?, last_name = ?, email = ?"
					   + "WHERE id = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, theStudent.getFirstName());
			statement.setString(2, theStudent.getLastName());
			statement.setString(3, theStudent.getEmail());
			statement.setInt(4, theStudent.getId());
			
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
	}
	
	public void deleteStudent(int id) throws SQLException {
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			connection = dataSource.getConnection();
		
			String sql = "DELETE FROM student WHERE id = ?";
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			
			statement.execute();
			
		} finally {
			close(connection, statement, null);
		}
	}
}
