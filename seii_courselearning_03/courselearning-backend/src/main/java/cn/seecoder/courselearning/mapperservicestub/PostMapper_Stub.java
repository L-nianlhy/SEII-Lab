package cn.seecoder.courselearning.mapperservicestub;

import cn.seecoder.courselearning.mapperservice.forum.PostMapper;
import cn.seecoder.courselearning.po.forum.Post;

import java.util.ArrayList;
import java.util.List;

public class PostMapper_Stub implements PostMapper {
    @Override
    public Post selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<Post> selectByCourseId(Integer courseId) {
        Post post1=new Post(1,888,1000000);
        Post post2=new Post(2,888,2000000);
        Post post3=new Post(3,888,3000000);
        List<Post> list=new ArrayList<>();
        list.add(post2);
        list.add(post3);
        list.add(post1);
        return list;
    }

    @Override
    public int insert(Post record) {
        return 0;
    }
}
