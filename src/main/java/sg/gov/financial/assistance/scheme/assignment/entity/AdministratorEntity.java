package sg.gov.financial.assistance.scheme.assignment.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "t_administrator", schema = "public")
public class AdministratorEntity extends AbstractAuditEntity implements UserDetails {

    @Id
    @SequenceGenerator(name = "administrator_seq", sequenceName = "administrator_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "administrator_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "uin")
    private String uin;


    @Column(name = "password")
    private String password;

    public AdministratorEntity() {
    }

    public AdministratorEntity(String uin, String password) {
        this.uin = uin;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_SYSTEM_ADMIN"));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.uin;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUin() {
        return uin;
    }

    public void setUin(String uin) {
        this.uin = uin;
    }
}
