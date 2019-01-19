package cc.orangejuice.srs.auth.domain;

import cc.orangejuice.srs.auth.config.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sys_user")
public class SysUser implements UserDetails {
    @Id
    @Column(name = "id", length = 32)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @NotNull
    @Pattern(regexp = Constants.USERNAME_REGEX)
    @Size(min = 1, max = 50)
    @Column(name = "username", unique = true, columnDefinition = "varchar(128) not null comment '用户名称'")
    private String username;

    @JsonIgnore
    @NotNull
    @Column(name = "password", columnDefinition = "varchar(128) not null comment '用户密码'")
    private String password;

    @Email
    @Column(name = "email", columnDefinition = "varchar(50) not null comment '邮箱'")
    private String email;

    @Column(name = "enabled", columnDefinition = "bit default 1 not null comment '是否可用:0 不可用；1 可用'")
    @Builder.Default
    private Boolean enabled = true;

    @Column(name = "lastPasswordResetDate", columnDefinition = "datetime not null comment '最近设置密码日期'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordResetDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @Builder.Default
    private Set<SysRole> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
