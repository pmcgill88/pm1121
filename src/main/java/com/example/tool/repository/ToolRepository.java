package com.example.tool.repository;

import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

/*
This class is just to be faux repo class. Everything in here won't be tested. It's purpose would be just to act as if
I was running sql statements against a db, though this just returns a string.
 */
@Component
public class ToolRepository {

    private static final String SELECT_ALL_TOOL = "src/main/resources/ToolTable.json";

    public String fakeAllToolsRepo() throws Exception {
        return Files.readString(Path.of(SELECT_ALL_TOOL));
    }
}
