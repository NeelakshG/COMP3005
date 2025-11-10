Student Database CRUD Application

youtube video link: https://www.youtube.com/watch?v=xyOMJ-MWOGw

This Java application connects to a PostgreSQL database and performs CRUD operations on a students table.
The program allows the user to:

    - Create a new student record
    - Read and display all student records
    - Update a student’s email using their student ID
    - Delete a student by ID

The project uses JDBC (Java Database Connectivity) to communicate with PostgreSQL.

Project Structure

project-folder/
│
├─ postgresql-42.7.8.jar
├─ PostgreSQLJDBCConnection.java
├─ PostgreSQLJDBCConnection.class
└─ ReadMe.md

Note, the `postgresql-42.7.8.jar` is required for Java to connect to PostgreSQL
Make sure this Jar is includedin in the classpath

Database Setup Instructions

    1. Open pgAdmin
    2. Create a new database (ex: studentdb)
    3. Open the Query Tool
    4. Run the following script:
        CREATE TABLE IF NOT EXISTS students (
        student_id SERIAL PRIMARY KEY,
        first_name TEXT NOT NULL,
        last_name TEXT NOT NULL,
        email TEXT UNIQUE NOT NULL,
        enrollment_date DATE
    );

    INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
    ('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
    ('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
    ('Jim', 'Bean', 'jim.bean@example.com', '2023-09-02');

Database Connection Configuration

Inside PostgreSQLJDBCConnection.java, update these values to match your PostgreSQL setup

The way this file does connection is, I make sure connection is done once properly and then continously use that connection rather than rewriting the connection code again and again for each method.

look at the top of the java file to find this chunk of code
    
    private static final String URL = "jdbc:postgresql://<HOST>:<PORT>/<DATABASE_NAME>";
    private static final String USER = "<USERNAME>";
    private static final String PASSWORD = "<PASSOWORD>";

update the database name, username, andpassword to ensure they are correct


How to run the Application

    Complie: javac -cp ".;postgresql-42.7.8.jar" PostgreSQLJDBCConnection.java
    Run: java -cp ".;postgresql-42.7.8.jar" PostgreSQLJDBCConnection

Finally, Verify the CRUD operations

    1. getAllStudents() - Prints all records to console
    2. addStudent() - Inserts a new student
    3. updateStudentEmail() - changes email field for a particular id
    4. deleteStudent() - deletes a student by a particular id


