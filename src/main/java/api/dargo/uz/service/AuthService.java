package api.dargo.uz.service;

import api.dargo.uz.dto.AppResponse;
import api.dargo.uz.dto.AuthDTO;
import api.dargo.uz.dto.ProfileDTO;
import api.dargo.uz.dto.RegistrationDTO;
import api.dargo.uz.entity.ProfileEntity;
import api.dargo.uz.enums.GeneralStatus;
import api.dargo.uz.enums.ProfileRole;
import api.dargo.uz.exps.AppBadException;
import api.dargo.uz.repository.ProfileRepository;
import api.dargo.uz.repository.ProfileRoleRepository;
import api.dargo.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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


    public AppResponse<String> registration(RegistrationDTO dto) {
//        validation
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(profile.getId());
                profileRepository.delete(profile);
                //send sms/email;
            } else {
                throw new AppBadException("Username already exists");
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
        emailSendingService.sendRegistrationEmail(dto.getUsername() , entity.getId());
        //send
        return new AppResponse<String>("Successfully registered");
    }

    public String regVerification(String token) {
        try {
            Integer profileId = JwtUtil.decodeRegVerToken(token);
            ProfileEntity profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.changeStatus(profileId , GeneralStatus.ACTIVE);
                return "VERIFICATION SUCCESSFUL";
            }
        }catch (JwtException e) {
        }
        throw new AppBadException("VERIFICATION FAILED");
    }

    public ProfileDTO login(AuthDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException("Username or password is wrong");
        }
        ProfileEntity profile = optional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new AppBadException("Username or password is wrong");
        }
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException("Wrong status");
        }
        ProfileDTO response = new ProfileDTO();
        response.setUsername(profile.getUsername());
        response.setName(profile.getName());
        response.setRoleList(profileRoleRepository.getAllRolesListByProfileId(profile.getId()));
        response.setJwt(JwtUtil.encode(profile.getUsername(), profile.getId() , response.getRoleList()));
        return response;
    }
}
