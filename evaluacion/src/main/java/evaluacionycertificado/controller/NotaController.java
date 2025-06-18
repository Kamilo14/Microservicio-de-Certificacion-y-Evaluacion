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
import evaluacionycertificado.model.NotaEvaluacion;
import evaluacionycertificado.service.EvaluacionService;
import evaluacionycertificado.service.NotaEvaluacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("api/v1/notas")

public class NotaController {
    @Autowired
    private NotaEvaluacionService notaservices;

    @Autowired
    private EvaluacionService evaluacionservices;

    @Operation(summary = "Lista todas las notas de evaluación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de notas obtenida con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotaEvaluacion.class))),
        @ApiResponse(responseCode = "404", description = "No se encontraron notas")
    })

    @GetMapping("/listarnota")
    public ResponseEntity<List<NotaEvaluacion>> listarnota(){
        List<NotaEvaluacion>notaevaluacion = notaservices.listarNota();
        if (notaevaluacion.isEmpty()) {
            return ResponseEntity.notFound().build();
            
        }
        return ResponseEntity.ok(notaevaluacion);
    }

    

    @Operation(summary = "Busca una nota por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota encontrada con éxito",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotaEvaluacion.class))),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotaEvaluacion>buscarNotaId(@PathVariable Long id){
        try {
            NotaEvaluacion notaevaluacion = notaservices.buscarNota(id);
            return ResponseEntity.ok(notaevaluacion);
            
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

     @Operation(summary = "Actualiza una nota de evaluación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota actualizada con éxito"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @PutMapping("/{id}")
public ResponseEntity<NotaEvaluacion> actualizarNota(@PathVariable Long id, @RequestBody NotaEvaluacion notaEvaluacion) {
    try {
        NotaEvaluacion nota = notaservices.buscarNota(id);
        Evaluacion evaluacion = evaluacionservices.buscarPorIdEva(notaEvaluacion.getEvaluacionId());

        nota.setIdNota(id);  
        nota.setNota(notaEvaluacion.getNota());
        nota.setPuntaje(notaEvaluacion.getPuntaje());
        nota.setDescripcion(notaEvaluacion.getDescripcion()); 
        nota.setUsuarioId(notaEvaluacion.getUsuarioId()); 
        nota.setEvaluacionId(evaluacion.getId()); 

        notaservices.guardarNota(nota);
        return ResponseEntity.ok(nota);
    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}

    @Operation(summary = "Crea una nueva nota de evaluación")
    @ApiResponse(responseCode = "201", description = "Nota creada con éxito",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = NotaEvaluacion.class)))
    

    @PostMapping
    public ResponseEntity<NotaEvaluacion>guardarNota(@RequestBody NotaEvaluacion notaevaluacion){
        NotaEvaluacion nuevaNota = notaservices.guardarNota(notaevaluacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNota);
    }


    @Operation(summary = "Elimina una nota de evaluación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Nota eliminada con éxito"),
        @ApiResponse(responseCode = "404", description = "Nota no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<NotaEvaluacion>eliminarNota(@PathVariable Long id){
        try {
            notaservices.eliminarNota(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

}
