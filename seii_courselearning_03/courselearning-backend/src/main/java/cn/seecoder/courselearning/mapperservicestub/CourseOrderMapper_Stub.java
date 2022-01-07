package cn.seecoder.courselearning.mapperservicestub;

import cn.seecoder.courselearning.mapperservice.course.CourseLikesMapper;
import cn.seecoder.courselearning.mapperservice.order.CourseOrderMapper;
import cn.seecoder.courselearning.po.order.CourseOrder;

import java.util.List;

public class CourseOrderMapper_Stub implements CourseOrderMapper {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(CourseOrder record) {
        return 0;
    }

    @Override
    public CourseOrder selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<CourseOrder> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(CourseOrder record) {
        return 0;
    }

    @Override
    public List<CourseOrder> selectByUserId(Integer userId) {
        return null;
    }

    @Override
    public CourseOrder queryMostRecentOrder(Integer userId, Integer courseId) {
        return null;
    }
}
