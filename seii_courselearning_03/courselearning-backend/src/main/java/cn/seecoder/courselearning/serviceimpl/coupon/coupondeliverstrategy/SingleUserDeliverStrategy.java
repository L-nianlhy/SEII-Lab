package cn.seecoder.courselearning.serviceimpl.coupon.coupondeliverstrategy;

import cn.seecoder.courselearning.po.coupon.Coupon;
import cn.seecoder.courselearning.po.user.User;
import cn.seecoder.courselearning.service.coupon.coupondeliverstrategy.CouponDeliverStrategy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 只发放给单个用户的
 */
@Component
public class SingleUserDeliverStrategy implements CouponDeliverStrategy {

    @Override
    //extra 额外的判断依据 这里是userId及具体数值
    public boolean canDeliver(User user, Coupon coupon, Map<String, String> extra) {
        String userIdStr = extra.get("userId");
        if (userIdStr == null) {
            return false;
        }
        try {
            Integer userId = Integer.valueOf(userIdStr);
            return user.getId().equals(userId);
        } catch (Exception e) {
            return false;
        }
    }
}
