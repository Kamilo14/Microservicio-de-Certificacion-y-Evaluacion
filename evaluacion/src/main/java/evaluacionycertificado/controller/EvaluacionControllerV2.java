package evaluacionycertificado.controller;

import evaluacionycertificado.assembler.EvaluacionModelAssembler;
import evaluacionycertificado.model.Evaluacion;
import evaluacionycertificado.service.EvaluacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/evaluaciones")
public class EvaluacionControllerV2 {

    @Autowired
    private EvaluacionService evaluacionService;

    @Autowired
    private EvaluacionModelAssembler assembler;

    @GetMapping
    public CollectionModel<EntityModel<Evaluacion>> getAll() {
        List<Evaluacion> lista = evaluacionService.listarEva();
        List<EntityModel<Evaluacion>> modelos = lista.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(modelos,
                linkTo(methodOn(EvaluacionControllerV2.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Evaluacion> getById(@PathVariable Long id) {
        Evaluacion evaluacion = evaluacionService.buscarPorIdEva(id);
        return assembler.toModel(evaluacion);
    }
}
