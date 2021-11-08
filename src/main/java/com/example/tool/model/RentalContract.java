package com.example.tool.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
public class RentalContract {
    private Tool tool;
    private RentalCharge rentalCharge;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private int daysCharged;
    private BigDecimal preDiscountCharge;
    private int discount;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    @Override
    public String toString() {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/uu");
        return "RentalContract{" +
                "tool=" + tool +
                ", rentalCharge=" + rentalCharge +
                ", rentalDays=" + rentalDays +
                ", checkoutDate=" + checkoutDate.format(formatters) +
                ", dueDate=" + dueDate.format(formatters) +
                ", daysCharged=" + daysCharged +
                ", preDiscountCharge=$" + preDiscountCharge +
                ", discount=" + discount + "%" +
                ", discountAmount=$" + discountAmount +
                ", finalCharge=$" + finalCharge +
                '}';
    }
}
