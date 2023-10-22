package com.infinity.usermanagement.model.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVM {

    private String id;

    private String email;

    private String password;

    private String role;
}