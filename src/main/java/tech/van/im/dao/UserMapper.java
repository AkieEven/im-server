package tech.van.im.dao;

import tech.van.im.entity.User;

import java.util.List;

public interface UserMapper {
    int save(User user);
    List<User> findAll();
    int delete(String userDisplayName);

    User selectByEmail(String userEmail);

    String selectNameById(Long userId);
}
