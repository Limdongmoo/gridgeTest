package com.example.GTC.log;

import com.example.GTC.log.model.Log;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {
    @Override
    Log save(Log log);

    List<Log> findAllByObjectAndCreatedDateBetween(String object, LocalDateTime start, LocalDateTime end, Pageable pageable);
    List<Log> findAllByCreatedDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    List<Log> findAllByObjectOrderByCreatedDateDesc(String object, Pageable pageable);

}
