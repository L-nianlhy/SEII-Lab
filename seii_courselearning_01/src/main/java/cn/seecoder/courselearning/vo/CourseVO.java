package cn.seecoder.courselearning.vo;

import cn.seecoder.courselearning.po.Course;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class CourseVO {
    private Integer id;

    private String name;

    private String type;

    private String intro;

    private String picture;

    private String school;

    private Date createTime;

    private Integer cost;

    private Integer teacherId;

    private String teacherName;

    // 标记当前用户（学生）是否已购买解锁
    private boolean bought;

    // 标记当前用户（老师）是否有权限管理课程
    private boolean manageable;

    public CourseVO() {
        bought = false;
    }

    public CourseVO(@NonNull Course course){
        id = course.getId();
        name = course.getName();
        type = course.getType();
        intro = course.getIntro();
        picture = course.getPicture();
        school = course.getSchool();
        createTime = course.getCreateTime();
        cost = course.getCost();
        teacherId = course.getTeacherId();
        teacherName = course.getTeacherName();
        bought = false;
        manageable = false;
    }

    public CourseVO(@NonNull Course course, @NonNull boolean bought, @NonNull boolean manageable){
        id = course.getId();
        name = course.getName();
        type = course.getType();
        intro = course.getIntro();
        picture = course.getPicture();
        school = course.getSchool();
        createTime = course.getCreateTime();
        cost = course.getCost();
        teacherId = course.getTeacherId();
        teacherName = course.getTeacherName();
        this.bought = bought;
        this.manageable = manageable;
    }
}
