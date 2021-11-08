package com.example.tool.controller;

import com.example.tool.model.Tool;
import com.example.tool.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tool")
public class ToolController {

    @Autowired
    private ToolService toolService;

    /*
    This api could be expanded to take in a search param like brand, or tool type so only those are returned.
     */
    @GetMapping(value = "/toolList")
    public ResponseEntity<List<Tool>> toolList() throws Exception {
        List<Tool> toolListService = toolService.getToolListService();
        return ResponseEntity.ok(toolListService);
    }
}
