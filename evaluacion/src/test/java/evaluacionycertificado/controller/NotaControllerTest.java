package evaluacionycertificado.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import evaluacionycertificado.model.Evaluacion;
import evaluacionycertificado.model.NotaEvaluacion;
import evaluacionycertificado.service.EvaluacionService;
import evaluacionycertificado.service.NotaEvaluacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotaController.class)
public class NotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotaEvaluacionService notaEvaluacionService;

    @MockBean
    private EvaluacionService evaluacionService;

    @Autowired
    private ObjectMapper objectMapper;

    private NotaEvaluacion nota;
    private Evaluacion evaluacion;

    @BeforeEach
    void setUp() {
        nota = new NotaEvaluacion();
        nota.setIdNota(1L);
        nota.setNota(6.0f);
        nota.setPuntaje(60);
        nota.setDescripcion("Buen trabajo");
        nota.setUsuarioId(2L);
        nota.setEvaluacionId(1L);

         evaluacion = new Evaluacion();
        evaluacion.setId(1L); 
    }

    @Test
    void testListarNotas() throws Exception {
        when(notaEvaluacionService.listarNota()).thenReturn(List.of(nota));

        mockMvc.perform(get("/api/v1/notas/listarnota"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idNota").value(1L))
                .andExpect(jsonPath("$[0].nota").value(6.0f))
                .andExpect(jsonPath("$[0].puntaje").value(60))
                .andExpect(jsonPath("$[0].descripcion").value("Buen trabajo"));
    }

    @Test
    void testCrearNota() throws Exception {
        when(notaEvaluacionService.guardarNota(any(NotaEvaluacion.class))).thenReturn(nota);

        mockMvc.perform(post("/api/v1/notas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nota)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idNota").value(1L))
                .andExpect(jsonPath("$.nota").value(6.0f))
                .andExpect(jsonPath("$.descripcion").value("Buen trabajo"));
    }

    @Test
    void testEliminarNota() throws Exception {
        doNothing().when(notaEvaluacionService).eliminarNota(1L);

        mockMvc.perform(delete("/api/v1/notas/1"))
                .andExpect(status().isOk());

        verify(notaEvaluacionService, times(1)).eliminarNota(1L);
    }

     @Test
    public void testActualizarNota() throws Exception {
        when(notaEvaluacionService.buscarNota(1L)).thenReturn(nota);
        when(evaluacionService.buscarPorIdEva(nota.getEvaluacionId())).thenReturn(evaluacion);
        when(notaEvaluacionService.guardarNota(any(NotaEvaluacion.class))).thenReturn(nota);

        mockMvc.perform(put("/api/v1/notas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nota)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idNota").value(1L))
                .andExpect(jsonPath("$.nota").value(6.0f))
                .andExpect(jsonPath("$.descripcion").value("Buen trabajo"));
                
    }
    
     @Test
    public void testGBuscarPorIdNota() throws Exception {
        when(notaEvaluacionService.buscarNota(1L)).thenReturn(nota);
        mockMvc.perform(get("/api/v1/notas/1"))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$.idNota").value(1L))
                .andExpect(jsonPath("$.nota").value(6.0f))
                .andExpect(jsonPath("$.descripcion").value("Buen trabajo"));
                
                
    }
}
