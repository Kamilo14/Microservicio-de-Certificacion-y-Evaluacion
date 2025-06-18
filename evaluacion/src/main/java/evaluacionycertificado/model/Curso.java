package evaluacionycertificado.model;
import java.util.Date;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "curso")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Immutable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})



public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCurso;

    @Column
    private String descripcionCurso;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) // Esto indica que solo se guarda la fecha, no la hora
    private Date duracionCurso;

    

}
