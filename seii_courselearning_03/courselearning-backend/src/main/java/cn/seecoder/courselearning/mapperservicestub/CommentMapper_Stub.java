package cn.seecoder.courselearning.mapperservicestub;

import cn.seecoder.courselearning.mapperservice.forum.CommentMapper;
import cn.seecoder.courselearning.po.forum.Comment;
import cn.seecoder.courselearning.po.forum.ReplyNotice;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper_Stub implements CommentMapper {
    Comment comment1=new Comment(1,3,10000000);
    Comment comment2=new Comment(2,2,60000000);
    Comment comment3=new Comment(3,1,30000000);

    @Override
    public Comment selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Comment> selectByPostIdSortedByTime(Integer postId) {
        ArrayList<Comment> res=new ArrayList<Comment>();
        switch (postId){
            case 1:res.add(comment3);break;
            case 2:res.add(comment2);break;
            case 3:res.add(comment1);break;
            default:break;
        }
        return res;
    }

    @Override
    public int insert(Comment record) {
        return 0;
    }

    @Override
    public List<ReplyNotice> selectReplyNoticeOfCommentByUser(Integer userId) {
        return null;
    }

    @Override
    public List<ReplyNotice> selectReplyNoticeOfPostByUser(Integer userId) {
        return null;
    }

    @Override
    public int updateSetRead(Integer commentId) {
        return 0;
    }
}
