package tech.van.im.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Friendship {
    private Long friendshipOwnerId;
    private Long friendshipFriendId;
    private String friendshipName;
}
