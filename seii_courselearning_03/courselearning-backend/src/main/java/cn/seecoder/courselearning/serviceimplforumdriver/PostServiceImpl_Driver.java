package cn.seecoder.courselearning.serviceimplforumdriver;

import cn.seecoder.courselearning.enums.PostSortStrategy;
import cn.seecoder.courselearning.mapperservicestub.*;
import cn.seecoder.courselearning.service.course.CourseService;
import cn.seecoder.courselearning.service.forum.PostService;
import cn.seecoder.courselearning.service.order.QueryOrderService;
import cn.seecoder.courselearning.serviceimpl.course.CourseServiceImpl;
import cn.seecoder.courselearning.serviceimpl.forum.CommentServiceImpl;
import cn.seecoder.courselearning.serviceimpl.forum.PostServiceImpl;
import cn.seecoder.courselearning.serviceimpl.order.QueryOrderServiceImpl;
import cn.seecoder.courselearning.serviceimpl.user.UserServiceImpl;
import cn.seecoder.courselearning.vo.forum.PostVO;

import java.util.List;

public class PostServiceImpl_Driver {
    public void drive(PostService postService){
        List<PostVO> list=postService.getSortedPostsByCourse(888, PostSortStrategy.CREATE_TIME);
        for(PostVO postVO:list){
            System.out.println(postVO.getId());
            System.out.println(postVO.getPostTime());
        }
    }

    public static void main(String[] args){

        UserServiceImpl userService=new UserServiceImpl();
        userService.setUserMapper(new UserMapper_Stub());

        CourseServiceImpl courseService=new CourseServiceImpl();
        courseService.setCourseMapper(new CourseMapper_Stub());
        courseService.setCourseLikesMapper(new CourseLikesMapper_Stub());
        QueryOrderServiceImpl queryOrderService=new QueryOrderServiceImpl();
        queryOrderService.setCourseOrderMapper(new CourseOrderMapper_Stub());
        courseService.setQueryOrderService(queryOrderService);

        PostServiceImpl postService=new PostServiceImpl();
        postService.setPostMapper(new PostMapper_Stub());
        CommentServiceImpl commentService=new CommentServiceImpl();
        commentService.setCommentMapper(new CommentMapper_Stub());

        postService.setCommentService(commentService);
        postService.setUserService(userService);
        commentService.setPostService(postService);
        commentService.setCourseService(courseService);
        commentService.setUserService(userService);

        PostServiceImpl_Driver driver=new PostServiceImpl_Driver();
        driver.drive(postService);
    }
}
