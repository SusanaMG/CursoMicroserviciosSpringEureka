INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('susana3','12345',true, 'Susana', 'MG','susana@correo.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin3','12345',true, 'John', 'Doe','jhon.doe@correo.com');


INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);
