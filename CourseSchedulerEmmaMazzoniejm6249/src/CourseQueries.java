
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author emmamazzoni
 */
public class CourseQueries {
    private static Connection connection;
    private static ArrayList<String> getAllCourseCodes = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement queryAllCourseCodes;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (courseCode,description) values (?,?)");
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2,course.getDescription());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    
    public static ArrayList<String> getAllCourseCodes()
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseCodes = new ArrayList<String>();
        try
        {
            queryAllCourseCodes = connection.prepareStatement("select courseCode from app.course order by courseCode");
            resultSet = queryAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                String currentCourseCode = resultSet.getString("courseCode");
                courseCodes.add(currentCourseCode);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseCodes;
        
    }
    
}
