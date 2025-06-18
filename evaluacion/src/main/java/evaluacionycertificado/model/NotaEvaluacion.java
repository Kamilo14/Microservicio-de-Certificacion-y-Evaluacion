package evaluacionycertificado.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="NotaEvaluacion")

@Schema(description = "Entidad que representa la nota obtenida por un usuario")

public class NotaEvaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNota;

    @Column(nullable = false)
    private float nota;

    @Column(nullable = false)
    private Integer puntaje;

    @Column(nullable = true)
    private String descripcion;

    @Column(name= "usuario_id",nullable = false)
    private Long usuarioId;

    @Column(name = "evaluacion_id", nullable = false)
    private Long evaluacionId;

    


}
