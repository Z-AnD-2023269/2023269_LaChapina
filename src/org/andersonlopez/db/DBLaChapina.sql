drop database if exists DBLaChapina;
create database DBLaChapina;

use DBLaChapina;

create table Clientes(
	codigoCliente int not null,
    NITcliente varchar(10) not null,
	nombreCliente varchar(60) not null,
    apellidoCliente varchar(60) not null,
    direccionCliente varchar(150),
    telefonoCliente varchar(20),
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

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numeroDocumento)
);
 
 create table TipoProducto(
	codigoTipoProducto int not null,
    descripcion varchar(45),
    primary key PK_codigoTipoProducto (codigoTipoProducto)
);
 
 create table CargoEmpleado(
	codigoCargoEmpleado int not null,
    nombreCargo varchar(45),
    descripcionCargo varchar(45),
    primary key PK_codigoCargoEmpleado(codigoCargoEmpleado)
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
		Clientes.NITcliente,
		Clientes.nombreCliente,
		Clientes.apellidoCliente,
		Clientes.direccionCliente,
		Clientes.telefonoCliente,
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
	in direccionClientes varchar(150),in telefonoClientes varchar(20),in correoClientes varchar(45))
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

-- -----------------------Proveedores  Procedimiento Almacenados ------------------------
-- CRUD PROVEEDORES
-- ---------------------------Agregar proveedores-----------------------------
delimiter $$

create procedure sp_agregarProveedor (
    in _codigoproveedor int,
    in _nitproveedor varchar(13),
    in _nombresproveedor varchar(60),
    in _apellidosproveedor varchar(60),
    in _direccionproveedor varchar(150),
    in _razonsocial varchar(60),
    in _contactoprincipal varchar(100),
    in _paginaweb varchar(50)
)
begin
    insert into Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    values (_codigoproveedor, _nitproveedor, _nombresproveedor, _apellidosproveedor, _direccionproveedor, _razonsocial, _contactoprincipal, _paginaweb);
end $$

delimiter ;

call sp_agregarProveedor(1, '061400011', 'Gasolinera Express', 'S.A.', 'Av. Principal 123, Zona 1', 'Gasolinera Express S.A.', 'Juan Pérez', 'www.gasolineraexpress.com');
call sp_agregarProveedor(2, '061400024', 'Distribuidora de Alimentos', 'Dialiment S.A.', 'Av. Comercial 456, Zona 2', 'Dialiment S.A.', 'María Gómez', 'www.dialiment.com');
call sp_agregarProveedor(3, '061400037', 'Bebidas Refrescantes', 'Refrescos del Sur S.A.', 'Calle Refrescante 789, Zona 3', 'Refrescos del Sur S.A.', 'Pedro Martínez', 'www.refrescosdelsur.com');
call sp_agregarProveedor(4, '061400040', 'Lubricantes y Aceites', 'Lubriaceites Ltda.', 'Carrera Lubricante 101, Zona 4', 'Lubriaceites Ltda.', 'Luis Rodríguez', 'www.lubriaceites.com');
call sp_agregarProveedor(5, '061400053', 'Productos de Limpieza', 'Limpiafacil S.A.', 'Pasaje Limpio 202, Zona 5', 'Limpiafacil S.A.', 'Ana López', 'www.limpiafacil.com');
 
delimiter $$

create procedure sp_Listarproveedor()
begin
    select
        codigoProveedor,
        NITproveedor,
        nombreProveedor,
        apellidoProveedor,
        direccionProveedor,
        razonSocial,
        contactoPrincipal,
        paginaWeb
	from
        Proveedores;
end $$

delimiter ;

call sp_Listarproveedor();
.-- ---------------------Buscar Proveedores --------------------------------

delimiter $$

create procedure sp_buscarproveedor (in _codigoproveedor int)
begin
    select
        codigoProveedor,
        NITproveedor,
        nombreProveedor,
        apellidoProveedor,
        direccionProveedor,
        razonSocial,
        contactoPrincipal,
        paginaWeb
    from
        Proveedores
    where
        codigoproveedor = _codigoproveedor;
