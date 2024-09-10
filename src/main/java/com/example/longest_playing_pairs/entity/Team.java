package com.example.longest_playing_pairs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Team name cannot be blank")
    @Size(max = 100, message = "Team name must be less than 100 characters")
    private String name;

    @NotBlank(message = "Manager full name cannot be blank")
    @Size(max = 100, message = "Manager full name must be less than 100 characters")
    private String managerFullName;

    @NotBlank(message = "Group name cannot be blank")
    @Size(max = 10, message = "Group name must be less than 10 characters")
    private String groupName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManagerFullName() {
        return managerFullName;
    }

    public void setManagerFullName(String managerFullName) {
        this.managerFullName = managerFullName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
