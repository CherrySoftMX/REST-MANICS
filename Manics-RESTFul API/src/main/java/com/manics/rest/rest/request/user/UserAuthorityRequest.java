package com.manics.rest.rest.request.user;

import com.google.common.collect.Sets;
import com.manics.rest.model.auth.UserRole;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserAuthorityRequest {

    /**
     * {
     *     "roles": [
     *
     *             "ADMIN" || "NORMAL"
     *
     *     ]
     * }
     */
    @NotNull(message = "Se debe especificar el nuevo rol del usuario.")
    private Set<UserRole> roles = Sets.newHashSet(UserRole.NORMAL);

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

}
