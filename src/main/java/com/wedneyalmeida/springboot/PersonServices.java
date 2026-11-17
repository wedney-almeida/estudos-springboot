package com.wedneyalmeida.springboot;

import com.wedneyalmeida.springboot.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class PersonServices {


    private final AtomicLong counter = new AtomicLong();

    private Logger logger = Logger.getLogger(PersonServices.class.getName());

    public List<Person> findAll(){
        logger.info("Finding all People");
        List<Person> persons = new ArrayList<Person>();

        for (int i = 0; i < 8; i++) {
            Person person = mockPerson(i);
            persons.add(person);
        }
        return persons;
    }

    public Person create(Person person){
        logger.info("Creating one Person");
        return person;
    }

    public Person update(Person person){
        logger.info("Updating one Person");
        return person;
    }

    public void delete(String id){
        logger.info("Deleting one Person!");
    }

    public Person findById(String id){
        logger.info("Finding one Person!");

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFristName("Wed");
        person.setLastName("alm");
        person.setAddress("Salvador-BA");
        person.setGender("Male");

        return person;
    }

    private Person mockPerson(int i) {

        Person person = new Person();
        person.setId(counter.incrementAndGet());
        person.setFristName("FirstName" + i);
        person.setLastName("LastName" + i);
        person.setAddress("Som in Brazil");
        person.setGender("Male");

        return person;
    }
}
