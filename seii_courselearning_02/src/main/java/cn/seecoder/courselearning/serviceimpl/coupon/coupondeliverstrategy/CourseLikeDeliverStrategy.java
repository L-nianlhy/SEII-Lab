package cn.seecoder.courselearning.serviceimpl.coupon.coupondeliverstrategy;

import cn.seecoder.courselearning.mapperservice.course.CourseLikesMapper;
import cn.seecoder.courselearning.mapperservice.course.CourseMapper;
import cn.seecoder.courselearning.po.coupon.Coupon;
import cn.seecoder.courselearning.po.course.Course;
import cn.seecoder.courselearning.po.user.User;
import cn.seecoder.courselearning.service.coupon.coupondeliverstrategy.CouponDeliverStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 用户点赞数发放策略
 */
@Component
public class CourseLikeDeliverStrategy implements CouponDeliverStrategy {

    @Resource
    protected CourseLikesMapper courseLikesMapper;

    @Resource
    protected CourseMapper courseMapper;

    /**
     * 这里实现的逻辑为如果给extra中courseId对应的课程点过赞，则只要优惠券适用范围是该课程同学校的其他所有课程，则可以发放
     * @param user 用户
     * @param coupon 优惠券
     * @param extra 额外的判断数据
     * @return
     */
    @Override
    //TODO:emmm感觉唯一要改的地方就是41行参数调用的顺序
    //extra 额外的判断依据 这里是courseId及具体数值
    public boolean canDeliver(User user, Coupon coupon, Map<String, String> extra) {
        String courseIdStr = extra.get("courseId");
        if (courseIdStr == null) return false;
        Integer courseId = Integer.valueOf(courseIdStr);
        // 获取用户点赞数
        int result = courseLikesMapper.count(courseId,user.getId());
        if (result == 1) {
            // 判断优惠券的适用范围
            Course course = courseMapper.selectByPrimaryKey(courseId);
//            Integer thisTeacher=course.getTeacherId();
//            String thisSchool=course.getSchool();
//            boolean canDeli=true;
//            switch (coupon.getScope()){
//                case SINGLE:
//                    canDeli=(Integer.parseInt(coupon.getMetadata().split("\"courseId\":\"")[1].split("\"")[0]/*?????????*/)==courseId);
//                    break;
//                case TEACHER:
//                    canDeli=(Integer.parseInt(coupon.getMetadata().split("\"teacherId\":\"")[1].split("\"")[0]/*?????????*/)==thisTeacher);
//                    break;
//                case SCHOOL:
//                    canDeli=(coupon.getMetadata().split("\"schoolId\":\"")[1].split("\"")[0]).equals(thisSchool);
//                    break;
//                default:
//                    break;
//            }
//            if(!canDeli) return false;
            // 判断能否适用
            if (coupon.getScope().canUse(course, coupon)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
