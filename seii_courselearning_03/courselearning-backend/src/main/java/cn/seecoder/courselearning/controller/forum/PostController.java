package cn.seecoder.courselearning.controller.forum;

import cn.seecoder.courselearning.enums.PostSortStrategy;
import cn.seecoder.courselearning.exception.MyException;
import cn.seecoder.courselearning.service.forum.PostService;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.forum.PostVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 接收查询帖子信息、获取课程帖子列表、创建帖子的请求
 * 调用PostService业务逻辑对象的相关方法
 * @author 李梓迦 2021-07-02
 */
@RestController
@RequestMapping("/post")
public class PostController {
    @Resource
    private PostService postService;

    /**
     * 获取单个帖子的信息
     * 通过讨论区或回帖通知页面进入某个帖子内容页面时调用的服务
     * @param postId 帖子id
     * @return 此帖子的PostVO对象，参见PostVO的定义；可能为null
     */
    @GetMapping("/postById/{postId}")
    public PostVO getPostById(@PathVariable Integer postId){
        try {
            return postService.getPostVO(postId);
        }catch (MyException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * 获取某课程讨论区中所有帖子信息的列表，按最新回复时间倒序排序
     * @param courseId 当前课程id
     * @return 非null对象，此课程讨论区中所有帖子信息的列表，按最新回复时间倒序排序
     */
    @GetMapping("/commentTime/{courseId}")
    public List<PostVO> getPostsOrderByLastReplyTime(@PathVariable Integer courseId){
        return postService.getSortedPostsByCourse(courseId, PostSortStrategy.LATEST_REPLY_TIME);
    }

    /**
     * 获取某课程讨论区中所有帖子信息的列表，按发帖时间倒序排序
     * @param courseId 当前课程id
     * @return 非null对象，此课程讨论区中所有帖子信息的列表，按发帖时间倒序排序
     */
    @GetMapping("/postTime/{courseId}")
    public List<PostVO> getPostsOrderByPostTime(@PathVariable Integer courseId){
        System.out.print(courseId);
        return postService.getSortedPostsByCourse(courseId, PostSortStrategy.CREATE_TIME);
    }

    /**
     * 创建帖子，会验证输入是否合法并返回创建结果
     * @param post 一个PostVO对象，latestReplyTime属性为帖子创建时间
     * @return 创建结果，包括提示窗中的信息及所创建的PostVO对象
     */
    @PostMapping("/create")
    public ResultVO<PostVO> createPost(@RequestBody PostVO post){
        return postService.createPost(post);
    }
}
