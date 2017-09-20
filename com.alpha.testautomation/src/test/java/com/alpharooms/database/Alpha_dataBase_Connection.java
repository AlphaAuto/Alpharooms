package com.alpharooms.database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
 
/**
 * This program demonstrates how to establish database connection to Microsoft
 * SQL Server.
 * @author www.codejava.net
 *
 */
public class Alpha_dataBase_Connection {
	
	    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
	 
	        Connection conn = null;
	 
	        try {
	        	 
	        	String url = "jdbc:sqlserver://172.16.33.208\\Staging;database=Alphabeds;integratedSecurity=true;";
	            //private static String userName = "sa";
	            
	        		//Loading the required JDBC Driver class
	        		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();	
	        	
	        		 conn = DriverManager.getConnection(url,"StagingUser","kNPnbvkZBp9VhUe" );
	        		 System.out.println("Connecetd");
	        		
	        		//Executing SQL query and fetching the result
	        		Statement st = conn.createStatement();
	        		String sqlStr = "select * from AutoSuggest";
	        		ResultSet rs = st.executeQuery(sqlStr);
	        		while (rs.next()) 
	        		{
	        			System.out.println(rs.getString("strName"));
	        		}		
	      
	       
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        } finally {
	            try {
	                if (conn != null && !conn.isClosed()) {
	                    conn.close();
	                }
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }
	

}
