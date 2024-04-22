public class City {

    private final String cityName;
    private final String district;
    private final String[] streets;

    public City (String cityName, String district, String[] streets) {
        this.cityName = cityName;
        this.district = district;
        this.streets = streets;
    }

    public String getCityName() { //Complexity: 0(1)
        return cityName;
    }

    public String getDistrict() { //Complexity: 0(1)
        return district;
    }

    public String[] getStreets() { //Complexity: 0(1)
        return streets;
    }

}