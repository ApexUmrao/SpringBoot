package com.apex.ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name="users", uniqueConstraints = {
                     @UniqueConstraint( columnNames = {
                             "userName", "email"})
                        })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(min = 3, max = 20)
    private String userName;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max=120)
    private String password;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST} ,
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles= new HashSet<>();

    @Getter
    @Setter
    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @JoinTable(name ="user_address",
            joinColumns = @JoinColumn(name ="userId"),
            inverseJoinColumns = @JoinColumn (name = "addressId"))
    private List<Address> address= new ArrayList<>();

    @Getter
    @Setter
    @ToString.Exclude
    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.MERGE,CascadeType.PERSIST},
            orphanRemoval = true)
    private Set<Product> products= new HashSet<>();

}
