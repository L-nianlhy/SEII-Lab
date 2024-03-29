package cn.seecoder.courselearning.vo;

import cn.seecoder.courselearning.po.RechargeOrder;
import lombok.Data;

import java.util.Date;

@Data
public class RechargeOrderVO {
    private Integer orderId;

    private Integer userId;

    private Integer value;

    private Date createTime;

    public RechargeOrderVO() {
    }

    public RechargeOrderVO(RechargeOrder rechargeOrder) {
        this.orderId = rechargeOrder.getOrderId();
        this.userId = rechargeOrder.getUserId();
        this.value = rechargeOrder.getValue();
        this.createTime = rechargeOrder.getCreateTime();
    }
}