package com.example.tool.service;

import com.example.tool.model.Tool;

import java.util.List;
import java.util.Optional;

public interface ToolService {
    List<Tool> getToolListService() throws Exception;

    Optional<Tool> getToolByCode(String toolCode) throws Exception;
}
