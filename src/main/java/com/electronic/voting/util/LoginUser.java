package com.electronic.voting.util;

public class LoginUser{

    private String username;
    private String password;
    private boolean validUser;
    private boolean hasAccess;
    private String guid;
    private String adsPath;
    private Long grpNumber;
//
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.userRoles;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAccountNonExpired() {
        return this.validUser;
    }

    public boolean isAccountNonLocked() {
        return this.hasAccess;
    }

    public boolean isCredentialsNonExpired() {
        return this.hasAccess;
    }

    public boolean isEnabled() {
        return this.validUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isValidUser() {
        return validUser;
    }

    public void setValidUser(boolean validUser) {
        this.validUser = validUser;
    }

    public boolean isHasAccess() {
        return hasAccess;
    }

    public void setHasAccess(boolean hasAccess) {
        this.hasAccess = hasAccess;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAdsPath() {
        return adsPath;
    }

    public void setAdsPath(String adsPath) {
        this.adsPath = adsPath;
    }

    public Long getGrpNumber() {
        return grpNumber;
    }

    public void setGrpNumber(Long grpNumber) {
        this.grpNumber = grpNumber;
    }


}