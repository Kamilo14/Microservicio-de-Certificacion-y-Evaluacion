package evaluacionycertificado.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import evaluacionycertificado.model.Curso;
import evaluacionycertificado.model.Evaluacion;
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

@WebMvcTest(EvaluacionController.class)
public class EvaluacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EvaluacionService evaluacionService;
    

    @MockBean
    private Curso cursoClient;

    @Autowired
    private ObjectMapper objectMapper;

    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() {
        evaluacion = new Evaluacion();
        evaluacion.setId(1L);
        evaluacion.setTitulo("Prueba Final");
        evaluacion.setFecha(Date.valueOf("2025-06-10"));
        evaluacion.setPonderacion("30%");
        evaluacion.setCursoId(2L);
    }

    @Test
    public void testListarEvaluaciones() throws Exception {
        when(evaluacionService.listarEva()).thenReturn(List.of(evaluacion));

        mockMvc.perform(get("/api/v1/evaluaciones/listarevaluacion"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].titulo").value("Prueba Final"))
                .andExpect(jsonPath("$[0].ponderacion").value("30%"));
    }

    @Test
    public void testCrearEvaluacion() throws Exception {
        when(evaluacionService.guardarEvaluacion(any(Evaluacion.class))).thenReturn(evaluacion);

        mockMvc.perform(post("/api/v1/evaluaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evaluacion)))
                .andExpect(status().isCreated()) // Tu controlador devuelve CREATED
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Prueba Final"))
                .andExpect(jsonPath("$.ponderacion").value("30%"));
    }

    @Test
    public void testEliminarEvaluacion() throws Exception {
        doNothing().when(evaluacionService).eliminarEva(1L);

        mockMvc.perform(delete("/api/v1/evaluaciones/1"))
                .andExpect(status().isOk());

        verify(evaluacionService, times(1)).eliminarEva(1L);
    }

     @Test
    public void testActualizarEvaluacion() throws Exception {
        when(evaluacionService.buscarPorIdEva(1L)).thenReturn(evaluacion);
        when(evaluacionService.guardarEvaluacion(any(Evaluacion.class))).thenReturn(evaluacion);

        mockMvc.perform(put("/api/v1/evaluaciones/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(evaluacion)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Prueba Final"))
                ;
    }

      @Test
    public void testGBuscarPorIdEvaluacion() throws Exception {
        when(evaluacionService.buscarPorIdEva(1L)).thenReturn(evaluacion);
        mockMvc.perform(get("/api/v1/evaluaciones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Prueba Final"))
                ;
                
                
    }
}   
       