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

    public void assignTagToThread(int threadID, String tagName) {
        try {
            Statement stmt = conn.createStatement();
            String query = "INSERT INTO TagInThread(ThreadID, TagName) VALUES ('"+threadID+"', '"+tagName+"')";
            stmt.execute(query);

        } catch (Exception e) {
            System.out.println("Error: Something went wrong when adding tag to thread: \n" + e);
        }
    }

    public int nextThreadID() {
        int nextThreadID = 1;
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT MAX(ThreadID) AS MaxThreadID FROM Thread";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int maxThreadID = rs.getInt("MaxThreadID");
                nextThreadID = maxThreadID + 1;
            }

        } catch (Exception e) {
            System.out.println("Error: Search for next threadID failed, " + e);
        }

        return nextThreadID;
    }

    public int nextPostID(int threadID) {
        int nextPostID = 1;
        try {
            Statement stmt = conn.createStatement();
            String query = "SELECT MAX(PostID) AS MaxPostID\n" +
                    "FROM Post\n" +
                    "WHERE Post.ThreadID ='"+threadID+"'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                int maxPostID = rs.getInt("MaxPostID");
                nextPostID = maxPostID + 1;
            }

        } catch (Exception e) {
            System.out.println("Error: Search for next postID failed, " + e);
        }

        return nextPostID;
    }

    /**
     * This method is a simple search method
     * @param folderName The folderName used to find the folderID
     */
    public int findFolderID(String folderName) {
        try {
            Statement stmt1 = conn.createStatement();
            String query1 =
                    "SELECT FolderID\n" +
                            "from Folder\n" +
                            "where Folder.FolderName like '"+folderName+"';";
            int FolderID;
            ResultSet rs1 = stmt1.executeQuery(query1);
            if (rs1.next()) {
                FolderID = rs1.getInt("FolderID");
            }
            else{
                FolderID = -1;
            }
            return FolderID;
        }
        catch (Exception e) {
            System.out.println("Error finding folder: "+e);
            return -1;
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
        //postCtrl.startPostAdding();
        postCtrl.startThreadAdding();
        //postCtrl.simpleTestSearch();
        postCtrl.addThread(4, "Hjelp", "Jeg trenger hjelp", false, 1, "håkonhotmail.com");
        //postCtrl.addPost(5, "Hei", "Trenger venner", true, "håkonhotmail.com", 1);
        //System.out.println(postCtrl.nextThreadID());
        //System.out.println(postCtrl.nextPostID(1));
        //postCtrl.assignTagToThread(2, "Question");
        postCtrl.closeConnection();
    }
}
