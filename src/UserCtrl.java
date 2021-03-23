import java.sql.*;

public class UserCtrl extends DBConn {

    private PreparedStatement regStatement;

    public void startUserAdding() {
        try {
            regStatement = conn.prepareStatement("INSERT INTO ForumUser VALUES ( (?), (?), (?))");
        }
        catch (Exception e) {
            System.out.println("DB error during insertion");
        }

    }

    public void addUser(String username, String fullname, String userpassword) {
        try {
            regStatement.setString(1, username);
            regStatement.setString(2, fullname);
            regStatement.setString(3, userpassword);
            regStatement.execute();
        }
        catch (Exception e) {
            System.out.println("Error in adding of user with username: " + username);
            System.out.println(e);
        }
    }

    public boolean checkUserExist(String username, String password) {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT Username, Userpassword FROM ForumUser WHERE (Username = '"+username+"' AND Userpassword = '"+password+"')";

            ResultSet rs = stmt.executeQuery(query);

            Boolean userExists = false;
            if (rs.next()) {
                userExists = true;
            }

            if (userExists) {
                System.out.println("User found with username " + username);
                return true;
            }
        } catch (Exception e) {
            System.out.println("db error during checking of user " + e);
        }

        return false;
    }

    public boolean checkInstructor(String username) {
        String string1 = "";
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT UserInCourse.UserRole\n" +
                    "FROM ForumUser JOIN UserInCourse \n" +
                    "ON ForumUser.Username = UserInCourse.Username\n" +
                    "WHERE ForumUser.Username = \""+username+"\";";

            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                System.out.println(rs.getString("UserRole"));
                return (rs.getString("UserRole").equals("Instructor"));
            } else {
                System.out.println("Error: User with username " + username + " not found in database");
            }


        } catch (Exception e) {
            System.out.println("Something went wrong with instructor check. Error: " + e);
        }
        return false;
    }

    public static void main(String[] args) {
        UserCtrl userCtrl = new UserCtrl();
        userCtrl.connect();
        // userCtrl.startUserAdding();
        // userCtrl.addUser("test@test.com", "Test User", "test123");
        if (userCtrl.checkInstructor("håkonhotmail.com")) {
            System.out.println("Bruker er intruktør");
        } else {
            System.out.println("Bruker er ikke instruktør");
        }


    }
}
