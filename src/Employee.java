
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fatih
 */
public class Employee {
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    
    
    
    public Employee(){
        Database db = new Database();
        
        String url = "jdbc:mysql://" + db.getHost()+ ":" + db.getPort() + "/" + db.getDb_name() + "?useUnicode=true&characterEncoding=utf8";
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch(ClassNotFoundException ex){
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE,null,ex);
            System.out.println("Driver Bulunamadı.");
        }
        
        try {
            connection = DriverManager.getConnection(url,db.getUsername(),db.getPw() );
            System.out.println("Bağlantı başarılı.");
        } catch (SQLException ex) {
            System.out.println("Bağlantı kurulamadı.");
        }
    }
    
    public boolean addNewEmployee(String name,String surname,String dept,int salary){
        String sql = "insert into calisanlar Values(id,?,?,?,?)";
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, dept);
            preparedStatement.setInt(4, salary);
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean deleteEmployee(int id){
        String sql = "delete from calisanlar where id = " + id;
        try {
            statement = (Statement) connection.createStatement();
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean updateEmployeeInfo(int id,String name,String surname,String dept,String salary){
        String sql = "update calisanlar set ad = ?,soyad = ?,departman = ?,maas = ? where id = ?";
        try {
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, dept);
            preparedStatement.setString(4, salary);
            preparedStatement.setInt(5, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
    
    public boolean login(String username,String pw){
        
        String sql = "Select * from adminler where username = ?  and password = ?";
        
        try {
            
            preparedStatement = (PreparedStatement) connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, pw);
            ResultSet rs = preparedStatement.executeQuery();
            
            return rs.next();
            
        } catch (SQLException ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }
    
    public ArrayList<EmployeeInfo> fetchEmployeeInfos(){
        
        ArrayList<EmployeeInfo> output = new ArrayList<>();
        
        try {
            String sql = "SELECT * FROM calisanlar";
            statement = (Statement) connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            
            while(rs.next()){
                
                int id = rs.getInt("id");
                String ad = rs.getString("ad");
                String soyad = rs.getString("soyad");
                String departman = rs.getString("departman");
                int maas = rs.getInt("maas");
                EmployeeInfo emp = new EmployeeInfo(id, ad, soyad, departman, maas);
                output.add(emp);
            }
            return output;
        } catch (SQLException e) {
            return null;
        }
        
    }
    
    
    
    
}
