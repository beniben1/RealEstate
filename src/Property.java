public class Property {
    private City city;
    private String street;
    private int numberOfRooms;
    private int price;
    private int type;
    private Boolean forRent;
    private int houseNumber;
    private Integer floorNumber;
    private User user;

    public Property () {

    }

    public City getCity() { //Complexity: 0(1)
        return city;
    }

    public void setCity(City city) { //Complexity: 0(1)
        this.city = city;
    }

    public String getStreet() { //Complexity: 0(1)
        return street;
    }

    public void setStreet(String streetName) { //Complexity: 0(n)
        if(checkIfStreetExists(this.city,streetName)){
            streetName = streetName.substring(0,1).toUpperCase() + streetName.substring(1);
            this.street = streetName;
        }
    }

    private boolean checkIfStreetExists (City city, String streetName) { //Complexity: O(n)
        boolean result = false;

        for (int i = 0; i < city.getStreets().length; i++) {
            if(streetName.equals(city.getStreets()[i].toLowerCase().trim())){
                result = true;
                break;
            }
        }

        return result;
    }

    public int getNumberOfRooms() { //Complexity: 0(1)
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) { //Complexity: 0(1)
        this.numberOfRooms = numberOfRooms;
    }

    public int getPrice() { //Complexity: 0(1)
        return price;
    }

    public void setPrice(int price) { //Complexity: 0(1)
        this.price = price;
    }

    public int getType() { //Complexity: 0(1)
        return type;
    }

    public void setType(int type) { //Complexity: 0(1)
        if (checkType(type)) {
            this.type = type;
        }
    }

    private boolean checkType (int type) { //Complexity: 0(1)
        return type >= 1 && type <= 3;
    }

    public Boolean isForRent() { //Complexity: 0(1)
        return forRent;
    }

    public void setForRent(int forRent) { //Complexity: 0(1)
        switch (forRent) {
            case Constants.FOR_RENT_OPTION -> this.forRent = true;
            case Constants.FOR_SALE_OPTION -> this.forRent = false;
        }
    }

    public int getHouseNumber() { //Complexity: 0(1)
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) { //Complexity: 0(1)
        this.houseNumber = houseNumber;
    }

    public int getFloorNumber() { //Complexity: 0(1)
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) { //Complexity: 0(1)
        this.floorNumber = floorNumber;
    }

    public User getUser() { //Complexity: 0(1)
        return user;
    }

    public void setUser(User user) { //Complexity: 0(1)
        this.user = user;
    }

    public String toString () { //Complexity: 0(1)
        String output = this.city.getCityName() + " - " + this.street + " " + this.houseNumber + ". \n";
        switch(this.type){
            case Constants.REGULAR_APARTMENT_TYPE -> output += "Regular apartment ";
            case Constants.PENTHOUSE_APARTMENT_TYPE -> output += "Penthouse apartment ";
            case Constants.LAND_HOUSE_TYPE -> output += "Land house ";
        }
        if(isForRent()){
            output += "- for rent: ";
        }else{
            output += "- for sale: ";
        }
        output += this.numberOfRooms + " rooms" + (this.floorNumber == null ? ".\n" : ", floor " + this.floorNumber + ".\n");
        output += "Price: " + this.price + "$.\n";
        output += "Contact info: " + this.user + " ";
        if(user.getBroker()){
            output += "(real estate broker).";
        }else{
            output += "(regular user).";
        }

        return output;
    }
}