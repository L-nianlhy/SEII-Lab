package cn.seecoder.courselearning.po.forum;

import cn.seecoder.courselearning.vo.forum.CommentVO;

import java.util.Date;

public class Comment {
    private Integer id;
    private Date createTime;
    private String content;
    private Integer postId;
    private Integer userId;
    private Integer repliedId;
    private boolean haveRead;

    public Date getCreateTime() {
        return createTime;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getRepliedId() {
        return repliedId;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public boolean getHaveRead(){
        return haveRead;
    }

    public Comment(){

    }
    public Comment(CommentVO commentVO){
        this.id=commentVO.getId();
        this.createTime=commentVO.getCreateTime();
        this.content=commentVO.getContent();
        this.postId=commentVO.getPostId();
        this.userId=commentVO.getUserId();
        this.repliedId=commentVO.getRepliedId();
        this.haveRead=commentVO.getHaveRead();
    }
    public Comment(int id, int postId, int time){
        this.id=id;
        this.postId=postId;
        this.createTime=new Date(time);
    }
}
