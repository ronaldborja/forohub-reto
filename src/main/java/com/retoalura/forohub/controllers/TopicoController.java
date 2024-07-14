package com.retoalura.forohub.controllers;


import com.retoalura.forohub.domain.cursos.Curso;
import com.retoalura.forohub.domain.cursos.CursoRepository;
import com.retoalura.forohub.domain.topicos.*;
import com.retoalura.forohub.domain.usuarios.Usuario;
import com.retoalura.forohub.domain.usuarios.UsuarioRepository;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @PostMapping
    public ResponseEntity crearTopico(@RequestBody @Valid TopicoRequest topicoRequest,
                                      UriComponentsBuilder uriComponentsBuilder) {

        //Necesitamos recuperar el id:
        Optional<Usuario> autorOpt = usuarioRepository.findById(topicoRequest.usuarioId());
        Optional<Curso> cursoOpt = cursoRepository.findById(topicoRequest.cursoId());

        if (!autorOpt.isPresent() || !cursoOpt.isPresent()) {
            return  ResponseEntity.badRequest().build();
        }

        Usuario autor = autorOpt.get();
        Curso curso = cursoOpt.get();

        Topico topico = topicoRepository.save(new Topico(topicoRequest, autor, curso));
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(topicoRequest);
    }

    @GetMapping
    public ResponseEntity<Page<DatosListarTopico>> listarTopicos(@PageableDefault(size=10, sort = "fecha", direction = Sort.Direction.ASC) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findAll(paginacion).map(DatosListarTopico::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTopico> buscarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);

        var datosRespuestaTopico = new ResponseTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus().toString(),
                topico.getCurso().getNombre(),
                topico.getAutor().getNombre()
        );

        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarTopico(@RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {

        //Obtenemos el objeto de tipo topico
        Topico topico = topicoRepository.getReferenceById(datosActualizarTopico.id()); //Objeto de tipo topico

        //Obtenemos el curso
        Curso curso = topicoRepository.getReferenceById(datosActualizarTopico.curso().getId()).getCurso();

        // -> Debemos actualizar los datos:
        topico.actualizarDatos(datosActualizarTopico);
        topico.setCurso(curso);

        var datosRespuestaTopico = new ResponseTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFecha(),
                topico.getStatus().toString(),
                topico.getCurso().getNombre(),
                topico.getAutor().getNombre()
        );

        return ResponseEntity.ok(datosRespuestaTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);

        if (topico == null) {
            throw new ValidationException("El topico no se encontró en la base de datos");
        }

        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
