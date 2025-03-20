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

public class ClassQueries {
    private static Connection connection;
    private static ArrayList<ClassEntry> getAllCourseCodes = new ArrayList<ClassEntry>();
    private static PreparedStatement addClass;
    private static PreparedStatement queryAllCourseCodes;
    private static PreparedStatement getClassSeats;
    private static ResultSet resultSet;
    
    public static void addClass(ClassEntry class1)
    {
        connection = DBConnection.getConnection();
        try
        {
            addClass = connection.prepareStatement("insert into app.class (semester,courseCode,seats) values (?,?,?)");
            addClass.setString(1,class1.getSemester());
            addClass.setString(2, class1.getCourseCode());
            addClass.setInt(3,class1.getSeats());
            addClass.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try {
            queryAllCourseCodes = connection.prepareStatement("SELECT courseCode FROM app.class WHERE semester = ?");
            queryAllCourseCodes.setString(1, semester);
            resultSet = queryAllCourseCodes.executeQuery();

            while (resultSet.next()) {
                String currentCourseCode = resultSet.getString("courseCode");
                courseCodes.add(currentCourseCode);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }
    
    public static int getClassSeats(String semester, String courseCode) {
        connection = DBConnection.getConnection();
        int seats = 0;

        try {
            getClassSeats = connection.prepareStatement("SELECT seats FROM app.class WHERE semester = ? AND courseCode = ?");
            getClassSeats.setString(1, semester);
            getClassSeats.setString(2, courseCode);

            resultSet = getClassSeats.executeQuery();

            if (resultSet.next()) {
                seats = resultSet.getInt("seats");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return seats;
    }
    
}
