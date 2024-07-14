package com.retoalura.forohub.domain.topicos;

import com.retoalura.forohub.domain.cursos.Curso;
import com.retoalura.forohub.domain.status.Estado;
import com.retoalura.forohub.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record ResponseTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        String status,
        String curso,
        String autor
) {
}
