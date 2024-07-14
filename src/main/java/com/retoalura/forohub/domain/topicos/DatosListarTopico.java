package com.retoalura.forohub.domain.topicos;

import com.retoalura.forohub.domain.cursos.Curso;
import com.retoalura.forohub.domain.status.Estado;
import com.retoalura.forohub.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record DatosListarTopico(
        Long id,
        String titulo,
        String mensaje,
        Estado status,
        String autor,
        String curso,
        LocalDateTime fecha
) {

    public DatosListarTopico(Topico topico) {
        this(topico.getId(),topico.getTitulo(), topico.getMensaje(), topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre(), topico.getFecha());
    }
}
