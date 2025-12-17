-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 17-12-2025 a las 09:35:57
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdzapatos`
--

DELIMITER $$
--
-- Funciones
--
CREATE DEFINER=`root`@`localhost` FUNCTION `total_zapatos` () RETURNS INT(11) DETERMINISTIC BEGIN
    DECLARE total INT;
    SELECT COUNT(*) INTO total FROM zapato;
    RETURN total;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zapato`
--

CREATE TABLE `zapato` (
  `id` int(11) NOT NULL,
  `marca` varchar(50) DEFAULT NULL,
  `modelo` varchar(50) DEFAULT NULL,
  `tamano` varchar(10) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `precio` decimal(10,2) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `zapato`
--

INSERT INTO `zapato` (`id`, `marca`, `modelo`, `tamano`, `color`, `stock`, `precio`, `descripcion`) VALUES
(1, 'Nike', 'Air Max', '42', 'Rojo', 20, 101.99, NULL),
(2, 'Adidas', 'Ultraboost', '44', 'Negro', 15, 129.99, NULL),
(3, 'Puma', 'RS-X', '43', 'Blanco', 10, 89.99, NULL),
(4, 'Reebok', 'Classic', '41', 'Azul', 12, 74.99, NULL),
(5, 'Converse', 'Chuck Taylor', '40', 'Negro', 25, 59.99, NULL),
(6, 'New Balance', '574', '42', 'Gris', 8, 79.99, NULL),
(7, 'Vans', 'Old Skool', '41', 'Blanco', 18, 69.99, NULL),
(8, 'Fila', 'Disruptor', '44', 'Rosa', 5, 109.99, NULL),
(9, 'Under Armour', 'HOVR Sonic', '43', 'Verde', 7, 119.99, NULL),
(10, 'Asics', 'Gel Kayano', '42', 'Amarillo', 9, 139.99, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `zapato`
--
ALTER TABLE `zapato`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `zapato`
--
ALTER TABLE `zapato`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
