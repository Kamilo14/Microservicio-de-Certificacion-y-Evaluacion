package evaluacionycertificado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import evaluacionycertificado.model.NotaEvaluacion;
@Repository

public interface NotaEvaluacionRepository extends JpaRepository<NotaEvaluacion, Long>{

}
