import java.util.Scanner;

public class RealEstate {
    private final Scanner scanner = new Scanner(System.in);
    private final City[] cities;
    private Property[] properties;
    private User[] users;


    public RealEstate () {
        cities = new City[] {
                new City("Ashkelon", "south", new String[] {"Dovev", "Acalanit"}),
                new City("Ashdod", "south", new String[] {"Mango", "Rambam"}),
                new City("Tel aviv", "central", new String[] {"Alenbi", "Dizingof"}),
                new City("Rishon Letsiyon", "central", new String[] {"Hadad", "Kaplan"}),
                new City("Beer sheva", "negev", new String[] {"Nesher", "Gilad"}),
                new City("Dimona", "negev", new String[] {"Agefen", "Ayona"}),
                new City("Netanya", "sharon", new String[] {"Holand", "Kedma"}),
                new City("Kfar Yona", "sharon", new String[] {"Egoz", "Alfasi"}),
                new City("Tiberias", "north", new String[] {"Golda", "Golani"}),
                new City("Haifa", "north", new String[] {"Sirkin", "Arad"}),
        };

    }

    public void createUser () { //Complexity: O(n)
        User newUser = new User();
        setUserNameFromUser(newUser);
        setPasswordFromUser(newUser);
        setPhoneNumberFromUser(newUser);
        setIsBrokerFromUser(newUser);

        users = addUser(users,newUser);
        System.out.println("Sign up is complete");
    }

    private void setUserNameFromUser(User newUser){ //Complexity: O(n)
        String userName;
        do {
            System.out.println("Enter the user name:");
            userName = scanner.nextLine();

            if(checkIfAvailable(userName)){
                newUser.setUserName(userName);
            }
        } while (newUser.getUserName() == null);
    }

    private void setPasswordFromUser(User newUser){ //Complexity: O(1)
        String userPassword;
        System.out.println("""
                The password must contain the following elements:
                - At least 1 character must be a digit
                - Password must be minimum 5 characters long
                - At least 1 of this special characters: % _ $""");
        do {
            System.out.println("Enter your password: ");
            userPassword = scanner.nextLine();
            newUser.setPassword(userPassword);
        } while (newUser.getPassword() == null);
    }

    private void setPhoneNumberFromUser(User newUser){ //Complexity: O(1)
        String phoneNumber;
        do {
            System.out.println("Enter your phone number");
            phoneNumber = scanner.nextLine();
            newUser.setPhoneNumber(phoneNumber);
        } while (newUser.getPhoneNumber() == null);
    }

    private void setIsBrokerFromUser(User newUser){ //Complexity : O(1)
        do {
            System.out.println("Are you a broker? Answer " + Constants.POSITIVE_ANSWER + "/" + Constants.NEGATIVE_ANSWER);
            String userAnswer = scanner.nextLine();
            userAnswer = userAnswer.toLowerCase().trim();

            if(checkAnswer(userAnswer)){
                switch(userAnswer){
                    case Constants.POSITIVE_ANSWER -> newUser.setBroker(true);
                    case Constants.NEGATIVE_ANSWER -> newUser.setBroker(false);
                }
            }
        } while (newUser.getBroker() == null);
    }


    public User login() { //Complexity: O(n)

        User currentUser = null;

        System.out.println("Enter your user name");
        String userLogin = scanner.nextLine();
        System.out.println("Enter your password");
        String userPassword = scanner.nextLine();

        int userIndex = checkIfUserExists(userLogin, userPassword);

        if (userIndex != Constants.INVALID_VALUE) {
            currentUser = users[userIndex];
        }

        return currentUser;
    }

    public boolean postNewProperty (User user) {  //Complexity O(n)
        final int PUBLISHMENT_QUANTITY;
        boolean result = false;

        if(user.getBroker()){
            PUBLISHMENT_QUANTITY = 5;
        }else{
            PUBLISHMENT_QUANTITY = 3;
        }
        int countUserPublishment = countUserPublishments(user);

        if(countUserPublishment>=PUBLISHMENT_QUANTITY){
            System.out.println("You have reached the maximum amount of publishments");
        }else{
            Property newProperty = new Property();

            String cityName = getCityNameFromUser();
            int cityIndex = checkIfCityExists(cityName);

            if(cityIndex == Constants.INVALID_VALUE){
                System.out.println("The city you wrote doesnt exist");
            }else{
                newProperty.setCity(cities[cityIndex]);

                String streetName = getStreetNameFromUser(cityIndex);
                newProperty.setStreet(streetName);

                if(newProperty.getStreet() == null){
                    System.out.println("The street doesnt exist");
                }else{

                    int typeOfProperty = getTypeOfProperty();
                    newProperty.setType(typeOfProperty);

                    if(newProperty.getType() != typeOfProperty) {
                        System.out.println("Your input was not correct");
                    }else{
                        if (    newProperty.getType() == Constants.REGULAR_APARTMENT_TYPE ||
                                newProperty.getType() == Constants.PENTHOUSE_APARTMENT_TYPE)
                        {

                            setFloorNumberFromUser(newProperty);
                        }

                        setRoomsNumberFromUser(newProperty);
                        setHouseNumberFromUser(newProperty);
                        setForRentFromUser(newProperty);

                        if (newProperty.isForRent() == null) {
                            System.out.println("You chose invalid option");
                        } else {
                            setPropertyPriceFromUser(newProperty);
                            newProperty.setUser(user);
                            properties = addProperty(properties, newProperty);
                            result = true;
                        }
                    }
                }
            }
        }
        return result;
    }

