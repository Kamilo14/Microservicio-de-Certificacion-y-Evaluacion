package evaluacionycertificado.assembler;

import evaluacionycertificado.controller.EvaluacionControllerV2;
import evaluacionycertificado.model.Evaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class EvaluacionModelAssembler implements RepresentationModelAssembler<Evaluacion, EntityModel<Evaluacion>> {

    @Override
    public EntityModel<Evaluacion> toModel(Evaluacion evaluacion) {
        return EntityModel.of(evaluacion,
                linkTo(methodOn(EvaluacionControllerV2.class).getById(evaluacion.getId())).withSelfRel(),
                linkTo(methodOn(EvaluacionControllerV2.class).getAll()).withRel("evaluaciones"));
    }
}
