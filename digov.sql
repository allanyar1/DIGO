-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 04-06-2019 a las 12:59:38
-- Versión del servidor: 5.7.23
-- Versión de PHP: 7.1.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `digov`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulos`
--

CREATE TABLE `articulos` (
  `id_productos` int(10) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `tipoVenta` varchar(15) NOT NULL,
  `preCosto` float NOT NULL,
  `preVenta` float NOT NULL,
  `id_departamentos` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `articulos`
--

INSERT INTO `articulos` (`id_productos`, `descripcion`, `tipoVenta`, `preCosto`, `preVenta`, `id_departamentos`) VALUES
(1, 'COCA COLA 600 ML', 'Por Unidad', 15, 22.5, 1),
(2, 'ACEITE 123 1LT', 'Por Unidad', 15, 22.5, 1),
(3, 'TAKIS FUEGO 300GR', 'Por Unidad', 15, 22.5, 1),
(4, 'AGUA EPURA 1LT', 'Por Unidad', 5, 7.5, 1),
(5, 'LATA ELOTE HERDEZ ', 'Por Unidad', 18, 27, 1),
(6, 'FRIJOL', 'Por Unidad', 18, 27, 1),
(7, 'ARROZ', 'Por Unidad', 18, 27, 1),
(8, 'SACO DE PAPA', 'Por Unidad', 15, 22.5, 1),
(13, 'LATA CHILE CHIPOTLE', 'Por Unidad', 18, 27, 1),
(14, 'CREMA LALA 1 LT', 'Por Unidad', 15, 22.5, 1),
(15, 'CONSOME DE POLLO', 'Por Unidad', 6, 9, 1),
(16, 'SALCHICHA PARA ASAR ', 'Por Unidad', 50, 75, 4),
(18, 'PLATOS DESECHABLES PKT 50', 'Paquete', 18, 25, 14),
(20, 'CUCHARAS DESECHABLES', 'Paquete', 25, 37.5, 1),
(21, 'CANELA', 'Por Unidad', 15, 22.5, 1),
(22, 'BUBU LUBU', 'Por Unidad', 8, 12, 2),
(23, 'DONITAS BIMBO', 'Por Unidad', 15, 22.5, 3),
(24, 'SPRITE 3LT', 'Por Unidad', 30, 45, 11),
(25, 'MANZANITA 600 ML', 'Por Unidad', 13, 19.5, 11),
(26, 'CHOCOLATE ABUELITA', 'Por Unidad', 14, 21, 1),
(27, 'NESTEA', 'Por Unidad', 6, 9, 1),
(28, 'BOLOGNA LOGMON', 'Por Unidad', 25, 37.5, 4),
(29, 'SALCHICHA FUD', 'Por Unidad', 35, 52.5, 4),
(30, 'CHORIZO DE PUERCO', 'Por Unidad', 16, 24, 4),
(31, 'QUESO AMARILLO', 'Por Unidad', 15, 22.5, 2),
(32, 'NESCAFE 500GR', 'Por Unidad', 23, 34.5, 1),
(33, 'TE VERDE ', 'Paquete', 8, 12, 1),
(36, 'SABRITAS BLANCAS 300 GR', 'Por Unidad', 14, 21, 3),
(37, 'LECHE CONDENSADA 1LT', 'Por Unidad', 25, 37.5, 2),
(38, 'DANONINO PAQUETE', 'Paquete', 30, 45, 2),
(39, 'MAZAPAN', 'Por Unidad', 10, 15, 1),
(40, 'GALLETAS ', 'Por Unidad', 15, 22.5, 1),
(41, 'SUBMARINO', 'Por Unidad', 14, 21, 1),
(42, 'SIX COCA COLA LATA', 'Paquete', 14, 21, 11),
(45, 'PIMIENTA ', 'Por Unidad', 25, 37.5, 1),
(50, 'ASDAS', 'Por Unidad', 14, 21, 1),
(55, 'ROCKALETA', 'Por Unidad', 8, 12, 1),
(56, 'PAN BARRA BIMBO', 'Por Unidad', 8, 12, 2),
(57, 'PERIODICO', 'Por Unidad', 18, 27, 1),
(58, 'MANGAS', 'Por Unidad', 50, 75, 1),
(60, 'MARUCHAN', 'Por Unidad', 8, 12, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `corte_temporal`
--

CREATE TABLE `corte_temporal` (
  `Codigo` int(10) NOT NULL,
  `Monto_Caja` float NOT NULL,
  `Entradas` float NOT NULL,
  `Salidas` float NOT NULL,
  `Total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `corte_temporal`
--

