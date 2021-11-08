package com.example.tool.repository;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
This class is just to be faux repo class. Everything in here won't be tested. It's purpose would be just to act as if
I was running sql statements against a db, though this just returns a string.
 */
@Component
public class RentalChargeRepository {

    private static final String SELECT_ALL_CHARGES = "src/main/resources/RentalChargeTable.json";

    public String fakeAllChargesRepo() throws IOException {
        return Files.readString(Path.of(SELECT_ALL_CHARGES));
    }

}
