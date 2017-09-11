package com.jdv.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Ticker {
    private BigDecimal last;
    private BigDecimal low;
    private BigDecimal high;
    private BigDecimal amount;
}
