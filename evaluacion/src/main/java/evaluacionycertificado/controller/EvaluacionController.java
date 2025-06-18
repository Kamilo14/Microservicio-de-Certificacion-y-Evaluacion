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
import evaluacionycertificado.model.Evaluacion;
import evaluacionycertificado.service.EvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v1/evaluaciones")

public class EvaluacionController {
    
    @Autowired
    private EvaluacionService evaluacionservices;
     @Operation(summary = "Obtiene el listado de evaluaciones")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron evaluaciones")
    })

    @GetMapping("/listarevaluacion")
    public ResponseEntity<List<Evaluacion>> listar(){
        List<Evaluacion>eva = evaluacionservices.listarEva();
        if (eva.isEmpty()) {
            return ResponseEntity.notFound().build();
            
        }
        return ResponseEntity.ok(eva);
    }   

    @Operation(summary = "Busca por id las evaluaciones")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluación encontrada con éxito",
        content = @Content(mediaType = "application/json" ,schema = @Schema(implementation = Evaluacion.class))),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })

    @GetMapping("/{id}")
    public ResponseEntity<Evaluacion> buscarId(@PathVariable Long id){
        try {
            Evaluacion eva = evaluacionservices.buscarPorIdEva(id);
            return ResponseEntity.ok(eva);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crea una nueva evaluación")
    @ApiResponse(responseCode = "201", description = "Evaluación creada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class)))

    @PostMapping
    public ResponseEntity<Evaluacion>guardar(@RequestBody Evaluacion eva){
        Evaluacion evaluacionNueva = evaluacionservices.guardarEvaluacion(eva);
        return ResponseEntity.status(HttpStatus.CREATED).body(evaluacionNueva);
        

    }

    @Operation(summary = "Actualiza una evaluación existente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Evaluación actualizada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Evaluacion.class))),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Evaluacion>actualizarEvaluacion(@PathVariable Long id,@RequestBody Evaluacion evaluacion){
        try { 
            
            Evaluacion eva = evaluacionservices.buscarPorIdEva(id);
            eva.setTitulo(evaluacion.getTitulo());
            eva.setFecha(evaluacion.getFecha());
            eva.setPonderacion(evaluacion.getPonderacion());
            eva.setCursoId(id);
            
            evaluacionservices.guardarEvaluacion(eva);

            return ResponseEntity.ok(evaluacion);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


        @Operation(summary = "Elimina una evaluación por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Evaluación eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Evaluación no encontrada")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Evaluacion>eliminarEvaluacion(@PathVariable Long id){
        try {
            evaluacionservices.eliminarEva(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    

}
