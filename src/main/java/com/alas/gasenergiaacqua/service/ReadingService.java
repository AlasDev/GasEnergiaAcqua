package com.alas.gasenergiaacqua.service;

import com.alas.gasenergiaacqua.dto.*;
import com.alas.gasenergiaacqua.entity.Reading;
import com.alas.gasenergiaacqua.filter.ReadingFilter;
import com.alas.gasenergiaacqua.mapper.ReadingMapper;
import com.alas.gasenergiaacqua.repository.ReadingRepository;
import com.alas.gasenergiaacqua.repository.UtilityMeterRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.chrono.ChronoLocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ReadingService {
    private final ReadingRepository readingRepository;
    private final ReadingMapper readingMapper;
    private final UtilityMeterRepository utilityMeterRepository;

    public ReadingService(ReadingRepository readingRepository, ReadingMapper readingMapper, UtilityMeterRepository utilityMeterRepository) {
        this.readingRepository = readingRepository;
        this.readingMapper = readingMapper;
        this.utilityMeterRepository = utilityMeterRepository;
    }

    /**
     * @param id uuid of the Reading you want to get
     * @return a DTO if found
     */
    public ReadingDTO getById(UUID id) {
        return readingMapper.mapToDto(readingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find!\nNo reading was found with id: " + id)));
    }

    /**
     * @param params filters applied to the search
     * @param pageable pageable
     * @return a pageDTO containing a list of DTO
     */
    public PageDTO<ReadingDTO> searchBySpecification(Pageable pageable, ReadingFilter params) {
        return readingMapper.mapToPageDTO(readingRepository.findAll(params.toSpecification(), pageable));
    }

    /**
     * Deletes a Reading with given uuid
     * @param id uuid of the Reading which is going to be deleted
     * @return a ResponseMessage
     */
    public ResponseMessage deleteById(UUID id) {
        readingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot delete!\nNo reading was found with id: " + id));
        readingRepository.findById(id).ifPresent(readingRepository::delete);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("Reading deleted successfully")
                .build();
    }

    /**
     * @param readingDTO info needed to create a new reading
     * @return a ResponseMessage
     */
    public ResponseMessage postNew(ReadingNewDTO readingDTO) {
        Reading newReading = Reading.builder()
                .utilityMeter(utilityMeterRepository.findById(readingDTO.getUtilityMeterId())
                        .orElseThrow(() -> new NoSuchElementException("Cannot create new reading!\nNo utility meter was found")))
                .valueRecorded(readingDTO.getValueRecorded())
                .readingTimestamp(readingDTO.getReadingTimestamp())
                .notes(readingDTO.getNotes())
                .build();
        readingRepository.save(newReading);

        return ResponseMessage.builder()
                .timestamp(Instant.now())
                .message("New address registered successfully")
                .build();
    }

    /**
     * @param readingDTO the changes you want to apply
     * @return the updated Reading
     */
    public ReadingDTO updateReading(ReadingUpdateDTO readingDTO) {
        Reading reading = readingRepository.findById(readingDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Cannot update!\nNo reading was found"));

        if (readingDTO.getValueRecorded() != null) {
            reading.setValueRecorded(readingDTO.getValueRecorded());
        }
        if (readingDTO.getReadingTimestamp() != null && readingDTO.getReadingTimestamp().isBefore(ChronoLocalDateTime.from(Instant.now()))) {
            reading.setReadingTimestamp(readingDTO.getReadingTimestamp());
        }
        if (readingDTO.getNotes() != null) {
            reading.setNotes(readingDTO.getNotes());
        }

        return readingMapper.mapToDto(readingRepository.save(reading));
    }
}
