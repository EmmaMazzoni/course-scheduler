/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author emmamazzoni
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    private static ArrayList<StudentEntry> getAllStudents = new ArrayList<StudentEntry>();
    private static PreparedStatement addStudent;
    private static PreparedStatement queryAllStudents;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (studentID,firstName,lastName) values (?,?,?)");
            addStudent.setString(1,student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3,student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> students = new ArrayList<StudentEntry>();
        try
        {
            queryAllStudents = connection.prepareStatement("select studentID,firstName,lastName from app.student order by studentID,firstName,lastName");
            resultSet = queryAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry currentStudent = new StudentEntry(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3));
                students.add(currentStudent);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return students;
        
    }
    
}

