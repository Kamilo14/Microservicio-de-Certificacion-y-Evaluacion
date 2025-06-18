package evaluacionycertificado.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import evaluacionycertificado.model.Certificacion;
import evaluacionycertificado.repository.CertificacionRepository;
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
public class CertificacionServiceTest {

    @Mock
    private CertificacionRepository certificacionRepository;

    @InjectMocks
    private CertificacionService certificacionService;

    private Certificacion certificacion;

     @BeforeEach
    public void setUp() {
        certificacion = new Certificacion(1L, "Certificado Java", "Backend",
                Date.valueOf("2025-06-01"), Date.valueOf("2027-06-01"), "Curso intensivo",
                1L, 1L, 1L,null,null);
    }

    @Test
    public void testListarCertificaciones() {
        when(certificacionRepository.findAll()).thenReturn(List.of(certificacion));

        List<Certificacion> certificado = certificacionService.listarCertficacion();

        assertNotNull(certificado);
        assertEquals(1, certificado.size());
        assertEquals("Certificado Java", certificado.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId() {
        Long id = 1L;
        when(certificacionRepository.findById(id)).thenReturn(Optional.of(certificacion));

        Certificacion certificado = certificacionService.buscarCertificadoId(id);

        assertNotNull(certificado);
        assertEquals(id, certificado.getId());
    }

    @Test
    public void testGuardarCertificacion() {
        
        when(certificacionRepository.save(certificacion)).thenReturn(certificacion);

        Certificacion certificado = certificacionService.guardarCertificado(certificacion);

        assertNotNull(certificado);
        assertEquals("Certificado Java", certificado.getNombre());
    }

    @Test
    public void testEliminarCertificacion() {
        Long id = 1L;
        doNothing().when(certificacionRepository).deleteById(id);

        certificacionService.eliminarCertificacion(id);

        verify(certificacionRepository, times(1)).deleteById(id);
    }
}
