package com.events.diplomna_project.Config;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class Permissions implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if ((authentication == null) || !(permission instanceof String)) {
            return false;
        }

        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
        String targetPermission = permission.toString().toUpperCase();

        return hasRole(authentication, targetPermission) &&
                hasPrivilege(authentication, targetType, targetPermission);
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || !(permission instanceof String)) {
            return false;
        }

        String targetPermission = permission.toString().toUpperCase();

        return hasRole(auth, targetPermission) &&
                hasPrivilege(auth, targetType.toUpperCase(), targetPermission);
    }

    private boolean hasRole(Authentication auth, String targetRole) {
        for (GrantedAuthority grantedAuth : auth.getAuthorities()) {
            if (grantedAuth.getAuthority().equals(targetRole)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        // Your existing hasPrivilege logic
        // ...

        return false; // Return the actual result
    }
}
