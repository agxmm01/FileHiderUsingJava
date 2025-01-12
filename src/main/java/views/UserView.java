package views;

import dao.DataDAO;
import model.Data;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserView {
    private String loggedEmail;

    public UserView(String loggedEmail) {
        this.loggedEmail = loggedEmail;
    }

    public void home() {
        do {
            System.out.println("Welcome " + loggedEmail);  // Menu Driven Program Home Page
            System.out.println("Press 1 to show your hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int choice = Integer.parseInt(sc.nextLine());  // Ask the user for input and parse it as Integer
            switch (choice) {
                case 1 -> {
                    try {
                        List<Data> files = DataDAO.getAllFiles(loggedEmail); //List to get list of all files present Data Database Access Operation
                        System.out.println("ID - File Name");
                        for (Data file : files) {    // For loop to get files of type Data
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {   //Check for SQL Exception if there is failure to access Database
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    System.out.println("Enter the file Path : ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0,f.getName(),path , this.loggedEmail);  // Create a file of type Data
                    try {
                        DataDAO.hideFile(file);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    List<Data> files = null;
                    try {
                        files = DataDAO.getAllFiles(this.loggedEmail);
                        System.out.println("ID - File Name");
                        for (Data file : files) {
                            System.out.println(file.getId() + " - " + file.getFileName());
                        }
                        System.out.println("Enter the id of file to unhide the file : ");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValid = false;   // Check for the hidden file that if it is present
                        for (Data file : files) {
                            if (file.getId() == id) {
                                isValid = true;     // Change it to true if it is valid
                                break;
                            }
                        }
                        if (isValid) {
                            DataDAO.unhideFile(id);     // And if it is valid then unhide the file of that id
                        } else {
                            System.out.println("Wrong ID!");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch ( IOException e) {
                        e.printStackTrace();
                    }
                }
                case 0 -> {
                    System.exit(0);
                }
            }
        } while (true);
    }
}
