package com.gaziyazilim.petclinic.web;

import com.gaziyazilim.petclinic.model.Owner;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.boot.test.context.SpringBootTest.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles("dev")
public class PetClinicRestControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        //rest template spring boot normal test sınıfı olan RestTemplate yerine spring bootun test sınıfı olan TestRestTemplate sınıfıyla yapılması. Böylyece tomcat sunucusunun açık olması gibi zorunluluklar ortadan kalkıyor
        /*restTemplate = new RestTemplate();
        BasicAuthorizationInterceptor basicAuthorizationInterceptor = new BasicAuthorizationInterceptor("user2","secret2");
        restTemplate.setInterceptors(Arrays.asList(basicAuthorizationInterceptor));*/

        //testin TestRestTemplate sınıfı ile yapılması için refactor edilmesi:
        restTemplate = restTemplate.withBasicAuth("user2","secret2");
    }

    @Test
    public void testGetOwnerById() {
        ResponseEntity<Owner> response = restTemplate.getForEntity("http://localhost:8080/rest/owner/1", Owner.class);

        MatcherAssert.assertThat(response.getStatusCodeValue(), CoreMatchers.equalTo(200));
        MatcherAssert.assertThat(response.getBody().getFirstName(), CoreMatchers.equalTo("osman"));
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

    @Test
    public void testServiceLevelValidation(){
        Owner owner = new Owner();
        //owner.setFirstName("ad");
        //owner.setLastName("soyad");

        ResponseEntity<URI> responseEntity = restTemplate.postForEntity("http://localhost:8080/rest/owner", owner, URI.class);

        MatcherAssert.assertThat(responseEntity.getStatusCode(), Matchers.equalTo(HttpStatus.PRECONDITION_FAILED));
    }
}