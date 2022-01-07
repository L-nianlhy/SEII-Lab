package cn.seecoder.courselearning.serviceimpl.forum;

import cn.seecoder.courselearning.mapperservice.forum.CommentMapper;
import cn.seecoder.courselearning.po.forum.Comment;
import cn.seecoder.courselearning.po.forum.Post;
import cn.seecoder.courselearning.po.forum.ReplyNotice;
import cn.seecoder.courselearning.service.course.CourseService;
import cn.seecoder.courselearning.service.forum.CommentService;
import cn.seecoder.courselearning.service.forum.PostService;
import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.serviceimpl.forum.strategyofgetcommentdetail.CommentToCommentStrategy;
import cn.seecoder.courselearning.serviceimpl.forum.strategyofgetcommentdetail.CommentToPostStrategy;
import cn.seecoder.courselearning.serviceimpl.forum.strategyofgetcommentdetail.StrategyOfGettingCommentDetail;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.course.CourseVO;
import cn.seecoder.courselearning.vo.forum.CommentVO;
import cn.seecoder.courselearning.vo.forum.ReplyNoticeVO;
import cn.seecoder.courselearning.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论相关功能的业务逻辑对象
 * CommentService接口的实现
 * @author 李梓迦 2021-07-03
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;

    UserService userService;
    PostService postService;
    CourseService courseService;

    //策略模式，获取所要展示的评论的相关信息（被回复者和被回复的内容）的策略
    StrategyOfGettingCommentDetail strategy;

    //测试需要
    public void setCommentMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Autowired
    public void setUserService(UserService userService){this.userService=userService;}
    @Autowired
    public void setPostService(PostService postService){this.postService=postService;}
    @Autowired
    public void setCourseService(CourseService courseService){this.courseService=courseService;}

    public UserService getUserService() {
        return userService;
    }

    public PostService getPostService() {
        return postService;
    }

    public CommentMapper getCommentMapper() {
        return commentMapper;
    }

    /**
     * @see CommentMapper
     * @see Comment
     * @param postId 非null
     * @return 非null
     */
    @Override
    public List<CommentVO> getSortedCommentsOfPost(Integer postId) {
        assert postId!=null;

        //获取已排好序的评论列表
        List<Comment> comments=commentMapper.selectByPostIdSortedByTime(postId);
        List<CommentVO> res=new ArrayList<>();
        for(Comment comment:comments) res.add(getCommentVOByComment(comment));
        return res;
    }

    /**
     * @see CommentMapper
     * @see Comment
     * @see Constant
     * @param commentVO 非null
     * @return 非null
     */
    @Override
    public ResultVO<CommentVO> createComment(CommentVO commentVO) {
        assert commentVO!=null;

        //设置插入时间
        commentVO.setCreateTime(new Date());

        //验证输入是否合法
        if(commentVO.getContent()==null||commentVO.getContent().equals("")) return new ResultVO<>(Constant.REQUEST_FAIL, "评论内容不能为空！");

        //插入并返回结果
        Comment comment=new Comment(commentVO);
        if(commentMapper.insert(comment)>0) return new ResultVO<CommentVO>(Constant.REQUEST_SUCCESS, "评论发布成功",commentVO);
        return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
    }

    /**
     * @see CommentMapper
     * @see ReplyNotice
     * @param userId 非null
     * @return 非null
     */
    @Override
    public List<ReplyNoticeVO> getReplyNoticesByUser(Integer userId) {
        assert userId!=null;

        //获取针对帖子的未读评论
        List<ReplyNotice> replyNoticeList=commentMapper.selectReplyNoticeOfPostByUser(userId);
        //获取针对评论的未读评论
        List<ReplyNotice> noticeOfComment=commentMapper.selectReplyNoticeOfCommentByUser(userId);

        replyNoticeList.addAll(noticeOfComment);

        //按回复时间倒序排序
        for(int i=0;i<replyNoticeList.size()-1;i++){
            for(int j=i;j<replyNoticeList.size();j++){
                if(replyNoticeList.get(i).getReplyTime().before(replyNoticeList.get(j).getReplyTime())){
                    ReplyNotice tmp=replyNoticeList.get(i);
                    replyNoticeList.set(i,replyNoticeList.get(j));
                    replyNoticeList.set(j,tmp);
                }
            }
        }

        List<ReplyNoticeVO> res=new ArrayList<ReplyNoticeVO>();
        //获取需要展示的回帖通知信息
        for(ReplyNotice replyNotice:replyNoticeList) res.add(getReplyNoticeVOByReplyNotice(replyNotice));
        return res;
    }

    /**
     * @see CommentMapper
     * @param commentId 非null
     * @return 更新成功返回null，否则返回“服务器错误”
     */
    @Override
    public String setHaveRead(Integer commentId) {
        assert commentId!=null;

        if(commentMapper.updateSetRead(commentId)>0) return null;
        return "服务器错误";
    }


    /**
     * 根据评论持久化对象获取需要展示的评论相关信息
     * @see UserVO
     * @see UserService
     * @see StrategyOfGettingCommentDetail
     * @see CommentToCommentStrategy
     * @see CommentToPostStrategy
     * @param comment 一个在数据库中有对应记录的评论持久化对象
     * @return 非null
     */
    public CommentVO getCommentVOByComment(Comment comment){
        assert comment!= null;

        //获取评论者姓名
        UserVO user=userService.getUser(comment.getUserId());
        String userName=user.getUname();

        String repliedContent;
        UserVO repliedUser;
        String repliedUserName=null;

        //配置需要展示的评论信息的策略
        if(comment.getRepliedId()!=null) {//针对评论的评论
            this.strategy=new CommentToCommentStrategy();
        }else{//针对帖子的评论
            this.strategy=new CommentToPostStrategy();
        }

        repliedContent=strategy.getRepliedContent(this,comment);
        repliedUser=strategy.getRepliedUser(this,comment);
        if(repliedUser!=null) repliedUserName=repliedUser.getUname();

        return new CommentVO(comment,userName,repliedUserName,repliedContent);
    }

    /**
     * 根据回帖通知po对象获取需要展示的回帖通知信息
     * @see Post
     * @see CourseVO
     * @see UserVO
     * @param replyNotice 非null
     * @return 非null
     */
    public ReplyNoticeVO getReplyNoticeVOByReplyNotice(ReplyNotice replyNotice){
        assert replyNotice!=null;

        //所属帖子主题
        Post post=postService.getPost(replyNotice.getPostId());
        String theme=post.getTheme();

        //所属课程名
        CourseVO course=courseService.getCourse(post.getCourseId(),replyNotice.getReplierId());
        String courseName=course.getName();

        //回复者姓名
        UserVO user=userService.getUser(replyNotice.getReplierId());
        String userName=user.getUname();
        return new ReplyNoticeVO(replyNotice,theme,courseName,userName);
    }
}