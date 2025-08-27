package vn.vdbc.core.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import vn.vdbc.core.model.UserData;
import vn.vdbc.core.model.base.OwnedEntity;
import vn.vdbc.core.repository.UserRepo;
import vn.vdbc.core.utils.Utils;

import java.util.Collections;
import java.util.List;

@Component
@RepositoryEventHandler
public class GenericEntityEventHandler {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    Utils utils;

    @HandleBeforeCreate
    public void handleBeforeCreate(Object entity) {
        if (entity instanceof OwnedEntity) {
            setCurrentUserAsOwner((OwnedEntity) entity);
        }
    }

    @HandleBeforeSave
    public void handleBeforeSave(Object entity) {
        if (entity instanceof OwnedEntity) {
            validateOwnership((OwnedEntity) entity);
        }
    }

    @HandleBeforeDelete
    public void handleBeforeDelete(Object entity) {
        if (entity instanceof OwnedEntity) {
            validateOwnership((OwnedEntity) entity);
        }
    }

    private void setCurrentUserAsOwner(OwnedEntity entity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            String username = auth.getName();
            UserData user = userRepository.findById(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));
            entity.setCreatedBy(user);
        }
    }

    private void validateOwnership(OwnedEntity entity) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            String username = auth.getName();
            UserData currentUser = userRepository.findById(username)
                    .orElseThrow(() -> new RuntimeException("User not found: " + username));

            List<Object> roles = Collections.singletonList(utils.getRoles(currentUser.getId()));
            if (entity.getCreatedBy() != null &&
                    !entity.getCreatedBy().getId().equals(currentUser.getId()) &&
                    !roles.contains("ROLE_ADMIN")) {
                throw new RuntimeException("You can only modify your own records");
            }

        }
    }
}

