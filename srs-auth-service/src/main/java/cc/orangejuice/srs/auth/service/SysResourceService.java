package cc.orangejuice.srs.auth.service;

import cc.orangejuice.srs.auth.domain.SysResource;
import cc.orangejuice.srs.auth.domain.SysRole;
import cc.orangejuice.srs.auth.domain.SysUser;
import cc.orangejuice.srs.auth.exception.UserNotFoundException;
import cc.orangejuice.srs.auth.repository.SysUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
public class SysResourceService {
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final SysUserRepository userRepository;

    @Autowired
    public SysResourceService(SysUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<SysResource> findPermittedResourcesByUserId(String userId) {
        SysUser user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return user.getRoles().stream()
                .map(SysRole::getResources)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        Object principal = authentication.getPrincipal();
        boolean result = false;
        if (principal instanceof UserDetails) {
//            UserDetails userDetails = (UserDetails) principal;
//
//            Object[] authorities = userDetails.getAuthorities().toArray();
//            String userId = authorities[0].toString();

            List<SysResource> permissions = findPermittedResourcesByUserId(((SysUser) principal).getId());

            for (SysResource sysResource : permissions) {
                //System.out.println("[url="+sysPermission.getLink()+"] [method="+sysPermission.getMethod()+"] URI=["+request.getRequestURI()+"] [URL="+request.getRequestURL()+"]");
                if (antPathMatcher.match(sysResource.getUrl(), request.getRequestURI())) {
                    //当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
                    if (sysResource.getMethod().equals(request.getMethod()) || "ALL".equals(sysResource.getMethod())) {
                        result = true;
                        break;
                    }
                }
            }
        }
        return result;
    }
}
