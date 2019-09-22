package com.agnieszkapawska.flashcards.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveDto {

    private String email;
    private String userName;
    private String password;
}
