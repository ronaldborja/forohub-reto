package com.retoalura.forohub.domain.topicos;

import com.retoalura.forohub.domain.status.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoRequest(
        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        @NotNull
        Estado status,

        @NotNull
        Long usuarioId,

        @NotNull
        Long cursoId
) {
}
