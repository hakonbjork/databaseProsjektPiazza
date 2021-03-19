import java.sql.PreparedStatement;
import java.sql.Statement;

public class UserCtrl extends DBConn {

    private PreparedStatement regStatement;
    private Statement statement;

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
            regStatement.executeUpdate();
        }
        catch (Exception e) {
            System.out.println("Error in adding of user with username: " + username);
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        UserCtrl userCtrl = new UserCtrl();
        userCtrl.connect();
        userCtrl.startUserAdding();
        System.out.println("Started user adding...");
        userCtrl.addUser("test2@test.com", "Test User", "test123");
    }
}
