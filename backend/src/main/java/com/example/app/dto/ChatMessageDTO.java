package com.example.app.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ChatMessageDTO {
    @Getter
    @Setter
    String nickname;
    @Getter
    @Setter
    String message;
}
