package com.logitrips.userapp.model;

/**
 * Created by Ulziiburen on 12/21/15.
 */
public class Car implements java.io.Serializable {
    private int car_id;
    private String car_model;
    private int year;
    private String car_class;
    private double car_rating;
    private String[] car_pic_urls;


    private String start_date;
    private String end_date;
    private int trip_status;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(int trip_status) {
        this.trip_status = trip_status;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }

    public double getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(double total_fee) {
        this.total_fee = total_fee;
    }

    private int payment_status;
    private double total_fee;

    private String[] driver_lang;
    private int location;
    private double hourly_price;
    private double daily_price;
    private double day2_price;
    private int driver_id;
    private String driver_name;
    private String driver_pic_url;
    private String driver_knowledge;

    public String getLang_driver() {
        return lang_driver;
    }

    public void setLang_driver(String lang_driver) {
        this.lang_driver = lang_driver;
    }

    private String lang_driver;
    private int driver_year;
    private int driver_smoking;

    public int getDriver_smoking() {
        return driver_smoking;
    }

    public void setDriver_smoking(int driver_smoking) {
        this.driver_smoking = driver_smoking;
    }



        public String[] getDriver_lang() {
        return driver_lang;
    }

    public void setDriver_lang(String[] driver_lang) {
        this.driver_lang = driver_lang;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public String getCar_model() {
        return car_model;
    }

    public void setCar_model(String car_model) {
        this.car_model = car_model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCar_class() {
        return car_class;
    }

    public void setCar_class(String car_class) {
        this.car_class = car_class;
    }

    public double getCar_rating() {
        return car_rating;
    }

    public void setCar_rating(double car_rating) {
        this.car_rating = car_rating;
    }

    public String[] getCar_pic_urls() {
        return car_pic_urls;
    }

    public void setCar_pic_urls(String[] car_pic_urls) {
        this.car_pic_urls = car_pic_urls;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public double getHourly_price() {
        return hourly_price;
    }

    public void setHourly_price(double hourly_price) {
        this.hourly_price = hourly_price;
    }

    public double getDaily_price() {
        return daily_price;
    }

    public void setDaily_price(double daily_price) {
        this.daily_price = daily_price;
    }

    public double getDay2_price() {
        return day2_price;
    }

    public void setDay2_price(double day2_price) {
        this.day2_price = day2_price;
    }

    public int getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(int driver_id) {
        this.driver_id = driver_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public String getDriver_pic_url() {
        return driver_pic_url;
    }

    public void setDriver_pic_url(String driver_pic_url) {
        this.driver_pic_url = driver_pic_url;
    }

    public String getDriver_knowledge() {
        return driver_knowledge;
    }

    public void setDriver_knowledge(String driver_knowledge) {
        this.driver_knowledge = driver_knowledge;
    }

    public int getDriver_year() {
        return driver_year;
    }

    public void setDriver_year(int driver_year) {
        this.driver_year = driver_year;
    }


}
