package evaluacionycertificado.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import evaluacionycertificado.model.NotaEvaluacion;
import evaluacionycertificado.repository.NotaEvaluacionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NotaEvaluacionServiceTest {

    @InjectMocks
    private NotaEvaluacionService notaEvaluacionService;

    @Mock
    private NotaEvaluacionRepository notaEvaluacionRepository;

    private NotaEvaluacion nota;

    @BeforeEach
    public void setUp(){
        nota = new NotaEvaluacion(1L, 6.5f, 65, "Muy bien", 1L, 1L,null);
    }

    @Test
    public void testListarNotas() {
        

        when(notaEvaluacionRepository.findAll()).thenReturn(List.of(nota));

        List<NotaEvaluacion> notaeva = notaEvaluacionService.listarNota();

        assertNotNull(notaeva);
        assertEquals(1, notaeva.size());
        assertEquals(1L, notaeva.get(0).getIdNota());
        assertEquals(6.5f, notaeva.get(0).getNota());
        assertEquals(65,notaeva.get(0).getPuntaje() );
        assertEquals("Muy bien",notaeva.get(0).getDescripcion());
        assertEquals(1L ,notaeva.get(0).getUsuarioId() );
        assertEquals(1L, notaeva.get(0).getEvaluacionId());
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        when(notaEvaluacionRepository.findById(id)).thenReturn(Optional.of(nota));

        NotaEvaluacion notaeva = notaEvaluacionService.buscarNota(id);

        assertNotNull(notaeva);
        assertEquals(id, notaeva.getIdNota());
        assertEquals(6.5f, notaeva.getNota());
        assertEquals(65,notaeva.getPuntaje() );
        assertEquals("Muy bien",notaeva.getDescripcion());
        assertEquals(1L ,notaeva.getUsuarioId() );
        assertEquals(1L, notaeva.getEvaluacionId());
    }

    @Test
    public void testGuardarNota() {
        
    
        when(notaEvaluacionRepository.save(nota)).thenReturn(nota);

        NotaEvaluacion notaeva = notaEvaluacionService.guardarNota(nota);

        assertNotNull(notaeva);
        assertEquals(1L, notaeva.getIdNota());
        assertEquals(6.5f, notaeva.getNota());
        assertEquals(65,notaeva.getPuntaje() );
        assertEquals("Muy bien",notaeva.getDescripcion());
        assertEquals(1L ,notaeva.getUsuarioId() );
        assertEquals(1L, notaeva.getEvaluacionId());
    }

    @Test
    public void testEliminarNota() {
        Long id = 1L;
        doNothing().when(notaEvaluacionRepository).deleteById(id);

        notaEvaluacionService.eliminarNota(id);

        verify(notaEvaluacionRepository, times(1)).deleteById(id);
    }
    
}
