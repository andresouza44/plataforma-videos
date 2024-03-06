package com.andre.plataformavideos.projections;

public interface UserDetailsProjection {
    String getUserName();
    String getPassword();
    Long getRoleId();
    String getAuthority();
}
