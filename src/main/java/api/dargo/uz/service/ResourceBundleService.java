package api.dargo.uz.service;

import api.dargo.uz.enums.AppLanguage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class ResourceBundleService {
    @Autowired
    private ResourceBundleMessageSource bundleMessage;
    public String getMessage(String key , AppLanguage lang) {
        return bundleMessage
                .getMessage(key, null , new Locale(lang.name()));
    }
}
