package com.xb.cinstar.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xb.cinstar.models.UserModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String userName;
    @JsonIgnore
    private String password;
    private Long id;
    private String email;
    private String phoneNumber;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String userName, String password, Long id, String email, String phoneNumber, Collection<? extends GrantedAuthority> authorities) {
        this.userName = userName;
        this.password = password;
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.authorities = authorities;
    }

    public static  UserDetailsImpl build(UserModel userModel) {
        List<GrantedAuthority> authorities = userModel.getRoles().stream().map(
                roleModel -> new SimpleGrantedAuthority(roleModel.getName().name())).collect(Collectors.toList());
        return new UserDetailsImpl(userModel.getUserName(), userModel.getPassword(), userModel.getId(), userModel.getEmail(),
                userModel.getPhoneNumber(), authorities);
    }


    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    public boolean equal(Object o) {
        if(this == o)
        {
           return  true;
        }
        else if(o==null || getClass()!= o.getClass())
        {
            return  false;
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) o;
        return  Objects.equals(id, userDetails.id);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
