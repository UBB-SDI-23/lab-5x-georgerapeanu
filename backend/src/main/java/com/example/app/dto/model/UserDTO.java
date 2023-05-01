package com.example.app.dto.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {
    @Getter
    @Setter
    private String handle;
    @Getter
    @Setter
    private String password;
}