    private String getCityNameFromUser(){ //Complexity: O(n)
        String cityName;
        for (int i = 0; i < cities.length; i++) {
            int num = i + 1;
            System.out.println(num + ". " + cities[i].getCityName());
        }

        System.out.println("Enter the name of the city");
        cityName = scanner.nextLine();
        cityName = cityName.toLowerCase().trim();
        return cityName;
    }

    private String getStreetNameFromUser(int cityIndex){ //Complexity: O(n)
        String streetName;
        for (int i = 0; i < cities[cityIndex].getStreets().length; i++) {
            System.out.println(cities[cityIndex].getStreets()[i]);
        }

        System.out.println("Enter the name of the street");
        streetName = scanner.nextLine();
        streetName = streetName.toLowerCase().trim();

        return streetName;
    }

    private int getTypeOfProperty(){ //Complexity : O(1)
        int typeOfProperty;

        printMessageToChooseType();
        typeOfProperty = scanner.nextInt();
        scanner.nextLine();

        return typeOfProperty;
    }

    private void setFloorNumberFromUser(Property newProperty){ //Complexity : O(1)
        int floorNumber;
        System.out.println("Enter the floor number");
        floorNumber = scanner.nextInt();
        scanner.nextLine();
        newProperty.setFloorNumber(floorNumber);
    }

    private void setHouseNumberFromUser(Property newProperty){ //Complexity : O(1)
        int houseNumber;
        System.out.println("Enter the house number");
        houseNumber = scanner.nextInt();
        scanner.nextLine();
        newProperty.setHouseNumber(houseNumber);
    }

    private void setRoomsNumberFromUser(Property newProperty){ //Complexity : O(1)
        int numberOfRooms;
        System.out.println("Enter the number of rooms");
        numberOfRooms = scanner.nextInt();
        scanner.nextLine();
        newProperty.setNumberOfRooms(numberOfRooms);
    }

    private void setForRentFromUser(Property newProperty){ //Complexity: O(1)
        int forRent;
        printMessageToChooseForRent();
        forRent = scanner.nextInt();
        scanner.nextLine();
        newProperty.setForRent(forRent);
    }

    private void setPropertyPriceFromUser(Property newProperty){ //Complexity: O(1)
        int propertyPrice;
        System.out.println("Enter the price of the property");
        propertyPrice = scanner.nextInt();
        scanner.nextLine();
        newProperty.setPrice(propertyPrice);
    }

