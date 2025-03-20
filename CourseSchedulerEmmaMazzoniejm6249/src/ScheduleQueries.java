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

public class ScheduleQueries {
    private static Connection connection;
    private static ArrayList<ScheduleEntry> getScheduleByStudent = new ArrayList<ScheduleEntry>();
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement queryScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static ResultSet resultSet;
    
    // Method to add a ScheduleEntry to the database
    public static void addScheduleEntry(ScheduleEntry entry) {
        connection = DBConnection.getConnection();

        try {
            addScheduleEntry = connection.prepareStatement("INSERT INTO app.schedule (semester, courseCode, studentID, status, TimeStamp) VALUES (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimeStamp());

            addScheduleEntry.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    // Method to get a list of ScheduleEntry objects for a given student and semester
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID) {
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduleEntries = new ArrayList<>();

        try {
            queryScheduleByStudent = connection.prepareStatement("SELECT * FROM app.schedule WHERE semester = ? AND studentID = ?");
            queryScheduleByStudent.setString(1, semester);
            queryScheduleByStudent.setString(2, studentID);

            resultSet = queryScheduleByStudent.executeQuery();

            while (resultSet.next()) {
                ScheduleEntry entry = new ScheduleEntry(
                        resultSet.getString("semester"),
                        resultSet.getString("courseCode"),
                        resultSet.getString("studentID"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("TimeStamp")
                );
                scheduleEntries.add(entry);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return scheduleEntries;
    }

    // Method to get the count of students scheduled for a given course in the current semester
    public static int getScheduledStudentCount(String currentSemester, String courseCode) {
        connection = DBConnection.getConnection();
        int count = 0;

        try {
            getScheduledStudentCount = connection.prepareStatement("SELECT COUNT(*) FROM app.schedule WHERE semester = ? AND courseCode = ?");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);

            resultSet = getScheduledStudentCount.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return count;
    }
}
