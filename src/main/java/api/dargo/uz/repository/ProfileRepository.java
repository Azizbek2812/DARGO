package api.dargo.uz.repository;

import api.dargo.uz.entity.ProfileEntity;
import api.dargo.uz.entity.ProfileRoleEntity;
import api.dargo.uz.enums.GeneralStatus;
import api.dargo.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);
    Optional<ProfileEntity> findByIdAndVisibleTrue(Integer id);

    @Modifying
    @Transactional
    @Query("update ProfileEntity set status = ?2 where id = ?1")
    void changeStatus(Integer profileId, GeneralStatus generalStatus);

}