end $$

delimiter ;

call sp_buscarproveedor(3);
-- -----------------------Eliminar Proveedor------------------------------
delimiter $$

create procedure sp_eliminarproveedor (in _codigoproveedor int)
begin
    delete from Proveedores
    where codigoProveedor = _codigoproveedor;
end $$

delimiter ;

call sp_eliminarproveedor(1);
-- ----------------------------Editar Proveedor------------------------------------
delimiter $$

create procedure sp_editarProveedor (
    in _codigoproveedor int,
    in _nitproveedor varchar(13),
    in _nombresproveedor varchar(60),
    in _apellidosproveedor varchar(60),
    in _direccionproveedor varchar(150),
    in _razonsocial varchar(60),
    in _contactoprincipal varchar(100),
    in _paginaweb varchar(50)
    )
begin
    update Proveedores
    set
        NITproveedor = _nitproveedor,
        nombreProveedor = _nombresproveedor,
        apellidoProveedor = _apellidosproveedor,
        direccionProveedor = _direccionproveedor,
        razonSocial = _razonsocial,
        contactoPrincipal = _contactoprincipal,
        paginaWeb = _paginaweb
    where
        codigoProveedor = _codigoproveedor;
end $$

delimiter ;

call sp_editarProveedor(1, '1234567890123', 'Juan', 'Perez', 'Calle 123', 'Razón Social', 'Contacto', 'www.proveedor.com');

-- -----------------------Tipo de Producto  Procedimiento Almacenados ------------------------
-- CRUD TIPO PRODUCTO
-- ---------------------------Agregar TipoDeProducto-----------------------------

Delimiter $$
	create procedure sp_AgregarTipoProducto(in codigoTipoProducto int, in descripcion varchar(45))
    Begin
		Insert Into TipoProducto(codigoTipoProducto,descripcion)
        values(codigoTipoProducto,descripcion);
    End $$
Delimiter ;

call sp_agregarTipoProducto(1, 'Combustibles');
call sp_agregarTipoProducto(2, 'Alimentos');
call sp_agregarTipoProducto(3, 'Bebidas');
call sp_agregarTipoProducto(4, 'Snacks');
call sp_agregarTipoProducto(5, 'Cuidado Personal');

-- -----------------------Listar TipodeProductos--------------------------------

Delimiter $$
	create procedure sp_ListarTipoProducto()
    Begin
		select
			TP.codigoTipoProducto,
            Tp.descripcion
		from TipoProducto TP;
    End $$
Delimiter ;

-- ---------------------Buscar TipoDeProducto --------------------------------

delimiter $$

create procedure sp_buscarTipoProducto (in _codigoTipoProducto int)
begin
    select
        codigoTipoProducto,
        descripcion
    from
        TipoProducto
    where
        codigoTipoProducto = _codigoTipoProducto;
end $$

delimiter ;

call sp_buscarTipoProducto(2);

-- ----------------------------Editar TipoDeProducto------------------------------------

Delimiter $$
create procedure sp_EditarTipoProducto(in _codigoTipoProducto int, in _descripcion varchar(45))
	Begin
		update TipoProducto TP
		set
			TP.descripcion = _descripcion
		where TP.codigoTipoProducto = _codigoTipoProducto;
	End $$
Delimiter ;

-- -----------------------Eliminar TipodeProducto------------------------------

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

CALL sp_agregarCargoEmpleado(1, 'Subgerente', 'Asistente del gerente');

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

DELIMITER $$
CREATE TRIGGER before_insert_productos
	BEFORE INSERT ON Productos
	FOR EACH ROW
		BEGIN
			IF NEW.precioUnitario IS NULL THEN
				SET NEW.precioUnitario = 0.00;
			END IF;

			IF NEW.precioDocena IS NULL THEN
				SET NEW.precioDocena = 0.00;
			END IF;

			IF NEW.precioMayor IS NULL THEN
				SET NEW.precioMayor = 0.00;
			END IF;
		END$$
DELIMITER ;

DELIMITER $$

