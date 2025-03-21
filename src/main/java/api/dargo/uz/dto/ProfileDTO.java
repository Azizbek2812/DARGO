package api.dargo.uz.dto;

import api.dargo.uz.enums.ProfileRole;

import java.util.List;

public class ProfileDTO {
    private String name;
    private String username;
    private List<ProfileRole> roleList;
    private String jwt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ProfileRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ProfileRole> roleList) {
        this.roleList = roleList;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
