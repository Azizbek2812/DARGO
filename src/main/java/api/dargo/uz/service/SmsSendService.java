package api.dargo.uz.service;

import api.dargo.uz.dto.sms.SmsAuthDto;
import api.dargo.uz.dto.sms.SmsAuthResponseDTO;
import api.dargo.uz.dto.sms.SmsRequestDTO;
import api.dargo.uz.dto.sms.SmsSendResponseDTO;
import api.dargo.uz.entity.SmsHistoryEntity;
import api.dargo.uz.entity.SmsProviderTokenHolderEntity;
import api.dargo.uz.entity.SmsProviderTokenHolderEntity;
import api.dargo.uz.enums.SmsType;
import api.dargo.uz.exps.AppBadException;
import api.dargo.uz.repository.SmsProviderTokenHolderRepository;
import api.dargo.uz.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Limit;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsSendService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SmsProviderTokenHolderRepository smsProviderTokenHolder;

    @Autowired
    private SmsHistoryService smsHistoryService;

    @Value("${eskiz.url}")
    private String smsURL;
    @Value("${eskiz.login}")
    private String accountLogin;
    @Value("${eskiz.password}")
    private String accountPassword;
    private Integer smsLimit = 2;
    @Autowired
    private SmsProviderTokenHolderRepository smsProviderTokenHolderRepository;

    public void sendRegistrationSms(String phoneNumber) {
        String code = RandomUtil.getRandomSmsCode();
        String message = "This is test from eskiz";
//        message = String.format(message, code);  😶‍🌫️
        sendSms(phoneNumber, message , code , SmsType.REGISTRATION);
    }



    private SmsSendResponseDTO sendSms(String phoneNumber, String message ,String code , SmsType smsType ){
        //check
        Long count = smsHistoryService.getSmsCount(phoneNumber);
        if (count >= smsLimit){
            System.out.println("Limit reached Phone: " + phoneNumber);
            throw new  AppBadException("SMS limit is reached");
        }

        SmsSendResponseDTO result = sendSms(phoneNumber , message);
        smsHistoryService.create(phoneNumber , message , code , smsType);
        return result;
    }

    private SmsSendResponseDTO sendSms(String phoneNumber, String message) {
//        get token
        String token = getToken();
//        send sms

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", "Bearer " + token);



        SmsRequestDTO body = new SmsRequestDTO();
        body.setMobile_phone(phoneNumber);
        body.setMessage(message);
        body.setFrom("4546");
        //request
        HttpEntity<SmsRequestDTO> entity = new HttpEntity<>(body, headers);
        String url = smsURL + "message/sms/send";
        try{
            ResponseEntity<SmsSendResponseDTO> response = restTemplate.exchange(url,
                    HttpMethod.POST,
                    entity,
                    SmsSendResponseDTO.class);
            //check response
            return response.getBody();
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }

    }

    private String getToken() {
        Optional<SmsProviderTokenHolderEntity> optional = smsProviderTokenHolderRepository.findTop1By();
        if (optional.isEmpty()) {
            String token = getTokenFromProvider();
            SmsProviderTokenHolderEntity entity = new SmsProviderTokenHolderEntity();
            entity.setToken(token);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setExpDate(LocalDateTime.now().plusMonths(1));
            smsProviderTokenHolderRepository.save(entity);
            return token;
        }
        SmsProviderTokenHolderEntity entity = optional.get();
//        LocalDateTime expDate = entity.getCreatedDate().plusMonths(1);
        if (LocalDateTime.now().isBefore(entity.getExpDate())) {
            return entity.getToken();
        }
//        if token expired
        System.out.println("yangi token olindi exp uchun ");
        String token = getTokenFromProvider();
        entity.setToken(token);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setExpDate(LocalDateTime.now().plusMonths(1));
        smsProviderTokenHolderRepository.save(entity);
        return token;
    }

    private String getTokenFromProvider() {
        SmsAuthDto smsAuthDto = new SmsAuthDto();
        smsAuthDto.setEmail(accountLogin);
        smsAuthDto.setPassword(accountPassword);

        try {
            SmsAuthResponseDTO response = restTemplate.postForObject(smsURL + "auth/login", smsAuthDto, SmsAuthResponseDTO.class);
            System.out.println(response.getData().getToken());
            return response.getData().getToken();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