    public void removeProperty(User user){ //Complexity: O(n)
        int userPosts = countUserPublishments(user);
        if(userPosts == 0){
            System.out.println("You have no publishments to delete");
        }else{
            int[][] postIdArray = new int[userPosts][];
            System.out.println("Choose the number of the property you'd like to delete");

            int option = 0;
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].getUser().getUserName().equals(user.getUserName())) {
                    postIdArray[option] = new int[]{
                            option+1, i
                    };
                    option++;
                    System.out.println(option + ") " + properties[i].getCity().getCityName() + " - " + properties[i].getStreet() + " " + properties[i].getHouseNumber());
                }
            }

            int userInput = scanner.nextInt();
            scanner.nextLine();
            int postIndex = getChosenPostIndex(postIdArray,userInput);
            if(postIndex != Constants.INVALID_VALUE){
                properties = deletePost(postIndex);
                System.out.println("The post was successfully removed!");
            }else{
                System.out.println("Remove process failed! You chose invalid option");
            }
        }
    }

    public void printAllProperties(){  //Complexity: O(n)
        int count = 0;
        if(properties != null) {
            for (int i = 0; i < properties.length; i++) {
                count++;
                System.out.println(count + ") " + properties[i] + "\n");
            }
        }else{
            System.out.println("Currently there are no publications");
        }
    }

    public void printProperties (User user) { //Complexity: O(n)
        int count = 0;
        if (properties != null) {
            for (int i = 0; i < properties.length; i++) {
                if (properties[i].getUser().getUserName().equals(user.getUserName())) {
                    count++;
                    System.out.println(count + ") " + properties[i] + "\n");
                }
            }
            if(count == 0){
                System.out.println("You have no publications");
            }
        }

        else {
            System.out.println("You have no publications");
        }
    }

    public Property[] search () { //Complexity O(n)
        Property[] filteredProperties = null;
        if (properties != null && properties.length > 0) {
            System.out.println("Lets start searching. \n" +
                    "If you want to skip a filter just write " + Constants.SKIP_FILTER_NUMBER);
            Boolean forRent = isForRentFilter();
            Integer type = getTypeFilter();
            Integer roomNumber = getRoomNumberFilter();
            Integer minimumPrice = getMinimumPriceFilter();
            Integer maximumPrice = getMaximumPriceFilter(minimumPrice);


            filteredProperties = createFilteredArray(properties,forRent,type,roomNumber,minimumPrice,maximumPrice);

            if (filteredProperties == null) {
                System.out.println("No results found");
            }
        }else {
            System.out.println("There are no properties to search");
        }

        return filteredProperties;
    }

    private Property[] createFilteredArray(Property[] properties, Boolean forRent,Integer type,Integer roomNumber,Integer minimumPrice,Integer maximumPrice){ //Complexity: O(n)
        Property[] filteredProperties = null;
        for (int i = 0; i < properties.length; i++) {

            if (    (forRent == null || properties[i].isForRent() == forRent) &&
                    (type == null || properties[i].getType() == type) &&
                    (roomNumber == null || properties[i].getNumberOfRooms() == roomNumber) &&
                    (minimumPrice == null || properties[i].getPrice() >= minimumPrice) &&
                    (maximumPrice == null || properties[i].getPrice() <= maximumPrice)
            ) {
                filteredProperties = addProperty(filteredProperties, properties[i]);
            }
        }
        return filteredProperties;
    }

    private Integer getMaximumPriceFilter (Integer minimumPrice) { //Complexity: O(1)
        Integer maximumPrice = null;
        int userInput;
        boolean endLoop = false;

        do {
            System.out.println("Enter the maximum price");
            userInput = scanner.nextInt();
            scanner.nextLine();

            if(userInput != Constants.SKIP_FILTER_NUMBER) {
                if(minimumPrice != null) {
                    if (minimumPrice >= userInput) {
                        System.out.println("Maximum price should be higher then minimum price");
                    }else{
                        maximumPrice = userInput;
                        endLoop = true;
                    }
                }else{
                    maximumPrice = userInput;
                    endLoop = true;
                }
            }else{
                endLoop = true;
            }
        } while (!endLoop);

        return maximumPrice;
    }

    private Integer getMinimumPriceFilter () { //Complexity O(1)
        Integer minimumPrice = null;
        int userInput;

        System.out.println("Enter the minimum price");
        userInput = scanner.nextInt();
        scanner.nextLine();

        if(userInput != Constants.SKIP_FILTER_NUMBER){
            minimumPrice = userInput;
        }

        return minimumPrice;
    }

    private Integer getRoomNumberFilter () { // Complexity: O(1)
        int userInput;
        Integer roomNumber = null;


        System.out.println("Enter the number of rooms you want");
        userInput = scanner.nextInt();
        scanner.nextLine();

        if (userInput != Constants.SKIP_FILTER_NUMBER) {
            roomNumber = userInput;
        }

        return roomNumber;
    }

    private void printMessageToChooseType(){
        System.out.println("""
                    Enter the number of property type
                    1. Regular apartment in apartment building
                    2. Penthouse in apartment building
                    3. Land house""");
    }

    private Integer getTypeFilter (){ // Complexity O(1)
        int userInput;
        Integer type = null;
        boolean endLoop = false;

        do {
            printMessageToChooseType();
            userInput = scanner.nextInt();

            if (userInput >= Constants.REGULAR_APARTMENT_TYPE && userInput<=Constants.LAND_HOUSE_TYPE
                    || userInput == Constants.SKIP_FILTER_NUMBER) {
                switch (userInput) {
                    case Constants.REGULAR_APARTMENT_TYPE, Constants.LAND_HOUSE_TYPE, Constants.PENTHOUSE_APARTMENT_TYPE -> type = userInput;
                }
                endLoop = true;
            }

            else {
                System.out.println("Please choose a relevant option or skip by writing " + Constants.SKIP_FILTER_NUMBER);
            }
        } while (!endLoop);
        return type;
    }

    private void printMessageToChooseForRent(){
        System.out.println("""
                    Enter the number of relevant option:
                    1. Property for rent
                    2. Property for sale""");
    }

    private Boolean isForRentFilter (){ //Complexity : O(1)
        int userInput;
        Boolean forRent = null;
        boolean endLoop = false;

        do {
            printMessageToChooseForRent();
            userInput = scanner.nextInt();
            scanner.nextLine();

            if (userInput == Constants.FOR_RENT_OPTION || userInput == Constants.FOR_SALE_OPTION
                    || userInput == Constants.SKIP_FILTER_NUMBER) {
                endLoop = true;

                switch (userInput) {
                    case Constants.FOR_RENT_OPTION -> forRent = true;
                    case Constants.FOR_SALE_OPTION -> forRent = false;
                }
            }

            else {
                System.out.println("Please choose a relevant option or skip by writing " + Constants.SKIP_FILTER_NUMBER);
            }
        } while (!endLoop);
        return forRent;
    }


    private Property[] deletePost(int postIndex){ // Complexity: O(n)
        int arrLength;

        if(properties == null) {
            arrLength = 0;
        }else{
            arrLength = properties.length;
        }

        Property[] tempProperties = new Property[arrLength - 1];

        for (int i = 0; i < properties.length; i++) {
            if(i != postIndex) {
                tempProperties[i] = properties[i];
            }
        }

        return tempProperties;
    }

    private int getChosenPostIndex(int[][] postId, int userInput){ // Complexity: O(n)
        int index = Constants.INVALID_VALUE;
        if(postId != null) {
            for (int i = 0; i < postId.length; i++) {
                if (postId[i][0] == userInput) {
                    index = postId[i][1];
                    break;
                }
            }
        }
        return index;
    }
    private int countUserPublishments(User user){ // Complexity: O(n)
        int quantity = 0;
        if(properties != null) {
            for (int i = 0; i < properties.length; i++) {
                if(properties[i].getUser().getUserName().equals(user.getUserName())){
                    quantity++;
                }
            }
        }
        return quantity;
    }




    private int checkIfCityExists (String cityName) { // Complexity : O(n)
        int indexOfCity = Constants.INVALID_VALUE;
        for (int i = 0; i < cities.length; i++) {
            if (cities[i].getCityName().toLowerCase().equals(cityName)) {
                indexOfCity = i;
                break;
            }
        }
        return indexOfCity;
    }

    private int checkIfUserExists (String userLogin, String userPassword) { // Complexity: O(n)
        int result = Constants.INVALID_VALUE;
        if(users != null) {
            for (int i = 0; i < users.length; i++) {
                if (users[i].getUserName().equals(userLogin)) {
                    if (users[i].getPassword().equals(userPassword)) {
                        result = i;
                        break;
                    }
                }
            }
        }

        if (result == Constants.INVALID_VALUE) {
            System.out.println("The username or the password is incorrect");
        }
        return result;
    }
    private User[] addUser (User[] users, User newUser) { // Complexity: O(n)
        int usersLength;
        if(users == null){
            usersLength = 0;
        }else{
            usersLength = users.length;
        }
        User[] tempUsers = new User[usersLength + 1];

        for (int i = 0; i < usersLength; i++) {
            tempUsers[i] = users[i];
        }

        tempUsers[usersLength] = newUser;

        return tempUsers;
    }

    private Property[] addProperty (Property[] properties, Property newProperty) { //Complexity : O(n)
        int arrLength;

        if (properties == null) {
            arrLength = 0;
        }else {
            arrLength = properties.length;
        }

        Property[] tempProperties = new Property[arrLength + 1];

        for (int i = 0; i < arrLength; i++) {
            tempProperties[i] = properties[i];
        }

        tempProperties[arrLength] = newProperty;
        return tempProperties;
    }

    private boolean checkAnswer(String userAnswer){  //Complexity : O(1)
        boolean result = false;
        if(userAnswer.equals(Constants.POSITIVE_ANSWER) || userAnswer.equals(Constants.NEGATIVE_ANSWER)){
            result = true;
        }else{
            System.out.println("Please answer correctly");
        }
        return result;
    }

    private boolean checkIfAvailable(String userName){ //Complexity: O(n)
        boolean isAvailable = true;
        if(users != null) {
            for (int i = 0; i < users.length; i++) {
                if (userName.equals(users[i].getUserName())) {
                    System.out.println("The user name is already taken");
                    isAvailable = false;
                    break;
                }
            }
        }
        return isAvailable;
    }

    public void printFilteredProperties (Property[] properties) { //Complexity: O(n)
        int count = 0;

        if (properties != null) {
            for (int i = 0; i < properties.length; i++) {
                count++;
                System.out.println(count + ") " + properties[i] + "\n");
            }
        }
    }
}