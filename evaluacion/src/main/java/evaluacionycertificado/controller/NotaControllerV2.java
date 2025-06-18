package evaluacionycertificado.controller;

import evaluacionycertificado.assembler.NotaModelAssembler;
import evaluacionycertificado.model.NotaEvaluacion;
import evaluacionycertificado.service.NotaEvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/notas")
public class NotaControllerV2 {

    @Autowired
    private NotaEvaluacionService notaEvaluacionService;

    @Autowired
    private NotaModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<NotaEvaluacion>> getAll() {
        List<NotaEvaluacion> notas = notaEvaluacionService.listarNota();
        List<EntityModel<NotaEvaluacion>> modelos = notas.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(modelos,
                linkTo(methodOn(NotaControllerV2.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<NotaEvaluacion> getById(@PathVariable Long id) {
        NotaEvaluacion nota = notaEvaluacionService.buscarNota(id);
        return assembler.toModel(nota);
    }
}
