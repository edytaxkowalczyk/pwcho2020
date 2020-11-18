import java.sql.*;
import java.lang.*; 
import java.util.Scanner;

public class Main {

   public static final String DB_URL = "jdbc:mysql://10.0.10.3:3306/test";
   public static void main(String[] args) throws SQLException, ClassNotFoundException {
      String JDBC_DRIVER = "com.mysql.jdbc.Driver";
      String DB_URL = "jdbc:mysql://10.0.10.3:3306/test?serverTimezone=UTC";
      String DB_USER = "EKowalczyk";
      String DB_PASS = "root";
 
      Statement stmt = null;
      Connection conn = null;
 
      while(conn == null){
      try {
	  conn = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS);
          if(conn != null)
          {
              System.out.println("Polaczono");
              createDB(conn);
              insertUser(conn,1, "User_1", "pass1");
              insertUser(conn,2, "User_2", "pass2");
              insertUser(conn,3, "User_3", "pass3");
              Menu();
	      String choose_option = new Scanner(System.in).next();

	      while((choose_option.equals("1") || choose_option.equals("2")|| choose_option.equals("3") || choose_option.equals("4") || choose_option.equals("5")))
	      {
                switch(choose_option)
                {
                  case "1":
                    System.out.println("Wprowadz id: ");
                    String id_scanner = new Scanner(System.in).next();
                    int id = Integer.parseInt(id_scanner);  
                    System.out.println("Wpisz login: ");
                    String login = new Scanner(System.in).next();
                    System.out.println("Wpisz haslo: ");
                    String pass = new Scanner(System.in).next();
                    insertUser(conn, id, login, pass);
                    break;
                  case "2":
                    showUsers(conn);
                    break;
                  case "3":
                      System.out.println("Wprowadz id uzytkownika, ktorego chcesz usunac: ");
                      id_scanner = new Scanner(System.in).next();
                      id=Integer.parseInt(id_scanner);
                      deleteUser(conn, id);
                      break;
                  case "4":
                      System.out.println("Wprowadz id uzytkownika, ktorego chcesz zaktualizowac:");
                      id_scanner = new Scanner(System.in).next();
                      id = Integer.parseInt(id_scanner);
                      System.out.println("Wpisz login");
                      login = new Scanner(System.in).next();
                      System.out.println("Wpisz haslo");
                      pass = new Scanner(System.in).next();
                      updateUser(conn, id, login, pass);
                      break;
                  case "5":
                      System.exit(0);
                  default:
                    System.out.println("Wybierz akcje:");
                    Menu();
                    break;       
                }
 
                Menu();
                System.out.println("Wybierz akcje: ");
                choose_option = new Scanner(System.in).next();
              }
          }
      }
      catch (SQLException ex) {
          System.out.println("Nie mozna polaczyc sie z baza danych");
      }
      catch (Exception e) {
          e.printStackTrace();
      }
      try {
        Thread.sleep(10000);
      }
      catch (Exception e) {
          e.printStackTrace();
      }
      }
   }

   public static void createDB(Connection conn) throws SQLException {
       String sql = "CREATE TABLE User (id INT NOT NULL, login VARCHAR(20) NOT NULL, password VARCHAR(25) NOT NULL, PRIMARY KEY(id));";
 
       PreparedStatement statement = conn.prepareStatement(sql);
 
       statement.execute(sql);
       System.out.println("Tabela User zostala utworzona.");
   }
 
      public static void insertUser(Connection conn, int id, String user,String password) throws SQLException {
 
       String sql = "INSERT INTO User (id, login, password) VALUES(?, ?, ?)";
 
       PreparedStatement statement = conn.prepareStatement(sql);
       statement.setInt(1, id);
       statement.setString(2, user);
       statement.setString(3, password);
 
       int rowsInserted = statement.executeUpdate();
       if(rowsInserted > 0) {
           System.out.println("Dodano nowego uzytkownika");
       }
 
   }

   public static void showUsers(Connection conn) throws SQLException {
       String sql = "SELECT * FROM User;";
       Statement statement = conn.createStatement();
       ResultSet rs = statement.executeQuery(sql);
 
       while(rs.next())
       {
           int id = rs.getInt("id");
           String login = rs.getString("login");
           String password = rs.getString("password");
 
           System.out.println("ID: " + id + ", Login: " + login + ", Password: " + password);
       }
       rs.close();
   }
 
   public static void deleteUser(Connection conn, int id) throws SQLException {
       String sql = "DELETE FROM User WHERE id = ?";
       PreparedStatement statement = conn.prepareStatement(sql);
       statement.setInt(1, id);
 
       int rowsDeleted = statement.executeUpdate();
       if(rowsDeleted > 0) {
           System.out.println("Uzytkownik o id = " + id + " zostal usuniety.");
       }
   }
 
   public static void updateUser(Connection conn, int id, String login, String password) throws SQLException {
       String sql = "UPDATE User SET login = ?, password = ? WHERE id = ?";
       PreparedStatement statement = conn.prepareStatement(sql);
       statement.setString(1, login);
       statement.setString(2, password);
       statement.setInt(3, id);
 
       int rowsUpdated = statement.executeUpdate();
       if(rowsUpdated > 0) {
           System.out.println("Uzytkownik o id = " + id + " zostal zaktualizowany.");
       }
       statement.close();
   }
 
   public static void Menu() {
        System.out.println();
        System.out.println("Akcje: ");
        System.out.println("1. Dodaj nowego uzytkownika");
        System.out.println("2. Wyswietl wszystkich uzytkownikow");
        System.out.println("3. Usun uzytkownika");
        System.out.println("4. Zaktualizuj uzytkownika");
        System.out.println("5. Zakoncz");	
        System.out.println();		
   }
 
 
}
