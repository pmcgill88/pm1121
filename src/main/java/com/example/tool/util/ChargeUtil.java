package com.example.tool.util;

import com.example.tool.model.RentalContract;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

@Component
public class ChargeUtil {
    //Not worrying about timezones. This method could probably be clearer and smarter with a bit more time spent.
    public static void daysChargedCalc(RentalContract rentalContract) {
        BigDecimal cost = new BigDecimal("0.00");
        int daysChargedCalc = 0;
        LocalDate dateCheck = rentalContract.getCheckoutDate();
        DayOfWeek dayCheck;
        Month monthCheck;
        for (int i = rentalContract.getRentalDays(); i > 0; i--) {
            dateCheck = dateCheck.plusDays(1);
            dayCheck = dateCheck.getDayOfWeek();
            monthCheck = dateCheck.getMonth();
            if (rentalContract.getRentalCharge().isWeekendCharge() && (dayCheck.equals(DayOfWeek.SATURDAY) || dayCheck.equals(DayOfWeek.SUNDAY))) {
                if ((dateCheck.getDayOfMonth() == 4 && monthCheck.equals(Month.JULY))) {
                    if (rentalContract.getRentalCharge().isHolidayCharge()) {
                        daysChargedCalc++;
                        cost = cost.add(rentalContract.getRentalCharge().getDailyCharge());
                    }
                } else {
                    daysChargedCalc++;
                    cost = cost.add(rentalContract.getRentalCharge().getDailyCharge());
                }
            } else if (rentalContract.getRentalCharge().isWeekdayCharge() && !(dayCheck.equals(DayOfWeek.SATURDAY) || dayCheck.equals(DayOfWeek.SUNDAY))) {
                if (dateCheck.isEqual(dateCheck.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY))) && monthCheck.equals(Month.SEPTEMBER)) {
                    if (rentalContract.getRentalCharge().isHolidayCharge()) {
                        daysChargedCalc++;
                        cost = cost.add(rentalContract.getRentalCharge().getDailyCharge());
                    }
                } else {
                    daysChargedCalc++;
                    cost = cost.add(rentalContract.getRentalCharge().getDailyCharge());
                }
            }
        }
        rentalContract.setPreDiscountCharge(cost);
        rentalContract.setDaysCharged(daysChargedCalc);
        rentalContract.setDueDate(dateCheck);
    }

    public static void discountCalc(RentalContract rentalContract) {
        BigDecimal discountCalc = rentalContract.getPreDiscountCharge().multiply(BigDecimal.valueOf((double) rentalContract.getDiscount() / 100)).setScale(2, RoundingMode.HALF_UP);
        rentalContract.setDiscountAmount(discountCalc);
        rentalContract.setFinalCharge(rentalContract.getPreDiscountCharge().subtract(discountCalc));
    }
}