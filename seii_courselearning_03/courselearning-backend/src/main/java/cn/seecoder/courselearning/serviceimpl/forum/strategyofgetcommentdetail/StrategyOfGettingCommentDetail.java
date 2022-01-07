package cn.seecoder.courselearning.serviceimpl.forum.strategyofgetcommentdetail;

import cn.seecoder.courselearning.po.forum.Comment;
import cn.seecoder.courselearning.serviceimpl.forum.CommentServiceImpl;
import cn.seecoder.courselearning.vo.user.UserVO;

/**
 * 获取所要展示的评论的相关信息（被回复者和被回复的内容）的策略
 * @author 李梓迦 2021-07-03
 */
public interface StrategyOfGettingCommentDetail {

    /**
     * 获取回复所针对的内容
     * @param commentService 非null
     * @param comment 在数据库中存在的评论的持久化对象
     * @return 非null
     */
    public String getRepliedContent(CommentServiceImpl commentService, Comment comment);

    /**
     * 获取评论所针对的用户
     * @param commentService 非null
     * @param comment 在数据库中存在的评论的持久化对象
     * @return 非null
     */
    public UserVO getRepliedUser(CommentServiceImpl commentService,Comment comment);
}
