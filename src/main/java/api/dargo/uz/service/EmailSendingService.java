package api.dargo.uz.service;

import api.dargo.uz.util.JwtUtil;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class EmailSendingService {

    @Value("${spring.mail.username}")
    private String fromAccount;

    @Value("${server.domain}")
    private String serverDomain;

    @Autowired
    private JavaMailSender  javaMailSender;



    public void sendRegistrationEmail(String email , Integer profileId){
        String subject = "Complete Registration";
        String body  = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Email Verification</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            background: #fff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        h1 {\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        p {\n" +
                "            color: #555;\n" +
                "            font-size: 16px;\n" +
                "            line-height: 1.5;\n" +
                "        }\n" +
                "        .btn {\n" +
                "            display: inline-block;\n" +
                "            padding: 12px 25px;\n" +
                "            font-size: 16px;\n" +
                "            color: #fff;\n" +
                "            background-color: #d9534f;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "            margin-top: 15px;\n" +
                "            font-weight: bold;\n" +
                "        }\n" +
                "        .btn:hover {\n" +
                "            background-color: #c9302c;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 14px;\n" +
                "            color: #777;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<div class=\"container\">\n" +
                "    <h1>Complete Your Registration</h1>\n" +
                "    <p>Salom, umid qilamizki, siz yaxshi kayfiyatdasiz!</p>\n" +
                "    <p>Iltimos, ro'yxatdan o'tishni yakunlash uchun quyidagi tugmani bosing:</p>\n" +
                "    <a class=\"btn\" href=\"%s/auth/registration/verification/%s\" target=\"_blank\">Verify Email</a>\n" +
                "    <p class=\"footer\">Agar siz bu so‘rovni yubormagan bo‘lsangiz, iltimos, ushbu xabarni e'tiborsiz qoldiring.</p>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
        body = String.format(body,serverDomain, JwtUtil.encode(profileId));
        sendMimeEmail(email, subject, body);
    }


//    private void sendEmail(String email, String subject, String body) {
//        SimpleMailMessage msg = new SimpleMailMessage();
//        msg.setFrom(fromAccount);
//        msg.setTo(email);
//        msg.setSubject(subject);
//        msg.setText(body);
//        JavaMailSender.send(msg);
//    }
    private void sendMimeEmail(String email , String subject, String body){
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromAccount);

            MimeMessageHelper helper = new MimeMessageHelper(msg , true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(body , true);
            CompletableFuture.runAsync(()->{
                javaMailSender.send(msg);
            });
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
