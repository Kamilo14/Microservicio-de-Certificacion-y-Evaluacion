package evaluacionycertificado.assembler;

import evaluacionycertificado.controller.CertificacionControllerV2;
import evaluacionycertificado.model.Certificacion;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CertificacionModelAssembler implements RepresentationModelAssembler<Certificacion, EntityModel<Certificacion>> {

    @Override
    public EntityModel<Certificacion> toModel(Certificacion cert) {
        return EntityModel.of(cert,
                linkTo(methodOn(CertificacionControllerV2.class).getById(cert.getId())).withSelfRel(),
                linkTo(methodOn(CertificacionControllerV2.class).getAll()).withRel("certificaciones"));
    }
}
