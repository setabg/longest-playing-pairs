package com.example.longest_playing_pairs.controller;

import com.example.longest_playing_pairs.entity.Record;
import com.example.longest_playing_pairs.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping
    public ResponseEntity<List<Record>> getAllRecords() {
        List<Record> records = recordService.getAllRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Record> getRecordById(@PathVariable Long id) {
        Record record = recordService.getRecordById(id);
        if (record != null) {
            return ResponseEntity.ok(record);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Record> createRecord(@Valid @RequestBody Record record) {
        Record newRecord = recordService.saveRecord(record);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Record> updateRecord(@PathVariable Long id, @Valid @RequestBody Record record) {
        Record existingRecord = recordService.getRecordById(id);
        if (existingRecord != null) {
            record.setId(id);
            Record updatedRecord = recordService.saveRecord(record);
            return ResponseEntity.ok(updatedRecord);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        Record existingRecord = recordService.getRecordById(id);
        if (existingRecord != null) {
            recordService.deleteRecord(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/import")
    public ResponseEntity<Void> importRecords() {
        recordService.importRecordsFromCsv();
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
