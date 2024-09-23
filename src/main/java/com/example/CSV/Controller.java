package com.example.CSV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/csv")
public class Controller {
    @Autowired
    CSVService csvService;

    @PostMapping("/add")
    public ResponseEntity<String> addCsv(@RequestBody CSVRequestDto csvRequestDto) {
        try {
            String result = csvService.processCSV(csvRequestDto.getStrCsv());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error csv: " + e.getMessage());
        }
    }
}
