package cc.orangejuice.srs.auth.repository;

import cc.orangejuice.srs.auth.domain.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRoleRepository extends JpaRepository<SysRole, String> {
}
