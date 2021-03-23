import java.sql.*;

public class PostCtrl extends DBConn {

    private PreparedStatement regStatement;

    public void startThreadAdding() {
        try {
            regStatement = conn.prepareStatement("INSERT INTO Thread VALUES ( (?), (?), (?), (?), (?), (?))");
        }
        catch (Exception e) {
            System.out.println("DB error during preparation for thread adding");
        }
    }

    public void startPostAdding() {
        try {
            regStatement = conn.prepareStatement("INSERT INTO Post VALUES ( (?), (?), (?), (?), (?), (?))");
        }
        catch (Exception e) {
            System.out.println("DB error during preparation for post adding");
        }
    }

    public void addThread(int threadID, String title, String threadText, boolean anonymous, int folderID, String threadCreator) {
        try {
            regStatement.setInt(1, threadID);
            regStatement.setString(2, title);
            regStatement.setString(3, threadText);
            regStatement.setBoolean(4, anonymous);
            regStatement.setInt(5, folderID);
            regStatement.setString(6, threadCreator);
            regStatement.execute();
        }
        catch (Exception e) {
            System.out.println("Error in adding of thread");
            System.out.println(e);
        }
    }

    public void addPost(int postID, String title, String postText, boolean anonymous, String postCreator, int threadID) {
        try {
            regStatement.setInt(1, postID);
            regStatement.setString(2, title);
            regStatement.setString(3, postText);
            regStatement.setBoolean(4, anonymous);
            regStatement.setString(5, postCreator);
            regStatement.setInt(6, threadID);
            regStatement.execute();
        }
        catch (Exception e) {
            System.out.println("Error in adding of post");
            System.out.println(e);
        }
    }

    public void simpleTestSearch() {
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM ForumUser";
            ResultSet rs = stmt.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Her gikk noe galt! Feil: " + e);
        }
    }

    public static void main(String[] args) {
        PostCtrl postCtrl = new PostCtrl();
        postCtrl.connect();
        postCtrl.startPostAdding();
        //postCtrl.startThreadAdding();
        //postCtrl.simpleTestSearch();
        //postCtrl.addThread(4, "Hjelp", "Jeg trenger hjelp", false, 1, "håkonhotmail.com");
        postCtrl.addPost(5, "Hei", "Trenger venner", true, "håkonhotmail.com", 1);
    }
}
