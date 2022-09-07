DROP TABLE public.auditoria CASCADE;
DROP TABLE public.cierre CASCADE;
DROP TABLE public.evento CASCADE;
DROP TABLE public.evento_detalle CASCADE;
DROP TABLE public.iglesia CASCADE;
DROP TABLE public.miembro CASCADE;
DROP TABLE public.movimiento CASCADE;
DROP TABLE public.movimiento_detalle CASCADE;
DROP TABLE public.rol CASCADE;
DROP TABLE public.tipo_evento CASCADE;
DROP TABLE public.tipo_registro_movimiento CASCADE;
DROP TABLE public.tipo_persona CASCADE;
DROP TABLE public.cierre_detalle CASCADE;
DROP TABLE public.usuarios CASCADE;
DROP TABLE public.errores CASCADE;
DROP TABLE public.usuario_rol CASCADE;

select * from rol r 

select * from usuarios u 

select * from usuario_rol ur order by 1, 2

select * from iglesia i;

update iglesia set nombre = 'XYZ' where nombre = 'Prueba7777'

select * from auditoria a; 

select * from tipo_persona te 

insert into tipo_persona(descripcion,estado,iglesia_id,ultimo_usuario) values ('TipoPersona1','ACTIVO',2,'root');
insert into tipo_persona(descripcion,estado,iglesia_id,ultimo_usuario) values ('TipoPersona2','ACTIVO',2,'root');
insert into tipo_persona(descripcion,estado,iglesia_id,ultimo_usuario) values ('TipoPersona3','ACTIVO',4,'root');
insert into tipo_persona(descripcion,estado,iglesia_id,ultimo_usuario) values ('TipoPersona4','ACTIVO',2,'root');


select * from errores e; 

select * from tipo_evento te 

select * from evento e 

select * from rol;

update usuarios set iglesia_id = 1 where iglesia_id is null;

update tipo_evento set iglesia_id = 3 where id = 2;


select * from usuarios;
select * from usuario_rol ;

insert into auditoria (accion, fecha, iglesia_id, llave_registro, registro, tabla, usuario)
values('PRUEBA 2',111111111111,2,2,'PRUEBA 2','NUNGUNA 2','YO 2')


/*
insert into rol(id,descripcion, estado,fecha,usuario) values (1,'ADMINISTRADOR','A',current_date,user);

insert into rol(id,descripcion, estado,fecha,usuario) values (2,'USUARIO','A',current_date,user);

insert into usuarios(id,usuario,password,correo,estado,fecha,rol_id) values (1,'juanito','123','juanito@algo.com','A',current_date,1);

insert into usuarios(id,usuario,password,correo,estado,fecha,rol_id) values (2,'pepito','456','pepito@algo.com','A',current_date,2);

select * from usuarios;

select * from rol;

select u.usuario, r.descripcion from usuarios u inner join rol r on u.rol_id = r.id ;

select * from usuario_rol
*/


INSERT INTO public.rol (id, nombre) VALUES(1, 'ROLE_ADMIN');
INSERT INTO public.rol (id, nombre) VALUES(2, 'ROLE_USER');
INSERT INTO public.rol (id, nombre) VALUES(3, 'ROLE_CONSULTA');

INSERT INTO public.usuarios (id, correo, estado, fecha, "password", usuario, iglesia_id)
VALUES(1, 'escm.1990@gmail.com', 'ACTIVO', 1636560482937, '$2a$10$qX.mevR2hlsXHOSTU9qTteyqvuDE5.uo0WnzcbbUQh/Lc0SadfcTu', 'root', 0);
INSERT INTO public.usuarios (id, correo, estado, fecha, "password", usuario, iglesia_id)
VALUES(2, 'escm.backup@gmail.com', 'ACTIVO', 1636560537110, '$2a$10$qX.mevR2hlsXHOSTU9qTteyqvuDE5.uo0WnzcbbUQh/Lc0SadfcTu', 'user', 0);
INSERT INTO public.usuarios (id, correo, estado, fecha, "password", usuario, iglesia_id)
VALUES(3, 'noreply.buenasnuevas@gmail.com', 'ACTIVO', 1636560844422, '$2a$10$8gsWuX6pX5bLEwenJdn1YeYUGb.G1DKc5ihp07XnPSFZe9ShX5vum', 'visor', 0);
INSERT INTO public.usuarios (id, correo, estado, fecha, "password", usuario, iglesia_id)
VALUES(4, 'juanito@algo.com', 'ACTIVO', 1637095012359, '$2a$10$LuRhSL.4kEs7cbohy4UeCOtG.rqWGahfUnwWUOIYRo.RQmY/GeV0.', 'juanito', 0);

INSERT INTO public.usuario_rol (usuario_id, rol_id) VALUES(1, 2);
INSERT INTO public.usuario_rol (usuario_id, rol_id) VALUES(1, 1);
INSERT INTO public.usuario_rol (usuario_id, rol_id) VALUES(2, 2);
INSERT INTO public.usuario_rol (usuario_id, rol_id) VALUES(3, 2);
INSERT INTO public.usuario_rol (usuario_id, rol_id) VALUES(3, 3);
INSERT INTO public.usuario_rol (usuario_id, rol_id) VALUES(4, 2);
INSERT INTO public.usuario_rol (usuario_id, rol_id) VALUES(4, 1);

INSERT INTO public.iglesia
(correo, direccion, estado, fecha_fundacion, logo, nombre, pais, telefono, ultimo_usuario)
VALUES('', '', 'ACTIVO', 0, '', 'ADMINISRTACION', '', '', '');

update iglesia set id = 0;

update usuarios set iglesia_id = 0;

select * from iglesia i 

select * from errores e 

select * from auditoria a 

select * from miembro m 

