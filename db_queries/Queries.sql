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

select * from iglesia i



/*
insert into rol(id,descripcion, estado,fecha,usuario) values (1,'ADMINISTRADOR','A',current_date,user);

insert into rol(id,descripcion, estado,fecha,usuario) values (2,'USUARIO','A',current_date,user);

insert into usuarios(id,usuario,password,correo,estado,fecha,rol_id) values (1,'juanito','123','juanito@algo.com','A',current_date,1);

insert into usuarios(id,usuario,password,correo,estado,fecha,rol_id) values (2,'pepito','456','pepito@algo.com','A',current_date,2);

select * from usuarios;

select * from rol;

select u.usuario, r.descripcion from usuarios u inner join rol r on u.rol_id = r.id ;
*/

select * from auditoria 