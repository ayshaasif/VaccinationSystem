/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinePackage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JOptionPane;
/**
 *
 * @author Acer
 */

public class serverconnection {
        private String url;
        private String username ;
        private String pass;
        private Connection conn;
        private ResultSet rs;
        private Statement stmt;
        private PreparedStatement pstmt ;
        
       
    public Connection getConnection(){
         try{
            url="jdbc:mysql://localhost:3306/javaproject?";
            username = "root";
            pass = "";
             
             Class.forName("com.mysql.jdbc.Driver");
             conn = DriverManager.getConnection(url,username,pass);
             System.out.println("connected");
             
         }catch(SQLException e){
             System.out.println("Error from connection "+e.getMessage());
         }catch(ClassNotFoundException e){
             System.out.println("Error from connection  "+e.getMessage());
         }
         return conn;
    }    
    public ResultSet searchQuery(String id,String dob){
             try{
                 Connection con = getConnection();
                 String sql = "SELECT * FROM `registerdetails` WHERE emiratesid = ? and dateofbirth = ?;";
                 pstmt = con.prepareStatement(sql);
                 pstmt.setString(1,id);
                 pstmt.setString(2,dob); 
                 rs = pstmt.executeQuery();
                System.out.println("searched");
             }catch(SQLException e){
                 
                 System.out.println("Error from searchquery " + e.getMessage());
             }
           return rs;  
    }
    
   public int newUser(String fname, String lname,String id,String nationality,String phone,String email,String dob){
       System.out.println("--new user--");
       int inserted = 0;
       try
       {
       Connection con = getConnection();
       //String sql = "INSERT INTO registerdetails values(?,?,?);";
       String sql = "INSERT into registerdetails(FirstName,LastName,EmiratesId,Nationality,PhoneNumber,	Email,DateOfBirth,dose) VALUES(?,?,?,?,?,?,?,0);";
        pstmt = conn.prepareStatement(sql);
         pstmt.setString(1,fname);
         pstmt.setString(2,lname);
         pstmt.setString(3,id);
         pstmt.setString(4,nationality);
         pstmt.setString(5,phone);
         pstmt.setString(6,email);
         pstmt.setString(7,dob);
         pstmt.executeUpdate(); 
         inserted = 1;
       System.out.println("inserted");

       }
       catch(SQLException e){
              System.out.println("Error from NEW USER query " + e.getMessage());
       }
       return inserted;
     }
   
   public int Update(String eid,String dob,Date apptdate){
       System.out.println("in UPDATE query");
       int update = 0;
       try{
           Connection con = getConnection();
           int d=0;
           String vaccine;
           String sql;
           Date frstappt = new Date();
           System.out.println(frstappt);
           Calendar cal = Calendar.getInstance();
           cal.setTime(frstappt);
           cal.add(Calendar.DAY_OF_MONTH,21);
           Date scndappt = cal.getTime();
           System.out.println(scndappt);
           //int days;
            rs = searchQuery(eid,dob);
            SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
              while(rs.next()){
                 d = rs.getInt("dose");
                 //frstappt = rs.getDate("FirstAppt");
              }                 
                 
                 //not needed
//                 if(d == 1)//second dose
//                 { 
//                  days=(int) TimeUnit.MILLISECONDS.toDays(apptdate.getTime() - frstappt.getTime());
//                  System.out.println(days);
//                     if(days > 21)  { 
//                       System.out.println("second dose - second time user d = 1 ");
//                      sql = "UPDATE `registerdetails` SET `dose` = ? ,`SecondAppt` = ? WHERE `registerdetails`.`EmiratesId` = ?;";
//                       pstmt = con.prepareStatement(sql);
//                       pstmt.setInt(1, k);
//                        String apptd = dcn.format(apptdate);
//                        pstmt.setString(2,apptd);
//                        pstmt.setString(3,eid);
//                         update = 1;
//                            System.out.println("Updated");
//                     }
//                     else{
//                         JOptionPane.showMessageDialog(null,"Book a date after 21 days from first appointment");
//                     }}
   // ------
                 if(d == 0){
                     //System.out.println("first dose - first time user d = 0 ");
                     //first timeuser
                     vaccine = selectVaccineDate.vaccinetype;
                     System.out.println(vaccine);                   
                     sql = "UPDATE `registerdetails` SET `dose` = ? ,`FirstAppt` = ?,`SecondAppt` = ?,`VaccineType` = ? WHERE `registerdetails`.`EmiratesId` = ?;";   
                        pstmt = con.prepareStatement(sql); 
                        pstmt.setInt(1, 2);
                        String firstappt = dcn.format(apptdate);
                        String secondappt = dcn.format(scndappt);
                        pstmt.setString(2,firstappt);
                        pstmt.setString(3,secondappt);
                        pstmt.setString(4,vaccine);
                        pstmt.setString(5,eid);
                        System.out.println(apptdate+"|"+eid+"|"+d+"|"+vaccine);
                               System.out.println("Updated");
                               update = 1;
                 }else{
                       JOptionPane.showMessageDialog(null,"--- Only 2 doses per person --- ");
                 }
             pstmt.executeUpdate();
             
             }catch(SQLException e){
                 System.out.println("Error from Updatequery " + e.getMessage());
             }
       return update;
   }
   
  
}

