package cn.seecoder.courselearning.mapperservicestub;

import cn.seecoder.courselearning.mapperservice.course.CourseMapper;
import cn.seecoder.courselearning.po.course.Course;

import java.util.List;

public class CourseMapper_Stub implements CourseMapper {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Course record) {
        return 0;
    }

    @Override
    public Course selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Course> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(Course record) {
        return 0;
    }

    @Override
    public List<Course> selectByType(String type) {
        return null;
    }

    @Override
    public List<Course> queryAll(String key) {
        return null;
    }

    @Override
    public List<Course> selectByTeacherId(Integer userId) {
        return null;
    }

    @Override
    public List<Course> selectByStudentId(Integer userId) {
        return null;
    }

    @Override
    public List<Course> selectHotCourses() {
        return null;
    }
}