INSERT INTO `corte_temporal` (`Codigo`, `Monto_Caja`, `Entradas`, `Salidas`, `Total`) VALUES
(1, 800, 909, 208.5, 1500.5),
(2, 900, 394.5, 315, 979.5),
(3, 900, 37.5, 45, 892.5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `corte_totales`
--

CREATE TABLE `corte_totales` (
  `Codigo` int(50) NOT NULL,
  `Usuario` varchar(50) NOT NULL,
  `Fecha` varchar(30) NOT NULL,
  `Hora` varchar(30) NOT NULL,
  `Tipo` varchar(30) NOT NULL,
  `Total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `corte_turno`
--

CREATE TABLE `corte_turno` (
  `Codigo` int(10) NOT NULL,
  `Usuario` varchar(50) NOT NULL,
  `Fecha` varchar(30) NOT NULL,
  `Hora` varchar(30) NOT NULL,
  `Tipo` varchar(30) NOT NULL,
  `Total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `departamentos`
--

CREATE TABLE `departamentos` (
  `id_departamentos` int(10) NOT NULL,
  `departamento` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `departamentos`
--

INSERT INTO `departamentos` (`id_departamentos`, `departamento`) VALUES
(1, 'Abarrotes'),
(2, 'Lacteos'),
(3, 'Botanas'),
(4, 'Carnes frias'),
(5, 'Cigarros'),
(6, 'Limpieza'),
(7, 'Verduras'),
(8, 'Frutas'),
(9, 'Cerveza'),
(10, 'Panaderia'),
(11, 'Sodas'),
(12, 'Carniceria'),
(13, 'Miscelania'),
(14, 'Desechables'),
(15, 'Periodicos'),
(16, 'Informatica'),
(17, 'Electronica');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles_venta`
--

CREATE TABLE `detalles_venta` (
  `id_venta` int(11) NOT NULL,
  `id_productos` int(11) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `preventa` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `detalles_venta`
--

INSERT INTO `detalles_venta` (`id_venta`, `id_productos`, `descripcion`, `cantidad`, `preventa`) VALUES
(1, 1, 'COCA COLA 600 ML', 1, 22.5),
(1, 4, 'AGUA EPURA 500ML', 1, 7.5),
(2, 1, 'COCA COLA 600 ML', 1, 22.5),
(3, 4, 'AGUA EPURA 500ML', 1, 7.5),
(4, 6, 'FRIJOL', 1, 27),
(5, 5, 'LATA ELOTE HERDEZ ', 1, 27),
(5, 4, 'AGUA EPURA 500ML', 1, 7.5),
(6, 1, 'COCA COLA 600 ML', 1, 22.5),
(7, 1, 'COCA COLA 600 ML', 1, 22.5),
(8, 1, 'COCA COLA 600 ML', 2, 22.5),
(9, 1, 'COCA COLA 600 ML', 2, 22.5),
(10, 1, 'COCA COLA 600 ML', 2, 22.5),
(11, 1, 'COCA COLA 600 ML', 1, 22.5),
(12, 1, 'COCA COLA 600 ML', 2, 22.5),
(13, 1, 'COCA COLA 600 ML', 1, 22.5),
(13, 2, 'ACEITE 123 1LT', 1, 22.5),
(14, 1, 'COCA COLA 600 ML', 1, 22.5),
(15, 1, 'COCA COLA 600 ML', 1, 22.5),
(15, 14, 'CREMA LALA 1 LT', 1, 22.5),
(16, 21, 'CANELA', 1, 22.5),
(17, 30, 'CHORIZO DE PUERCO', 1, 24),
(18, 23, 'DONITAS BIMBO', 1, 22.5),
(19, 23, 'DONITAS BIMBO', 1, 22.5),
(20, 23, 'DONITAS BIMBO', 1, 22.5),
(21, 23, 'DONITAS BIMBO', 1, 22.5),
(22, 15, 'CONSOME DE POLLO', 1, 9),
(23, 8, 'SACO DE PAPA', 1, 22.5),
(24, 1, 'COCA COLA 600 ML', 1, 22.5),
(25, 1, 'COCA COLA 600 ML', 1, 22.5),
(26, 30, 'CHORIZO DE PUERCO', 1, 24),
(27, 23, 'DONITAS BIMBO', 1, 22.5),
(27, 1, 'COCA COLA 600 ML', 1, 22.5),
(28, 15, 'CONSOME DE POLLO', 1, 9),
(29, 20, 'CUCHARAS DESECHABLES', 1, 37.5),
(29, 39, 'MAZAPAN', 1, 15),
(29, 39, 'MAZAPAN', 1, 15),
(30, 33, 'TE VERDE ', 1, 12),
(31, 1, 'COCA COLA 600 ML', 1, 22.5),
(31, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(31, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(34, 1, 'COCA COLA 600 ML', 3, 22.5),
(35, 1, 'COCA COLA 600 ML', 6, 22.5),
(36, 1, 'COCA COLA 600 ML', 2, 22.5),
(37, 1, 'COCA COLA 600 ML', 3, 22.5),
(37, 15, 'CONSOME DE POLLO', 1, 9),
(37, 30, 'CHORIZO DE PUERCO', 3, 24),
(38, 5, 'LATA ELOTE HERDEZ ', 1, 27),
(39, 39, 'MAZAPAN', 1, 15),
(40, 1, 'COCA COLA 600 ML', 1, 22.5),
(41, 3, 'TAKIS FUEGO 300GR', 3, 22.5),
(42, 32, 'NESCAFE 500GR', 5, 34.5),
(43, 32, 'NESCAFE 500GR', 4, 34.5),
(44, 32, 'NESCAFE 500GR', 1, 34.5),
(45, 38, 'DANONINO PAQUETE', 2, 45),
(46, 1, 'COCA COLA 600 ML', 1, 22.5),
(47, 1, 'COCA COLA 600 ML', 1, 22.5),
(48, 2, 'ACEITE 123 1LT', 1, 22.5),
(48, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(48, 2, 'ACEITE 123 1LT', 1, 22.5),
(49, 1, 'COCA COLA 600 ML', 1, 22.5),
(50, 2, 'ACEITE 123 1LT', 1, 22.5),
(50, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(51, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(52, 3, 'TAKIS FUEGO 300GR', 2, 22.5),
(53, 4, 'AGUA EPURA 500ML', 1, 7.5),
(54, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(55, 1, 'COCA COLA 600 ML', 1, 22.5),
(56, 1, 'COCA COLA 600 ML', 1, 22.5),
(57, 27, 'NESTEA', 2, 9),
(57, 56, 'PAN BARRA BIMBO', 5, 12),
(58, 32, 'NESCAFE 500GR', 6, 34.5),
(59, 12, 'SOPA MARUCHAN', 5, 27),
(60, 39, 'MAZAPAN', 52, 15),
(61, 30, 'CHORIZO DE PUERCO', 5, 24),
(62, 57, 'PERIODICO', 3, 27),
(63, 58, 'MANGAS', 21, 75),
(64, 13, 'LATA CHILE CHIPOTLE', 6, 27),
(65, 36, 'SABRITAS BLANCAS 300 GR', 1, 21),
(66, 1, 'COCA COLA 600 ML', 5, 22.5),
(67, 5, 'LATA ELOTE HERDEZ ', 1, 27),
(68, 5, 'LATA ELOTE HERDEZ ', 1, 27),
(69, 4, 'AGUA EPURA 1LT', 13, 7.5),
(70, 5, 'LATA ELOTE HERDEZ ', 1, 27),
(71, 2, 'ACEITE 123 1LT', 1, 22.5),
(72, 15, 'CONSOME DE POLLO', 4, 9),
(73, 1, 'COCA COLA 600 ML', 1, 22.5),
(73, 15, 'CONSOME DE POLLO', 5, 9),
(74, 15, 'CONSOME DE POLLO', 1, 9),
(74, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(75, 20, 'CUCHARAS DESECHABLES', 4, 37.5),
(75, 27, 'NESTEA', 1, 9),
(76, 30, 'CHORIZO DE PUERCO', 3, 24),
(76, 21, 'CANELA', 4, 22.5),
(77, 30, 'CHORIZO DE PUERCO', 7, 24),
(78, 32, 'NESCAFE 500GR', 1, 34.5),
(79, 30, 'CHORIZO DE PUERCO', 1, 24),
(80, 31, 'QUESO AMARILLO', 1, 22.5),
(81, 25, 'MANZANITA 600 ML', 3, 19.5),
(82, 32, 'NESCAFE 500GR', 1, 34.5),
(83, 31, 'QUESO AMARILLO', 3, 22.5),
(84, 1, 'COCA COLA 600 ML', 1, 22.5),
(85, 1, 'COCA COLA 600 ML', 1, 22.5),
(86, 1, 'COCA COLA 600 ML', 8, 22.5),
(87, 1, 'COCA COLA 600 ML', 1, 22.5),
(88, 2, 'ACEITE 123 1LT', 1, 22.5),
(89, 3, 'TAKIS FUEGO 300GR', 1, 22.5),
(90, 5, 'LATA ELOTE HERDEZ ', 1, 27),
(91, 1, 'COCA COLA 600 ML', 1, 22.5),
(92, 1, 'COCA COLA 600 ML', 16, 22.5),
(93, 32, 'NESCAFE 500GR', 1, 34.5),
(93, 20, 'CUCHARAS DESECHABLES', 5, 37.5),
(94, 32, 'NESCAFE 500GR', 5, 34.5),
(95, 28, 'BOLOGNA LOGMON', 1, 37.5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inventario_productos`
--

CREATE TABLE `inventario_productos` (
  `id_productos` int(10) NOT NULL,
  `existencia` int(10) NOT NULL,
  `minimoInv` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `inventario_productos`
--

INSERT INTO `inventario_productos` (`id_productos`, `existencia`, `minimoInv`) VALUES
(4, 100, 5),
(1, 24, 5),
(2, 45, 5),
(3, 33, 5),
(5, 12, 15),
(6, 48, 5),
(8, 26, 1),
(13, 16, 2),
(14, 12, 5),
(15, 17, 2),
(16, 50, 5),
(20, 73, 5),
(21, 20, 3),
(22, 15, 2),
(23, 19, 5),
(24, 2, 1),
(25, 1, 10),
(26, 36, 2),
(27, 17, 10),
(28, 26, 10),
(29, 20, 12),
(30, 0, 10),
(31, 16, 2),
(32, 21, 10),
(33, 31, 5),
(36, 1, 10),
(37, 5, 10),
(38, 10, 10),
(39, 5, 5),
(41, 10, 5),
(42, 12, 5),
(45, 15, 5),
(55, 11, 5),
(56, 10, 5),
(57, 1, 10),
(58, 20, 5),
(60, 1, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login`
--

CREATE TABLE `login` (
  `id_empleado` int(10) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contraseña` varchar(50) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `permisoVenta` tinyint(1) NOT NULL,
  `permisoProductos` tinyint(1) NOT NULL,
  `permisoCompras` tinyint(1) NOT NULL,
  `permisoCorte` tinyint(1) NOT NULL,
  `permisoConfiguracion` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `login`
--

INSERT INTO `login` (`id_empleado`, `usuario`, `contraseña`, `nombreUsuario`, `permisoVenta`, `permisoProductos`, `permisoCompras`, `permisoCorte`, `permisoConfiguracion`) VALUES
(6, 'allanyar1', 'geekdovelopers', 'Allan yarif ibarra diaz', 1, 0, 1, 0, 0),
(7, 'paezj', 'papumx', 'Juan carlos paez ruiz', 1, 0, 1, 0, 1),
(8, 'roberto', '12345', 'toberto becerra', 1, 1, 1, 0, 1),
(12, 'Admin', '12345', 'Administrador', 1, 1, 1, 1, 1),
(13, 'UABC', '12345', 'Universidad', 1, 1, 1, 0, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `logotipo`
--

CREATE TABLE `logotipo` (
  `id_logotipo` int(10) NOT NULL,
  `imagenLogo` mediumblob NOT NULL,
  `longuitud` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `logotipo`
--

INSERT INTO `logotipo` (`id_logotipo`, `imagenLogo`, `longuitud`) VALUES
(1, 0x89504e470d0a1a0a0000000d49484452000003e1000000440806000000ee803426000000017352474200aece1ce90000000467414d410000b18f0bfc6105000000097048597300000ec400000ec401952b0e1b0000202d49444154785eeddd77605be5bd3ef0475bb2bc633b89ed4c67ef3d80b0d7af8c1fb4ec55cabd502ed04b1997d1b25b20cc52d68552026dd950462904285076421292901dc771a69dc4f1b6ac2df9bedf5747b12dcb899dd84e02cf078e7596ce52fe79cebb4c4d0a88888888888888a8db998d4f22222222222222ea6607450897a2fa759e30bedc1944853f1a5b49444444444444749039e043784865ee3b5634e0c2f9b5d8ea8be0f98d5ebcbac5676c252222222222223a781cf021fcc3ed01bcbf2d0029ff8e34012a87e391e2466cf6aa19222222222222a283c8011fc21756077575f4a8fa23e1fbdd72bf2e1d5f561b8eed40444444444444749038e043788e23f925f672988c39222222222222a283c3011fc24f2f7422cbdefa32c76658312ddb6e2c111111111111111d1c0e8a71c2373546f0e2661fb6f9a218a502f885035c48b5b2249c8888888888880e2e0745088f930b65f4262222222222a283d57e0fe1d2e1dae73b83787d8b4ff77e5e946ac191790e4ccdb63170131111111111d10fca7e0fe16f6ef5e3fe351e84d5553c372d136956137eb9a80e378f4c55619cedbe898888888888e88763bf76cc561f6ac29c0d5e1dc0850c3ff60f35d586a278b2a451978c13111111111111fd50ecd792f0e28630ce9f5fabdb7a2772594c78756626f25d16634df7f307c2d8b1d3632cb565369be04eb12333c309b3a9fdcaf25e5f083bab1a8da51897d386bc1cb7b1945c65b517a59baaf5a7df1f86c56246aadb8e3e79a91832301b0e87d5d873cfe418c5a555a854d7118e44e176d991df270d4307f782b313c7212222222222a2aeb35f43b8f47a7ecefc1a84a2c68a16a4f7f3370fcd4276c2f064dd69e1d2325cf59bf78ca5e4540e475aaa03d32715e2dcd3c762d4b03c634bb38fbf588fdfcefec4588a993eb1008fde7d92b1d4da378bb6e0f9571663c59a0a84c21124fe2212fe33d39d38ebd431b8f08cf1ed86f1a8fae2bc855bf0d7d79762f9ea1d6d8e157f8970dc1145f8f9991350d037ddd8424444444444443d61bf5647efeb32636c86cd586a6d662f7b9bf1c1f795740027d5dfe755059396be4b608d44a2bb9d42e128aa6b7d98fbe93a5c7afd3ff0cadbcb75f86d4996da7c577aa04b20c77af4d9f9b8f6f6b958bc7c1b82a1b6015c44d577e59c4ffd75216ebef763040261634b3339d61f9efe06d7dff901be5b569ef458729c064f006fbeb70abff8f55bf862fe26630b111111111111f584fd1ac2ed66136e1a998a6169d65d3da14b49f3844c1bae19e6eed2ded1bde126dcbbda83274bbcb8f1fb06ec0c24297eef2409c38f3c330f5f7dbbd958d3711290fff2ea12fcedf5a508ab009da8bddaee5fccdb8897de5a6e2c357be68545fa858084f18e90507ffb039f62d9ea1dc61a222222222222ea6efb35848bc16e0b9e9992817bc7a5e1bf87baf1d0f8743c36291d79ceaebdb4adbe88ee084e48f8cfb4752ce26767ba70e629a371ea09233076646fd8acadaf4b02f413cf7dabdb8177c6bad24afce5b525ad4aab25784f18d307f7dc7c2c9e7df834dc75c3d1185694636c6df6c63f57c2d3183496a0db7e4b15f4c492effcde69b8f8ec89f8efff9c81e38f2882dddeba7dbd948a3ff2a7791d0eee444444444444b46ff67b08176eab09c7f676e0a2812ecccab5eb4ed9da23b5ba1be3dda9774269630441a34af8e0548b0ee21d219da2dd78d52cdc76ed9198f387d371dfad27c0e56cdd265b3a535bb66abbb1d431afbdbb123e7feb6ae5d327f5c363779f8ce38f1c8271a3fae027c70cc3a3bfff097ae7a622b7971b33a7f4d36dc2affcc534ddbe3b4e4ac0134bd3470ecdc50b4f9c81ab2e998e8bce9c807b7e731c66ab29f125c2cab5159dbe76222222222222da3b074408ef88e575615cb3b41e877d5a85d3bfaec1e5dfd5e191e24654053b568abbaabe39f08e4cdfbbdec1a5a4faf0190370d4a1838d35315202bd7069b9b1b467526abe60c9566329467a5bbfec82c96d027e4e760ade9a732ede7ff14215d04fc2d597ced4e13cc5156b4b2f55e2172e29d3f37112d0afb8781ad2d31cc69a98593306aa6b1f642cc5487bf5f9df6d3196888888888888a83bf558085fa342b0b4c93e6b5e8d9e1e5cdb88325fc4d8ba7b1f6d0fe04a15babfdc1984435df19c69197874623a2a0351fcd7a23a6cf6eef93872feb8bd0de17113c7f635e69aaddf586dcced990c1f5655e3339662b2b35c18d82fcb588a05f5b26df57a92e1cecab7c7e6e353bc3abac71b6c33ac9a0c6b2625e189e425c294f105c652b3d24d35c61c11111111111175a71e09e15fa8f0fc7071232e18e0c265835350ea89e095cd3efc5205e8d52dc27132e52aa84b78f7466255c99d161372551297eae47d9c665dcd7cb6dabebb1aeab2adc4133b8f7c4fdaa1ef8b5e992e63ae597d83df98dbb346159c137b384f4f75c0d1a2cdf6570b36e3bc2bde68777af7a3b57abfda3a7f9bded9651cf0f4f4d6a5e07152b29ea8ae13d74e4444444444447bafdb43784d308afbd678743bef02974505e0e652e8edfe28ee57db928d131ef74e79000d2d12b6947edff07d031e5edb88d7b7c6c2e392da50abeae689a4a45c7a4717e93613f2d575ec93245d97878d97041d91d8819a9670cc7038a2c37a7b5328d47ee9bf491d4bfe4b2631b00ba90a4f444444444444ddafdb43f8c2ea1076a8b03dbf2a88ab16d7e1faefeb8d2d316b1ac2bb4aa913495c5c55d77a9bacfbba32889736fb7675d02621be6575f34425ea1cf11c5fa80278aa75df42a784e044ee94e4e39d2723edb9edb6d62f023c8d01047613acdb9396dab6c43b188cc0eb6b7b8da2a6ae6da9777a9ad398232222222222a2eed4ed21bc5c05702105c50b54204f6cbf2d017a779dab35e9d8bd6791a4c5cb3112f4e3f6b53db858575a65cc35eb9b9766cced59af2c1732335a075f6927be7d4783b1146b777edf2dc7ebe9b6eb8ed4d5d5939110de2bab75157309f41b36d71a4badad5a5b61cc35eb5f9861cc1111111111115177eaf610de5b7a52db0d2994ceb225df47caab87a4ee3934cb685d43dbd94fa2795776ca56bebd011f7e56622c351b37aab731b767129c6508b296a2d126bcf8e632dd5bb990507fccacc17a3a644a7f581386168b93f6df891dc5c9b8df320679e2f8df9bb6d6e2d3af37184b3152157d6a92ceda888888888888a8eb757b089f9a6d43ce6e8278910acf43d26255b3c3d10076d4ad4024da5c95faa47cc76ec70d1723d2ac1897993c5c4b097c892756fa2e871992dab9f6e0d28bf90b7fff1e735e5e8cd98f7d89cbfee79d36bd91e7e5b8316bc60063a9637efa93516d82f5dc4fd7e1ae873fd363776faff060e3965abc3d7735aebef57dd4d4b5ee4d3d4e9a739f79cae836edba3ffb6603aeb96d2e3efa7cbd1e0eedc5bf2fc35537bf87bafad6d5d1070dc84adadb3b111111111111753d539362cc779b0fb70770f7aae61ecee3b2ed663c303e0d63d29bb0b96a1e16953e8bca8662e4674dc28c2157202f7d940a9966bcb4c987c74bbc0846db5eaa04fc8727a463543b25dcd2bbfaa95fc586e0925ed55f9999850c5bf250bf604919aeb8e95d63a963644ceeffb9e2301d84e3fef5c57adc7cf7bf8ca598e9930af1c4bd271b4bb1f1b9ef7ce833bcff49b1b1a699c562d6e385074311ddbe3b99eb2e3f14e79e3e56cfcb2f78c7839fea6375e6d7b4db2d98fd9be370f8cc81c61a222222222222ea4edd5e122e4ee8e3c0a393d271549e5d07ef3ca719a7e43bf1bf93ddc80c2fc43f165f89f7975e8f2acf3a64a6f44759cd22bcbde897f87ccd6c34f8b7e1dcfe4efc6e4c2a86a559f5106352855d4ac70fcfb5e3c94919ed0670d1b23db8f48a2ebda3771509e0179e31013f3b6994b1a6e324685f7fc5a13a9c27764e2e015dc6014f16c0259c5f7cf6449c7cdc30634dac34fc862b0fc3ccc9fdda1cab3d12c07f7de94ccc9ac1004e4444444444d4537aa424bcad266ca95e88ef4a9f55817bb10a8e260cca3d029307fd02368b0b358d9bf06dc993a86e2c55cb291855701a260c381f298eded8e68fa021d4a483bc04fa3d79b2c48b391bbc7afeac7e4edc302255cf27d3d1927087c38a09a3fbe892e8c3a6b5ad86de9192f03829ed7ee9cd6578f9ade5a8aa895d6732ee143b8e3fa24887fef63a5293d0feca3bcbf1cadbcb5151d968ac6d4bdaa35f75c9744c1cd3b7c3a19d888888888888f65d8f86f040b801a53bfe8de55b5e45a5679d1ecb7a40ce613a7cfb43f55858fa342aea57a37faf43d4ba8b51ed29c5d24d2fa0d6bb1916b31d03d5bea30a4f4341d654b5bce721c1e4c6ae5e5c8f6faa626dcc6f1f9daa4be0db23eda55716ef3496dab25955f0cf74a1b06f3aec766bbb0156c2f4daf5ad7b50cf4c7762d4b05c63a92d09d02b8b2b74cfebd253ba2c4b69754e760a060fc8c2e8e1bd7529784748876cabd7ed44f1fa4a1dc6654c7197d386bebdd3740772fd0b3219be898888888888f6831e08e14da86edc8035e5ff44f1b6b9680c54c26a71a8a03d13e3fb9f8b4853188b373c8ff29aef106d6aae7e6d353b30a4cff17a1fe9ac6d65d95ba86c58abb74995f591f9a7aaedc721cd29bd8c274f94be4813ce9e57abdb85cb1e2fcec8d455da89888888888888f6876e0fe15facb95f07f050c407b723174355b01ed6e74434f8b763c596d7505ebbb4556fe8423a636b6a8a0dafe5b0a5abef9c80e17dff1fc291803ed6a6caafe00fd5e96de3fa9d83694597e97d1349f83eedeb1a15ee639dc0bd71485697b60927222222222222ea8c6e0fe1df6d780ea515ffc6e8c29fa2307b1ab6562fc07215be2b1bd6a9ad4dbaa47b70de51ba7af9c72b6e8727508113c7dd07a72d031eff0e7cb0ec467d1ca98e5e90350563fafd0cd9eec1d858f9255697bdababa78feb77b6de27d1e73b83b86e69bd9e1f9769c3335332f4306544444444444444fb43b7877029e536992c686a8ae0bda5d7604bd5b7c6969863c7dc85e17d7fa2e75f99770eea7de538ef90d791eaec0d5fb01a733e3f5e6f6b29d33d00478dfc2dfa648ed725e66615e09365eba7d77bf14c69acb3b3330a9db86964fb9db21111111111111175b76e1fa24c4ab0d55f94567ca602f882d8ca0452b5dc1bac42a429a4c3bacc4b000f2754538fabf7966163f50accdd1ec2ad2bfdf8e5a23a5cbbb45ef7825e1988556317abeb9b87271bb99b61cc888888888888887a42b795846ff146f076991ff3ab427860bc1bdfaebc12e5358b8dadcd24a44b1b7095be8dd0dd145b2765db2613c2117f6c478354531f3cf81e3c55360ca59e88ee015dc60b3fafbf0b8b6b4278758b0fd70d4fc5116add05dfd6eaeb10d229dbf07de8944dce231dbd39cd26a8ff898888888888883aadcb4bc203d1263cbfd1878b5400fe8bfab4aa33d822e5a8a85f65ecd19ab4093f7ad46d3876ecef90e2e8a5db86cf1a7ebdaea67ef4a85b6301dd20e17cf8e05bf0d0a6a1586f047097c584ab87ba3125db864b8b5290e7b0e091e246943646b0c31f2b154fb19ad03fc5a2e73b62714d58bf3c6829a002f839f3ebb04e9df787ae2210c53be50104a547bb04f25cbeaf6dae61b0afe4146f9705b0b3450d062222222222a21faa2e0de17e1554ef5dedc113eb1ad1108e05b81169563478d7b729d18e2bec355df7985e9477346c16975aa30273af1928ea7d8c9e5aea9f73283eac9b8ced46b8161214771801ae519db32e14458ec3ac435d3c440e765be0e844f1f523ebbc78b8d8abef274ee6aa024d88746f13fa36beac0ce1b98d3e63a9676c6c8ce051f50c7c49de372c56017c458b6afefb4a7eb98f2b82ad7e532222222222a21faa2e0be1124dff54eac57be5013d1f2781b8a67193b1d4562c1acb379abf159b6b82d494d7d5d20d39bd4ec5e73b5b97504b4ebe757903ee5ae5c1658bea74296e51aa05eb3dcd4151c606ef68062ff3457520ac0a4675697a22b93695f3f59448ae45827fcb02649997f5f229ef2564537cb9e5316459be6bbcbbd064ddba8630bead0aebefc4c9acbabca4d720df976d2d76df45d6c5af41c8a71c37bedc11970f76e1dc7e4e3d2fd7179fe49cf229e2cfa7e571e3e7896f8bb3aadfe58f13d23026a3b9a940ec786d9f852cb79ce2e71372fcc4674f444444444474a0b1dca118f3fbe4fbda1066aff1b40a8be2a83c3b32234b505ef39db1a6b5eac652acaff8046bb7cdd5f3d1a6107636acd6bda857798ae1b0a6c165cfd481dc997d0ee656388c6f3693b6da6b555895e06c330367f773e1bb9a103619edc14f2f7076b863b67f6e0be82027c17d67a00953b36d7abd84bebf6ef2ab306fc283c58d787dab1f693693de4facaa0fe3372b3c786abd1f5f57853021d38a0c7531f3abe5b978f18d5a2743a6c9fa5f2f6dc05a4f048f95787172be03f5eae077ae6ac4c3c53eccdd1ed0639a0f725bf0cc069f3a4f00dbfc512caf0be3c43e0e5dddff7e75bcd96bbd78abcc0fbbc5b4ebdee41c372ff7e0f98d7eac6f8c609aba765b8bb70f1e759e2b1737e863f7719a7595fe1bd5fe87f6b2e92afb71f222e2938aa07a8e4e3813c674936b2e51df1ba7eee3c5cd7ebc591650d711c013eb7dfaa5853cbb1b9779f0f216bf3ef768e3da2e5954af5f6edcbda611afa9676757db46a96df25caf52d734405d536f754df5a126dcb632f62cde53bf459e5a37506d7b57cdcb3392f3c5a7c2140bfaa9499e8ddcf7d3a57ecc53cf7b62a68de3c11311111111d101a9cb4ac29f57813159c96cad0a55564bdbe01cb7b33e16b8b754cdd755d6f3d2476164c119c8cabf096b2c17e37bc79dd894f147b807bd865c773ffcb4d099b46ab9ac92b07dfdf0547c5519c2b72a8c09d9b3c0d5b1f6e012203f5341f9b01c1b0e57d3976a3ef19ea49afb43e3d3f033751df7aef6eafb936aeb121ca7abd0fbc2f4741d00ff541aab422ec177514d08b90e33ceefef44581d4fda954b8771d70c4bd1f7f27b152ee5187f9a9ca68752bb4b2d4b95f033d5fc497ded18926ac14d23dcfa782f6cf2634d4304ff3b290dbf1a9a82c7d679f5cb0679117187ba06f9ce936adb9afa305e5541b825b70ad40e35cd33dabb2f50cf48ee4f427f474990ae94626f455e7a481bf1f3d47ddd36ca8d7fed08eafbbe776caa5e2743c4c93e423ad1dbacaef30fead9c93d3e54ecd52f17a476ff0675af5ef59cd4ac6e0a10562b9f9b9aae7e6b877e8151a79ef121bd6cb8559d43a6e37adb752ff803540097ebff8d0ae0b3d4eff5d769e9ba8f8067d5bf452222222222a2035197847029bd944097a5c2dc15435270efb8349c92efd4c158aa8767a6f437f66c9f74c036beff79281cf428666f998eab9706f178890f7fdfeac78b2a78deb7c68f5f2da9d7a1edf6d1a9ba9a7b9c94aa5e3bccad4bc1ef5fe3c1a715815dedb9e5efddab3d5856b7e776cc122e57d64570880a746333ac3afc255649bf7080134355283e4305c454ab092bd4712d2613ee1aedd6db24d08e57df958019af1a9da3d6496096126bb5ab76e510970e965e759d52727e79914b97f89e56e0d06dd8a5da7d96dda44bd3a5345a4aaee578b2fed47c872ecd9e9563d7a5c72bd535481895ead86ea313babb55103e5c6d6f497e0f39e792dab03ed6773561cc54cbb27e6f8dcfb4ea6348a9bb5cf7b12a200f4bb3e0e83cbbae35d0b2c3b5ffafae5b9a0ac8b3cb71987409764bf29b4969fe592aa417b8cc38a177ece58d8474f9bda554bd9fcba2c3fe25835cc857fbc8f394d07fbe7af6b2cf38f5ecb7266bcc4e44444444447400e892102ee3714be9e52da35255384ac1712a3cddaae64f2b7062b50a5a6ed720a3d3b5f69830b6f04c54b82fc76dab62d5ae6548b16b8c601d271daf49156c697b2ea15b4a9c25405e5e94a2ab48bfb6c5dfa63abc2857a1ece665b1ead0bb2325e812fea4b45a0a7025f87e5dd97aacf278ad6d9b4a7f1292eb55fa956bb4aae5db5736e23f16d6ebead6f1002ee41a5bd4f6d6e4c581a85221555e2cc44beb653f39afbcbc4824879412e0bf6df2e1c205f5b8449d4baa8ecbcb027916970e76e10fc55e5d355b4a9607abc09be8d01c9bfebda4845a3e67a800bd2fe2b7253f933c07298916721f728bf22ce3e2615f4affe56585bcbc69497ebb1a755d524a2ef777e5927addd15ebc933ff1c266bf7ed1f0d382584097f3480ffcbf5d1e7bf66f243c7b2222222222a203498b88bbf724d065aa043625ab39d049e03a26cf810d8d613498f2919336dcd8d2566efa70b8722fc7436b7dba54f5ecfe2e5dd27bde00170ecf6d5b955daa6abfa082e8f96a3f19237c84a30a8b2bb621dd548f14930f0e535885b3688b2edda0872b7b6e83d7586a2b56ca1ceba5fba205757a9260ffef9da1561d8cc5c93af98e94f6cacb815b567874b5f1fbc7a5e26705b18ecb3a429e931cab65afeb326764d9a44ec977e09aa129b876588aeed4ec98deb1126fa9eefecc94745d22fcab250df86447eb170862608a456f97b6ef520b5daeb9a7353fbbd872a20b0638f5fd5d3fcc8dc727a6ebd26db1b641c69e0fe06ab52dde565daae1dfb0cc8351e9b1671f0fe744444444444407a22e09e11220a55ab54c2d49556429c47ca72c8431fdce506b92a52e93da762e1e2d89e84ec752ade65d7bc9677a6211b2413a429300fcdb220fa6941e8f37a253f191653afe613b11af38cec61cdbcff1b8f53f708fed26dce07c1c97395e4476f5eb08041b8c23b4561d8c757ef6c7896978f7b04c3dfd5905daadde0836b5a8921ebf47a93a2da5d5b90e930eeed2365cc2b1540f9792d98e92fda554bc58054c2125f05bbcd156e39ac74b76e54948db72c99f93b2ac7a926af3796a9d549b9761d57ad94db87e788a0ee61f2609e1127ce545c7cb9b03ba7a7c5a3bcfb73bc4ff794870966726f7d2925c4a2fbb1929ea065bde5f86cda49fc143c58d38b18f5daf8b939a0052a22ed5f8e559cabf092222222222a203559784f0be4e0b022a58ddb7c6a3c3ac642de9adfce9f58d7afb3be57e20e50814664fd5cb2db9ec59f0d80fd5a15a7cb423802535211dba16abcf7fa9e5f6ec0c449059f6001028d7cbe6a610d2236528087d8f11917998daf4398e8dbe8133428fe03fc3b7e3d2c035b0eff89bde3791b46997f027edb9a54ab54c529d5b7adf966aea718faef3e96aefbf5fdda8f71f9b61d355ab25403eb7c1873fab49dab0cb0b8578e8dc1da99a7d66a1030facf5aae714d01d9349156c695b2da477f0124f18ef6f0bea002dedc15fdaec5721da8f37b60670e1823addcb7996cdac7b349776f473d5becb6ac3189996bc945b3a9eab55e790aae9ed91daf0d2fbf91c753f32c93dabafec93a7d6fbf0aebac7fbd634ea17099355c86e493a8d93902d3db04b89b7744227f727ffa63ed81ed02f49e499cbf3956d2bd5bf99f84b893f6f90660ab1eb4c7c1944444444444474a0e89221ca6478ab5755609330286db665c82a0989d256594878db1130e1ec21e3b1a9f2338422cdd5c2b3dc03b0d5761a161abd994b09b304ae5736fb7478f7b7d3c79604af5b8a1a90b5f12620da89deb0036530e55f04985a0754e9b17c7ca6add55066127aa5fdb194e6cb705a52da7d663f07be50a15caa72df30dcad43b204f611e916ddf99b8c6c7ef1c05875f429d9561dc4a5bdb8745c2664b951fd91e5f8b060938d6afcf3aac25087c38d23dcba5db8c8579f725ea9f22f25d823d2acba03bc6fabc3d8e68bea76f7d20c408e25dba593b6550d113d34dc39fd9dea39c5ced192d436785b85e1ab86a4242d0997df4b7e0799a4777799e4be26aad02ca3be4929bd341790b02b55dbe599c92f2d43ad0d57c15fdab7cb4b14f99edc9b0ce5f6dc46bfbe9e55f5117d4d37b4b84779213049ed27f7259f428676937f3f170c7061a8bae71d8126f56c4cba145dd6cb94afce23d5e9e5fc4b6bc3b0a8ed52955ded02e9808e8888888888e840636a9201b8bbc0754beb759beaf648d43ba39f13e7f72ec1074baf41201cab163e30771696d87f87b75578ef0c095e73fa7f00cbeacb8d351d6476c23ce5639544c7182b7e7ca464fff98d3e3c35395d97e0f784c3ff5d8307c7a7ee7a19414444444444f463d425d5d1859458c67bfc4e4692be0c37f65ac5501c3fe109e4ea8eda4cf005ab938efbbd27535498b3d47c6e2c7542d48fa68665c6c28f8b944e4b75ed07d736ea6aed3d15c089888888888828a6cb42f8c42c1b4e2f70ea12eff648087c69b30f77941460dc8827307dc8157a7dbeb3f3e33a0f704411aefec258ea246f8931f3e3232f437e31c88593fbf66c2fe2ff55e4d2edeb8988888888887eccbaac3aba08aa947de74a0f3edcde7e676a7132a6b40c3f766e3f33b2ed569c35af41b7bdee081913fbe9a275185c72b2ba8158876e9d61ea7b014c231f3796888888888888887a469795840ba98e7efbe8545c35d4bdabd3b1f684a3404d308a6c87137d5d764ccd8ef506de1147e7dad1abfa6f7b15c0b5ce74e446444444444444d445ba34840b09e23f1fe8c2cb333271fe00178a522d4855815c7a33976dd29bf6613976dc373e0d8f4d4a478edd8c2d9e08ce2974ea31a2f7a4b7fafe29e9a5c8a8f9bbb1662f987bb62a3611111111111191e8d2eae8c94815f59a60931e375bfa5f73ab349e6635ebe5ea40149e5054b715179bbd113cb5c18b325ff201a9336d66fc7aa00f476f3f0f4eeff7c6dace33f5bb1ca6a1b38d25222222222222a29ed1a525e192a52550cb14527f644ce7809a6c2a7c3bd464922ae82a7897d487b0b1218cfa6073001732fef48dc352716a5f0732d497a4f45cc84791db825b0655e3c88a4bf729806bd60c6386888888888888a8e7ec7549b884675f3816b42570c726402d22ac0eb9afe5eb2a9fa3dc1f415d48427c149303afa170fb3db086771a7bec3dd3883fc294ff736389888888888888a867743a847b55f0ae50e1d8abc2f13ee6ec0eb3073622b5ee03d842db60d5d30e58c2952a9057aacf5ab547f2eaeb4999cc304ffc27907988b182888888888888a867743a84977b23f0a8002e25dffb4f14a668485d7c08e6269f0ee53a9c07b7b508eadbd5b41396482dacd15a98221ea04985756b1accd3be069cfd8d6311111111111111f58cbdaa8eaeab9cefaa7edea4aba6cb41e448f26992f6dff2293bcb677c46917da4dc5a7faa3f720ce9bc4d3e65598edd552c0823d31a40aeddafc2b80ae1c11d680ad7c1d4eb78755156632f222222222222a29ed1edbda37794047909f48108e00947d1108c95b677f6e2a4077687fa93663723dd668223debb1b111111111111d17e76c084f0447251d2febc21a802b951fd3df14275bc567f9c2a74a7a8c0edb69ae052a1db2a499c888888888888e80073c086f04452fd5d6572f52901bd0916930adb2a6b4be066e6262222222222a283c14113c2898888888888880e7666e39388888888888888ba194338111111111111510f610827222222222222ea210ce1444444444444443d02f83fb79dbddd0343dac70000000049454e44ae426082, 8344);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `movimientos`
--

CREATE TABLE `movimientos` (
  `id_movimiento` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` varchar(10) NOT NULL,
  `descripcion` varchar(50) NOT NULL,
  `habia` int(15) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `cantidad` int(10) NOT NULL,
  `existencia` int(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `movimientos`
--

INSERT INTO `movimientos` (`id_movimiento`, `fecha`, `hora`, `descripcion`, `habia`, `tipo`, `cantidad`, `existencia`) VALUES
(1, '2018-12-02', '21:41', 'CUCHARAS DESECHABLES', 25, 'Entrada', 12, 37),
(2, '2018-12-02', '21:43', 'AZUCAR', 0, 'Entrada', 15, 15),
(3, '2018-12-05', '15:01', 'FRIJOL', 35, 'Entrada', 10, 45),
(4, '2018-12-05', '15:03', 'CANELA', 0, 'Entrada', 15, 15),
(5, '2018-12-05', '15:03', 'CANELA', 15, 'Entrada', 10, 25),
(6, '2018-12-05', '15:04', 'CANELA', 25, 'Salida', 1, 24),
(7, '2018-12-05', '15:05', 'BUBU LUBU', 0, 'Entrada', 15, 15),
(8, '2018-12-05', '15:06', 'DONITAS BIMBO', 0, 'Entrada', 20, 20),
(9, '2018-12-05', '15:07', 'SPRITE 3LT', 0, 'Entrada', 2, 2),
(10, '2018-12-05', '15:09', 'MANZANITA 600 ML', 0, 'Entrada', 3, 3),
(11, '2018-12-05', '16:48', 'CHOCOLATE ABUELITA', 0, 'Entrada', 20, 20),
(12, '2018-12-05', '16:48', 'NESTEA', 0, 'Entrada', 5, 5),
(13, '2018-12-05', '16:49', 'BOLOGNA LOGMON', 0, 'Entrada', 25, 25),
(14, '2018-12-05', '16:50', 'SALCHICHA FUD', 0, 'Entrada', 20, 20),
(15, '2018-12-05', '16:50', 'CHORIZO DE PUERCO', 0, 'Entrada', 20, 20),
(16, '2018-12-05', '16:54', 'QUESO AMARILLO', 0, 'Entrada', 20, 20),
(17, '2018-12-05', '16:55', 'CHORIZO DE PUERCO', 20, 'Salida', 1, 19),
(18, '2018-12-05', '16:56', 'DONITAS BIMBO', 17, 'Salida', 1, 16),
(19, '2018-12-05', '16:58', 'SACO DE PAPA', 25, 'Salida', 1, 24),
(20, '2018-12-05', '17:00', 'CHORIZO DE PUERCO', 19, 'Salida', 1, 18),
(21, '2018-12-05', '17:00', 'LATA CHILE CHIPOTLE', 20, 'Entrada', 2, 22),
(22, '2018-12-05', '17:01', 'CUCHARAS DESECHABLES', 37, 'Entrada', 3, 40),
(23, '2018-12-05', '17:01', 'CREMA LALA 1 LT', 10, 'Entrada', 2, 12),
(24, '2018-12-10', '14:52', 'COCA COLA 600 ML', 14, 'Entrada', 10, 24),
(25, '2018-12-10', '14:53', 'DONITAS BIMBO', 16, 'Entrada', 4, 20),
(26, '2018-12-10', '14:53', 'DONITAS BIMBO', 20, 'Salida', 1, 19),
(27, '2018-12-10', '14:53', 'COCA COLA 600 ML', 24, 'Salida', 1, 23),
(28, '2018-12-10', '14:54', 'NESCAFE 500GR', 0, 'Entrada', 5, 5),
(29, '2018-12-10', '14:56', 'TE VERDE ', 0, 'Entrada', 1, 1),
(30, '2018-12-10', '14:57', 'SABRITAS BLANCAS 300 GR', 0, 'Entrada', 2, 2),
(31, '2018-12-10', '15:01', 'LECHE CONDENSADA 1LT', 0, 'Entrada', 5, 5),
(32, '2018-12-10', '15:02', 'DANONINO PAQUETE', 0, 'Entrada', 2, 2),
(33, '2018-12-10', '15:04', 'MANZANITA 600 ML', 3, 'Entrada', 1, 4),
(34, '2018-12-10', '17:15', 'MAZAPAN', 0, 'Entrada', 10, 10),
(35, '2018-12-10', '17:18', 'MAZAPAN', 10, 'Entrada', 50, 60),
(36, '2018-12-10', '17:23', 'CUCHARAS DESECHABLES', 40, 'Entrada', 40, 80),
(37, '2018-12-10', '17:29', 'CUCHARAS DESECHABLES', 80, 'Salida', 1, 79),
(38, '2018-12-10', '17:29', 'MAZAPAN', 60, 'Salida', 1, 59),
(39, '2018-12-10', '17:29', 'MAZAPAN', 60, 'Salida', 1, 59),
(40, '2018-12-11', '13:23', 'GALLETAS ', 0, 'Entrada', 10, 10),
(41, '2018-12-11', '14:06', 'SUBMARINO', 0, 'Entrada', 10, 10),
(42, '2018-12-11', '14:09', 'SIX COCA COLA LATA', 0, 'Entrada', 12, 12),
(43, '2018-12-11', '15:10', 'ROCKALETA', 0, 'Entrada', 1, 1),
(44, '2018-12-11', '15:27', 'ROCKALETA', 1, 'Entrada', 10, 11),
(45, '2018-12-11', '16:10', 'FRIJOL', 45, 'Entrada', 5, 50),
(46, '2018-12-11', '16:25', 'FRIJOL', 45, 'Entrada', 1, 46),
(47, '2018-12-11', '16:25', 'AGUA EPURA 500ML', 81, 'Entrada', 9, 90),
(48, '2018-12-11', '16:30', 'ACEITE 123 1LT', 49, 'Entrada', 1, 50),
(49, '2018-12-11', '16:33', 'LATA ELOTE HERDEZ ', 14, 'Entrada', 1, 15),
(50, '2018-12-11', '17:01', 'FRIJOL', 46, 'Entrada', 1, 47),
(51, '2018-12-12', '13:33', 'COCA COLA 600 ML', 23, 'Salida', 1, 22),
(52, '2018-12-12', '13:33', 'TAKIS FUEGO 300GR', 44, 'Salida', 1, 43),
(53, '2018-12-12', '13:33', 'TAKIS FUEGO 300GR', 44, 'Salida', 1, 43),
(54, '2018-12-12', '14:16', 'COCA COLA 600 ML', 21, 'Salida', 6, 15),
(55, '2018-12-12', '14:19', 'COCA COLA 600 ML', 19, 'Salida', 3, 16),
(56, '2018-12-12', '14:19', 'CONSOME DE POLLO', 13, 'Salida', 1, 12),
(57, '2018-12-12', '14:19', 'CHORIZO DE PUERCO', 18, 'Salida', 3, 15),
(58, '2018-12-12', '14:20', 'LATA ELOTE HERDEZ ', 15, 'Salida', 1, 14),
(59, '2018-12-12', '14:22', 'MAZAPAN', 58, 'Salida', 1, 57),
(60, '2018-12-12', '14:23', 'COCA COLA 600 ML', 18, 'Salida', 1, 17),
(61, '2018-12-12', '14:23', 'TAKIS FUEGO 300GR', 42, 'Salida', 3, 39),
(62, '2018-12-12', '14:40', 'NESCAFE 500GR', 5, 'Salida', 5, 0),
(63, '2018-12-12', '14:47', 'NESCAFE 500GR', 4, 'Salida', 4, 0),
(64, '2018-12-12', '14:49', 'NESCAFE 500GR', 0, 'Salida', 1, -1),
(65, '2018-12-12', '14:54', 'NESCAFE 500GR', -1, 'Entrada', 40, 39),
(66, '2018-12-12', '14:54', 'TE VERDE ', 0, 'Entrada', 30, 30),
(67, '2018-12-12', '14:55', 'TE VERDE ', 30, 'Entrada', 1, 31),
(68, '2018-12-12', '15:07', 'DANONINO PAQUETE', 2, 'Salida', 2, 0),
(69, '2018-12-12', '15:57', 'COCA COLA 600 ML', 17, 'Salida', 1, 16),
(70, '2018-12-12', '15:58', 'COCA COLA 600 ML', 16, 'Salida', 1, 15),
(71, '2018-12-12', '16:17', 'ACEITE 123 1LT', 50, 'Salida', 1, 49),
(72, '2018-12-12', '16:17', 'TAKIS FUEGO 300GR', 41, 'Salida', 1, 40),
(73, '2018-12-12', '16:17', 'ACEITE 123 1LT', 50, 'Salida', 1, 49),
(74, '2018-12-12', '16:25', 'COCA COLA 600 ML', 15, 'Salida', 1, 14),
(75, '2018-12-12', '16:30', 'ACEITE 123 1LT', 48, 'Salida', 1, 47),
(76, '2018-12-12', '16:30', 'TAKIS FUEGO 300GR', 40, 'Salida', 1, 39),
(77, '2018-12-12', '16:34', 'TAKIS FUEGO 300GR', 39, 'Salida', 1, 38),
(78, '2018-12-12', '16:42', 'TAKIS FUEGO 300GR', 38, 'Salida', 2, 36),
(79, '2018-12-12', '16:43', 'AGUA EPURA 500ML', 90, 'Salida', 1, 89),
(80, '2018-12-12', '16:44', 'TAKIS FUEGO 300GR', 36, 'Salida', 1, 35),
(81, '2018-12-12', '16:45', 'COCA COLA 600 ML', 14, 'Salida', 1, 13),
(82, '2018-12-12', '16:45', 'COCA COLA 600 ML', 13, 'Salida', 1, 12),
(83, '2018-12-12', '17:48', 'PAN BARRA BIMBO', 0, 'Entrada', 15, 15),
(84, '2018-12-12', '17:48', 'NESTEA', 5, 'Entrada', 10, 15),
(85, '2018-12-12', '17:49', 'NESTEA', 15, 'Entrada', 8, 23),
(86, '2018-12-12', '17:53', 'NESTEA', 23, 'Salida', 2, 21),
(87, '2018-12-12', '17:53', 'PAN BARRA BIMBO', 15, 'Salida', 5, 10),
(88, '2019-02-04', '14:34', 'NESCAFE 500GR', 39, 'Salida', 6, 33),
(89, '2019-02-11', '16:12', 'SOPA MARUCHAN', 12, 'Salida', 5, 7),
(90, '2019-02-11', '16:14', 'MAZAPAN', 57, 'Salida', 52, 5),
(91, '2019-02-11', '16:15', 'AGUA EPURA 500ML', 89, 'Entrada', 2, 91),
(92, '2019-02-21', '10:47', 'DANONINO PAQUETE', 0, 'Entrada', 10, 10),
(93, '2019-02-21', '10:58', 'CHORIZO DE PUERCO', 17, 'Salida', 5, 12),
(94, '2019-02-21', '11:40', 'PERIODICO', 0, 'Entrada', 1, 1),
(95, '2019-02-21', '11:40', 'PERIODICO', 1, 'Entrada', 2, 3),
(96, '2019-02-21', '11:41', 'PERIODICO', 3, 'Entrada', 1, 4),
(97, '2019-02-22', '11:16', 'MANGAS', 20, 'Entrada', 1, 21),
(98, '2019-02-22', '11:20', 'PERIODICO', 4, 'Salida', 3, 1),
(99, '2019-02-22', '11:55', 'MANGAS', 21, 'Salida', 21, 0),
(100, '2019-02-22', '11:55', 'MANGAS', 0, 'Entrada', 20, 20),
(101, '2019-03-25', '09:51', 'AGUA EPURA 500ML', 91, 'Entrada', 10, 101),
(102, '2019-03-25', '10:09', 'LATA CHILE CHIPOTLE', 22, 'Salida', 6, 16),
(103, '2019-05-05', '11:29', 'SABRITAS BLANCAS 300 GR', 2, 'Salida', 1, 1),
(104, '2019-05-05', '11:30', 'COCA COLA 600 ML', 12, 'Entrada', 50, 62),
(105, '2019-05-05', '12:47', 'COCA COLA 600 ML', 62, 'Salida', 5, 57),
(106, '2019-05-05', '12:48', 'LATA ELOTE HERDEZ ', 14, 'Salida', 1, 13),
(107, '2019-05-05', '12:50', 'LATA ELOTE HERDEZ ', 13, 'Salida', 1, 12),
(108, '2019-05-05', '12:50', 'AGUA EPURA 1LT', 101, 'Salida', 13, 88),
(109, '2019-05-05', '12:54', 'LATA ELOTE HERDEZ ', 12, 'Salida', 1, 11),
(110, '2019-05-05', '12:54', 'ACEITE 123 1LT', 47, 'Salida', 1, 46),
(111, '2019-05-05', '14:55', 'CONSOME DE POLLO', 12, 'Salida', 4, 8),
(112, '2019-05-05', '14:59', 'COCA COLA 600 ML', 57, 'Salida', 1, 56),
(113, '2019-05-05', '14:59', 'CONSOME DE POLLO', 8, 'Salida', 5, 3),
(114, '2019-05-05', '15:02', 'CONSOME DE POLLO', 3, 'Salida', 1, 2),
(115, '2019-05-05', '15:02', 'TAKIS FUEGO 300GR', 35, 'Salida', 1, 34),
(116, '2019-05-05', '15:05', 'CUCHARAS DESECHABLES', 79, 'Salida', 4, 75),
(117, '2019-05-05', '15:05', 'NESTEA', 18, 'Salida', 1, 17),
(118, '2019-05-05', '15:08', 'CHORIZO DE PUERCO', 12, 'Salida', 3, 9),
(119, '2019-05-05', '15:08', 'CANELA', 24, 'Salida', 4, 20),
(120, '2019-05-05', '17:16', 'FRIJOL', 47, 'Entrada', 1, 48),
(121, '2019-05-05', '17:20', 'AGUA EPURA 1LT', 88, 'Entrada', 2, 90),
(122, '2019-06-01', '13:43', 'CHORIZO DE PUERCO', 8, 'Salida', 7, 1),
(123, '2019-06-01', '13:51', 'AGUA EPURA 1LT', 90, 'Entrada', 10, 100),
(124, '2019-06-01', '13:51', 'NESCAFE 500GR', 33, 'Salida', 1, 32),
(125, '2019-06-01', '14:03', 'LATA ELOTE HERDEZ ', 11, 'Entrada', 2, 13),
(126, '2019-06-01', '14:05', 'SACO DE PAPA', 24, 'Entrada', 1, 25),
(127, '2019-06-01', '14:17', 'CHORIZO DE PUERCO', 1, 'Entrada', 1, 0),
(128, '2019-06-01', '14:19', 'SACO DE PAPA', 25, 'Salida', 1, 26),
(129, '2019-06-01', '14:20', 'QUESO AMARILLO', 20, 'Entrada', 1, 19),
(130, '2019-06-01', '14:22', 'MANZANITA 600 ML', 4, 'Entrada', 3, 1),
(131, '2019-06-01', '14:23', 'CHOCOLATE ABUELITA', 20, 'Salida', 1, 21),
(132, '2019-06-01', '14:27', 'NESCAFE 500GR', 32, 'Entrada', 1, 31),
(133, '2019-06-01', '14:28', 'BOLOGNA LOGMON', 25, 'Salida', 2, 27),
(134, '2019-06-01', '14:31', 'QUESO AMARILLO', 19, 'Entrada', 3, 16),
(135, '2019-06-01', '15:21', 'COCA COLA 600 ML', 52, 'Entrada', 1, 51),
(136, '2019-06-01', '15:25', 'COCA COLA 600 ML', 51, 'Entrada', 1, 50),
(137, '2019-06-01', '15:28', 'COCA COLA 600 ML', 50, 'Entrada', 8, 42),
(138, '2019-06-01', '15:29', 'COCA COLA 600 ML', 42, 'Entrada', 1, 41),
(139, '2019-06-01', '15:32', 'ACEITE 123 1LT', 46, 'Entrada', 1, 45),
(140, '2019-06-01', '15:34', 'TAKIS FUEGO 300GR', 34, 'Entrada', 1, 33),
(141, '2019-06-01', '15:37', 'LATA ELOTE HERDEZ ', 13, 'Entrada', 1, 12),
(142, '2019-06-01', '15:45', 'COCA COLA 600 ML', 41, 'Entrada', 1, 40),
(143, '2019-06-01', '16:33', 'COCA COLA 600 ML', 40, 'Entrada', 16, 24),
(144, '2019-06-01', '16:35', 'CONSOME DE POLLO', 2, 'Salida', 10, 12),
(145, '2019-06-04', '12:02', 'NESCAFE 500GR', 31, 'Entrada', 1, 30),
(146, '2019-06-04', '12:02', 'CUCHARAS DESECHABLES', 78, 'Entrada', 5, 73),
(147, '2019-06-04', '12:02', 'CHOCOLATE ABUELITA', 21, 'Salida', 15, 36),
(148, '2019-06-04', '12:03', 'NESCAFE 500GR', 26, 'Entrada', 5, 21),
(149, '2019-06-04', '12:44', 'BOLOGNA LOGMON', 27, 'Entrada', 1, 26),
(150, '2019-06-04', '12:45', 'CONSOME DE POLLO', 12, 'Salida', 5, 17);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id_venta` int(11) NOT NULL,
  `total` float NOT NULL,
  `fecha` date NOT NULL,
  `usuario` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id_venta`, `total`, `fecha`, `usuario`) VALUES
(1, 30, '2018-01-01', 'TipoUsuario'),
(2, 22.5, '2018-01-01', 'TipoUsuario'),
(3, 7.5, '2018-01-01', 'TipoUsuario'),
(4, 27, '2018-01-01', 'TipoUsuario'),
(5, 34.5, '2018-01-01', 'TipoUsuario'),
(6, 22.5, '2018-01-01', 'TipoUsuario'),
(7, 22.5, '2018-01-01', 'TipoUsuario'),
(8, 22.5, '2018-12-01', 'Administrador'),
(9, 22.5, '2018-12-01', 'Administrador'),
(10, 22.5, '2018-12-01', 'Administrador'),
(11, 22.5, '2018-12-01', 'Administrador'),
(12, 22.5, '2018-12-02', 'Administrador'),
(13, 45, '2018-12-02', 'Administrador'),
(14, 22.5, '2018-12-02', 'Administrador'),
(15, 45, '2018-12-02', 'Administrador'),
(16, 22.5, '2018-12-05', 'Administrador'),
(17, 24, '2018-12-05', 'Administrador'),
(18, 22.5, '2018-12-05', 'Administrador'),
(19, 22.5, '2018-12-05', 'Administrador'),
(20, 22.5, '2018-12-05', 'Administrador'),
(21, 22.5, '2018-12-05', 'Administrador'),
(22, 9, '2018-12-05', 'Administrador'),
(23, 22.5, '2018-12-05', 'Administrador'),
(24, 54, '2018-12-05', 'Administrador'),
(25, 54, '2018-12-05', 'Administrador'),
(26, 24, '2018-12-05', 'Administrador'),
(27, 45, '2018-12-10', 'Administrador'),
(28, 31.5, '2018-12-10', 'Administrador'),
(29, 67.5, '2018-12-10', 'Administrador'),
(30, 36, '2018-12-10', 'Administrador'),
(31, 67.5, '2018-12-12', 'Administrador'),
(32, 157.5, '2018-12-12', 'Administrador'),
(33, 45, '2018-12-12', 'Administrador'),
(34, 67.5, '2018-12-12', 'Administrador'),
(35, 135, '2018-12-12', 'Administrador'),
(36, 63, '2018-12-12', 'Administrador'),
(37, 148.5, '2018-12-12', 'Administrador'),
(38, 27, '2018-12-12', 'Administrador'),
(39, 15, '2018-12-12', 'Administrador'),
(40, 22.5, '2018-12-12', 'Administrador'),
(41, 67.5, '2018-12-12', 'Administrador'),
(42, 172.5, '2018-12-12', 'Administrador'),
(43, 138, '2018-12-12', 'Administrador'),
(44, 34.5, '2018-12-12', 'Administrador'),
(45, 90, '2018-12-12', 'Administrador'),
(46, 22.5, '2018-12-12', 'Administrador'),
(47, 22.5, '2018-12-12', 'Administrador'),
(48, 67.5, '2018-12-12', 'Administrador'),
(49, 22.5, '2018-12-12', 'Administrador'),
(50, 45, '2018-12-12', 'Administrador'),
(51, 22.5, '2018-12-12', 'Administrador'),
(52, 45, '2018-12-12', 'Administrador'),
(53, 7.5, '2018-12-12', 'Administrador'),
(54, 22.5, '2018-12-12', 'Administrador'),
(55, 22.5, '2018-12-12', 'Administrador'),
(56, 22.5, '2018-12-12', 'Administrador'),
(57, 78, '2018-12-12', 'Administrador'),
(58, 207, '2019-02-04', 'Administrador'),
(59, 135, '2019-02-11', 'Administrador'),
(60, 780, '2019-02-11', 'Administrador'),
(61, 120, '2019-02-21', 'Administrador'),
(62, 81, '2019-02-22', 'Administrador'),
(63, 1575, '2019-02-22', 'Administrador'),
(64, 162, '2019-03-25', 'Admin'),
(65, 21, '2019-05-05', 'Admin'),
(66, 112.5, '2019-05-05', 'Admin'),
(67, 27, '2019-05-05', 'Admin'),
(68, 27, '2019-05-05', 'Admin'),
(69, 97.5, '2019-05-05', 'Admin'),
(70, 27, '2019-05-05', 'Admin'),
(71, 22.5, '2019-05-05', 'Admin'),
(72, 36, '2019-05-05', 'Admin'),
(73, 67.5, '2019-05-05', 'Admin'),
(74, 31.5, '2019-05-05', 'Admin'),
(75, 159, '2019-05-05', 'Admin'),
(76, 162, '2019-05-05', 'Admin'),
(77, 168, '2019-06-01', 'Admin'),
(78, 34.5, '2019-06-01', 'Admin'),
(79, 24, '2019-06-01', 'Admin'),
(80, 22.5, '2019-06-01', 'Admin'),
(81, 58.5, '2019-06-01', 'Admin'),
(82, 34.5, '2019-06-01', 'Admin'),
(83, 67.5, '2019-06-01', 'Admin'),
(84, 22.5, '2019-06-01', 'Admin'),
(85, 22.5, '2019-06-01', 'Admin'),
(86, 180, '2019-06-01', 'Admin'),
(87, 22.5, '2019-06-01', 'Admin'),
(88, 22.5, '2019-06-01', 'Admin'),
(89, 22.5, '2019-06-01', 'Admin'),
(90, 27, '2019-06-01', 'Admin'),
(91, 22.5, '2019-06-01', 'Admin'),
(92, 360, '2019-06-01', 'Admin'),
(93, 222, '2019-06-04', 'Admin'),
(94, 172.5, '2019-06-04', 'Admin'),
(95, 37.5, '2019-06-04', 'Admin');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD PRIMARY KEY (`id_productos`),
  ADD KEY `id_departamentos` (`id_departamentos`);

--
-- Indices de la tabla `corte_temporal`
--
ALTER TABLE `corte_temporal`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `corte_totales`
--
ALTER TABLE `corte_totales`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `corte_turno`
--
ALTER TABLE `corte_turno`
  ADD PRIMARY KEY (`Codigo`);

--
-- Indices de la tabla `departamentos`
--
ALTER TABLE `departamentos`
  ADD PRIMARY KEY (`id_departamentos`);

--
-- Indices de la tabla `detalles_venta`
--
ALTER TABLE `detalles_venta`
  ADD KEY `fk_id_ventas` (`id_venta`);

--
-- Indices de la tabla `inventario_productos`
--
ALTER TABLE `inventario_productos`
  ADD KEY `id_productos` (`id_productos`);

--
-- Indices de la tabla `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id_empleado`);

--
-- Indices de la tabla `logotipo`
--
ALTER TABLE `logotipo`
  ADD PRIMARY KEY (`id_logotipo`);

--
-- Indices de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  ADD PRIMARY KEY (`id_movimiento`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id_venta`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `corte_temporal`
--
ALTER TABLE `corte_temporal`
  MODIFY `Codigo` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `corte_totales`
--
ALTER TABLE `corte_totales`
  MODIFY `Codigo` int(50) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `corte_turno`
--
ALTER TABLE `corte_turno`
  MODIFY `Codigo` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `login`
--
ALTER TABLE `login`
  MODIFY `id_empleado` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `logotipo`
--
ALTER TABLE `logotipo`
  MODIFY `id_logotipo` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `id_movimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=151;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `articulos`
--
ALTER TABLE `articulos`
  ADD CONSTRAINT `articulos_ibfk_1` FOREIGN KEY (`id_departamentos`) REFERENCES `departamentos` (`id_departamentos`);

--
-- Filtros para la tabla `detalles_venta`
--
ALTER TABLE `detalles_venta`
  ADD CONSTRAINT `fk_id_ventas` FOREIGN KEY (`id_venta`) REFERENCES `ventas` (`id_venta`);

--
-- Filtros para la tabla `inventario_productos`
--
ALTER TABLE `inventario_productos`
  ADD CONSTRAINT `inventario_productos_ibfk_1` FOREIGN KEY (`id_productos`) REFERENCES `articulos` (`id_productos`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
