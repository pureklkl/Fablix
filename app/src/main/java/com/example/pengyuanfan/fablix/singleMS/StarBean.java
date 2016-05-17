package com.example.pengyuanfan.fablix.singleMS;

/**
 * Created by pengyuanfan on 5/14/2016.
 */
public class StarBean {
    String firstName;
    String lastName;
    String name;
    String id;

    public StarBean(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.name = firstName+" "+lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
