package evaluacionycertificado.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import evaluacionycertificado.model.Certificacion;
import evaluacionycertificado.service.CertificacionService;
import evaluacionycertificado.service.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping ("api/v1/certificaciones")

public class CertificacionController {
    @Autowired
    private CertificacionService certificadoservices;

    @Autowired
    private EvaluacionService evaluacionservices;

    @Operation(summary = "Lista todas las certificaciones")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Certificacion.class))),
        @ApiResponse(responseCode = "204", description = "No hay certificaciones")
    })

    @GetMapping("/listarcertificado")
    public ResponseEntity<List<Certificacion>> listar(){
        List<Certificacion>certificado = certificadoservices.listarCertficacion();
        if (certificado.isEmpty()) {
            return ResponseEntity.noContent().build();
            
        }
        return ResponseEntity.ok(certificado);

        
    }
     @Operation(summary = "Crea una nueva certificación")
    @ApiResponse(responseCode = "201", description = "Certificación creada",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Certificacion.class)))

    @PostMapping
    public ResponseEntity<Certificacion>guardar(@RequestBody Certificacion certificacacion){
        Certificacion certificacionNueva= certificadoservices.guardarCertificado(certificacacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(certificacionNueva);
    }

     @Operation(summary = "Busca una certificación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Certificación encontrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Certificacion.class))),
        @ApiResponse(responseCode = "404", description = "Certificación no encontrada")
    })

    @GetMapping("/{id}")
    public ResponseEntity<Certificacion> buscarid(@PathVariable Long id){
        try {
            Certificacion certificacion = certificadoservices.buscarCertificadoId(id);
            return ResponseEntity.ok(certificacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @Operation(summary = "Actualiza una certificación existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Certificación actualizada exitosamente",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Certificacion.class))),
        @ApiResponse(responseCode = "404", description = "Certificación no encontrada")
    })

    @PutMapping("/{id}")
public ResponseEntity<Certificacion> actualizar(@PathVariable long id, @RequestBody Certificacion certificacion) {
    try {
        Certificacion cert = certificadoservices.buscarCertificadoId(id);

        
        evaluacionservices.buscarPorIdEva(certificacion.getEvaluacionId());

        
        cert.setNombre(certificacion.getNombre());
        cert.setEspecializacion(certificacion.getEspecializacion());
        cert.setFechaEmision(certificacion.getFechaEmision());
        cert.setFechaExpiracion(certificacion.getFechaExpiracion());
        cert.setDescripcion(certificacion.getDescripcion());
        cert.setCursoId(certificacion.getCursoId());
        cert.setUsuarioId(certificacion.getUsuarioId());
        cert.setEvaluacionId(certificacion.getEvaluacionId());

        certificadoservices.guardarCertificado(cert);

        return ResponseEntity.ok(cert);
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}


     @Operation(summary = "Elimina una certificación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Certificación eliminada correctamente"),
        @ApiResponse(responseCode = "404", description = "Certificación no encontrada")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            certificadoservices.eliminarCertificacion(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



}
