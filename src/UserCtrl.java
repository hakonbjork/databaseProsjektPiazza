import java.sql.*;

public class UserCtrl extends DBConn {

    /**
     * Checks if a given username and password exists in the same
     * row in the ForumUser table in the database.
     * Returns true if it exists, false if not.
     *
     * @param username  the username being checked.
     * @param password  the password being checked.
     */
    public boolean checkUserExist(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Username, Userpassword FROM ForumUser WHERE (Username = '"+username+"' AND Userpassword = '"+password+"')";

            ResultSet rs = stmt.executeQuery(query);

            boolean userExists = false;
            if (rs.next()) {
                userExists = true;
            }

            if (userExists) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("db error during checking of user " + e);
        }

        return false;
    }

    /**
     * Checks what role a given user has.
     * If the user is an instructor it return true, if not, false.
     *
     * @param username  the string that corresponds to username that is to be checked.
     */
    public boolean checkInstructor(String username) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT UserInCourse.UserRole\n" +
                    "FROM ForumUser JOIN UserInCourse \n" +
                    "ON ForumUser.Username = UserInCourse.Username\n" +
                    "WHERE ForumUser.Username = \""+username+"\";";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return (rs.getString("UserRole").equals("Instructor"));
            } else {
                System.out.println("Error: User with username " + username + " not found in database");
            }


        } catch (Exception e) {
            System.out.println("Something went wrong with instructor check. Error: " + e);
        }
        return false;
    }
}
