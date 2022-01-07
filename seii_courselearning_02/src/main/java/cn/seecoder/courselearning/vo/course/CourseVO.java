package cn.seecoder.courselearning.vo.course;

import cn.seecoder.courselearning.po.course.Course;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    private Integer cost;

    private Integer teacherId;

    private String teacherName;

    // 课程点赞数
    private Integer likes;

    private boolean liked;

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
        likes = course.getLikes();
        liked = likes != null && likes > 0;
        bought = false;
        manageable = false;
    }



    //TODO:下面两个构造函数新增的 需要设置“当前用户是否点赞过” likes属性应该是当前用户是否点赞过，而不是当前课程被点赞过，应该需要动态设置
    //因此上一个构造函数不正确，并保证了上一个构造函数没被调用
    public CourseVO(@NonNull Course course, boolean bought, boolean manageable,boolean liked){
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
        likes = course.getLikes();
        this.liked=liked;
        this.bought = bought;
        this.manageable = manageable;
    }

    public CourseVO(@NonNull Course course,boolean liked){
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
        likes = course.getLikes();
        this.liked=liked;
        bought = false;
        manageable = false;
    }

    public CourseVO(@NonNull Course course, boolean bought, boolean manageable){
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
        likes = course.getLikes();
        this.liked=likes!=null&&likes>0;
        this.bought = bought;
        this.manageable = manageable;
    }
}
