package sg.gov.financial.assistance.scheme.assignment.entity;

import jakarta.persistence.*;
import sg.gov.financial.assistance.scheme.assignment.constant.RoleName;

@Entity
@Table(name = "t_role", schema = "public")
public class RoleEntity {

    @Id
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;

    public RoleEntity() {
    }

    public RoleEntity(RoleName roleName) {

        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
