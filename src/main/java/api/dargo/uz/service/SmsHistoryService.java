package api.dargo.uz.service;

import api.dargo.uz.entity.SmsHistoryEntity;
import api.dargo.uz.enums.SmsType;
import api.dargo.uz.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SmsHistoryService {

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void create(String phoneNumber, String message, String code, SmsType smsType) {

        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phoneNumber);
        entity.setMessage(message);
        entity.setCode(code);
        entity.setSmsType(smsType);
        entity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(entity);
    }

    public Long getSmsCount(String phoneNumber) {
        LocalDateTime now = LocalDateTime.now();
        return smsHistoryRepository.countByPhoneAndCreatedDateBetween(phoneNumber, now.minusMinutes(1), now);

    }
}
