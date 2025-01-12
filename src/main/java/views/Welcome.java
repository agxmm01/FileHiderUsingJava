package views;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Welcome {
    public void welcomeScreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the File Encrypter");
        System.out.println("Press 1 to Login");
        System.out.println("Press 2 to Signup");
        System.out.println("Press 0 to Exit");

        int choice = 0;
        try {
          choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (choice) {
            case 1 -> login();
            case 2 -> signUp();
            case 0 -> System.exit(0);
        }
    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your registered email address: ");
        String email = sc.nextLine().trim();  // Added trim() to remove leading/trailing spaces

        try {
            // Fixed UserDAO call and simplified logic
            if (UserDAO.isExist(email)) {
                String genOTP = GenerateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("An OTP has been sent to your email. Enter your OTP: ");
                String userOTP = sc.nextLine().trim();

                if (genOTP.equals(userOTP)) {
                   new UserView(email).home();

                } else {
                    System.out.println("Wrong OTP! Please try again.");
                }
            } else {
                System.out.println("User Not Found! Please sign up first.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database Error. Please try again later.");
        }
    }


    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name : ");
        String name = sc.nextLine();
        System.out.println("Enter your email : ");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email , genOTP);
        System.out.println("Enter your OTP : ");
        String userOTP = sc.nextLine();
        if(genOTP.equals(userOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0 -> System.out.println("User Registered Successfully");
                case 1 -> System.out.println("User already Exists ! ");
            }
        } else {
            System.out.println("Wrong OTP! ");
        }

    }
}
