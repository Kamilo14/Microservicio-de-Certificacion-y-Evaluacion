package evaluacionycertificado.service;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import evaluacionycertificado.model.Evaluacion;
import evaluacionycertificado.repository.EvaluacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EvaluacionServiceTest {

    @InjectMocks
    private EvaluacionService evaluacionService;

    @Mock 
    private EvaluacionRepository evaluacionRepository;

    private Evaluacion evaluacion;

    @BeforeEach
    public void setUp(){
        
        evaluacion =new Evaluacion(1L, "Prueba Final", Date.valueOf("2025-06-10"), "30%", 1L,null);

    }

    @Test
    public void testListarEvaluaciones() {
        when(evaluacionRepository.findAll()).thenReturn(List.of(evaluacion));

        List<Evaluacion> eva = evaluacionService.listarEva();

        assertNotNull(eva);
        assertEquals(1, eva.size());
        assertEquals("Prueba Final", eva.get(0).getTitulo());
        assertEquals(Date.valueOf("2025-06-10"),eva.get(0).getFecha());
        assertEquals("30%", eva.get(0).getPonderacion());
        assertEquals(1L, eva.get(0).getCursoId());
    }

    @Test
    public void testBuscarPorId() {
    
        when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));

        Evaluacion eva = evaluacionService.buscarPorIdEva(1L);

        assertNotNull(eva);
        assertEquals(1L, eva.getId());
        assertEquals("Prueba Final", eva.getTitulo());
        assertEquals(Date.valueOf("2025-06-10"),eva.getFecha());
        assertEquals("30%", eva.getPonderacion());
        assertEquals(1L, eva.getCursoId());
        }
        

    @Test
    public void testGuardarEvaluacion() {
       

        when(evaluacionRepository.save(evaluacion)).thenReturn(evaluacion);

        Evaluacion eva = evaluacionService.guardarEvaluacion(evaluacion);

        assertNotNull(eva);
        assertEquals(1L, eva.getId());
        assertEquals("Prueba Final", eva.getTitulo());
        assertEquals(Date.valueOf("2025-06-10"),eva.getFecha());
        assertEquals("30%", eva.getPonderacion());
        assertEquals(1L, eva.getCursoId());
    }

    @Test
    public void testEliminarEvaluacion() {
        Long id = 1L;
        doNothing().when(evaluacionRepository).deleteById(id);

        evaluacionService.eliminarEva(id);

        verify(evaluacionRepository, times(1)).deleteById(id);
    }
}
