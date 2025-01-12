import views.Welcome;

public class Main {
    public static void main(String[] args) {
        Welcome welcome = new Welcome();
        do {
            welcome.welcomeScreen(); // This calls the method of the Welcome.java class
        } while (true);
    }
}
