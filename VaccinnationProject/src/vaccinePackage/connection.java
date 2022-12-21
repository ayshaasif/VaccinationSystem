/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinePackage;
import java.sql.*;
/**
 *
 * @author Acer
 */
public class connection {
    public static void main(String[] args) throws SQLException{
             String url="jdbc:mysql://localhost:3306/javaproject?zeroDateTimeBehavior=convertToNull";
             String username = "root";
             String pass = "";
            // String query = "Select * from registerdetails where id = 101";
             try{
                 Class.forName("com.mysql.jdbc.Driver"); //checking if we have the driver for this
             }catch(ClassNotFoundException e){
                 System.out.println("Error " + e.getMessage());
             }
             
             
            try{
                 Connection conn = DriverManager.getConnection(url,username,pass);
                 //Statement stmt = conn.createStatement();
                 String sql = "select * from registerdetails where id=?";
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 //LoginPage p = new LoginPage();
                // int id = p.getID();
                int id = 101;
                 pstmt.setLong(1,id);
                 ResultSet rs = pstmt.executeQuery();
                 
                 while(rs.next()){
                     String output = "";
//                     for(int i = 1;i<=2;i++){
//                        output+=rs.getString(i)+ " | ";
//                     }
                      output=rs.getString(2);
                     System.out.println(output);
                 }
                 
                 
             }catch(SQLException e){
                 System.out.println("Error " + e.getMessage());
             }
            
            System.out.println("executed");
            
    }
}
