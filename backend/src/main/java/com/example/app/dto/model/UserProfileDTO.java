package com.example.app.dto.model;

import com.example.app.model.UserProfile;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.sql.Date;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    @NotBlank
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String handle;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private Date birthday;
    @Getter
    @Setter
    private Date registeredAt;
    public static UserProfileDTO fromUserProfile(UserProfile userProfile) {
        return new UserProfileDTO(
                userProfile.getName(),
                userProfile.getHandle(),
                userProfile.getEmail(),
                userProfile.getBirthday(),
                userProfile.getRegisteredAt()
        );
    }

    public static UserProfile toUserProfile(UserProfileDTO userProfileDTO) {
        UserProfile userProfile = new UserProfile();
        userProfile.setName(userProfileDTO.getName());
        userProfile.setHandle(userProfileDTO.getHandle());
        userProfile.setEmail(userProfileDTO.getEmail());
        userProfile.setBirthday(userProfileDTO.getBirthday());
        userProfile.setRegisteredAt(userProfileDTO.getRegisteredAt());
        return userProfile;
    }
}
