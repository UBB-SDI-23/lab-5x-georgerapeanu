package com.example.app.dto.model;

import com.example.app.model.User;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;

public class UserDTO {
    private Integer id;
    @NotBlank
    private String name;
    private String handle;
    private String email;
    private Date birthday;
    private Date registeredAt;

    public UserDTO(Integer id, String name, String handle, String email, Date birthday, Date registeredAt) {
        this.id = id;
        this.name = name;
        this.handle = handle;
        this.email = email;
        this.birthday = birthday;
        this.registeredAt = registeredAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public static UserDTO fromUser(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getHandle(),
                user.getEmail(),
                user.getBirthday(),
                user.getRegisteredAt()
        );
    }

    public static User toUser(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setHandle(userDTO.getHandle());
        user.setEmail(userDTO.getEmail());
        user.setBirthday(userDTO.getBirthday());
        user.setRegisteredAt(userDTO.getRegisteredAt());
        return user;
    }
}
