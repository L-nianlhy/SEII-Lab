package cn.seecoder.courselearning.mapperservice.forum;

import cn.seecoder.courselearning.po.forum.Post;
import cn.seecoder.courselearning.po.forum.ReplyNotice;

import java.util.List;

/**
 * 帖子功能相关的数据库增删改查操作的接口
 * @author 陆健 2021-07-04
 */
public interface PostMapper {

    /**
     *查找数据库的post表中主键属性为id的po记录并返回对应的Post对象，若找不到则返回null
     * @param id 非null
     * @return 可能为null
     */
    Post selectByPrimaryKey(Integer id);

    /**
     * 查找数据库的post表中所有course_id属性值为courseId的po记录，
     * 返回这些记录对应的Post对象构成的列表List<Post>对象
     * @param courseId 非null
     * @return 非null
     */
    List<Post> selectByCourseId(Integer courseId);

    /**
     * 在数据库的post表中增加一个record对应的po记录
     * @param record 非null
     *               且id属性值与record.id相同的po记录在数据库的post表中不存在
     * @return 插入操作涉及的行数
     */
    int insert(Post record);

}
