package evaluacionycertificado.assembler;

import evaluacionycertificado.controller.NotaControllerV2;
import evaluacionycertificado.model.NotaEvaluacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class NotaModelAssembler implements RepresentationModelAssembler<NotaEvaluacion, EntityModel<NotaEvaluacion>> {

    @Override
    public EntityModel<NotaEvaluacion> toModel(NotaEvaluacion nota) {
        return EntityModel.of(nota,
                linkTo(methodOn(NotaControllerV2.class).getById(nota.getIdNota())).withSelfRel(),
                linkTo(methodOn(NotaControllerV2.class).getAll()).withRel("notas"));
    }
}
