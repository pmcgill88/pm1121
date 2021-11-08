package com.example.tool.service;

import com.example.tool.model.RentalCharge;

import java.util.Optional;

public interface RentalChargeService {
    Optional<RentalCharge> getRentalCharge(String toolType) throws Exception;
}
