package io.github.huobidev;


import io.github.huobidev.qusifan.entity.BaseEntity;
import lombok.Data;


@Data
public class Order extends BaseEntity { // 此类型为需要使用的消息内容

    private Long id;
    private Long ts;
    private String symbol;
    private Double price;

}
