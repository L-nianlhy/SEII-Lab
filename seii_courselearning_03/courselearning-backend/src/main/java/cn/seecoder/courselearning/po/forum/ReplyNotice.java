package cn.seecoder.courselearning.po.forum;

import cn.seecoder.courselearning.vo.forum.ReplyNoticeVO;

import java.util.Date;

public class ReplyNotice {
    private String replyContent;
    private String repliedContent;
    private Integer postId;
    private Integer commentId;
    private Integer replierId;
    private Integer repliedUserId;
    private Date replyTime;

    public Integer getCommentId() {
        return commentId;
    }

    public Integer getPostId() {
        return postId;
    }

    public Integer getRepliedUserId() {
        return repliedUserId;
    }

    public Integer getReplierId() {
        return replierId;
    }

    public String getRepliedContent() {
        return repliedContent;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public ReplyNotice(){
        System.out.print("e");
    }
}
