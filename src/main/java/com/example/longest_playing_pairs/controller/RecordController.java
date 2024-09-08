package com.example.longest_playing_pairs.controller;

import com.example.longest_playing_pairs.entity.Record;
import com.example.longest_playing_pairs.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping
    public List<Record> getAllRecords() {
        return recordService.getAllRecords();
    }

    @GetMapping("/{id}")
    public Record getRecordById(@PathVariable Long id) {
        return recordService.getRecordById(id);
    }

    @PostMapping
    public Record createRecord(@RequestBody Record record) {
        return recordService.saveRecord(record);
    }

    @PutMapping("/{id}")
    public Record updateRecord(@PathVariable Long id, @RequestBody Record record) {
        record.setId(id);
        return recordService.saveRecord(record);
    }

    @DeleteMapping("/{id}")
    public void deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
    }

    @PostMapping("/import")
    public void importRecords() {
        recordService.importRecordsFromCsv();
    }
}
