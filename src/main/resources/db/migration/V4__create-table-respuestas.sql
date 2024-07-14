CREATE TABLE respuestas(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(255) NOT NULL,
    fechaCreacion DATETIME NOT NULL,

    autor_id BIGINT NOT NULL,
    topico_id BIGINT NOT NULL,

    FOREIGN KEY(autor_id) REFERENCES usuarios(id),
    FOREIGN KEY(topico_id) REFERENCES topicos(id)
)