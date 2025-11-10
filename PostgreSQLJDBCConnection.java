import java.sql.*;

public class PostgreSQLJDBCConnection {

  //global so we do not need to repeatdly check to see if the connection works, we cheeck once, update the value and continue on

  private static final String URL = "jdbc:postgresql://localhost:5432/question1";
  private static final String USER = "postgres";
  private static final String PASSWORD = "St20050510!0";
  private static Connection conn = null;
  public static void main(String[] args) {

    //first connect to the database
    connect();

    //run crud operations
    getAllStudents();
    addStudent("Alice", "Brown", "alice.brown@example.com", "2023-09-03");
    updateStudentEmail(1, "newjohn@example.com");
    deleteStudent(3);
    //get final table
    getAllStudents();

    try { //if you are done, close the connection 
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void connect() {
    try {

      if (conn == null || conn.isClosed()) {
        //used for connecting to the database
        Class.forName("org.postgresql.Driver");

        //connect to DB
        conn = DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.println("Connected to PostgreSQL successfully!");
      }

    } catch (ClassNotFoundException | SQLException e) {
      System.out.println("Connection failed!");
      e.printStackTrace();
    }
  }

  public static void getAllStudents() {
    //sql statement to select all students ordered by their id
    String sql = "SELECT * FROM students ORDER BY student_id ASC;";

    //creating a statement object to execute the query 
    try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

      //loop through the result returned by teh databasse
      System.out.println("\n--- Students ---");
      while (rs.next()) {
        System.out.println(
          rs.getInt("student_id") + " | " +
          rs.getString("first_name") + " " +
          rs.getString("last_name") + " | " +
          rs.getString("email") + " | " +
          rs.getDate("enrollment_date")
        );
      }
    } catch (SQLException e) { //if the query isnt possible 
      e.printStackTrace();
    }
  }

  public static void addStudent(String first, String last, String email, String enrollmentDate) {
    //sql statement to select all students ordered by their id
    String sql = "INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)";

    //now we are preparing an SQL statement in which we bind the method arguments to the placeholders, then we execute
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, first);
      pstmt.setString(2, last);
      pstmt.setString(3, email);
      pstmt.setDate(4, Date.valueOf(enrollmentDate));
      pstmt.executeUpdate();
      // System.out.println("Student added successfully.");

    } catch (SQLException e) { //if the query isnt possible 
      System.out.println("email issue probably");
      e.printStackTrace();
    }
  }

  public static void updateStudentEmail(int id, String newEmail) {
    String sql = "UPDATE students SET email = ? WHERE student_id = ?";

    //prepare a new query and binding the method parameters, then we execute
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, newEmail);
      pstmt.setInt(2, id);
      pstmt.executeUpdate();
      // System.out.println("Email updated.");
    } catch (SQLException e) { //if the query isnt possible 
      e.printStackTrace();
    }
  }

  public static void deleteStudent(int id) {
    //sql statement 
    String sql = "DELETE FROM students WHERE student_id = ?";

    //preparing the statement and binding the arguments, then executing
    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);
      pstmt.executeUpdate();
      // System.out.println("Student deleted.");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}