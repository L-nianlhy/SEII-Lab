package cn.seecoder.courselearning.service.forum;

import cn.seecoder.courselearning.enums.PostSortStrategy;
import cn.seecoder.courselearning.exception.MyException;
import cn.seecoder.courselearning.po.forum.Post;
import cn.seecoder.courselearning.vo.ResultVO;
import cn.seecoder.courselearning.vo.forum.PostVO;

import java.util.List;

/**
 * PostServiceImpl业务逻辑对象实现的接口
 * 包括获取要展示的帖子信息、获取帖子持久化对象、获取课程的排序的帖子列表、创建帖子方法
 * @author 李梓迦 2021-07-02
 */
public interface PostService {

    //获取帖子VO
    PostVO getPostVO(Integer postId) throws MyException;

    //获取帖子PO
    Post getPost(Integer postId);

    //按排序策略获取课程的帖子列表
    List<PostVO> getSortedPostsByCourse(Integer courseId, PostSortStrategy sortStrategy);

    ResultVO<PostVO> createPost(PostVO postVO);

}
