package com.testing.todorvachev.models;

import com.github.javafaker.Faker;

public class RegistrationData {
    private String userId;
    private String username;
    private String password;
    private String address;
    private String country;
    private String zip;
    private String email;
    private String gender;
    private boolean speaksEnglish;
    private String about;

    // Constructor using Faker for generating test data
    public RegistrationData(boolean isValid) {
        Faker faker = new Faker();

        if (isValid) {
            this.userId = faker.bothify("??###");
            this.username = faker.name().firstName();
            this.password = faker.internet().password(8, 11);
            this.address = faker.bothify("????????????????????");
            this.country = "Canada";
            this.zip = faker.numerify("#####");
            this.email = faker.internet().emailAddress();
            this.gender = "Female";
        } else {
            this.userId = faker.lorem().characters(3);
            this.username = faker.name().username();
            this.password = faker.internet().password(4, 8);
            this.address = faker.address().streetAddress();
            this.country = "Australia";
            this.zip = faker.bothify("?###");
            this.email = faker.internet().emailAddress();
            this.gender = "Male";
        }
        this.speaksEnglish = true;
        this.about = faker.lorem().sentence();
    }

    // Getters for all fields
    public String getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getAddress() { return address; }
    public String getCountry() { return country; }
    public String getZip() { return zip; }
    public String getEmail() { return email; }
    public String getGender() { return gender; }
    public boolean isSpeaksEnglish() { return speaksEnglish; }
    public String getAbout() { return about; }
}