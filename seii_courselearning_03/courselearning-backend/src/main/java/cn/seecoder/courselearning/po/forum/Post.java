package cn.seecoder.courselearning.po.forum;

import cn.seecoder.courselearning.vo.forum.PostVO;

import java.util.Date;

public class Post {
    private Integer id;
    private Date createTime;
    private String theme;
    private String content;
    private Integer courseId;
    private Integer userId;

    public Integer getId(){return id;}
    public Date getCreateTime(){return createTime;}
    public String getTheme(){return theme;}
    public String getContent(){return content;}
    public Integer getCourseId(){return courseId;}
    public Integer getUserId(){return userId;}

    public Post(){

    }
    public Post(PostVO postVO){
        this.id=postVO.getId();
        this.createTime=postVO.getPostTime();
        this.theme=postVO.getTitle();
        this.content=postVO.getMessage();
        this.courseId=postVO.getCourseId();
        this.userId=postVO.getUserId();
    }

    public Post(int id,int courseId,int time){
        this.id=id;
        this.courseId=courseId;
        this.createTime=new Date(time);
    }
}
