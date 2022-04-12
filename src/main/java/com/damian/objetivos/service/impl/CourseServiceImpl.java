package com.damian.objetivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damian.objetivos.converter.CourseConverter;
import com.damian.objetivos.entity.Course;
import com.damian.objetivos.model.CourseModel;
import com.damian.objetivos.repository.CourseJpaRepository;
import com.damian.objetivos.service.CourseService;

@Service()
public class CourseServiceImpl implements CourseService{
	
	@Autowired
	private CourseJpaRepository courseJpaRepository;
	
	@Autowired
	private CourseConverter courseConverter;

	@Override
	public List<CourseModel> listAllCourses() {
		List<Course> courses = courseJpaRepository.findAll();
		List<CourseModel> courseModels = new ArrayList<>();
		for (Course course: courses) {
			courseModels.add(courseConverter.entity2Model(course));
		}
		return courseModels;
	}

	@Override
	public CourseModel addCourse(CourseModel course) {
		return updateCourse(course);
	}

	@Override
	public int removeCourse(int id) {
		courseJpaRepository.deleteById(id);
		return 0;
	}

	@Override
	public CourseModel updateCourse(CourseModel course) {
		return courseConverter.entity2Model(courseJpaRepository.save(courseConverter.model2Entity(course)));
	}

}
