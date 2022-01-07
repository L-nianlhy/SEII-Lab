package cn.seecoder.courselearning.dto.coupon;

import cn.seecoder.courselearning.enums.CouponScope;
import cn.seecoder.courselearning.enums.CouponType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 优惠券/优惠活动DTO类
 */
@Data
//网站管理员请求创建订单时，传入CouponDTO对象，这时发放策略属性一般为空（发放策略是发布优惠券时决定的）
public class CouponDTO {
    private CouponType type;   //枚举优惠券类型 满减 折扣 其中组合了优惠券策略（策略模式）
    private CouponScope scope; //枚举优惠券范围 单一 全部 指定教师 指定学校
    private String name;
    private String description;

    /**
     * 优惠券元数据，对不同的优惠券类型，优惠券发放策略，优惠券使用范围不同，格式为JSON字符串
     * 如优惠券类型为满减，这里的元数据可以是{"threshold": 100, "cutDown": 20}表示满100减20的优惠券
     * 如优惠券类型为折扣，这里的元数据可以是{"discount": 0.8}表示打八折
     * 格式很自由，可以自定义数据，如自定义优惠券为满200减50，低于200打95折，这里则存储着优惠券类型会使用到的数据
     */
    private String metadata;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean sharable;
    // 发放策略
    private DeliverCouponDTO deliverCouponDTO;
}
