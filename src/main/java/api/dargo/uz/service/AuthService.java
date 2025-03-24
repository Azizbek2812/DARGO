package api.dargo.uz.service;

import api.dargo.uz.dto.AppResponse;
import api.dargo.uz.dto.AuthDTO;
import api.dargo.uz.dto.ProfileDTO;
import api.dargo.uz.dto.RegistrationDTO;
import api.dargo.uz.entity.ProfileEntity;
import api.dargo.uz.enums.AppLanguage;
import api.dargo.uz.enums.GeneralStatus;
import api.dargo.uz.enums.ProfileRole;
import api.dargo.uz.exps.AppBadException;
import api.dargo.uz.repository.ProfileRepository;
import api.dargo.uz.repository.ProfileRoleRepository;
import api.dargo.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private EmailSendingService emailSendingService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private ResourceBundleService bundleService;

    @Transactional
    public AppResponse<String> registration(RegistrationDTO dto, AppLanguage lang) {
//        validation
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(profile.getId());
                profileRepository.delete(profile);
                //send sms/email;
            } else {
                throw new AppBadException(bundleService.getMessage("email.phone.exists", lang));
            }
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        //insert Roles
        profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);
        emailSendingService.sendRegistrationEmail(dto.getUsername(), entity.getId());
        //send
        return new AppResponse<>(bundleService.getMessage("email.confirm.send", lang));
    }

    public AppResponse<String> regVerification(String token, String lang) {
        try {
            Integer profileId = JwtUtil.decodeRegVerToken(token);
            ProfileEntity profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
                return new AppResponse<>(bundleService.getMessage("verification.successful", convertLanguage(lang)));
            }
        } catch (JwtException e) {
        }
        throw new AppBadException(bundleService.getMessage("verification.failed", convertLanguage(lang)));
    }

    private AppLanguage convertLanguage(String lang) {
        if (lang == null || lang.isBlank()) return AppLanguage.EN; // Default til
        for (AppLanguage language : AppLanguage.values()) {
            if (lang.toLowerCase().startsWith(language.name().toLowerCase())) {
                return language;
            }
        }
        return AppLanguage.EN; // Default qiymat
    }

    public AppResponse<ProfileDTO> login(AuthDTO dto , AppLanguage lang) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("username.password.wrong" , lang));
        }
        ProfileEntity profile = optional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new AppBadException(bundleService.getMessage("username.password.wrong" , lang));
        }
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException(bundleService.getMessage("wrong.status" , lang));
        }
        ProfileDTO response = new ProfileDTO();
        response.setUsername(profile.getUsername());
        response.setName(profile.getName());
        response.setRoleList(profileRoleRepository.getAllRolesListByProfileId(profile.getId()));
        response.setJwt(JwtUtil.encode(profile.getUsername(), profile.getId(), response.getRoleList()));
        return new AppResponse<>(response);
    }
}
