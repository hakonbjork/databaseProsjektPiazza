import java.sql.ResultSet;
import java.sql.Statement;

public class SearchCtrl extends DBConn {

    /**
     * Searches for a specific keyword in the title or text of both threads and posts.
     * Returns the IDs for the posts that contains the keyword and the IDs for threads
     * that contains the keyword.
     * @param keyword  the string to search for in posts and threads.
     */
   public void searchForKeyword(String keyword) {
       try {
           Statement stmt1 = conn.createStatement();
           String query1 =
                   "SELECT threadid\n" +
                           "from Thread\n" +
                           "where thread.threadtext like '%"+keyword+"%'or thread.Title like '%"+keyword+"%'";

           ResultSet rs1 = stmt1.executeQuery(query1);
           System.out.println("Threads som inneholder nøkkelordet "+keyword);
           while (rs1.next()) {
               System.out.println("ThreadID: "+rs1.getString("threadid") );
           }

           Statement stmt2 = conn.createStatement();
           String query2 =
                   "SELECT postid, threadid\n" +
                           "from Post\n" +
                           "where post.posttext like '%"+keyword+"%' or post.Title like '%"+keyword+"%'";

           ResultSet rs2 = stmt2.executeQuery(query2);
           System.out.println("Poster som inneholder nøkkelordet "+keyword);
           while (rs2.next()) {
               System.out.println("PostID: "+rs2.getString("postid") +", ThreadID: " + rs2.getString("threadID"));
           }
       } catch (Exception e) {
           System.out.println("Error finding posts");
       }
   }
}
