package api.dargo.uz.repository;

import api.dargo.uz.entity.ProfileRoleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity , Integer> {

    @Transactional
    @Modifying
    void deleteByProfileId(Integer profileId);
}
