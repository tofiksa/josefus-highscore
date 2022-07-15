package no.josefushighscore.register;

import no.josefushighscore.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableJpaAuditing
@ActiveProfiles("test")
public class UserRegisterIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRegister userRegister;

    @Before
    public void setUp() {
        User user = new User();
        user.setFirstname("TestUser");
        user.setLastname("TestUserLastname");
        user.setEmail("test@test.com");
        user.setPassword("TestPassword");

        this.entityManager.persist(user);
    }


    @Test
    public void should_find_userregistrations_in_table() {
        List<User> users = userRegister.findAll();
        Assert.assertNotNull(users);
    }

    @Test
    public void should_find_userregistrations_by_username() {
        Optional<User> users = userRegister.findByUsername("TestUser");
        Assert.assertNotNull(users);
    }

    @After
    public void tearDown() {
        entityManager.flush();
    }
}
