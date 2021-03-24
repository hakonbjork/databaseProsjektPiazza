import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {

    private static UserCtrl userCtrl = new UserCtrl();
    private static PostCtrl postCtrl = new PostCtrl();
    private static StatisticsCtrl statisticsCtrl = new StatisticsCtrl();
    private static SearchCtrl searchCtrl = new SearchCtrl();

    private String courseCode = "TTM4356";
    private String currentUsername = "";

    public boolean login(String username, String password) {
        return userCtrl.checkUserExist(username, password);
    }

    public static void addThread(Scanner scanner, String username) {
        postCtrl.connect();
        postCtrl.startThreadAdding();
        System.out.println("Enter title: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Enter text: ");
        String text = scanner.nextLine();
        System.out.println("Enter folder: ");
        String folderName = scanner.nextLine();
        System.out.println("Enter tag: ");
        String tagName = scanner.nextLine();

        int threadID = postCtrl.nextThreadID();
        int folderID = postCtrl.findFolderID(folderName);
        postCtrl.addThread(threadID, title, text, false, folderID, username);
        postCtrl.assignTagToThread(threadID, tagName);
        System.out.println("Thread added successfully.");
    }

    public static void main(String[] args) {
        // SETUP
        userCtrl.connect();
        Main main = new Main();
        System.out.println("Velkommen til piazza. Skriv inn brukernavn for å logge inn: ");
        Scanner lineScanner = new Scanner(System.in);
        String menuString = "Meny: \n" + "1: Lag en ny tråd. \n" + "2: Kommenter på en tråd. \n" + "3: Søk etter tråder eller poster med nøkkelord. \n" + "4: Finn statistikk (kun for instruktører.)";

        // LOGIN
        String username = "";
        String password = "";
        while (!main.login(username, password)) {
            System.out.println("Skriv inn brukernavn: ");
            username = lineScanner.nextLine();
            System.out.println("Skriv inn passord: ");
            password = lineScanner.nextLine();
        }
        System.out.println("Login sucessfull");

        int input = -1;
        System.out.println(menuString);
        while (input != 9) {
            input = lineScanner.nextInt();
            // USECASE 2, MAKE A POST (FIRST POST IN THREAD IS THREAD)
            if (input == 1) {
                addThread(lineScanner, username);
                System.out.println(menuString);
            }
        }
    }

    public static void main2(String[] args) {
        System.out.println("heiwf");
        Scanner scanner = new Scanner(System.in);
        while (scanner.nextInt() != 9) {
            int input = scanner.nextInt();
            if (input == 1) {
                addThread(scanner, "håkonhotmail.com");
            }
        }
    }
}
