package com.jtj.example.exfluxevent;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Goods {
    private int id;
    private String name;
}
