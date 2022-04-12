package com.damian.objetivos.service;

import java.util.List;

import com.damian.objetivos.model.CourseModel;

public interface CourseService {
	
	public abstract List<CourseModel> listAllCourses();
	
	public abstract CourseModel addCourse(CourseModel course);
	
	public abstract int removeCourse(int id);
	
	public abstract CourseModel updateCourse(CourseModel course);

}
