package cn.seecoder.courselearning.serviceimpl.coupon.couponstrategy;

import cn.seecoder.courselearning.po.coupon.Coupon;
import cn.seecoder.courselearning.util.JSONHelper;
import cn.seecoder.courselearning.vo.order.CourseOrderVO;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 这里的折扣策略不需要满一定数额才能用，可以根据业务需求调整
 */
@Component
public class DiscountCouponStrategy extends AbstractCouponStrategy {
    @Override
    public boolean canUse(CourseOrderVO orderVO, Coupon coupon) {
        Object discount = JSONHelper.getByProperty(coupon.getMetadata(), "discount");
        if (discount == null) {
            // 无效优惠券
            return false;
        }
        // 判断满减策略
        //return super.canUse(orderVO, coupon) && (Double)discount > 0 && (Double)discount < 1;

        double dis=Double.parseDouble((String)discount);
        return super.canUse(orderVO, coupon) && (Double)dis > 0 && (Double)dis < 1;
    }
    @Override
    public int useCoupon(CourseOrderVO orderVO, Coupon coupon) {
        //TODO:修改过，好像也是类型转换的问题，之前ide会报错
        Double discount = Double.parseDouble((String)(Objects.requireNonNull(JSONHelper.getByProperty(coupon.getMetadata(), "discount"))));
        int initialCost = orderVO.getCost() == null ? orderVO.getOrigin() : orderVO.getCost();
       return (int)(initialCost * discount);
    }
}
