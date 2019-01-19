package cc.orangejuice.srs.auth.service;

import cc.orangejuice.srs.auth.domain.SysUser;
import cc.orangejuice.srs.auth.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
public class SysUserService implements UserDetailsService {

    private final SysUserRepository sysUserRepository;

    @Autowired
    public SysUserService(SysUserRepository sysUserRepository) {
        this.sysUserRepository = sysUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Example<SysUser> example = Example.of(SysUser.builder().username(username).build());

        Optional<SysUser> sysUserOptional = sysUserRepository.findOne(example);
        if (sysUserOptional.isPresent()) {
            return sysUserOptional.get();
        } else {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        }
    }
}
