package io.samples.thrift.api.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.samples.thrift.api.entity.Author;
import io.samples.thrift.api.entity.Company;
import io.samples.thrift.api.repo.AuthorRepository;
import io.samples.thrift.api.repo.CompanyRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/polymorphism")
public class PolymorphismController {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CompanyRepository companyRepository;


    @GetMapping("/hello")
    public String hello(String name) {
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/save-author")
    public String saveAuthor(String name) {
        log.info("save-author, {}", name);

        Author author = new Author(new Random().nextLong(), name, null);
        authorRepository.save(author);
        return "save-author finish, ".concat(author.toString()).concat("!");
    }

    @GetMapping("/save-company")
    public String saveCompany(String name) {
        log.info("save-company, {}", name);

        Company company = new Company(new Random().nextLong(), name);
        companyRepository.save(company);
        return "save-company finish, ".concat(company.toString()).concat("!");
    }

    @GetMapping("/test-rest-template")
    public String testRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);
        log.info("=================status:{}, content:{}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity.getBody();
    }
}
