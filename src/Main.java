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
        System.out.println("Du valgte  1: Legg til ny post i ny tråd.");
        System.out.println("Skriv inn tittel: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Skriv inn innhold: ");
        String text = scanner.nextLine();
        System.out.println("Skriv inn mappenavn: ");
        String folderName = scanner.nextLine();
        System.out.println("Skriv inn tag: ");
        String tagName = scanner.nextLine();

        int threadID = postCtrl.nextThreadID();
        int folderID = postCtrl.findFolderID(folderName);
        if (folderID == -1) {
            System.out.println("Feil: Ingen mappe med dette navnet");
        }
        postCtrl.addThread(threadID, title, text, false, folderID, username);
        postCtrl.assignTagToThread(threadID, tagName);
        System.out.println("Ny post lagt til. ID til denn tråden er " + threadID);
    }

    public static void addPost(Scanner scanner, String username) {
        postCtrl.startPostAdding();
        System.out.println("Du valgte  2: Svar på en post.");
        System.out.println("Skriv inn ID på tråden du vil respondere på");
        scanner.nextLine();
        int threadID = scanner.nextInt();
        System.out.println("Skriv inn tittel: ");
        scanner.nextLine();
        String title = scanner.nextLine();
        System.out.println("Skriv inn innhold: ");
        String text = scanner.nextLine();

        int postID = postCtrl.nextPostID(threadID);
        postCtrl.addPost(postID, title, text, false, username, threadID);
        System.out.println("Posten ble lagt til som svar på tråden. ID til denne posten er: " + postID);

    }

    public static void searchForPost(Scanner scanner) {
        String keyword;
        System.out.println("Du valgte 3: Søk etter tråder eller poster med nøkkelord. \n" + "Skriv inn nøkkelordet du vil søke etter:");
        scanner.nextLine();
        keyword = scanner.nextLine();
        searchCtrl.searchForKeyword(keyword);
    }

    public static void getStatistics(Scanner scanner, String username) {
        System.out.println("Du valgte  4: Finn statistikk (kun for instruktører.)");
        if (userCtrl.checkInstructor(username)) {
            scanner.nextLine();
            statisticsCtrl.getStatistics();
        }
        else {
            System.out.println("Du er ikke instruktør. Statistikk ikke tilgjengelig");
        }
    }

    public static void main(String[] args) {
        // SETUP
        userCtrl.connect();
        postCtrl.connect();
        searchCtrl.connect();
        statisticsCtrl.connect();
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
        System.out.println("Innlogging vellykket");

        int input = -1;
        System.out.println(menuString);
        while (input != 9) {
            input = lineScanner.nextInt();

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
