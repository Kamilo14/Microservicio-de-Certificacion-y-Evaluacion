package evaluacionycertificado.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import evaluacionycertificado.model.Certificacion;
import evaluacionycertificado.service.CertificacionService;
import evaluacionycertificado.service.EvaluacionService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.List;

@WebMvcTest(CertificacionController.class)
public class CertificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CertificacionService certificacionService;

    @MockBean
    private EvaluacionService evaluacionservices;

    @Autowired
    private ObjectMapper objectMapper;

    private Certificacion certificacion;

    @BeforeEach
    void setUp() {
        certificacion = new Certificacion();
        certificacion.setId(1L); 
        certificacion.setNombre("Certificado Java"); 
        certificacion.setEspecializacion("Backend");
        certificacion.setFechaEmision(Date.valueOf("2025-06-01")); 
        certificacion.setFechaExpiracion(Date.valueOf("2027-06-01"));
        certificacion.setDescripcion("Curso intensivo");
        certificacion.setCursoId(1L);
        certificacion.setUsuarioId(1L);
        certificacion.setEvaluacionId(1L);
    }

    @Test
    public void testListarCertificaciones() throws Exception {
        when(certificacionService.listarCertficacion()).thenReturn(List.of(certificacion));

        mockMvc.perform(get("/api/v1/certificaciones/listarcertificado"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Certificado Java"))
                .andExpect(jsonPath("$[0].especializacion").value("Backend"))
                .andExpect(jsonPath("$[0].fechaEmision").value("2025-06-01"))
                .andExpect(jsonPath("$[0].fechaExpiracion").value("2027-06-01"))
                .andExpect(jsonPath("$[0].descripcion").value("Curso intensivo"))
                .andExpect(jsonPath("$[0].cursoId").value(1))
                .andExpect(jsonPath("$[0].usuarioId").value(1))
                .andExpect(jsonPath("$[0].evaluacionId").value(1));
    }

    @Test
    public void testGBuscarPorIdCertificacionById() throws Exception {
        when(certificacionService.buscarCertificadoId(1L)).thenReturn(certificacion);
        

        mockMvc.perform(get("/api/v1/certificaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Certificado Java"))
                .andExpect(jsonPath("$.especializacion").value("Backend")) 
                .andExpect(jsonPath("$.fechaEmision").value("2025-06-01"))
                .andExpect(jsonPath("$.fechaExpiracion").value("2027-06-01"))
                .andExpect(jsonPath("$.descripcion").value("Curso intensivo"))
                .andExpect(jsonPath("$.cursoId").value(1))
                .andExpect(jsonPath("$.usuarioId").value(1))
                .andExpect(jsonPath("$.evaluacionId").value(1));
    }

    @Test
    public void testCrearCertificacion() throws Exception {
        when(certificacionService.guardarCertificado(any(Certificacion.class))).thenReturn(certificacion);

        mockMvc.perform(post("/api/v1/certificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificacion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Certificado Java"))
                .andExpect(jsonPath("$.especializacion").value("Backend"));
    }

    @Test
    public void testActualizarCertificacion() throws Exception {
        when(certificacionService.buscarCertificadoId(1L)).thenReturn(certificacion);
        when(certificacionService.guardarCertificado(any(Certificacion.class))).thenReturn(certificacion);

        mockMvc.perform(put("/api/v1/certificaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(certificacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Certificado Java"))
                .andExpect(jsonPath("$.especializacion").value("Backend"));
    }

    @Test
    public void testEliminarCertificacion() throws Exception {
        doNothing().when(certificacionService).eliminarCertificacion(1L);

        mockMvc.perform(delete("/api/v1/certificaciones/1"))
                .andExpect(status().isOk());

        verify(certificacionService, times(1)).eliminarCertificacion(1L);
    }
}