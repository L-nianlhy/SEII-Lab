package cn.seecoder.courselearning.serviceimpl.forum.strategyofgetcommentdetail;

import cn.seecoder.courselearning.po.forum.Comment;
import cn.seecoder.courselearning.po.forum.Post;
import cn.seecoder.courselearning.service.forum.PostService;
import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.serviceimpl.forum.CommentServiceImpl;
import cn.seecoder.courselearning.vo.user.UserVO;

/**
 * 获取所要展示的评论的相关信息（被回复者和被回复的内容）的策略
 * 回复是针对整个帖子回复
 * @author 李梓迦 2021-07-03
 */
public class CommentToPostStrategy implements StrategyOfGettingCommentDetail {

    /**
     * 获取回复所针对的内容
     * @see PostService
     * @see Post
     * @param commentService 非null
     * @param comment 在数据库中存在的评论的持久化对象
     */
    @Override
    public String getRepliedContent(CommentServiceImpl commentService, Comment comment) {
        PostService postService=commentService.getPostService();

        //获取评论所针对的帖子
        Post post=postService.getPost(comment.getPostId());

        if(post==null) return null;
        return post.getContent();
    }

    /**
     * 获取评论所针对的用户
     * @see PostService
     * @see Post
     * @see UserService
     * @param commentService 非null
     * @param comment 在数据库中存在的评论的持久化对象
     */
    @Override
    public UserVO getRepliedUser(CommentServiceImpl commentService, Comment comment) {
        PostService postService=commentService.getPostService();
        Post post=postService.getPost(comment.getPostId());
        UserService userService=commentService.getUserService();

        //评论所针对的用户就是发帖者
        if(post==null||post.getUserId()==null) return null;
        return userService.getUser(post.getUserId());
    }
}
