package com.example.app.dto;

import com.example.app.dto.model.UserProfileDTO;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserReviewCountDTO {
    @Getter
    @Setter
    UserProfileDTO userProfileDTO;
    @Getter
    @Setter
    Integer count;
}
