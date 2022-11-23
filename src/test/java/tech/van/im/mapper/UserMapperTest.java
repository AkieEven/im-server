package tech.van.im.mapper;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.van.im.ImServerApplication;
import tech.van.im.entity.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ImServerApplication.class})
class UserMapperTest {
    private Integer idToDelete;
    @Autowired
    UserMapper userMapper;

    @Order(1)
    @Test
    void save() {
        User user = new User();
        user.setUserEmail("test");
        user.setUserSalt("test");
        user.setUserPassword("test");
        user.setUserDisplayName("test");
        System.out.println("one");
        assert userMapper.save(user) == 1;
    }

    @Order(2)
    @Test
    void findAll() {
        List<User> userList = userMapper.findAll();
        System.out.println("two");
        System.out.println(userList.size());
        assert !userList.isEmpty();
    }

    @Order(3)
    @Test
    void delete() {
        System.out.println("three");
        assert 0 != userMapper.delete("test");
    }
}