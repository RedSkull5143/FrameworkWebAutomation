package com.omshinde.capstone.models;

import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String first_name;
    private String last_name;
    private String emailID;
    private String password;

    public User init(){
        Faker faker=new Faker();
        return User.builder()
                .first_name(faker.name().firstName())
                .last_name(faker.name().lastName())
                .emailID(faker.internet().emailAddress())
                .password("Pass123@")
                .build();
    }

    public User userWithoutEmail(){
        User user=this.init();
        user.setEmailID("");
        return user;
    }

    public User userWithoutPassword() {
        User user=this.init();
        user.setPassword("");
        return user;
    }

    public User userWithValidCredentials(){
        return User.builder()
                .first_name("Om")
                .last_name("Shinde")
                .emailID("omshinde@gmail.com")
                .password("OmShinde@1234")
                .build();
    }

    public User userWithInvalidCredentials(){
        return User.builder()
                .first_name("Om")
                .last_name("Shinde")
                .emailID("omshnde@gmail.com")
                .password("OmShinde@1234")
                .build();
    }
}
