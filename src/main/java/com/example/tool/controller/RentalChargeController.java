package com.example.tool.controller;

import com.example.tool.model.RentalCharge;
import com.example.tool.model.RentalContract;
import com.example.tool.model.Tool;
import com.example.tool.service.RentalChargeService;
import com.example.tool.service.ToolService;
import com.example.tool.util.ChargeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/charge")
@Slf4j
public class RentalChargeController {
    /*
    Should be using ResponseEntity here. User friendly error messages could be used here for no content for a toolType.
     */
    @Autowired
    private RentalChargeService rentalChargeService;
    @Autowired
    private ToolService toolService;
    @Autowired
    private ChargeUtil chargeUtil;

    @GetMapping(value = "/chargeByType")
    public ResponseEntity<Optional<RentalCharge>> getRentalChargeByType(@RequestParam("toolType") String toolType) throws Exception {
        return ResponseEntity.ok(rentalChargeService.getRentalCharge(toolType));
    }

    //This would really be a post with it's own request object that extended the db object and then save the contract to a db after enriching.
    @GetMapping(value = "/checkout")
    public ResponseEntity<RentalContract> checkout(@RequestParam("toolCode") String toolCode,
                                                   @RequestParam("dayCount") int dayCount,
                                                   @RequestParam("discount") int discount,
                                                   @RequestParam("rentalDate")
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate rentalDate) throws Exception {
        //This validation could be in a validation util or some common place. It should also be done with not hardcoded values and maybe not exceptions.
        if (toolCode.isEmpty()) {
            //Added prints to console throughout since it was in the requirements. Also there should be logging if this was real.
            System.out.println("toolCode is empty");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "toolCode is empty");
        } else if (dayCount < 1) {
            System.out.println("Rental day count must be 1 or greater");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rental day count must be greater then 0");
        } else if (discount < 0 || discount > 100) {
            System.out.println("Discount must be between 0 and 100");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Discount must be between 0 and 100");
        }
        Tool tool = toolService.getToolByCode(toolCode).orElse(null);
        if (null == tool) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        RentalCharge rentalCharge = rentalChargeService.getRentalCharge(tool.toolType).orElse(null);
        RentalContract rentalContract = new RentalContract();
        rentalContract.setTool(tool);
        rentalContract.setRentalDays(dayCount);
        rentalContract.setDiscount(discount);
        rentalContract.setRentalCharge(rentalCharge);
        rentalContract.setCheckoutDate(rentalDate);
        chargeUtil.daysChargedCalc(rentalContract);
        chargeUtil.discountCalc(rentalContract);
        System.out.println(rentalContract.toString());
        return ResponseEntity.ok(rentalContract);
    }
}
