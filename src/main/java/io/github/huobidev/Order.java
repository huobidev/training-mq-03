package io.github.huobidev;


import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Order { // 此类型为需要使用的消息内容

    private Long id;
    private Long ts;
    private String symbol;
    private Double price;
	private String memo;
}
