package api.dargo.uz;

import api.dargo.uz.enums.SmsType;
import api.dargo.uz.service.SmsSendService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private SmsSendService smsSendService;

	@Test
	void contextLoads() throws JsonProcessingException {
		smsSendService.sendSms("998930802812" , "This is test from eskiz","1234" , SmsType.REGISTRATION);
	}

}
