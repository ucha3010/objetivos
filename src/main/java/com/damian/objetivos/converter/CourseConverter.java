package com.damian.objetivos.converter;

import org.springframework.stereotype.Component;

import com.damian.objetivos.entity.Course;
import com.damian.objetivos.model.CourseModel;

@Component
public class CourseConverter {
	
	public CourseModel entity2Model(Course externObject) {
		CourseModel localObject = new CourseModel();
		localObject.setId(externObject.getId());
		localObject.setName(externObject.getName());
		localObject.setDescription(externObject.getDescription());
		localObject.setPrice(externObject.getPrice());
		localObject.setHours(externObject.getHours());
		return localObject;
		
	}
	
	public Course model2Entity(CourseModel externObject) {
		Course localObject = new Course();
		localObject.setId(externObject.getId());
		localObject.setName(externObject.getName());
		localObject.setDescription(externObject.getDescription());
		localObject.setPrice(externObject.getPrice());
		localObject.setHours(externObject.getHours());
		return localObject;
		
	}

}
