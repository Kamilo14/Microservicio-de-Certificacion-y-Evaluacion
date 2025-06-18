package evaluacionycertificado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import evaluacionycertificado.model.Evaluacion;
import evaluacionycertificado.repository.EvaluacionRepository;
import jakarta.transaction.Transactional;
@Service
@Transactional

public class EvaluacionService {
    @Autowired
    private EvaluacionRepository evaluacionrepository;



    public List<Evaluacion>listarEva(){
        return evaluacionrepository.findAll();

    }

    public Evaluacion guardarEvaluacion(Evaluacion evaluacion){
        return evaluacionrepository.save(evaluacion);
    }

    public Evaluacion buscarPorIdEva(Long id){
        return evaluacionrepository.findById(id).get();
    }

    public void  eliminarEva(Long id){
        evaluacionrepository.deleteById(id);
    }

   



}
