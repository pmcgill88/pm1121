package com.example.tool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class RentalCharge {
    //toolType again should probably be an enum
    private String toolType;
    private BigDecimal dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    @Override
    public String toString() {
        return "RentalCharge{" +
                "toolType='" + toolType + '\'' +
                ", dailyCharge=$" + dailyCharge +
                ", weekdayCharge=" + weekdayCharge +
                ", weekendCharge=" + weekendCharge +
                ", holidayCharge=" + holidayCharge +
                '}';
    }
}
