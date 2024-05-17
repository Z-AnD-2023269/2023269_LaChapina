drop database if exists DBLaChapina;
create database DBLaChapina;

use DBLaChapina;

create table Clientes(
	codigoCliente int not null,
    NITcliente varchar(10) not null,
	nombreCliente varchar(60) not null,
    apellidoCliente varchar(60) not null,
    direccionCliente varchar(150),
    telefonoCliente varchar(10),
    correoCliente varchar(45),
    primary key PK_codigoCliente(codigoCliente)
);    

create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10) not null,
    nombreProveedor varchar(60),
    apellidoProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor (codigoProveedor)
);
 
create table TelefonoProveedor(
	codigoTelefonoProveedor int not null,
    numeroPrincipal varchar(8),
    numeroSecundario varchar(8),
    observaciones varchar(45),
    codigoProveedor int,
    primary key PK_codigoTelefonoProveedor(codigoTelefonoProveedor),
    constraint FK_codigoProveedor foreign key (codigoProveedor)
     references Proveedores(codigoProveedor)
);

create table EmailProveedor(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int,
    primary key PK_codigoEmailProveedor (codigoEmailProveedor),
    constraint FK_codigoProveedores foreign key (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table TipoProducto(
	codigoTipoProducto int not null,
    descripcion varchar(45),
    primary key PK_codigoTipoProducto (codigoTipoProducto)
);
 
create table Productos(
	codigoProducto varchar(15) not null,
    descripcionProducto varchar(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    imagenProducto varchar(45),
    existencia int,
	codigoTipoProducto int,
    codigoProveedor int,
    primary key PK_codigoProducto(codigoProducto),
    constraint FK_codigoTipoProducto_Productos foreign key (codigoTipoProducto)
		references TipoProducto(codigoTipoProducto),
	constraint FK_codigoProveedor_Productos foreign key (codigoProveedor)
		references Proveedores(codigoProveedor)
);

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numeroDocumento)
);

create table DetalleCompra(
	codigoDetalleCompra int not null,
    costoUnitario decimal(10,2),
    cantidad int,
    codigoProducto varchar(15),
    numeroDocumento int,
    primary key PK_codigoDetalleCompra(codigoDetalleCompra),
    constraint FK_codigoProducto_DetalleCompra foreign key (codigoProducto)
		references Productos(codigoProducto),
	constraint FK_numeroDocumento_DetalleCompra foreign key (numeroDocumento)
		references Compras(numeroDocumento)
);

create table CargoEmpleado(
	codigoCargoEmpleado int not null,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
);

create table Empleados(
	codigoEmpleado int not null,
    nombresEmpleado varchar(50),
    apellidosEmpleado varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int not null,
    primary key PK_codigoEmpleado(codigoEmpleado),
    constraint FK_codigoCargoEmpleado_Empleados foreign key (codigoCargoEmpleado)
		references CargoEmpleado(codigoCargoEmpleado)
);

create table Factura(
	numeroFactura int not null,
    estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    codigoCliente int not null,
    codigoEmpleado int not null,
    primary key PK_numeroFactura(numeroFactura),
    constraint FK_codigoCliente_Factura foreign key (codigoCliente)
		references Clientes(codigoCliente),
	constraint FK_codigoEmpleado_Factura foreign key (codigoEmpleado)
		references Empleados(codigoEmpleado)
);

create table DetalleFactura(
	codigoDetalleFactura int not null,
    precioUnitario decimal(10,2),
    cantidad int,
	numeroFactura int not null,
    codigoProducto varchar(15),
    primary key PK_codigoDetalleFactura(codigoDetalleFactura),
    constraint FK_numeroFactura_DetalleFactura foreign key (numeroFactura)
		references Factura(numeroFactura),
	constraint FK_codigoProducto_Productos foreign key(codigoProducto)
		references Productos(codigoProducto)
);
-- ------------------ Procedimientos Almacenados---------------------
-- ---Clientes -------
-- ---Agregar Clientes -------

DELIMITER $$ 
CREATE PROCEDURE sp_AgregarCliente(in codigoCliente int,in NITcliente varchar(10) ,in nombreCliente varchar(60) ,in apellidoCliente varchar(60) ,
	in direccionCliente varchar(150),in telefonoCliente varchar(10),in correoCliente varchar(45))
	BEGIN 	
		INSERT INTO Clientes (codigoCliente,NITcliente,nombreCliente,apellidoCliente,direccionCliente,telefonoCliente, correoCliente) values 
			(codigoCliente,NITcliente,nombreCliente,apellidoCliente,direccionCliente,telefonoCliente, correoCliente);
	END$$
DELIMITER ;

call sp_AgregarCliente(1, '2023269', 'Anderson', 'Lopez', 'carranza', '35978171', 'alopez-2023269@kinal.edu.gt');
call sp_AgregarCliente(11, '2023123', 'Santiago', 'Gómez', 'Calle Primavera, 123, Ciudad Esperanza, Provincia del Sol', '87654321', 'correo1-2023123@kinal.edu.gt');
call sp_AgregarCliente(2, '2023987', 'Valentina', 'Martínez', 'Avenida Libertad, 456, Pueblo Nuevo, Estado del Río', '23456789', 'correo2-2023987@kinal.edu.gt');
call sp_AgregarCliente(3, '2023556', 'Mateo', 'García', 'Carrera 7, 890, Villa Paz, Departamento del Bosque', '34567890', 'correo3-2023556@kinal.edu.gt');
call sp_AgregarCliente(4, '2023742', 'Sebastián', 'Rodríguez', 'Calle Aurora, 210, Colonia Aurora, Región de la Luna', '45678901', 'correo4-2023742@kinal.edu.gt');
call sp_AgregarCliente(5, '2023773', 'Alejandro', 'Papen', 'Avenida del Sol, 333, Urbanización Felicidad, País de la Alegría', '10987654', 'correo5-2023773@kinal.edu.gt');


-- ---Listar Clientes -------

DELIMITER $$ 
CREATE PROCEDURE sp_ListarClientes()
BEGIN 
	SELECT
		Clientes.codigoCliente,
		Clientes.nombreCliente,
		Clientes.apellidoCliente,
		Clientes.telefonoCliente,
		Clientes.direccionCliente,
		Clientes.NITcliente,
        Clientes.correoCliente
			FROM Clientes;
END$$
DELIMITER ;

-- ---Eliminar Clientes -------

DELIMITER $$ 
CREATE PROCEDURE sp_EliminarCliente(IN cliId INT)
BEGIN
	DELETE
	FROM Clientes 
		WHERE codigoCliente =  cliId;
END$$
DELIMITER ;

	
-- call sp_EliminarCliente(87);
-- ---Buscar Clientes -------

DELIMITER $$ 
CREATE PROCEDURE sp_BuscarClientes(IN cliId INT)
	BEGIN
		SELECT 	
			Clientes.codigoCliente,
			Clientes.nombreCliente,
			Clientes.apellidoCliente,
			Clientes.telefonoCliente,
			Clientes.direccionCliente,
			Clientes.NITcliente,
            Clientre.correoCliente
				FROM Clientes
				WHERE clienteId = cliId;
	END$$
DELIMITER ;

-- call sp_BuscarClientes(2);
-- ---Editar Clientes -------

DELIMITER $$ 
CREATE PROCEDURE sp_EditarCliente	(in codigoClientes int,in NITclientes varchar(10) ,in nombreClientes varchar(60) ,in apellidoClientes varchar(60) ,
	in direccionClientes varchar(150),in telefonoClientes varchar(10),in correoClientes varchar(45))
	BEGIN
		UPDATE Clientes C
			SET
				C.nombreCliente = nombreClientes,
				C.apellidoCliente = apellidoClientes,
				C.telefonoCliente = telefonoClientes,
				C.direccionCliente = direccionClientes,
                C.correoCliente = correoClientes,
				C.NITcliente = NITclientes
				WHERE codigoCliente = codigoClientes;
	END$$
DELIMITER ;

call sp_EditarCliente(1, '2023269', 'Anderson', 'Lopez', 'carranza lote 73', '35978171', 'alopez-2023269@kinal.edu.gt');

call sp_ListarClientes();

-- --------------------------Proveedores------------------------------------------------
-- -----------------------Agregar Proveedores ----------------------------------------
Delimiter $$
	create procedure sp_AgregarProveedores(in codigoProveedor int, in NITproveedor varchar(10), in nombreProveedor varchar(60), 
    in apellidoProveedor varchar(60), in direccionProveedor varchar(150), in razonSocial varchar(60),
    in contactoPrincipal varchar(100), in paginaWeb varchar(100))
		Begin
			Insert into Proveedores(codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor,
            razonSocial, contactoPrincipal, paginaWeb) values
            (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor,
            razonSocial, contactoPrincipal, paginaWeb);
		End $$
Delimiter ;
 
call sp_AgregarProveedores(1, '123456789', 'Dayrin', 'Alvarez', 'Guastatoya', 'Kinal', '22174589', 'Kinal.com');
call sp_AgregarProveedores(2, '567895462', 'Dulce', 'Rios', 'Cayala', 'Ciclo', '12356489', 'Ciclo.com');
call sp_AgregarProveedores(3, '202324456', 'Jose', 'Ochoa', 'Agua Tibia', 'Tequila', '10203040', 'Tequila.com');
call sp_AgregarProveedores(4, '202178941', 'Michael', 'Gonzales', 'Xelaju', 'Lala', '10504578', 'Lala.com');
 
 
-- -------------------------------Listar Proveedores---------------------------------------------------
Delimiter $$
	create procedure sp_ListarProveedores()
		Begin
			select
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P;
		End $$
Delimiter ;
 
call sp_ListarProveedores();
-- -----------------------------------Buscar Proveedores----------------------------------------------------
Delimiter $$
	Create procedure sp_BuscarProveedores(in codigoPro int)
		Begin
			select
				P.codigoProveedor,
				P.NITproveedor,
				P.nombreProveedor,
				P.apellidoProveedor,
				P.direccionProveedor,
				P.razonSocial,
				P.contactoPrincipal,
				P.paginaWeb
				from Proveedores P
                where codigoProveedor = codigoPro;
            End $$
Delimiter ;
 
-- call sp_BuscarProveedores(3);
 
-- --------------------------- Eliminar Proveedores--------------------------------------------------
Delimiter $$
	create procedure sp_EliminarProveedores(in codigoPro int)
		Begin
			Delete from Proveedores
				where codigoProveedor = codigoPro;
		End $$
Delimiter ;
 
/*call sp_EliminarProveedores(1);
call sp_ListarProveedores(); */

 
-- ----------------------------- Editar Proveedores ----------------------------------------------------
Delimiter $$
	create procedure sp_EditarProveedores(in codigoPro int, in NITpro varchar(10), in nombrePro varchar(60), 
    in apellidoPro varchar(60), in direccionPro varchar(150), in razonS varchar(60),
    in contactoPri varchar(100), in Web varchar(100))
		Begin
			Update Proveedores P
            set
            P.NITproveedor = NITpro,
            P.nombreProveedor = nombrePro,
            P.apellidoProveedor = apellidoPro,
            P.direccionProveedor = direccionPro,
            P.razonSocial = razonS,
            P.contactoPrincipal = contactoPri,
            P.paginaWeb = Web
            where codigoProveedor = codigoPro;
		End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarTelefonoProveedor(in codigoTelefonoProveedor int,in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
    Begin
		Insert Into TelefonoProveedor(codigoTeleonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor)
        values (codigoTeleonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarTelefonoProveedor()
    Begin
		Select
			TP.codigoTelefonoProveedor,
            TP.numeroPrincipal,
            TP.numeroSecundario,
            TP.observaciones,
            TP.codigoProveedor
		from TelefonoProveedor TP;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarTelefonoProveedor(in _codigoTelefonoProveedor int,in _numeroPrincipal varchar(8), in _numeroSecundario varchar(8), in _observaciones varchar(45), in _codigoProveedor int)
    Begin
		update TelefonoProveedor TP 
			set 
			 TP.numeroPrincipal = _numeroPrincipal,
             TP.numeroSecundario = _numeroSecundario,
			 TP.observaciones = _observaciones,
             TP.codigoProveedor = _codigoProveedor
		    where TP.codigoTelefonoProveedor = _codigoTelefonoProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarTelefonoProveedor(in _codigoTelefonoProveedor int)
    Begin
		Delete From TelefonoProveedor
        where codigoTelefonoProveedor = _codigoTelefonoProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)	
    Begin
		Insert Into EmailProveedor(codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor)
        values (codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor);
    End ;
Delimiter ;

Delimiter $$
	create procedure sp_ListarEmailProveedor()
    Begin
		Select
			EP.codigoEmailProveedor,
            EP.emailProveedor,
            EP.descripcion,
            EP.codigoProveedor
		from EmailProveedor EP;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarEmailProveedor(in _codigoEmailProveedor int, in _emailProveedor varchar(50), in _descripcion varchar(100), in _codigoProveedor int)
    Begin
		update EmailProveedor EP
		set
			EP.emailProveedor = _emailProveedor,
            EP.descripcion = _descripcion,
            EP.codigoProveedor = _codigoProveedor
		where EP.codigoEmailProveedor = _codigoEmailProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarEmailProveedor(in _codigoEmailProveedor int)
    Begin
		Delete from EmailProveedor 
        where codigoEmailProveedor = _codigoEmailProveedor;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_AgregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
    Begin
		Insert Into TipoProducto(codigoTipoProducto,descripcion)
        values(codigoTipoProducto,descripcion);
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_ListarTipoProducto()
    Begin
		select
			TP.codigoTipoProducto,
            Tp.descripcion
		from TipoProducto TP;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EditarTipoProducto(in _codigoTipoProducto int, in _descripcion varchar(45))
    Begin
		update TipoProducto TP
        set
			TP.descripcion = _descripcion
		where TP.codigoTipoProducto = _codigoTipoProdcuto;
    End $$
Delimiter ;

Delimiter $$
	create procedure sp_EliminarTipoProducto(in _codigoTipoProdcuto int)
    begin
		Delete From TipoProducto
        where codigoTipoProducto = _codigoTipoProdcuto;
    end $$
Delimiter ;

DELIMITER $$

CREATE PROCEDURE sp_agregarCargoEmpleado(
    IN p_codigoCargoEmpleado INT,
    IN p_nombreCargo VARCHAR(45),
    IN p_descripcionCargo VARCHAR(45)
)
BEGIN
    INSERT INTO CargoEmpleado (codigoCargoEmpleado, nombreCargo, descripcionCargo) 
    VALUES (p_codigoCargoEmpleado, p_nombreCargo, p_descripcionCargo);
END $$

DELIMITER ;

CALL sp_agregarCargoEmpleado(2, 'Subgerente', 'Asistente del gerente');

DELIMITER $$

CREATE PROCEDURE sp_listarCargoEmpleados()
BEGIN
    SELECT * FROM CargoEmpleado;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_editarCargoEmpleado(
    IN p_codigoCargoEmpleado INT,
    IN p_nombreCargo VARCHAR(45),
    IN p_descripcionCargo VARCHAR(45)
)
BEGIN
    UPDATE CargoEmpleado
    SET nombreCargo = p_nombreCargo, descripcionCargo = p_descripcionCargo
    WHERE codigoCargoEmpleado = p_codigoCargoEmpleado;
END $$

DELIMITER ;

CALL sp_editarCargoEmpleado(2, 'Subdirector', 'Asistente del director');

DELIMITER $$

CREATE PROCEDURE sp_eliminarCargoEmpleado(
    IN p_codigoCargoEmpleado INT
)
BEGIN
    DELETE FROM CargoEmpleado
    WHERE codigoCargoEmpleado = p_codigoCargoEmpleado;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_agregarCompra(
    IN p_numeroDocumento INT,
    IN p_fechaDocumento DATE,
    IN p_descripcion VARCHAR(60),
    IN p_totalDocumento DECIMAL(10,2)
)
BEGIN
    INSERT INTO Compras (numeroDocumento, fechaDocumento, descripcion, totalDocumento) 
    VALUES (p_numeroDocumento, p_fechaDocumento, p_descripcion, p_totalDocumento);
END $$

DELIMITER ;

CALL sp_agregarCompra(1, '2024-05-17', 'Compra de material de oficina', 150.00);

DELIMITER $$

CREATE PROCEDURE sp_listarCompras()
BEGIN
    SELECT * FROM Compras;
END $$

DELIMITER ;

DELIMITER $$

CREATE PROCEDURE sp_editarCompra(
    IN p_numeroDocumento INT,
    IN p_fechaDocumento DATE,
    IN p_descripcion VARCHAR(60),
    IN p_totalDocumento DECIMAL(10,2)
)
BEGIN
    UPDATE Compras
    SET fechaDocumento = p_fechaDocumento, descripcion = p_descripcion, totalDocumento = p_totalDocumento
    WHERE numeroDocumento = p_numeroDocumento;
END $$

DELIMITER ;

CALL sp_editarCompra(1, '2024-05-17', 'Compra de material de oficina y papelería', 175.00);

DELIMITER $$

CREATE PROCEDURE sp_eliminarCompra(
    IN p_numeroDocumento INT
)
BEGIN
    DELETE FROM Compras
    WHERE numeroDocumento = p_numeroDocumento;
END $$

DELIMITER ;

set global time_zone = '-6:00';

