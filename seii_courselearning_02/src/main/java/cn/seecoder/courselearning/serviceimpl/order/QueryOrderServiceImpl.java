package cn.seecoder.courselearning.serviceimpl.order;

import cn.seecoder.courselearning.mapperservice.order.CourseOrderMapper;
import cn.seecoder.courselearning.po.order.CourseOrder;
import cn.seecoder.courselearning.service.order.QueryOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

//TODO:修改过，任务3消除循环依赖，感觉大概需要添加的就这几行？
@Service
public class QueryOrderServiceImpl implements QueryOrderService {
    @Resource
    CourseOrderMapper courseOrderMapper;

    @Override
    public CourseOrder queryMostRecentOrder(Integer uid, Integer courseId){
        return courseOrderMapper.queryMostRecentOrder(uid,courseId);
    }

    @Override
    public CourseOrder getByPrimaryKey(Integer orderId) {
        return courseOrderMapper.selectByPrimaryKey(orderId);
    }
}
