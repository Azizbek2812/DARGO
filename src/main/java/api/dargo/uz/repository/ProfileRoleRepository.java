package api.dargo.uz.repository;

import api.dargo.uz.entity.ProfileRoleEntity;
import api.dargo.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity , Integer> {

    @Transactional
    @Modifying
    void deleteByProfileId(Integer profileId);

    @Query("select p.roles From ProfileRoleEntity p where p.profileId = ?1")
    List<ProfileRole> getAllRolesListByProfileId(Integer profileId);
}
