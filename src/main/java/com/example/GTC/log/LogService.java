package com.example.GTC.log;

import com.example.GTC.log.model.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LogService {
    private final LogRepository logRepository;

    @Autowired
    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }


    public List<Log> findAllLogByClass(String className, Pageable pageable) {
        return logRepository.findAllByObjectOrderByCreatedDateDesc(className,pageable);
    }
    public List<Log> findAllLogByClassAndDateBetween(String object, LocalDateTime start, LocalDateTime end,Pageable pageable){
        return logRepository.findAllByObjectAndCreatedDateBetween(object, start, end,pageable);
    }

    public List<Log> findAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return logRepository.findAllByCreatedDateBetween(start, end, pageable);
    }


}
