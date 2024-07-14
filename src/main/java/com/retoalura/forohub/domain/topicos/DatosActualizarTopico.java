package com.retoalura.forohub.domain.topicos;

import com.retoalura.forohub.domain.cursos.Curso;
import com.retoalura.forohub.domain.status.Estado;
import jakarta.validation.constraints.NotNull;

public record DatosActualizarTopico(
        @NotNull
        Long id,

        String titulo,
        String mensaje,
        Estado status,
        Curso curso
) {
}
