package tech.van.im.dao;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tech.van.im.ImServerApplication;
import tech.van.im.entity.User;

import java.util.List;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ImServerApplication.class})
class UserMapperTest {
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
        assert userMapper.save(user) == 1;
        log.info("save() test success");

    }

    @Order(2)
    @Test
    void findAll() {
        List<User> userList = userMapper.findAll();
        assert !userList.isEmpty();
        log.info("findAll() test success");
    }

    @Order(3)
    @Test
    void delete() {
        assert 0 != userMapper.delete("test");
        log.info("delete() test success");
    }
}