CREATE TRIGGER before_insert_productos_imagen
BEFORE INSERT ON Productos
FOR EACH ROW
BEGIN
    IF NEW.imagenProducto IS NULL THEN
        SET NEW.imagenProducto = 'Default.png';
    END IF;
END$$

DELIMITER ;

 -- -------------------------- Productos  Procedimiento Almacenados -----------------------------
 -- CRUD PRODUCTOS
 -- ---------------------------Agregar Producto-----------------------------

DELIMITER $$

CREATE PROCEDURE sp_agregarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_existencia INT,
    IN p_codigoTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos(codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, existencia, codigoTipoProducto, codigoProveedor)
    VALUES(p_codigoProducto, p_descripcionProducto, p_precioUnitario, p_precioDocena, p_precioMayor, p_existencia, p_codigoTipoProducto, p_codigoProveedor);
END$$
DELIMITER ;

CALL sp_agregarProducto('P001', 'Arroz', 5.99, 68.99, 129.99, 100, 2, 2);
CALL sp_agregarProducto('P002', 'Frijoles', 3.49, 39.99, 74.99, 150, 2, 2);
CALL sp_agregarProducto('P003', 'Aceite', 8.99, 102.99, 194.99,  80, 3, 2);
CALL sp_agregarProducto('P004', 'Leche Entera', 2.99, 32.99, 62.99, 120, 3, 4);
CALL sp_agregarProducto('P005', 'Azúcar', 4.49, 51.99, 98.99, 90, 4, 5);

-- -----------------------Listar Productos--------------------------------

Delimiter $$
create procedure sp_ListarProductos()
	begin
    select
		p.codigoProducto,
        p.descripcionProducto,
        p.precioUnitario,
        p.precioDocena,
        p.precioMayor,
        p.existencia,
        p.codigoTipoProducto,
        p.codigoProveedor
        from
        productos p;
	end$$
Delimiter ;

call sp_ListarProductos();

-- ----------------------------Editar Producto------------------------------------

DELIMITER $$
CREATE PROCEDURE sp_actualizarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_nuevaDescripcionProducto VARCHAR(15),
    IN p_nuevoPrecioUnitario DECIMAL(10,2),
    IN p_nuevoPrecioDocena DECIMAL(10,2),
    IN p_nuevoPrecioMayor DECIMAL(10,2),
    IN p_nuevaExistencia INT,
    IN p_nuevoCodigoTipoProducto INT,
    IN p_nuevoCodigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET descripcionProducto = p_nuevaDescripcionProducto,
        precioUnitario = p_nuevoPrecioUnitario,
        precioDocena = p_nuevoPrecioDocena,
        precioMayor = p_nuevoPrecioMayor,
        existencia = p_nuevaExistencia,
        codigoTipoProducto = p_nuevoCodigoTipoProducto,
        codigoProveedor = p_nuevoCodigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END$$
DELIMITER ;

call sp_actualizarProducto('P001', 'Pollo', 8.99, 69.99, 130.99, 100, 2, 2);

-- -----------------------Eliminar Producto------------------------------

Delimiter $$
CREATE PROCEDURE sp_eliminarProducto(IN _codigoProducto VARCHAR(15))
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = _codigoProducto;
END $$

DELIMITER ;

-- call sp_eliminarProducto('P001');

-- -----------------------TelefonoProveedor Procedimiento Almacenados ------------------------
-- CRUD PROVEEDORES
-- ---------------------------Agregar Telefono Proveedor-----------------------------

Delimiter $$
	create procedure sp_AgregarTelefonoProveedor(in codigoTelefonoProveedor int,in numeroPrincipal varchar(8), in numeroSecundario varchar(8), in observaciones varchar(45), in codigoProveedor int)
    Begin
		Insert Into TelefonoProveedor(codigoTeleonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor)
        values (codigoTeleonoProveedor,numeroPrincipal,numeroSecundario,observaciones,codigoProveedor);
    End $$
Delimiter ;

-- ---------------------------Listar Telefono Proveedor-----------------------------

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

-- ---------------------------Editar Telefono Proveedor-----------------------------

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

