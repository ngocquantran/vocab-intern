package com.example.myvocab.repoTest;

import com.example.myvocab.config.TestJpaConfig;
import com.example.myvocab.model.Roles;
import com.example.myvocab.model.UserRole;
import com.example.myvocab.model.Users;
import com.example.myvocab.repo.UserRoleRepo;
import com.example.myvocab.repo.UsersRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ContextConfiguration(
        classes = {TestJpaConfig.class},
        loader = AnnotationConfigContextLoader.class
)
@Transactional
public class UserRoleRepoTest {
    @Autowired
    private TestEntityManager entityManager;

    @Resource
    private UserRoleRepo userRoleRepo;

    @Resource
    private UsersRepo usersRepo;

    @BeforeEach
    void creatUser() {
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        Users user1 = Users.builder()
                .email("a@gmail.com")
                .password(passwordEncoder.encode("12345678"))
                .fullName("Trần Văn A")
                .avatar("avatar")
                .build();
        Users user2 = Users.builder()
                .email("b@gmail.com")
                .password(passwordEncoder.encode("12345678"))
                .fullName("Trần Văn B")
                .avatar("avatar")
                .build();
        entityManager.persist(user1);
        entityManager.persist(user2);

        Roles role1 = Roles.builder().name("USER_NORMAL").build();
        Roles role2 = Roles.builder().name("USER_VIP").build();
        entityManager.persist(role1);
        entityManager.persist(role2);


        UserRole userRole1 = UserRole.builder()
                .user(user1)
                .role(role1)
                .build();

        UserRole userRole2 = UserRole.builder()
                .user(user2)
                .role(role2)
                .build();
        entityManager.persist(userRole1);
        entityManager.persist(userRole2);

        entityManager.flush();
    }

    @Test
    void findByRoleIdAndUserId(){
        String userId=usersRepo.findByEmail("a@gmail.com").get().getId();
        Optional<UserRole> o_userRole=userRoleRepo.findByRole_IdAndUser_Id(1L,userId);
        assertThat(o_userRole).isPresent();
    }
}
