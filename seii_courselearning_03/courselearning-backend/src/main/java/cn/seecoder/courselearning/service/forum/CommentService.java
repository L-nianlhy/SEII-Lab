package cn.seecoder.courselearning.service.forum;

import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.forum.CommentVO;
import cn.seecoder.courselearning.vo.forum.ReplyNoticeVO;

import java.util.List;

/**
 * CommentServiceImpl业务逻辑对象实现的接口
 * 包括获取帖子评论列表、创建评论、获取用户回帖通知列表、设置评论为已读方法
 * @author 李梓迦 2021-07-02
 */
public interface CommentService {

    //获取帖子的按评论时间倒序排序的评论信息列表
    List<CommentVO> getSortedCommentsOfPost(Integer postId);

    ResultVO<CommentVO> createComment(CommentVO commentVO);

    //获取用户的按回复时间倒序排序的回帖通知列表
    List<ReplyNoticeVO> getReplyNoticesByUser(Integer userId);

    //设置评论为已读
    String setHaveRead(Integer commentId);
}
