package com.example.tool.service;

import com.example.tool.model.RentalCharge;
import com.example.tool.repository.RentalChargeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class RentalChargeServiceImpl implements RentalChargeService {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RentalChargeRepository rentalChargeRepository;

    public Optional<RentalCharge> getRentalCharge(String toolType) throws Exception {
        String rentalChargeListString = rentalChargeRepository.fakeAllChargesRepo();
        List<RentalCharge> rentalCharges = new ArrayList<>();
        try {
            rentalCharges = mapper.readValue(rentalChargeListString, new TypeReference<List<RentalCharge>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("tool-svc:RentalChargeService:getRentalCharge - failed to map JSON = " + rentalChargeListString, e);
        }
        //This would just be part of the db statement rather then having the logic here, but without a real repo I put it here.
        return rentalCharges.stream()
                .filter(n -> n.getToolType().equals(toolType))
                .findFirst();
    }
}
