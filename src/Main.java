import java.util.Scanner;
public class Main {
    private static final RealEstate realEstate = new RealEstate();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) { //Complexity: O(n)
        mainMenu();
    }

    private static void mainMenu() { //Complexity: O(n)
        int userInput;

        System.out.println("Please choose the action number");
        do {
            System.out.println("""
                    1. Sign up
                    2. Log in
                    3. End program""");
            userInput = scanner.nextInt();
            scanner.nextLine();

            if (userInput == Constants.FIRST_MAIN_MENU_OPTION) {
                realEstate.createUser();
                userInput = Constants.INVALID_VALUE;
            }

            else if (userInput == Constants.SECOND_MAIN_MENU_OPTION) {
                User currentUser = realEstate.login();

                if (currentUser != null) {
                    System.out.println("Login successful. Welcome: " + currentUser.getUserName());
                    loginMenu(currentUser);
                }
                userInput = Constants.INVALID_VALUE;
            }
        } while (!(userInput == Constants.THIRD_MAIN_MENU_OPTION));
    }

    private static void loginMenu(User currentUser){ //Complexity: O(n)
        int userInput;
        System.out.println("Please choose the action number");
        do {
            System.out.println("""
                                1. Publish new property
                                2. Remove property publication
                                3. Show all the property publication
                                4. Show all your property publications
                                5. Search property by filters
                                6. Sign out and return to main menu""");
            userInput = scanner.nextInt();
            scanner.nextLine();

            switch(userInput){
                case Constants.FIRST_LOGIN_MENU_OPTION -> {
                    if(realEstate.postNewProperty(currentUser)){
                        System.out.println("Publishment was successful!");
                    }else{
                        System.out.println("Publishment has failed!");
                    }
                }
                case Constants.SECOND_LOGIN_MENU_OPTION -> realEstate.removeProperty(currentUser);
                case Constants.THIRD_LOGIN_MENU_OPTION -> realEstate.printAllProperties();
                case Constants.FOURTH_LOGIN_MENU_OPTION -> realEstate.printProperties(currentUser);
                case Constants.FIFTH_LOGIN_MENU_OPTION -> {
                    Property[] filteredProperties = realEstate.search();
                    realEstate.printFilteredProperties(filteredProperties);
                }
            }
        } while (userInput != Constants.SIXTH_LOGIN_MENU_OPTION);
    }
}