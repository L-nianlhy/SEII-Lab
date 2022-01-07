package cn.seecoder.courselearning.mapperservice.course;

import org.apache.ibatis.annotations.Param;

public interface CourseLikesMapper {
    //TODO:任务1 相应的xml文件需要修改
    int deleteByPrimaryKey(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int insert(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int count(@Param("courseId") Integer courseId, @Param("userId") Integer userId);

    int countLikesOfCourse(@Param("courseId") Integer courseId, @Param("userId") Integer userId);
}
