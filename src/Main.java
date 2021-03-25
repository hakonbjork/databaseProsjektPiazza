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
        if (folderID == -1) {
            System.out.println("Error: No folder with this name found.");
        }
        postCtrl.addThread(threadID, title, text, false, folderID, username);
        postCtrl.assignTagToThread(threadID, tagName);
        System.out.println("Thread added successfully. ThreadID: " + threadID);
    }

    public static void addPost(Scanner scanner, String username) {
        postCtrl.startPostAdding();
        System.out.println("Enter ID of the thread you want to respond to");
        scanner.nextLine();
        int threadID = scanner.nextInt();
        System.out.println("Enter title: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Enter text: ");
        String text = scanner.nextLine();

        int postID = postCtrl.nextPostID(threadID);
        postCtrl.addPost(postID, title, text, false, username, threadID);
        System.out.println("Post added successfully. PostID: " + postID);

    }

    public static void searchForPost(Scanner scanner) {
        SearchCtrl searchCtrl = new SearchCtrl();
        searchCtrl.connect();
        String keyword;
        System.out.println("Du valgte 3: Søk etter tråder eller poster med nøkkelord. \n" + "Skriv inn nøkkelordet du vil søke etter:");
        scanner.nextLine();
        keyword = scanner.nextLine();
        searchCtrl.searchForKeyword(keyword);
    }

    public static void getStatistics(Scanner scanner, String username) {
        if (userCtrl.checkInstructor(username)) {
            StatisticsCtrl statCtrl = new StatisticsCtrl();
            statCtrl.connect();
            scanner.nextLine();
            System.out.println("Du valgte  4: Finn statistikk (kun for instruktører.)");
            statCtrl.getStatistics();
        }
    }

    public static void main(String[] args) {
        // SETUP
        userCtrl.connect();
        postCtrl.connect();
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

            if (input == 2) {
                addPost(lineScanner, username);
                System.out.println(menuString);
            }

            if (input == 3) {
                searchForPost(lineScanner);
                System.out.println(menuString);
            }

            if (input == 4) {
                getStatistics(lineScanner, username);
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
