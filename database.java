

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;



public class database {
    
    
    private static String DATABASE_URL = "jdbc:mysql://localhost:3306/posjavafx?useSSL=false" ;
     private static String DATABASE_USERNAME = "root";
     private static String DATABASE_PASSWORD = "";
     private static String SELECT_QUERY = "SELECT * FROM users WHERE username = ? AND password = ?";


     

     public boolean validate(String username, String password){

        try {
            
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME,DATABASE_PASSWORD);
            //create statement using  connection
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            System.out.println(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return true;
            }
          
        }catch (SQLException e){
            printSQLException (e);

        }
        return false;
 
     }
     public static Connection connectionDb(){
        try{
            
            Connection connect = DriverManager.getConnection(DATABASE_URL,DATABASE_USERNAME,DATABASE_PASSWORD);
            return connect;
        }catch(SQLException ex){
            printSQLException(ex);
        }
        return null;
    }
      public static void printSQLException(SQLException ex){
            for(Throwable e : ex) {
                if (ex instanceof SQLException){
                    e.printStackTrace(System.err);
                    System.err.println("SQLStates:" +  ((SQLException)e).getSQLState());
                    System.err.println("Error code:" + ((SQLException)e).getErrorCode());
                    System.err.println("Message: " + ex.getMessage());
                    Throwable t = ex.getCause();

                    while (t != null){
                         System.err.println("Casue: " + t );
                         t = t.getCause();

                    }
                }
            }

        }
    public static Connection coonectDb() {
        return null;
    }
} 

