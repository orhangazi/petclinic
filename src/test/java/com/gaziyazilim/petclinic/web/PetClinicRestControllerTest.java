package com.gaziyazilim.petclinic.web;

import com.gaziyazilim.petclinic.model.Owner;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PetClinicRestControllerTest {
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        BasicAuthorizationInterceptor basicAuthorizationInterceptor = new BasicAuthorizationInterceptor("user2","secret2");
        restTemplate.setInterceptors(Arrays.asList(basicAuthorizationInterceptor));
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), CoreMatchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), CoreMatchers.equalTo("Fadime"));
    }

    @Test
    public void testGetOwnerByLastName() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owner?ln=Kılıç", List.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), CoreMatchers.equalTo(200));

        List<Map<String, String>> body = response.getBody();

        List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames, CoreMatchers.hasItem("Orhan Gazi"));
    }

    @Test
    public void testGetOwners() {
        ResponseEntity<List> response = restTemplate.getForEntity("http://localhost:8080/rest/owners", List.class);
        //sunucudan 200 (ok) dönecek mi testi:
        MatcherAssert.assertThat(response.getStatusCodeValue(), CoreMatchers.equalTo(200));

        List<Map<String, String>> body = response.getBody();
        List<String> firstNames = body.stream().map(e -> e.get("firstName")).collect(Collectors.toList());

        MatcherAssert.assertThat(firstNames, CoreMatchers.hasItem("Oğuzhan"));
    }

    @Test
    public void testCreateOwner() {
        Owner owner = new Owner();
        owner.setFirstName("Bekir");
        owner.setLastName("Uslu");
        URI location = restTemplate.postForLocation("http://localhost:8080/rest/owner", owner);

        Owner owner2 = restTemplate.getForObject(location, Owner.class);
        MatcherAssert.assertThat(owner.getFirstName(), CoreMatchers.equalTo(owner2.getFirstName()));
        MatcherAssert.assertThat(owner.getLastName(), CoreMatchers.equalTo(owner2.getLastName()));
    }

    @Test
    public void testUpdateOwner() {
        Owner owner = restTemplate.getForObject("http://localhost:8080/rest/owner/7", Owner.class);
        owner.setFirstName("Orhan Gazi");
        owner.setLastName("Kılıç");

        MatcherAssert.assertThat(owner.getFirstName(), CoreMatchers.equalTo("Orhan Gazi"));

        restTemplate.put("http://localhost:8080/rest/owner/7", owner);

        Owner owner2 = restTemplate.getForObject("http://localhost:8080/rest/owner/7", Owner.class);

        MatcherAssert.assertThat(owner2.getLastName(), CoreMatchers.equalTo("Kılıç"));
    }

    @Test
    public void testDeleteOwner() {
        //restTemplate.delete("http://localhost:8080/rest/owner/1");
        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8080/rest/owner/1", HttpMethod.DELETE,null,Void.class);
        try {
            restTemplate.getForEntity("http://localhost:8080/rest/owner/1",Owner.class);
            Assert.fail("should have not returned owner");
        }
        catch (HttpClientErrorException e){
            MatcherAssert.assertThat(e.getStatusCode().value(),CoreMatchers.equalTo(404));
        }
    }
}