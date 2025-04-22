package api.dargo.uz.repository;

import api.dargo.uz.entity.SmsHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {
    Long countByPhoneAndCreatedDateBetween(String phone , LocalDateTime from, LocalDateTime to);
}