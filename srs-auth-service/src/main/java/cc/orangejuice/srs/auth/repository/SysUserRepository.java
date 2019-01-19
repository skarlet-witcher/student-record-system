package cc.orangejuice.srs.auth.repository;

import cc.orangejuice.srs.auth.domain.SysResource;
import cc.orangejuice.srs.auth.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SysUserRepository extends JpaRepository<SysUser, String> {

}
