-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-07-2024 a las 00:07:22
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemappizza`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` varchar(10) NOT NULL,
  `dni` int(20) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `ApellidoMa` varchar(150) NOT NULL,
  `ApellidoPa` varchar(150) NOT NULL,
  `Telefono` int(15) NOT NULL,
  `Fecha` datetime NOT NULL DEFAULT current_timestamp(),
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `dni`, `nombre`, `ApellidoMa`, `ApellidoPa`, `Telefono`, `Fecha`, `activo`) VALUES
('CLI001', 98989898, 'Favio', 'Linares', 'Quezada', 951236781, '2024-06-14 19:57:56', 1),
('CLI002', 12549876, 'Xavi', 'Fernandez', 'Rodriguez', 465987421, '2024-06-14 19:58:05', 0),
('CLI004', 6542398, 'Arturo', 'Raymondi', 'Soller', 136458987, '2024-06-19 19:02:05', 1),
('CLI005', 69251487, 'Nick', 'Gonzales', 'Uchasara', 951123654, '2024-06-20 10:21:51', 1),
('CLI006', 46597412, 'Alonzo', 'sanchez', 'gomez', 987236541, '2024-06-20 11:05:58', 1),
('CLI007', 12345678, 'Jackelin', 'Perez', 'Gutierrez', 951748362, '2024-06-21 08:44:56', 0),
('CLI008', 12313131, 'Joseph', 'Diaz', 'Diestra', 995563121, '2024-07-15 12:42:38', 0),
('CLI009', 64591237, 'Nicolle', 'Yactayo', 'Aguirre', 984214631, '2024-07-15 12:55:05', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle`
--

CREATE TABLE `detalle` (
  `id` int(11) NOT NULL,
  `cod_pro` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `id_ve` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `detalle`
--

INSERT INTO `detalle` (`id`, `cod_pro`, `cantidad`, `precio`, `id_ve`) VALUES
(1, 112223, 1, 17.50, 1),
(2, 112223, 1, 17.50, 2),
(3, 22113, 1, 25.70, 3),
(4, 22111, 2, 32.20, 4),
(5, 22113, 1, 25.70, 4),
(6, 22113, 1, 25.70, 1),
(7, 312, 32, 32.00, 2),
(8, 321423, 1, 1231.00, 3),
(9, 312, 1, 32.00, 4),
(10, 4515, 14, 34.10, 5),
(11, 4515, 1, 34.10, 5),
(12, 321423, 1, 1231.00, 6),
(13, 4515, 1, 34.10, 7),
(14, 312, 1, 32.00, 8),
(15, 312, 2, 32.00, 9),
(16, 312, 2, 32.00, 10),
(17, 4515, 2, 34.10, 11),
(18, 4515, 2, 34.10, 12),
(19, 4515, 14, 34.10, 13),
(20, 4515, 1, 34.10, 14),
(21, 4515, 2, 34.10, 15),
(22, 12313, 2, 15.79, 16);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `cliente` varchar(100) NOT NULL,
  `vendedor` varchar(100) NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id`, `cliente`, `vendedor`, `total`, `fecha`) VALUES
