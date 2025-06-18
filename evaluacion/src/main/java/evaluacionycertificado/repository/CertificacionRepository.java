package evaluacionycertificado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import evaluacionycertificado.model.Certificacion;
@Repository

public interface CertificacionRepository extends JpaRepository<Certificacion,Long>{

}
