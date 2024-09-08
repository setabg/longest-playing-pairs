package com.example.longest_playing_pairs.service;

import com.example.longest_playing_pairs.entity.Record;
import com.example.longest_playing_pairs.repository.RecordRepository;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CsvRecordImporter {

    private final RecordRepository recordRepository;

    public CsvRecordImporter(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public void importRecordsFromCsv() {
        String filePath = "src/main/resources/data/records.csv";
        List<Record> recordsToSave = new ArrayList<>();
        int validRecordCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip the header

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 5) {
                    try {
                        Long id = Long.parseLong(data[0].trim());
                        Long playerId = Long.parseLong(data[1].trim());
                        Long matchId = Long.parseLong(data[2].trim());
                        Integer fromMinutes = Integer.parseInt(data[3].trim());
                        Integer toMinutes = data[4].trim().equals("NULL") ? 90 : Integer.parseInt(data[4].trim());

                        // Validate and process records
                        if (isValidRecord(fromMinutes, toMinutes)) {
                            Record record = processRecord(id, playerId, matchId, fromMinutes, toMinutes);
                            recordsToSave.add(record);
                            validRecordCount++;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Skipping invalid record: " + line);
                    }
                }
            }

            // Save all valid records
            if (!recordsToSave.isEmpty()) {
                recordRepository.saveAll(recordsToSave);
            }

            System.out.println("All valid records have been imported. Total valid records: " + validRecordCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidRecord(Integer fromMinutes, Integer toMinutes) {
        return fromMinutes != null && fromMinutes > 0 && toMinutes <= 90;
    }

    private Record processRecord(Long id, Long playerId, Long matchId, Integer fromMinutes, Integer toMinutes) {
        Optional<Record> existingRecord = recordRepository.findByPlayerIdAndMatchId(playerId, matchId);

        if (existingRecord.isPresent()) {
            Record record = existingRecord.get();
            record.setFromMinutes(fromMinutes);
            record.setToMinutes(toMinutes);
            return record;
        } else {
            Record newRecord = new Record();
            newRecord.setId(id);
            newRecord.setPlayerId(playerId);
            newRecord.setMatchId(matchId);
            newRecord.setFromMinutes(fromMinutes);
            newRecord.setToMinutes(toMinutes);
            return newRecord;
        }
    }
}
