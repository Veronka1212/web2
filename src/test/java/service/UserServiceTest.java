package service;

import dto.CreateUserDTO;
import dto.UserDTO;
import entity.user.User;
import mapper.CreateUserMapper;
import mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserServiceTest {

    UserService userService = new UserService();
    CreateUserDTO userDTO = CreateUserDTO.builder().name("Name").email("test@google.com").password("12345678").role("USER").build();
    CreateUserMapper createUserMapper = new CreateUserMapper();
    UserMapper userMapper = new UserMapper();

    @Test
    public void create() {
        Integer id = userService.create(userDTO);
        User user = createUserMapper.mapFrom(userDTO);
        user.setId(id);
        UserDTO userDTO = userMapper.getFrom(user);
        Assert.assertEquals(Optional.of(userDTO), userService.findById(id));
        userService.delete(id);
    }

    @Test
    public void login() {
        Integer id = userService.create(userDTO);
        User user = createUserMapper.mapFrom(userDTO);
        user.setId(id);
        UserDTO userDTO = userMapper.getFrom(user);
        Assert.assertEquals(Optional.of(userDTO), userService.login("test@google.com", "12345678"));
        userService.delete(id);
    }

    @Test
    public void findById() {
        Integer id = userService.create(userDTO);
        User user = createUserMapper.mapFrom(userDTO);
        user.setId(id);
        UserDTO userDTO = userMapper.getFrom(user);
        Assert.assertEquals(Optional.of(userDTO), userService.findById(id));
        userService.delete(id);
    }

    @Test
    public void delete() {
        Integer id = userService.create(userDTO);
        userService.delete(id);
        Assert.assertEquals(Optional.empty(), userService.findById(id));
    }
}