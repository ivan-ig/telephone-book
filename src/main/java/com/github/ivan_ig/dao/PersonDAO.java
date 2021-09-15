package com.github.ivan_ig.dao;

import com.github.ivan_ig.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDAO {

    public final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny()
                .orElse(new Person(id, "There are no users with id " + id, null, null));
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person (name, phoneNumber, email) VALUES(?, ?, ?)", person.getName(),
                person.getPhoneNumber(), person.getEmail());
    }

    public void edit(Person updatedPerson, int id) {
        jdbcTemplate.update("UPDATE Person SET name=?, phoneNumber=?, email=? WHERE id=?", updatedPerson.getName(),
                updatedPerson.getPhoneNumber(), updatedPerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }
}
