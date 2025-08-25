package br.edu.utfpr.pb.pw44s.server;

import br.edu.utfpr.pb.pw44s.server.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import br.edu.utfpr.pb.pw44s.server.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserControllerTest{


        @Autowired
        private TestRestTemplate testRestTemplate;

        @Autowired
        private UserRepository userRepository;

        @BeforeEach
        public void cleanup(){
            this.userRepository.deleteAll();
        }

        //methodName_condition_expectedBehaviour
        @Test
        public void postUser_whenIsValid_reciveOK(){
                User user = new User();
                user.setUsername("test-user");
                user.setDisplayName("test_Display");
                user.setPassword("P4word");

                ResponseEntity<Object> response = testRestTemplate.postForEntity("/users", user, Object.class);

                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @Test
        public void putUser_whenUserIsValid_userSavedToDatabase(){
            User user = new User();
            user.setUsername("test-user");
            user.setDisplayName("test_Display");
            user.setPassword("P4word");

            ResponseEntity<Object> response = testRestTemplate.postForEntity("/users", user, Object.class);

            assertThat(userRepository.count()).isEqualTo(1);
        }

        @Test
        public void putUser_whenUserIsValid_passwordIsHasehdInDatabaseuse(){
            User user = new User();
            user.setUsername("test-user");
            user.setDisplayName("test_Display");
            user.setPassword("P4word");

            testRestTemplate.postForEntity("/users", user, Object.class);

            List<User> users = userRepository.findAll();
            User userDB = users.get(0);

            assertThat(user.getPassword()).isNotEqualTo(userDB.getPassword());
        }
}