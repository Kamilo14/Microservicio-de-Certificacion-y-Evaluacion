package evaluacionycertificado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import evaluacionycertificado.model.Evaluacion;
@Repository

public interface EvaluacionRepository extends JpaRepository <Evaluacion,Long>{

}
