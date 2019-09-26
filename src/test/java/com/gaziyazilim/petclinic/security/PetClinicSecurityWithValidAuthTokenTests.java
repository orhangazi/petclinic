package com.gaziyazilim.petclinic.security;

import com.gaziyazilim.petclinic.model.Owner;
import com.gaziyazilim.petclinic.service.PetClinicService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.active.profile=dev")
public class PetClinicSecurityWithValidAuthTokenTests {
    @Autowired
    private PetClinicService petClinicService;

    @Before
    public void setUp(){
        TestingAuthenticationToken auth = new TestingAuthenticationToken("user1","secret1","ROLE_USER");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @After
    public void tearDown(){
        SecurityContextHolder.clearContext();
    }

    @Test
    public void testFindOwners(){
        List<Owner> owners = petClinicService.findOwners();

        MatcherAssert.assertThat(owners.size(), Matchers.greaterThan(0));
    }
}