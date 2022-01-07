package cn.seecoder.courselearning.vo.forum;

import cn.seecoder.courselearning.po.forum.ReplyNotice;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 包括某条回帖通知的界面需要展示的信息
 * @author 李梓迦 2021-07-01
 */
@Data
public class ReplyNoticeVO {
    //回帖所属帖子的所属课程的课程名
    private String courseName;
    //回帖所属帖子的主题
    private String postTheme;
    //回帖所针对的帖子或评论的内容
    private String repliedContent;
    //回帖的时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date replyTime;
    //回复者姓名
    private String replierName;
    //回复的评论内容
    private String replyContent;
    //回帖所属帖子的id
    private Integer repliedPostId;
    //对应评论（回复的评论而非被回复的评论）的id
    private Integer commentId;
    //回帖通知对应回复者id
    private Integer replierId;
    //被回复者id
    private Integer repliedUserId;

    public ReplyNoticeVO(){

    }

    public ReplyNoticeVO(ReplyNotice replyNotice, String postTheme, String courseName, String replierName){
        this.replyContent=replyNotice.getReplyContent();
        this.repliedContent=replyNotice.getRepliedContent();
        this.repliedPostId=replyNotice.getPostId();
        this.commentId=replyNotice.getCommentId();
        this.replierId=replyNotice.getReplierId();
        this.repliedUserId=replyNotice.getRepliedUserId();
        this.replyTime=replyNotice.getReplyTime();
        this.postTheme=postTheme;
        this.courseName=courseName;
        this.replierName=replierName;
    }
}
