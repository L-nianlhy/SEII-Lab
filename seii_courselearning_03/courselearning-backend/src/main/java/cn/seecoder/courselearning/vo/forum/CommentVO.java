package cn.seecoder.courselearning.vo.forum;

import cn.seecoder.courselearning.po.forum.Comment;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 某条评论的界面需要展示的信息
 * @author 李梓迦 2021-07-01
 */
@Data
public class CommentVO {
    //评论id
    private Integer id;
    //评论发布时间                                 前端创建时传递null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    //评论内容
    private String content;
    //所属帖子id
    private Integer postId;
    //评论者id
    private Integer userId;
    //评论者姓名
    private String userName;
    //被回复的评论的id                             若评论是针对帖子则为null
    private Integer repliedId;
    //被回复的用户姓名                             若评论是针对帖子则为发帖者姓名，前端创建时传递null
    private String repliedUserName;
    //被回复的帖子或评论的内容                      若评论是针对帖子则为帖子内容，前端创建时传递null
    private String repliedContent;
    //是否已经读过
    private boolean haveRead;

    public CommentVO(){

    }

    public CommentVO(Comment comment,String userName,String repliedUserName,String repliedContent){
        this.id=comment.getId();
        this.createTime=comment.getCreateTime();
        this.content=comment.getContent();
        this.postId=comment.getPostId();
        this.userId=comment.getUserId();
        this.repliedId=comment.getRepliedId();
        this.haveRead=comment.getHaveRead();
        this.userName=userName;
        this.repliedUserName=repliedUserName;
        this.repliedContent=repliedContent;
    }

    public boolean getHaveRead() {
        return haveRead;
    }
}
