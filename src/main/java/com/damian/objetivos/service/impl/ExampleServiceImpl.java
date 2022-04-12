package com.damian.objetivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Service;

import com.damian.objetivos.model.Person;
import com.damian.objetivos.service.ExampleService;
import com.damian.objetivos.util.LoggerMapper;

@Service()
public class ExampleServiceImpl implements ExampleService{

	@Override
	public List<Person> getListPeople() {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("Juan", 30));
		persons.add(new Person("Antonio", 34));
		persons.add(new Person("María", 21));
		persons.add(new Person("Ana", 46));
		LoggerMapper.log(Level.INFO, "getListPeople", persons, getClass());
		return persons;
	}

}
