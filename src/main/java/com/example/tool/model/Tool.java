package com.example.tool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Tool {
    private String toolCode;
    private String toolBrand;
    /*
    This probably should be an enum to enforce the same tool types,
    and the enum list should probably live in a config somewhere so it is easily changeable
     */
    public String toolType;

    @Override
    public String toString() {
        return "Tool{" +
                "toolCode='" + toolCode + '\'' +
                ", toolBrand='" + toolBrand + '\'' +
                ", toolType='" + toolType + '\'' +
                '}';
    }
}
