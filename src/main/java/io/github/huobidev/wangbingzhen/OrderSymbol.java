package io.github.huobidev.wangbingzhen;

import lombok.Builder;
import lombok.Data;

/**
 * Created by wwwpro on 2019/5/5.
 */
public class OrderSymbol {
    private Long id;
    private Long ts;
    private String symbol;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }
}