-- ---------------------------Eliminar Telefono Proveedor-----------------------------

Delimiter $$
	create procedure sp_EliminarTelefonoProveedor(in _codigoTelefonoProveedor int)
    Begin
		Delete From TelefonoProveedor
        where codigoTelefonoProveedor = _codigoTelefonoProveedor;
    End $$
Delimiter ;

-- -----------------------EmailProveedor Procedimiento Almacenados ------------------------
-- CRUD PROVEEDORES
-- ---------------------------Agregar Email Proveedor-----------------------------

Delimiter $$
	create procedure sp_AgregarEmailProveedor(in codigoEmailProveedor int, in emailProveedor varchar(50), in descripcion varchar(100), in codigoProveedor int)	
    Begin
		Insert Into EmailProveedor(codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor)
        values (codigoEmailProveedor,emailProveedor,descripcion,codigoProveedor);
    End ;
Delimiter ;

-- ---------------------------Listar Email Proveedor-----------------------------

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

-- ---------------------------Editar Email Proveedor-----------------------------

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

-- ---------------------------Eliminar Email Proveedor-----------------------------

Delimiter $$
	create procedure sp_EliminarEmailProveedor(in _codigoEmailProveedor int)
    Begin
		Delete from EmailProveedor 
        where codigoEmailProveedor = _codigoEmailProveedor;
    End $$
Delimiter ;

DELIMITER $$

CREATE TRIGGER before_insert_detalle_compra
	BEFORE INSERT ON DetalleCompra
	FOR EACH ROW
		BEGIN
			-- Calcula precios y existencia
			DECLARE totalDetalle DECIMAL(10,2);
			DECLARE nuevoPrecioUnitario DECIMAL(10,2);
			DECLARE nuevoPrecioDocena DECIMAL(10,2);
			DECLARE nuevoPrecioMayor DECIMAL(10,2);

			SET totalDetalle = NEW.costoUnitario * NEW.cantidad;
			SET nuevoPrecioUnitario = NEW.costoUnitario * 1.40;
			SET nuevoPrecioDocena = NEW.costoUnitario * 1.35;
			SET nuevoPrecioMayor = NEW.costoUnitario * 1.25;

			UPDATE Productos
			SET precioUnitario = nuevoPrecioUnitario,
				precioDocena = nuevoPrecioDocena,
				precioMayor = nuevoPrecioMayor,
				existencia = existencia + NEW.cantidad
			WHERE codigoProducto = NEW.codigoProducto;
		END$$
DELIMITER ;

-- -----------------------DetalleCompra Procedimientos Almacenados ------------------------
-- CRUD DetalleCompra
-- ---------------------------Agregar DetalleCompra-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleCompra(
	IN p_codigoDetalleCompra int,
	IN p_costoUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_codigoProducto VARCHAR(15),
    IN p_numeroDocumento INT
)
BEGIN
	INSERT INTO DetalleCompra (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento)
	VALUES (p_codigoDetalleCompra, p_costoUnitario, p_cantidad, p_codigoProducto, p_numeroDocumento);
END $$
DELIMITER ;

CALL sp_AgregarDetalleCompra(1, 10.50, 5, 'P001', 1); 

DELIMITER $$

CREATE TRIGGER after_insert_detalle_compra
	AFTER INSERT ON DetalleCompra
	FOR EACH ROW
		BEGIN
			DECLARE nuevoTotal DECIMAL(10,2);

			SELECT SUM(costoUnitario * cantidad) INTO nuevoTotal
			FROM DetalleCompra
			WHERE numeroDocumento = NEW.numeroDocumento;

			UPDATE Compras
			SET totalDocumento = nuevoTotal
			WHERE numeroDocumento = NEW.numeroDocumento;
		END$$
DELIMITER ;

-- ---------------------------Listar DetalleCompra-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleCompra()
BEGIN
	SELECT * FROM DetalleCompra;
END $$
DELIMITER ;

-- ---------------------------Buscar DetalleCompra-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleCompra(
	IN p_codigoDetalleCompra INT
)
BEGIN
	SELECT * FROM DetalleCompra WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END $$
