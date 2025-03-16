package api.dargo.uz.repository;

import api.dargo.uz.entity.ProfileEntity;
import api.dargo.uz.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);
    Optional<ProfileEntity> findByIdAndVisibleTrue(Integer username);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set status = ?2 where id = ?1")
    void changeStatus(Integer profileId, GeneralStatus generalStatus);
}
