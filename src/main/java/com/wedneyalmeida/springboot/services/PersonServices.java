package com.wedneyalmeida.springboot.services;

import com.wedneyalmeida.springboot.data.dto.v1.PersonDTO;
import com.wedneyalmeida.springboot.data.dto.v2.PersonDTOV2;
import com.wedneyalmeida.springboot.exception.ResourceNotFoundException;
import com.wedneyalmeida.springboot.mapper.custom.PersonMapper;
import com.wedneyalmeida.springboot.model.Person;
import com.wedneyalmeida.springboot.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static com.wedneyalmeida.springboot.mapper.ObjectMapper.parseListObjects;
import static com.wedneyalmeida.springboot.mapper.ObjectMapper.parseObject;


@Service
public class PersonServices {


    private final AtomicLong counter = new AtomicLong();
    private Logger logger = LoggerFactory.getLogger(PersonServices.class.getName());

    @Autowired
    PersonRepository repository;

    @Autowired
    PersonMapper converter;

    public List<PersonDTO> findAll(){

        logger.info("Finding all People");

        return parseListObjects(repository.findAll(), PersonDTO.class);
    }

    public PersonDTO findById(Long id){

        logger.info("Finding one Person!");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id"));
        return parseObject(entity, PersonDTO.class);
    }

    public PersonDTO create(PersonDTO person){

        logger.info("Creating one Person");
        var entity = parseObject(person, Person.class);

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    public PersonDTOV2 createV2(PersonDTOV2 person){

        logger.info("Creating one Person V2");
        var entity = parseObject(person, Person.class);

        return converter.convertEntityToDTO(repository.save(entity));
    }

    public PersonDTO update(PersonDTO person){

        logger.info("Updating one Person");
        Person entity = repository.findById(person.getId())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id"));

        entity.setFirstName(person.getFirstName());
        entity.setLastName(person.getFirstName());
        entity.setAddress(person.getAddress());
        entity.setGender(person.getGender());

        return parseObject(repository.save(entity), PersonDTO.class);
    }

    @Transactional
    public PersonDTO disablePerson(Long id){

        logger.info("Disable one Person!");

        repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id"));
        repository.disablePerson(id);

        var entity = repository.findById(id).get();
        var dto = parseObject(entity, PersonDTO.class);
        return dto;
    }

    public void delete(Long id){

        logger.info("Deleting one Person!");
        Person entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this id"));

        repository.delete(entity);
    }
}
