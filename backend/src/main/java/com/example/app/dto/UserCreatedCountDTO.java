package com.example.app.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserCreatedCountDTO {
    @Getter
    @Setter
    String userHandle;
    @Getter
    @Setter
    Integer reviewCount;
    @Getter
    @Setter
    Integer productCount;
    @Getter
    @Setter
    Integer manufacturerCount;
}
