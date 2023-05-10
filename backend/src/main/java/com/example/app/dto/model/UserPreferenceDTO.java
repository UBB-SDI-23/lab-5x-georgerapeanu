package com.example.app.dto.model;

import com.example.app.model.User;
import com.example.app.model.UserPreference;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserPreferenceDTO {
    @Getter
    @Setter
    String handle;
    @Getter
    @Setter
    Integer page_size;

    public static UserPreference toUserPreference(UserPreferenceDTO preference, User user) {
        return new UserPreference(preference.getHandle(), preference.getPage_size(), user);
    }
    public static UserPreferenceDTO fromUserPreference(UserPreference preference) {
        return new UserPreferenceDTO(preference.getHandle(), preference.getPage_size());
    }
}
