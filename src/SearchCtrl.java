import java.sql.ResultSet;
import java.sql.Statement;

public class SearchCtrl extends DBConn {
   public void searchForKeyword(String keyword) {
       try {
           Statement stmt1 = conn.createStatement();
           String query1 =
                   "SELECT threadid\n" +
                           "from Thread\n" +
                           "where thread.threadtext like \"%"+keyword+"%\" or thread.Title like \"%"+keyword+"%\";";

           ResultSet rs1 = stmt1.executeQuery(query1);
           System.out.println("Threads som inneholder nøkkelordet "+keyword);
           while (rs1.next()) {
               System.out.println("ThreadID: "+rs1.getString("threadid") );
           }

           Statement stmt2 = conn.createStatement();
           String query2 =
                   "SELECT postid, threadid\n" +
                           "from Post\n" +
                           "where post.posttext like \"%"+keyword+"%\"or post.Title like \"%"+keyword+"%\";";

           ResultSet rs2 = stmt2.executeQuery(query2);
           System.out.println("Poster som inneholder nøkkelordet "+keyword);
           while (rs2.next()) {
               System.out.println("PostID: "+rs2.getString("postid") +", ThreadID: " + rs2.getString("threadID"));
           }
       } catch (Exception e) {
           System.out.println("Error finding posts");
       }


   }

   public static void main(String[] args) {
       SearchCtrl searchCtrl = new SearchCtrl();
       searchCtrl.connect();
       searchCtrl.searchForKeyword("hei");
       System.out.println("Posts retrieved");
   }
}
