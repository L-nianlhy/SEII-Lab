package cn.seecoder.courselearning.controller.forum;

import cn.seecoder.courselearning.service.forum.CommentService;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.forum.CommentVO;
import cn.seecoder.courselearning.vo.forum.ReplyNoticeVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 接收获取帖子的评论列表、创建评论、获取用户回帖通知列表、将回帖通知设为已读的请求
 * 调用CommentService业务逻辑对象的相关方法
 * @author 李梓迦 2021-07-02
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    /**
     * 获取某帖子所有评论信息的列表
     * @param postId 帖子id
     * @return 非null对象，此帖子所有评论信息的列表
     */
    @GetMapping("/{postId}")
    public List<CommentVO> getCommentsOrderByTime(@PathVariable Integer postId){
        return commentService.getSortedCommentsOfPost(postId);
    }

    /**.
     * 添加评论，会验证输入是否合法并返回创建结果
     * @param comment 一个CommentVO对象，repliedId属性可取null（参见CommentVO定义）
     * @return 非null对象，创建结果，包括提示窗中的信息及所创建的commentVO对象
     */
    @PostMapping("/create")
    public ResultVO<CommentVO> createComment(@RequestBody CommentVO comment){
        return commentService.createComment(comment);
    }

    /**
     * 得到某用户的所有未读的回帖通知信息
     * @param uid 用户id
     * @return 非null对象，此用户所有未读的回帖通知信息的列表
     */
    @GetMapping("/noRead/{uid}")
    public List<ReplyNoticeVO> getReplyNoticesByUser(@PathVariable Integer uid){
        return commentService.getReplyNoticesByUser(uid);
    }

    /**
     * 将某个回帖通知信息设为已读
     * @param commentId 此回帖通知信息对应评论（回复的评论，不是被回复的评论）的id
     * @return 操作结果，取值范围为{null,"服务器错误"}
     *         仅当返回非null时需弹窗提示“服务器错误”
     */
    @GetMapping("/read/{commentId}")
    public String setHaveRead(@PathVariable Integer commentId){
        return commentService.setHaveRead(commentId);
    }
}
