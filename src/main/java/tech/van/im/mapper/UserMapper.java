package tech.van.im.mapper;

import tech.van.im.entity.User;

import java.util.List;

public interface UserMapper {
    int save(User user);
    List<User> findAll();
    int delete(String userDisplayName);
}
