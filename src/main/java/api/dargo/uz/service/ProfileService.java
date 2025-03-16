package api.dargo.uz.service;

import api.dargo.uz.entity.ProfileEntity;
import api.dargo.uz.exps.AppBadException;
import api.dargo.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileEntity getById(Integer id) {
        return profileRepository.findByIdAndVisibleTrue(id).orElseThrow(()->{
            throw new AppBadException("Profile Not Found");
        });
    }
}
