package dao;

import dto.CreateUserDTO;
import entity.user.Role;
import entity.user.User;
import mapper.CreateUserMapper;
import mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import service.UserService;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserDAOimplTest {

    UserDAOimpl userDAOimpl = new UserDAOimpl();
    User user = User.builder().name("Name").email("test@google.com").password("12345678").role(Role.USER).build();

    @Test
    public void findByEmailAndPass() {
        Integer id = userDAOimpl.save(user);
        user.setId(id);
        Assert.assertEquals(Optional.of(user), userDAOimpl.findByEmailAndPass("test@google.com", "12345678"));
        userDAOimpl.delete(id);
    }

    @Test
    public void getEmail() {
        Integer id = userDAOimpl.save(user);
        user.setId(id);
        Assert.assertEquals(Optional.of("test@google.com"), userDAOimpl.getEmail(id));
        userDAOimpl.delete(id);
    }

    @Test
    public void save() {
        Integer id = userDAOimpl.save(user);
        user.setId(id);
        Assert.assertEquals(Optional.of(user), userDAOimpl.findById(id));
        userDAOimpl.delete(id);
    }

    @Test
    public void findById() {
        Integer id = userDAOimpl.save(user);
        user.setId(id);
        Assert.assertEquals(Optional.of(user), userDAOimpl.findById(id));
        userDAOimpl.delete(id);
    }

    @Test
    public void delete() {
        Integer id = userDAOimpl.save(user);
        userDAOimpl.delete(id);
        Assert.assertEquals(Optional.empty(), userDAOimpl.findById(id));
    }
}