DELIMITER ;

-- ---------------------------Editar DetalleCompra-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleCompra(
	IN p_codigoDetalleCompra INT,
    IN p_costoUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_codigoProducto VARCHAR(15),
    IN p_numeroDocumento INT
)
BEGIN
	UPDATE DetalleCompra
	SET costoUnitario = p_costoUnitario,
		cantidad = p_cantidad,
		codigoProducto = p_codigoProducto,
		numeroDocumento = p_numeroDocumento
	WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END $$
DELIMITER ;

-- ---------------------------Eliminar DetalleCompra-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleCompra(
	IN p_codigoDetalleCompra INT
)
BEGIN
	DELETE FROM DetalleCompra WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END $$
DELIMITER ;


-- -----------------------Empleados Procedimientos Almacenados ------------------------
-- CRUD Empleados
-- ---------------------------Agregar Empleados-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_AgregarEmpleado(
	IN p_codigoEmpleado int,
	IN p_nombresEmpleado VARCHAR(50),
    IN p_apellidosEmpleado VARCHAR(50),
    IN p_sueldo DECIMAL(10,2),
    IN p_direccion VARCHAR(150),
    IN p_turno VARCHAR(15),
    IN p_codigoCargoEmpleado INT
)
BEGIN
	INSERT INTO Empleados (codigoEmpleado, nombresEmpleado, apellidosEmpleado, sueldo, direccion, turno, codigoCargoEmpleado)
	VALUES (p_codigoEmpleado, p_nombresEmpleado, p_apellidosEmpleado, p_sueldo, p_direccion, p_turno, p_codigoCargoEmpleado);
END $$
DELIMITER ;

CALL sp_AgregarEmpleado(1, 'Juan', 'Perez', 1500.00, 'Calle 123', 'Mañana', 1);

-- ---------------------------Listar Empleados-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados()
BEGIN
	SELECT * FROM Empleados;
END $$
DELIMITER ;

-- ---------------------------Buscar Empleados-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleado(
	IN p_codigoEmpleado INT
)
BEGIN
	SELECT * FROM Empleados WHERE codigoEmpleado = p_codigoEmpleado;
END $$
DELIMITER ;

-- ---------------------------Editar Empleados-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EditarEmpleado(
	IN p_codigoEmpleado INT,
    IN p_nombresEmpleado VARCHAR(50),
    IN p_apellidosEmpleado VARCHAR(50),
    IN p_sueldo DECIMAL(10,2),
    IN p_direccion VARCHAR(150),
    IN p_turno VARCHAR(15),
    IN p_codigoCargoEmpleado INT
)
BEGIN
	UPDATE Empleados
	SET nombresEmpleado = p_nombresEmpleado,
		apellidosEmpleado = p_apellidosEmpleado,
		sueldo = p_sueldo,
		direccion = p_direccion,
		turno = p_turno,
		codigoCargoEmpleado = p_codigoCargoEmpleado
	WHERE codigoEmpleado = p_codigoEmpleado;
END $$
DELIMITER ;

-- ---------------------------Eliminar Empleados-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleado(
	IN p_codigoEmpleado INT
)
BEGIN
	DELETE FROM Empleados WHERE codigoEmpleado = p_codigoEmpleado;
END $$
DELIMITER ;


-- -----------------------Factura Procedimientos Almacenados ------------------------
-- CRUD Factura
-- ---------------------------Agregar Factura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_AgregarFactura(
	IN p_numeroFactura INT,
    IN p_estado VARCHAR(50),
    IN p_totalFactura DECIMAL(10,2),
    IN p_fechaFactura VARCHAR(45),
    IN p_codigoCliente INT,
    IN p_codigoEmpleado INT
)
BEGIN
	INSERT INTO Factura (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleado)
	VALUES (p_numeroFactura, p_estado, p_totalFactura, p_fechaFactura, p_codigoCliente, p_codigoEmpleado);
END $$
DELIMITER ;

CALL sp_AgregarFactura(1, 'Pendiente', 100.00, '2024-05-20', 1, 1);

