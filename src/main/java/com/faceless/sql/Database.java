package com.faceless.sql;

import java.sql.*;

public class Database
{
	// addiction "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC":
	// Solve problem with wrong server timezone on windows
	private final String     DB_URL   = "jdbc:mysql://192.168.99.100:3310?login=root&password=root&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false&maxReconnects=20";
	private final String     username = "root";
	private final String     password = "root";
	private       Connection connection;
	private Statement statement;

	public int executeUpdate(String stmt)
	{
		try
		{
			return statement.executeUpdate(stmt);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}

	public ResultSet executeQuery(String stmt)
	{
		try
		{
			return statement.executeQuery(stmt);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public boolean databaseExist(Connection connection) throws SQLException
	{
		return connection.prepareStatement("SHOW DATABASES LIKE 'VMDB';").executeUpdate() == 1;
	}

	public void connect() throws Exception
	{
		//Тут можно взаимодействовать с ней, добавлять таблицы, заполнять их, получать значения и тд
		//STEP 2: Register JDBC driver
//		Class.forName("com.mysql.jdbc.Driver");
		//STEP 3: Open a connection
//		Thread.sleep(20000);
		System.out.println("Connecting to database... " + DB_URL);
		try
		{
			boolean           dbExisted = false;
			Connection        conn      = DriverManager.getConnection(DB_URL, username, password);
			PreparedStatement stmt      = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS VMDB;");
			stmt.execute();
			statement = conn.createStatement();
			connection = conn;
			System.out.println("Database created successfully...");

			statement.executeUpdate("USE VMDB;");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id INT UNIQUE AUTO_INCREMENT PRIMARY KEY, login VARCHAR(255) UNIQUE, password VARCHAR(255));");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS vms (id INT AUTO_INCREMENT PRIMARY KEY,\n" +
									" owner VARCHAR(255),\n" +
									" vmname VARCHAR(255),\n" +
									" cpuvendor VARCHAR(255),\n" +
									" cpufrequency VARCHAR(255),\n" +
									" cpucorecount INT,\n" +
									" ramvolume INT,\n" +
									" hddvolume INT,\n" +
									" monitor BOOL,\n" +
									"os VARCHAR(255));");
		}
		catch (Exception se)
		{
			//Handle errors for JDBC
			se.printStackTrace();
		}//Handle errors for Class.forName
	}
}