package com.example.tool.service;


import com.example.tool.model.Tool;
import com.example.tool.repository.ToolRepository;
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
public class ToolServiceImpl implements ToolService {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private ToolRepository toolRepository;

    public List<Tool> getToolListService() throws Exception {
        String toolString = toolRepository.fakeAllToolsRepo();
        List<Tool> toolList = new ArrayList<>();
        try {
            toolList = mapper.readValue(toolString, new TypeReference<List<Tool>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("tool-svc:ToolService:getToolListService - failed to map JSON = " + toolString, e);
        }
        return toolList;
    }

    public Optional<Tool> getToolByCode(String toolCode) throws Exception {
        //This would be a different db statement that would return by code in reality. I'll use the same fake one for this demo though and filter it.
        String toolString = toolRepository.fakeAllToolsRepo();
        List<Tool> toolList = new ArrayList<>();
        try {
            toolList = mapper.readValue(toolString, new TypeReference<List<Tool>>() {
            });
        } catch (JsonProcessingException e) {
            log.error("tool-svc:ToolService:getToolListService - failed to map JSON = " + toolString, e);
        }
        return toolList.stream()
                .filter(n -> n.getToolCode().equals(toolCode))
                .findFirst();
    }
}
