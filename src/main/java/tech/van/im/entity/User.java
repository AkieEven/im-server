package tech.van.im.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String userPassword;
    private String userSalt;
    private String userEmail;
    private String userDisplayName;
    private Date userAddTime;
    private Date userUpdateTime;
}
