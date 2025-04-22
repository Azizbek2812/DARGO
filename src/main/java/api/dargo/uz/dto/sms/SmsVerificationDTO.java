package api.dargo.uz.dto.sms;

import jakarta.validation.constraints.NotBlank;

public class SmsVerificationDTO {
    @NotBlank(message = "Phone required")
    private String phone;
    @NotBlank(message = "code required")
    private String code;
}
