import cn.seecoder.courselearning.enums.PostSortStrategy;
import cn.seecoder.courselearning.mapperservice.course.CourseLikesMapper;
import cn.seecoder.courselearning.mapperservice.course.CourseMapper;
import cn.seecoder.courselearning.mapperservice.forum.CommentMapper;
import cn.seecoder.courselearning.mapperservice.forum.PostMapper;
import cn.seecoder.courselearning.mapperservice.order.CourseOrderMapper;
import cn.seecoder.courselearning.mapperservice.user.UserMapper;
import cn.seecoder.courselearning.mapperservicestub.*;
import cn.seecoder.courselearning.po.forum.Post;
import cn.seecoder.courselearning.po.order.CourseOrder;
import cn.seecoder.courselearning.service.course.CourseService;
import cn.seecoder.courselearning.service.forum.CommentService;
import cn.seecoder.courselearning.service.forum.PostService;
import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.serviceimpl.course.CourseServiceImpl;
import cn.seecoder.courselearning.serviceimpl.forum.CommentServiceImpl;
import cn.seecoder.courselearning.serviceimpl.forum.PostServiceImpl;
import cn.seecoder.courselearning.serviceimpl.order.QueryOrderServiceImpl;
import cn.seecoder.courselearning.serviceimpl.user.UserServiceImpl;
import cn.seecoder.courselearning.vo.forum.PostVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PostServiceImpl.class)
public class PostServiceTest {
    @MockBean
    private PostMapper postMapper;
    @MockBean
    private CommentService commentService;
    @MockBean
    private UserService userService;
    @Autowired
    private PostServiceImpl postService;

    @Test
    public void test(){
        given(postMapper.insert(anyObject())).willReturn(1);
        PostVO post1=new PostVO(); post1.setId(1000);
        PostVO post3=new PostVO(); post3.setId(1001);
        PostVO post2=new PostVO(); post2.setId(1002);
        postService.createPost(post1);
        postService.createPost(post2);
        postService.createPost(post3);
        List<PostVO> posts=new ArrayList<>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        postService.sort(posts,PostSortStrategy.LATEST_REPLY_TIME);
        int[] excepted={1000,1002,1001};
        int[] res=new int[3];
        for(int index=0;index<3;index++) res[index]=posts.get(index).getId();
        assert Arrays.equals(res, excepted);
    }



}
