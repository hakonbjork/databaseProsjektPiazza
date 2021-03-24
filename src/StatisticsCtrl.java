import java.sql.*;

public class StatisticsCtrl extends DBConn {

    public void getStatistics() {
        try {
            Statement stmt = conn.createStatement();
            String query =
                    "SELECT c.Username, NumberOfPostsRead, NumberOfPostsCreated\n" +
                    "FROM\n" +
                    "(SELECT Username, COUNT(ReadPost) AS NumberOfPostsRead\n" +
                    "FROM\n" +
                    "((SELECT ForumUser.Username as Username, UserReadPost.PostID AS ReadPost \n" +
                    "FROM ForumUser LEFT JOIN UserReadPost \n" +
                    "ON ForumUser.Username = UserReadPost.Username)\n" +
                    "UNION ALL\n" +
                    "(SELECT ForumUser.Username as Username, UserReadThread.ThreadID AS ReadPost \n" +
                    "FROM ForumUser LEFT JOIN UserReadThread \n" +
                    "ON ForumUser.Username = UserReadThread.Username)) as a\n" +
                    "GROUP BY Username) as c\n" +
                    "LEFT JOIN\n" +
                    "(SELECT Username, COUNT(CreatePost) AS NumberOfPostsCreated\n" +
                    "FROM\n" +
                    "((SELECT ForumUser.Username as Username, Post.PostID AS CreatePost \n" +
                    "FROM ForumUser LEFT JOIN Post \n" +
                    "ON ForumUser.Username = Post.PostCreator)\n" +
                    "UNION ALL\n" +
                    "(SELECT ForumUser.Username as Username, Thread.ThreadID AS CreatePost \n" +
                    "FROM ForumUser LEFT JOIN Thread \n" +
                    "ON ForumUser.Username = Thread.ThreadCreator)) as b\n" +
                    "GROUP BY Username) as d\n" +
                    "ON c.Username = d.Username\n" +
                    "ORDER BY NumberOfPostsRead DESC;"
                    ;
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Statistikk for emnet");
            while (rs.next()) {
                System.out.println(rs.getString("Username") + " Posts read: " + rs.getString("NumberOfPostsRead") + ", Posts created: " + rs.getInt("NumberOfPostsCreated"));
            }
        } catch (Exception e) {
            System.out.println("Error finding statistics");
        }


    }

    public static void main(String[] args) {
        StatisticsCtrl statCtrl = new StatisticsCtrl();
        statCtrl.connect();
        statCtrl.getStatistics();
        System.out.println("Statistics retrieved");
    }
}
