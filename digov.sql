-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 02-12-2018 a las 22:47:07
-- Versión del servidor: 5.7.23
-- Versión de PHP: 7.1.22

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
(4, 'AGUA EPURA 500ML', 'Por Unidad', 5, 7.5, 1),
(5, 'LATA ELOTE HERDEZ ', 'Por Unidad', 18, 27, 1),
(6, 'FRIJOL', 'Por Unidad', 18, 27, 1),
(7, 'ARROZ', 'Por Unidad', 18, 27, 1),
(8, 'SACO DE PAPA', 'Por Unidad', 15, 22.5, 1),
(12, 'SOPA MARUCHAN', 'Por Unidad', 18, 27, 1),
(13, 'LATA CHILE CHIPOTLE', 'Por Unidad', 18, 27, 1),
(14, 'CREMA LALA 1 LT', 'Por Unidad', 15, 22.5, 1),
(15, 'CONSOME DE POLLO', 'Por Unidad', 6, 9, 1),
(16, 'SALCHICHA PARA ASAR ', 'Por Unidad', 50, 75, 4),
(18, 'PLATOS DESECHABLES PKT 50', 'Paquete', 18, 25, 14),
(20, 'CUCHARAS DESECHABLES', 'Paquete', 25, 37.5, 1);

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
(14, 'Desechables');

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
(15, 14, 'CREMA LALA 1 LT', 1, 22.5);

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
(4, 81, 5),
(1, 16, 5),
(2, 49, 5),
(3, 44, 5),
(5, 14, 15),
(6, 35, 5),
(8, 25, 1),
(12, 12, 3),
(13, 20, 2),
(14, 10, 5),
(15, 15, 2),
(16, 50, 5),
(20, 37, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login`
--

CREATE TABLE `login` (
  `id_empleado` int(10) NOT NULL,
  `usuario` varchar(50) NOT NULL,
  `contraseña` varchar(50) NOT NULL,
  `tipoUsuario` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `login`
--

INSERT INTO `login` (`id_empleado`, `usuario`, `contraseña`, `tipoUsuario`) VALUES
(1, 'Admin', '12345', 'Administrador'),
(2, 'Socorro', '12345', 'Cajero Matutino');

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
(2, '2018-12-02', '21:43', 'AZUCAR', 0, 'Entrada', 15, 15);

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
(15, 45, '2018-12-02', 'Administrador');

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
-- AUTO_INCREMENT de la tabla `movimientos`
--
ALTER TABLE `movimientos`
  MODIFY `id_movimiento` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

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
