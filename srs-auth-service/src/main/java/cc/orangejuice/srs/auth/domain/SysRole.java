package cc.orangejuice.srs.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@Builder
@Table(name = "sys_role")
public class SysRole {

    @Id
    @Column(name = "id", length = 32)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String id;

    @Size(max = 50)
    @NotNull
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Size(max = 128)
    @NotNull
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToMany
    @JoinTable(joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "resource_id")})
    private List<SysResource> resources;
}
