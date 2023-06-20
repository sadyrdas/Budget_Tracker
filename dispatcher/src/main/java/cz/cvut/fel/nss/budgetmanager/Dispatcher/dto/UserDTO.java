package cz.cvut.fel.nss.budgetmanager.Dispatcher.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String email;
    private String password;
    private String username;
    private Long id;
}
