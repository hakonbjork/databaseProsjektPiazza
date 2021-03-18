public class TestCtrl extends DBConn {

    public static void main(String[] args) {
        TestCtrl test = new TestCtrl();
        test.connect();
        System.out.println("Hei på deg");
        test.closeConnection();
    }
}
