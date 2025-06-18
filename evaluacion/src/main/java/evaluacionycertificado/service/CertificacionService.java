package evaluacionycertificado.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import evaluacionycertificado.model.Certificacion;
import evaluacionycertificado.repository.CertificacionRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional


public class CertificacionService {
    @Autowired
    private CertificacionRepository certificacionRepository;

   

    public List<Certificacion> listarCertficacion(){
        return certificacionRepository.findAll();
    }

    public Certificacion guardarCertificado(Certificacion certificado){
        return certificacionRepository.save(certificado);
         }
    public void eliminarCertificacion(long id){
        certificacionRepository.deleteById(id);
    }

    public Certificacion buscarCertificadoId(long id){
        return certificacionRepository.findById(id).get();
    }

    
   
 }