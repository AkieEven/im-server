package tech.van.im.dao;

import tech.van.im.entity.Friendship;

import java.util.List;

public interface FriendshipMapper {
    int save(Friendship friendship);
    List<Long> finaAllFriendById(Long ownerId);
}
