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

public class MultiTableQueries {
    private static Connection connection;
    private static ArrayList<ClassDescription> getAllClassDescriptions = new ArrayList<ClassDescription>();
    private static PreparedStatement queryClassDescriptions;
    private static ResultSet resultSet;
    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester) {
    connection = DBConnection.getConnection();
    ArrayList<ClassDescription> classDescriptions = new ArrayList<>();

    try {
        queryClassDescriptions = connection.prepareStatement(
                "SELECT c.courseCode, c.description, cl.seats " +
                        "FROM app.course c, app.class cl " +
                        "WHERE c.courseCode = cl.courseCode AND cl.semester = ?"
        );

        queryClassDescriptions.setString(1, semester);
        resultSet = queryClassDescriptions.executeQuery();

        while (resultSet.next()) {
            ClassDescription classDescription = new ClassDescription(
                    resultSet.getString("courseCode"),
                    resultSet.getString("description"),
                    resultSet.getInt("seats")
            );
            classDescriptions.add(classDescription);
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }

    return classDescriptions;
}

}
