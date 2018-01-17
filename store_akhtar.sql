-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 17, 2018 at 02:40 PM
-- Server version: 10.1.25-MariaDB
-- PHP Version: 7.1.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `store_akhtar`
--

-- --------------------------------------------------------

--
-- Table structure for table `mstr_customer`
--

CREATE TABLE `mstr_customer` (
  `cust_id` varchar(32) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `telp` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mstr_customer`
--

INSERT INTO `mstr_customer` (`cust_id`, `nama`, `alamat`, `telp`) VALUES
('1', 'Muhammad Aditya', 'Jln . Teluk Tiram', '082828822'),
('12', 'Doe', 'Jln. Adhyaksa', '09889892'),
('23', 'Julia', 'ln julia', '90291');

-- --------------------------------------------------------

--
-- Table structure for table `mstr_product`
--

CREATE TABLE `mstr_product` (
  `prd_id` varchar(32) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `satuan` varchar(15) NOT NULL,
  `harga` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mstr_product`
--

INSERT INTO `mstr_product` (`prd_id`, `nama`, `satuan`, `harga`) VALUES
('1', 'Buku', '1', 10000),
('2', 'Penggaris', '1', 5000),
('3', 'Pensil', '1', 2000),
('4', 'Penghapus', '4', 2000),
('90', 'Pulpen', '8', 9000);

-- --------------------------------------------------------

--
-- Table structure for table `mstr_user`
--

CREATE TABLE `mstr_user` (
  `user_id` varchar(32) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jenis_kelamin` varchar(20) NOT NULL,
  `tempat_lahir` varchar(50) NOT NULL,
  `tanggal_lahir` date NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `password` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mstr_user`
--

INSERT INTO `mstr_user` (`user_id`, `nama`, `jenis_kelamin`, `tempat_lahir`, `tanggal_lahir`, `alamat`, `password`) VALUES
('1', 'M Aditya', 'Laki-Laki', 'Banjarmasin', '2017-12-05', 'Jln. Ampera Update', '[C@18fe008'),
('4', 'Aditya', 'Laki-Laki', 'Banjarmasin', '2017-12-05', 'Jln . Teluk', '123'),
('5', 'Edy Rahman', 'Laki-Laki', 'Kandangan', '2018-12-27', 'Jl.bbc', '[C@1faf05b'),
('8', 'agus', 'Laki-Laki', 'agusol', '2017-12-06', 'asksd', '[C@747edc');

-- --------------------------------------------------------

--
-- Table structure for table `ttrs_penjualan`
--

CREATE TABLE `ttrs_penjualan` (
  `faktur` varchar(32) NOT NULL,
  `tanggal` date NOT NULL,
  `cust_id` varchar(32) NOT NULL,
  `user_id` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ttrs_penjualan`
--

INSERT INTO `ttrs_penjualan` (`faktur`, `tanggal`, `cust_id`, `user_id`) VALUES
('1001', '2018-01-10', '1', ''),
('1008', '2018-01-14', '1', ''),
('1023', '2018-01-10', '12', 'U1'),
('12', '2017-12-28', '23', 'U1'),
('13', '2017-12-28', '12', 'U1'),
('2', '2017-12-23', '12', 'U1'),
('28', '2017-12-28', '12', ''),
('32', '2018-01-10', '1', ''),
('34', '2018-01-10', '12', 'U1'),
('39', '2018-01-10', '1', ''),
('45', '2018-01-10', '1', ''),
('5', '2017-12-23', '23', 'U1'),
('777', '2018-01-03', '1', 'U1'),
('90', '2017-12-23', '23', 'U1');

-- --------------------------------------------------------

--
-- Table structure for table `ttrs_penjualan_detail`
--

CREATE TABLE `ttrs_penjualan_detail` (
  `faktur` varchar(32) NOT NULL,
  `prd_id` varchar(32) NOT NULL,
  `qty` double NOT NULL,
  `diskon` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ttrs_penjualan_detail`
--

INSERT INTO `ttrs_penjualan_detail` (`faktur`, `prd_id`, `qty`, `diskon`) VALUES
('11', '90', 2, 0),
('11', '90', 2, 0),
('11', '90', 0, 0),
('11', '1', 0, 0),
('7', '1', 1, 0),
('23', '90', 2, 0),
('21', '3', 1, 0),
('31', '1', 2, 0),
('21', '1', 0, 0),
('21', '1', 4, 0),
('21', '1', 4, 0),
('23', '90', 3, 0),
('1', '1', 3, 0),
('1', '2', 3, 0),
('1', '1', 4, 0),
('48', '1', 4, 0),
('48', '1', 5, 0),
('1', '3', 3, 0),
('1008', '2', 3, 0),
('1001', '2', 10, 0),
('1023', '2', 2, 0),
('1023', '2', 3, 0),
('1001', '1', 10, 0),
('1008', '1', 5, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mstr_customer`
--
ALTER TABLE `mstr_customer`
  ADD PRIMARY KEY (`cust_id`);

--
-- Indexes for table `mstr_product`
--
ALTER TABLE `mstr_product`
  ADD PRIMARY KEY (`prd_id`);

--
-- Indexes for table `mstr_user`
--
ALTER TABLE `mstr_user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `ttrs_penjualan`
--
ALTER TABLE `ttrs_penjualan`
  ADD PRIMARY KEY (`faktur`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
