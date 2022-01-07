package cn.seecoder.courselearning.mapperservicestub;

import cn.seecoder.courselearning.mapperservice.user.UserMapper;
import cn.seecoder.courselearning.po.user.User;

import java.util.List;

public class UserMapper_Stub implements UserMapper {
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public List<User> selectAll() {
        return null;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    @Override
    public void increaseBalance(Integer id, Integer delta) {

    }

    @Override
    public void decreaseBalance(Integer id, Integer delta) {

    }

    @Override
    public User selectByPhone(String phone) {
        return null;
    }
}
