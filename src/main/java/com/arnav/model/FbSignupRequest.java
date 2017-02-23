package com.arnav.model;

/**
 * Created by HP on 2/23/2017.
 */
public class FbSignupRequest {

    /**
     * email : shankar_palsaniya@yahoo.in
     * first_name : Shankar
     * gender : male
     * id : 1295850720475223
     * name : Shankar Palsaniya
     */

    private String email;

    private String first_name;
    private String gender;
    private String id;
    private String name;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FbSignupRequest{" +
                "email='" + email + '\'' +
                ", first_name='" + first_name + '\'' +
                ", gender='" + gender + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
