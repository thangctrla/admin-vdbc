package vn.vdbc.core.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import vn.vdbc.core.model.UserData
import vn.vdbc.core.repository.UserRepo
import vn.vdbc.core.service.DatabaseService

import java.util.stream.Collectors

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    DatabaseService dbs

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        def roles = dbs.rows("xcore","SELECT r.name FROM roles r LEFT JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = ?", [user.id])
//        roles.collect { it.name }
        return User
                .withUsername(username)
                .password(user.getId())
                .authorities(roles.collect { it.name }.stream()
                        .map(role -> new SimpleGrantedAuthority(role.toString()))
                        .collect(Collectors.toList()))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}