(1, 'Luis Alberto', 'PpizzaSAC', 25.70, '2023-09-11 06:25:04'),
(2, 'qweq', 'PpizzaSAC', 124.00, '2024-06-14 20:33:57'),
(3, 'ghhg', '', 121.00, '2024-06-19 19:02:17'),
(4, 'ghhg', '', 32.00, '2024-06-19 19:03:36'),
(5, 'ghhg', '   Juan Pedro \n\n-Administrador', 51.50, '2024-06-20 10:17:49'),
(6, 'arturo', '   Juan Pedro \n\n-Administrador', 1231.00, '2024-06-20 11:06:16'),
(7, 'qweq', '   Juan Pedro \n\n-Administrador', 34.10, '2024-06-20 13:35:16'),
(8, 'Favio', '   Juan Pedro \n\n-Administrador', 32.00, '2024-06-20 15:30:40'),
(9, 'Favio', '   Juan Pedro \n\n-Administrador', 64.00, '2024-06-20 15:43:28'),
(10, 'weqe', '   Juan Pedro \n\n-Administrador', 64.00, '2024-06-20 15:49:27'),
(11, 'arturo', '   Juan Pedro \n\n-Administrador', 68.20, '2024-06-20 16:07:31'),
(12, 'Jack', '   Juan Pedro \n\n-Administrador', 68.20, '2024-06-21 08:45:24'),
(13, 'Jack', '   Marc Caceres \n\n-Asistente', 477.40, '2024-06-21 08:50:33'),
(14, 'Jack', '   Marc Caceres \n\n-Asistente', 34.10, '2024-06-21 08:51:58'),
(15, 'Favio', '   Juan Pedro \n\n - Administrador', 68.20, '2024-06-21 12:23:34'),
(16, 'Nicolle', '   Juan Pedro \n\n - Administrador', 31.58, '2024-07-17 09:22:27');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `id` varchar(11) NOT NULL,
  `desc_Prod` varchar(100) NOT NULL,
  `codigo` varchar(30) NOT NULL,
  `tipo_Pizza` varchar(100) NOT NULL,
  `tama_Pizz` varchar(100) NOT NULL,
  `stock` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `fecha` datetime NOT NULL DEFAULT current_timestamp(),
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`id`, `desc_Prod`, `codigo`, `tipo_Pizza`, `tama_Pizz`, `stock`, `precio`, `fecha`, `activo`) VALUES
('PRO002', 'ewq', '321423', 'Americana', 'Personal', 0, 1231.00, '2024-06-14 20:33:39', 1),
('PRO004', 'pizza hawaina grande', '12313', 'Hawaiana', 'Grande', 48, 15.79, '2024-06-21 12:26:31', 1),
('PRO005', 'qweqewq', '31231', 'Americana', 'Personal', 32, 32.00, '2024-07-15 13:01:49', 1),
('PRO006', 'wrqrqwr', '4312341', 'Peperoni', 'Mediana', 32, 123.00, '2024-07-15 13:01:58', 0),
('PRO008', 'qweqwee', '312333', 'Americana', 'Personal', 12, 23.00, '2024-07-15 13:41:02', 0),
('PRO009', 'qweqe', '3212', 'Americana', 'Personal', 43, 12.00, '2024-07-15 13:44:12', 0),
('PRO010', 'qeweq', '3213', 'Americana', 'Personal', 32, 321.00, '2024-07-15 13:49:36', 0),
('PRO012', 'qqq', '12322', 'Americana', 'Personal', 43, 43.00, '2024-07-15 13:55:29', 0),
('PRO013', 'tgrdttsdf', '321231', 'Americana', 'Personal', 32, 32.00, '2024-07-15 13:57:45', 0),
('PRO014', 'aaaa', '33323', 'Peperoni', 'Mediana', 23, 12.00, '2024-07-17 09:48:55', 0),
('PRO015', 'asfasf', '123', 'Peperoni', 'Mediana', 23, 12.00, '2024-07-17 09:50:08', 0),
('PRO016', 'pruebados', '1233', 'Americana', 'Personal', 23, 12.00, '2024-07-17 09:51:45', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `ID` int(11) NOT NULL,
  `Nombre` varchar(100) NOT NULL,
  `Correo` varchar(100) NOT NULL,
  `Pass` varchar(100) NOT NULL,
  `rol` varchar(20) NOT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`ID`, `Nombre`, `Correo`, `Pass`, `rol`, `activo`) VALUES
(1, 'Juan Pedro', 'jp123@gmail.com', '1234', 'Administrador', 1),
(5, 'DWD', 'jp123DE@gmail.com', 'DEDEW', 'Administrador', 0),
(6, 'Marc Caceres', 'marC1958@gmail.com', 'abcde', 'Asistente', 0),
(7, 'fsdf', 'dsfhshdfdsfdf', 'fsdf', 'Asistente', 0),
(8, 'juan', 'xxx@gmail.com', '12345678', 'Asistente', 0),
(9, 'qweqeqeq', 'fdfas@gmail.com', '123124124', 'Administrador', 0),
(10, 'Rodrigo Hernadez', 'Rodrnadez@gmail.com', '12345678', 'Asistente', 1),
(11, 'Jaden Ochoa ', 'Jaden15@ppizza.pe', 'J159ws1414', 'Asistente', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalle`
--
ALTER TABLE `detalle`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `detalle`
--
ALTER TABLE `detalle`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
