package org.example.schedule.entity;

import jakarta.persistence.*;
import lombok.Getter;



@Getter
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public UserEntity(){
    }

    public UserEntity(String username,String email,String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void updateUserInfo(String username, String email) {
        this.username = username;
        this.email = email;
    }

}
