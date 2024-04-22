public class User {
    private String userName;
    private String password;
    private String phoneNumber;
    private Boolean isBroker;

    public User(){
        this.userName = null;
        this.password = null;
        this.phoneNumber = null;
        this.isBroker = null;

    }
    public String getUserName() { //Complexity: 0(1)
        return userName;
    }

    public void setUserName(String userName) { //Complexity: 0(1)
        this.userName = userName;
    }

    public String getPassword() { //Complexity: 0(1)
        return password;
    }

    public void setPassword(String password) { //Complexity: 0(1)
        if (isValidPassword(password)) {
            this.password = password;
        }else{
            System.out.println("The password is not strong enough");
        }
    }

    private boolean isValidPassword(String password){ // Complexity: O(n)
        boolean result = false;
        if (password.length() >= Constants.MINIMUM_PASSWORD_LENGTH) {
            if (password.contains("$") || password.contains("_") || password.contains("%")) {
                if (containsNumber(password)) {
                    result = true;
                }
            }
        }
        return result;
    }

    private boolean containsNumber(String string) { // Complexity O(n)
        boolean isNumberCheck = false;
        for (int i = 0; i <= Constants.LAST_DIGIT; i++) {
            if (string.contains("" + i)) {
                isNumberCheck = true;
                break;
            }
        }
        return isNumberCheck;
    }

    public String getPhoneNumber() { //Complexity: 0(1)
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) { //Complexity: 0(1)
        if(phoneNumberValidation(phoneNumber)){
            this.phoneNumber = phoneNumber;
        }
    }

    private boolean phoneNumberValidation(String phoneNumber){ // Complexity O(n)
        boolean isPhoneNumber = true;
        if (phoneNumber.length() == Constants.PHONE_NUMBER_LENGTH && phoneNumber.startsWith("05")) {
            for (int i = 0; i < phoneNumber.length(); i++) {
                if (!(containsNumber( "" + phoneNumber.charAt(i)))) {
                    isPhoneNumber = false;
                    System.out.println("Phone number is not acceptable, please try again");
                    break;
                }
            }
        }

        else {
            isPhoneNumber = false;
            System.out.println("Phone number is not acceptable, please try again");
        }
        return isPhoneNumber;
    }

    public void setBroker(boolean broker) { //Complexity: 0(1)
        this.isBroker = broker;
    }

    public Boolean getBroker() { //Complexity: 0(1)
        return isBroker;
    }

    public String toString(){  //Complexity: 0(1)
        return this.getUserName() + " " + this.getPhoneNumber();
    }


}