-- ---------------------------Listar Factura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_ListarFacturas()
BEGIN
	SELECT * FROM Factura;
END $$
DELIMITER ;

-- ---------------------------Buscar Factura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_BuscarFactura(
	IN p_numeroFactura INT
)
BEGIN
	SELECT * FROM Factura WHERE numeroFactura = p_numeroFactura;
END $$
DELIMITER ;

-- ---------------------------Editar Factura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EditarFactura(
	IN p_numeroFactura INT,
    IN p_estado VARCHAR(50),
    IN p_totalFactura DECIMAL(10,2),
    IN p_fechaFactura VARCHAR(45),
    IN p_codigoCliente INT,
    IN p_codigoEmpleado INT
)
BEGIN
	UPDATE Factura
	SET estado = p_estado,
		totalFactura = p_totalFactura,
		fechaFactura = p_fechaFactura,
		codigoCliente = p_codigoCliente,
		codigoEmpleado = p_codigoEmpleado
	WHERE numeroFactura = p_numeroFactura;
END $$
DELIMITER ;

-- ---------------------------Eliminar Factura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EliminarFactura(
	IN p_numeroFactura INT
)
BEGIN
	DELETE FROM Factura WHERE numeroFactura = p_numeroFactura;
END $$
DELIMITER ;

DELIMITER $$

CREATE TRIGGER before_insert_detalle_factura
	BEFORE INSERT ON DetalleFactura
	FOR EACH ROW
		BEGIN
			DECLARE precio DECIMAL(10,2);

			SELECT precioUnitario INTO precio
			FROM Productos
			WHERE codigoProducto = NEW.codigoProducto;

			SET NEW.precioUnitario = precio;
		END$$
DELIMITER ;
-- -----------------------DetalleFactura Procedimientos Almacenados ------------------------
-- CRUD DetalleFactura
-- ---------------------------Agregar DetalleFactura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleFactura(
	IN p_codigoDetalleFactura int,
	IN p_precioUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_numeroFactura INT,
    IN p_codigoProducto VARCHAR(15)
)
BEGIN
	INSERT INTO DetalleFactura (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
	VALUES (p_codigoDetalleFactura, p_precioUnitario, p_cantidad, p_numeroFactura, p_codigoProducto);
END $$
DELIMITER ;

CALL sp_AgregarDetalleFactura(1, 5.00, 2, 1, 'P001');

DELIMITER $$
CREATE TRIGGER after_insert_detalle_factura
	AFTER INSERT ON DetalleFactura
	FOR EACH ROW
		BEGIN
			DECLARE nuevoTotal DECIMAL(10,2);

			SELECT SUM(precioUnitario * cantidad) INTO nuevoTotal
			FROM DetalleFactura
			WHERE numeroFactura = NEW.numeroFactura;

			UPDATE Factura
			SET totalFactura = nuevoTotal
			WHERE numeroFactura = NEW.numeroFactura;
		END$$
DELIMITER ;

-- ---------------------------Listar DetalleFactura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_ListarDetalleFactura()
BEGIN
	SELECT * FROM DetalleFactura;
END $$

-- ---------------------------Buscar DetalleFactura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleFactura(
	IN p_codigoDetalleFactura INT
)
BEGIN
	SELECT * FROM DetalleFactura WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END $$
DELIMITER ;

-- ---------------------------Editar DetalleFactura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleFactura(
	IN p_codigoDetalleFactura INT,
    IN p_precioUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_numeroFactura INT,
    IN p_codigoProducto VARCHAR(15)
)
BEGIN
	UPDATE DetalleFactura
	SET precioUnitario = p_precioUnitario,
		cantidad = p_cantidad,
		numeroFactura = p_numeroFactura,
		codigoProducto = p_codigoProducto
	WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END $$
DELIMITER ;

-- ---------------------------Eliminar DetalleFactura-----------------------------
DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleFactura(
	IN p_codigoDetalleFactura INT
)
BEGIN
	DELETE FROM DetalleFactura WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END $$
DELIMITER ;


set global time_zone = '-6:00';
