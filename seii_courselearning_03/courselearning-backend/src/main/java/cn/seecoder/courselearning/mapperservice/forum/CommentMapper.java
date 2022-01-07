package cn.seecoder.courselearning.mapperservice.forum;

import cn.seecoder.courselearning.po.forum.Comment;
import cn.seecoder.courselearning.po.forum.ReplyNotice;

import java.util.List;

/**
 * 评论功能相关的数据库增删改查操作的接口
 * @author 陆健 2021-07-04
 */
public interface CommentMapper {

    /**
     * 查找数据库的comment表中主键属性为id的po记录并返回对应的Comment对象，若找不到则返回null
     * @param id 非null
     * @return 可能为null
     */
    Comment selectByPrimaryKey(Integer id);

    /**
     * 查找数据库的comment表中post_id属性为postId的po记录，将其按照create_time属性值降序排序
     * 返回这些记录对应的Comment对象构成的列表List<Comment>对象
     * @param postId 非null
     * @return 非null
     */
    List<Comment> selectByPostIdSortedByTime(Integer postId);

    /**
     * 在数据库的comment表中增加一个record对应的po记录
     * @param record 非null
     *               且id属性值与record.id相同的po记录在数据库的comment表中不存在
     * @return 插入操作涉及的行数
     */
    int insert(Comment record);

    /**
     * 通过数据库的post与comment表查找
     * 并返回回帖通知信息对应的ReplyNotice对象对应的列表List<ReplyNotice>对象
     * （查找发布者id为userId的帖子信息与针对此帖子的评论信息，
     *     取回复内容、被回复的内容、所属帖子id、评论id、回复者id、回复时间信息
     *     作为回帖通知信息的属性）
     * @param userId 非null
     * @return 非null
     */
    List<ReplyNotice> selectReplyNoticeOfCommentByUser(Integer userId);

    /**
     * 通过数据库的comment表查找并返回回帖通知信息
     * 对应的ReplyNotice对象对应的列表List<ReplyNotice>对象
     * （查找发布者id为userId的评论信息与针对此评论的评论信息，
     *     取回复内容、被回复的内容、所属帖子id、评论id、
     *     回复者id、回复时间信息作为回帖通知信息的属性）
     * @param userId 非null
     * @return 非null
     */
    List<ReplyNotice> selectReplyNoticeOfPostByUser(Integer userId);

    /**
     * 将数据库的comment表中id属性值与commentId相同的po记录的have_read属性设为true
     * @param commentId 非null
     *                  且id属性值与commentId相同的po记录在数据库的comment表中存在
     * @return 更新操作涉及的行数
     */
    int updateSetRead(Integer commentId);
}
