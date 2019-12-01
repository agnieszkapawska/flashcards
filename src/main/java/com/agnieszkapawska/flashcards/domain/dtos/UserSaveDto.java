package com.agnieszkapawska.flashcards.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveDto {
    private String email;
    private String userName;
    private String password;
    private Set<String> roles;
}
