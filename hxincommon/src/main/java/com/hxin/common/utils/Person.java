package com.hxin.common.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * @author huangxin
 * @date 2021/8/14
 */
public class Person {
    private final Set<Course> courses = new HashSet<>();

    private BloodGroup bloodGroup;

    public Person(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public Set<Course> getCourse() {
        return Collections.unmodifiableSet(courses);
    }

    public void setCourses(Set<Course> courses) {
        initializeCourses(courses);
    }

    private void initializeCourses(Set<Course> courses) {
        this.courses.addAll(courses);
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
    }


    public void testCourse() {
        Iterator<Course> iterator = getCourse().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            Course course = iterator.next();
            if (course.isAdvanced()) {
                count++;
            }
        }
    }

    public static void main(String[] args) {
        Person kent = new Person(BloodGroup.A);
        kent.setBloodGroup(BloodGroup.AB);
        Set<Course> s = new HashSet<>();
        s.add(new Course("Small Talk", false));
        kent.setCourses(s);
        Objects.requireNonNull(kent);
    }

    public int numberOfAdvanceCourse() {
        Iterator<Course> iterator = getCourse().iterator();
        int count = 0;
        while (iterator.hasNext()) {
            if (iterator.next().isAdvanced()) {
                count++;
            }
        }
        return count;
    }

    public int numberOfCourses() {
        return courses.size();
    }
}
