package cn.seecoder.courselearning.vo.forum;

import cn.seecoder.courselearning.po.forum.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 包括某个帖子的界面需要展示的信息
 * @author 李梓迦 2021-07-01
 */
@Data
public class PostVO {
    //帖子id
    private Integer id;
    //帖子发布时间                  前端创建时传递null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date postTime;
    //帖子主题
    private String title;
    //帖子内容
    private String message;
    //发布者id
    private Integer userId;
    //发布者姓名
    private String userName;
    //帖子所属课程id
    private Integer courseId;
    //帖子对应评论列表中最新评论的时间 前端创建时传递null
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date latestReplyTime;


    public PostVO(){}

    public PostVO(Post post,String userName,Date latestReplyTime){
        this.id=post.getId();
        this.postTime =post.getCreateTime();
        this.title =post.getTheme();
        this.message =post.getContent();
        this.userId=post.getUserId();
        this.courseId=post.getCourseId();
        this.userName=userName;
        this.latestReplyTime=latestReplyTime;
    }
}