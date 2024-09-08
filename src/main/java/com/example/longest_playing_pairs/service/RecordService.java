package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Record;
import com.example.longest_playing_pairs.repository.RecordRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final CsvRecordImporter csvRecordImporter;

    public RecordService(RecordRepository recordRepository, CsvRecordImporter csvRecordImporter) {
        this.recordRepository = recordRepository;
        this.csvRecordImporter = csvRecordImporter;
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Record getRecordById(Long id) {
        return recordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id " + id));
    }

    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }

    public void deleteRecord(Long id) {
        recordRepository.deleteById(id);
    }

    // importing records from CSV
    @Transactional
    public void importRecordsFromCsv() {
        csvRecordImporter.importRecordsFromCsv();
    }
}
