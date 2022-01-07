package cn.seecoder.courselearning.serviceimpl.forum;

import cn.seecoder.courselearning.enums.ExceptionType;
import cn.seecoder.courselearning.enums.PostSortStrategy;
import cn.seecoder.courselearning.exception.MyException;
import cn.seecoder.courselearning.mapperservice.forum.PostMapper;
import cn.seecoder.courselearning.po.forum.Post;
import cn.seecoder.courselearning.service.forum.CommentService;
import cn.seecoder.courselearning.service.forum.PostService;
import cn.seecoder.courselearning.service.user.UserService;
import cn.seecoder.courselearning.util.Constant;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.forum.CommentVO;
import cn.seecoder.courselearning.vo.forum.PostVO;
import cn.seecoder.courselearning.vo.user.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 帖子相关功能的业务逻辑对象
 * PostService接口的实现
 * @author 李梓迦 2021-07-03
 */
@Service
public class PostServiceImpl implements PostService {
    @Resource
    private PostMapper postMapper;

    private CommentService commentService;
    private UserService userService;

    //测试需要
    public void setPostMapper(PostMapper postMapper) {
        this.postMapper = postMapper;
    }

    @Autowired
    public void setCommentService(CommentService commentService){this.commentService=commentService;}
    @Autowired
    public void setUserService(UserService userService){this.userService=userService;}

    /**
     * @see PostMapper
     * @see Post
     * @throws MyException 帖子不存在
     * @param postId 非null
     * @return 非null
     */
    @Override
    public PostVO getPostVO(Integer postId) throws MyException {
        assert postId !=null;

        Post post=postMapper.selectByPrimaryKey(postId);
        if(post==null||post.getId()==null){
            throw MyException.of(ExceptionType.NOT_FOUND,"帖子不存在");
        }
        return getPostVOByPost(post);
    }

    /**
     * @see PostMapper
     * @see Post
     * @param courseId 非null
     * @param sortStrategy 非null
     * @return 非null
     */
    @Override
    public List<PostVO> getSortedPostsByCourse(Integer courseId, PostSortStrategy sortStrategy) {
        assert courseId !=null;

        List<Post> posts=postMapper.selectByCourseId(courseId);

        List<PostVO> res=new ArrayList<>();
        for(Post post:posts)res.add(getPostVOByPost(post));
        sort(res,sortStrategy);

        return res;
    }

    /**
     * @see PostMapper
     * @see Post
     * @see Constant
     * @param postVO 非null
     * @return 非null
     */
    @Override
    public ResultVO<PostVO> createPost(PostVO postVO) {
        assert (postVO != null):("postVO为null!");

        postVO.setPostTime(new Date());

        //确认输入是否合法
        if(postVO.getTitle()==null||postVO.getTitle().equals("")) return new ResultVO<>(Constant.REQUEST_FAIL, "帖子主题不能为空！");
        else if(postVO.getMessage()==null||postVO.getMessage().equals("")) return new ResultVO<>(Constant.REQUEST_FAIL, "帖子内容不能为空！");

        //持久化插入并返回结果
        Post post=new Post(postVO);
        if(postMapper.insert(post)>0) return new ResultVO<PostVO>(Constant.REQUEST_SUCCESS, "发帖成功",postVO);
        return new ResultVO<>(Constant.REQUEST_FAIL, "服务器错误");
    }

    /**
     * @see PostMapper
     * @param postId 非null
     * @return 可能为null
     */
    @Override
    public Post getPost(Integer postId) {
        assert postId != null;
        return postMapper.selectByPrimaryKey(postId);
    }

    /**
     * 根据帖子持久化对象获取需要展示的帖子信息
     * @see UserService
     * @see UserVO
     * @param post 在数据库中存在的帖子持久化对象
     * @return 非null
     */
    public PostVO getPostVOByPost(Post post){
        assert post!= null;

        //查找最后评论时间
        Date latestReplyTime=getLatestReplyTime(post.getId());
        if(latestReplyTime==null) latestReplyTime=post.getCreateTime();

        //查找发帖者姓名
        if(post.getUserId()==null) return new PostVO(post,null,latestReplyTime);
        UserVO user=userService.getUser(post.getUserId());
        String userName=user.getUname();

        return new PostVO(post,userName,latestReplyTime);
    }

    /**
     * 按照排序策略将列表posts中各项排序
     * @param posts 非null
     * @param sortStrategy 非null
     */
    public void sort(List<PostVO> posts, PostSortStrategy sortStrategy){
        assert posts!=null&& sortStrategy!=null;
        //简单选择排序
        for(int i=0;i<posts.size()-1;i++){
            for(int j=i+1;j<posts.size();j++){
                if(isAfter(posts.get(i),posts.get(j),sortStrategy)){
                    PostVO temp=posts.get(i);
                    posts.set(i,posts.get(j));
                    posts.set(j,temp);
                }
            }
        }
    }

    /**
     * 判断按照此排序策略，postA是否判断应该排在postB的后面
     * @param postA 非null
     * @param postB 非null
     * @param sortStrategy 非null
     */
    public boolean isAfter(PostVO postA, PostVO postB, PostSortStrategy sortStrategy){
        assert postA!=null&&postB!=null&& sortStrategy!=null;
        switch (sortStrategy){
            case CREATE_TIME://按发布时间
                return postA.getPostTime().before(postB.getPostTime());
            case LATEST_REPLY_TIME://按最后评论时间
                if(postA.getLatestReplyTime()==null) postA.setLatestReplyTime(postA.getPostTime());
                if(postB.getLatestReplyTime()==null) postB.setLatestReplyTime(postB.getPostTime());
                return postA.getLatestReplyTime().before(postB.getLatestReplyTime());
            default:
                return false;
        }
    }

    /**
     * 获取帖子的最后评论时间，若无评论则返回null
     * @see CommentVO
     * @see CommentService
     * @param postId 非null
     */
    public Date getLatestReplyTime(Integer postId) {
        assert postId!=null;

        //获取评论列表
        List<CommentVO> comments=commentService.getSortedCommentsOfPost(postId);
        if(comments.size()==0) return null;

        //寻找评论时间最靠后的评论
        Date res=comments.get(0).getCreateTime();
        for(CommentVO comment:comments){
            Date temp=comment.getCreateTime();
            if(temp.after(res))res=temp;
        }

        return res;
    }
}