CREATE TABLE topicos(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    mensaje VARCHAR(255) NOT NULL,
    fecha DATETIME NOT NULL,
    status VARCHAR(50) NOT NULL,
    autor_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    FOREIGN KEY(autor_id) REFERENCES usuarios(id),
    FOREIGN KEY(curso_id) REFERENCES cursos(id)
)