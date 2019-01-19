package cc.orangejuice.srs.auth.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@Table(name = "sys_resource")
public class SysResource {

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

    private String type;
    private String url;
    private String method;
    private String description;
}
