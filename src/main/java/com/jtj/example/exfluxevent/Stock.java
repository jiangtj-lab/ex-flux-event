package com.jtj.example.exfluxevent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Stock {
    private int goodsId;
    private int amount;
}
