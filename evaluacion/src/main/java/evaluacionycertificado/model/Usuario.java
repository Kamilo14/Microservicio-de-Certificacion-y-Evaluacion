package evaluacionycertificado.model;
import org.hibernate.annotations.Immutable;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String nombre;
    private String password;

    @Column(name = "is_enable")
    private boolean enable;

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;
    
    @Column(name = "account_no_locked")
    private boolean accountNolocked;

    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

}