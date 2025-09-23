-- ===== Asignaturas (IDs autoincrement) =====
insert into asignatura (
   asinombre,
   asicodigo
) values ( 'Arquitectura de Software',
           101 );
insert into asignatura (
   asinombre,
   asicodigo
) values ( 'Bases de Datos II',
           102 );

-- ===== Espacios físicos (IDs autoincrement) =====
insert into espacio_fisico (
   espnombre,
   espcapacidad
) values ( 'Aula Tulcán 101',
           40 );
insert into espacio_fisico (
   espnombre,
   espcapacidad
) values ( 'Laboratorio Redes 2',
           28 );