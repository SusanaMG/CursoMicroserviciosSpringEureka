INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('susana','$2a$10$hKCmSvkMtZk31AD0.HtfOOjk3bz09rWbsWZGeghV1Bb6QtgxlsJry',true, 'Susana', 'MG','susana@correo.com');
INSERT INTO usuarios (username, password, enabled, nombre, apellido, email) VALUES ('admin','$2a$10$JLqbf1NBsIkq5asDj63SzeCUlzFBx3iL24C/ryeu2HLWknqjejUey',true, 'John', 'Doe','jhon.doe@correo.com');


INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (1, 1);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 2);
INSERT INTO usuarios_roles (usuario_id, role_id) VALUES (2, 1);
 