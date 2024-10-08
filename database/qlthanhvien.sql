-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th10 08, 2024 lúc 03:00 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `qlthanhvien`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `passwordresettoken`
--

CREATE TABLE `passwordresettoken` (
  `id` int(11) NOT NULL,
  `expiryDateTime` datetime(6) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `memberID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thanhvien`
--

CREATE TABLE `thanhvien` (
  `MaTV` int(11) NOT NULL,
  `Hoten` varchar(100) NOT NULL,
  `Khoa` varchar(100) DEFAULT NULL,
  `Nganh` varchar(100) DEFAULT NULL,
  `SDT` varchar(15) DEFAULT NULL,
  `Password` varchar(20) DEFAULT NULL,
  `Email` varchar(250) DEFAULT NULL,
  `reset_password_token` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thanhvien`
--

INSERT INTO `thanhvien` (`MaTV`, `Hoten`, `Khoa`, `Nganh`, `SDT`, `Password`, `Email`, `reset_password_token`) VALUES
(123456, 'Administrator', NULL, NULL, NULL, 'admin@123', 'administrator@gmail.com', NULL),
(1190308010, 'Bùi Đình Thái My', 'SP KHTN', 'Sư phạm Lí', '0911210200', '123', '1190308010@gmail.com', NULL),
(1190411014, 'Lê Xuân Nam', 'SP KHXH', 'Sư phạm Văn', '0911220900', '1190411014', '1190411014@gmail.com', NULL),
(1190412013, 'Phạm Thị Lan Khôi', 'SP KHXH', 'Sư phạm Địa', '0911211100', '1190412013', '1190412013@gmail.com', NULL),
(1190413011, 'Trương Hoài Nga', 'SP KHXH', 'Sư phạm Sử', '0911211000', '1190413011', '1190413011@gmail.com', NULL),
(1190413012, 'Nguyễn Đắc Phương Linh', 'SP KHXH', 'Sư phạm Sử', '0911211000', '1190413012', '1190413012@gmail.com', NULL),
(1200104043, 'Phạm Thị Y', 'CNTT', 'Kỹ thuật máy tính', '0123456793', '1200104043', '1200104043@gmail.com', NULL),
(1200104044, 'Ngô Văn Z', 'CNTT', 'Kỹ thuật máy tính', '0123456794', '1200104044', '1200104044@gmail.com', NULL),
(1200104045, 'Nguyễn Thị AA', 'CNTT', 'Kỹ thuật máy tính', '0123456787', '1200104045', '1200104045@gmail.com', NULL),
(1200205046, 'Trần Thị BB', 'Ngoại ngữ', 'Sư phạm Anh', '0123456804', '1200205046', '1200205046@gmail.com', NULL),
(1200205047, 'Lê Văn CC', 'Ngoại ngữ', 'Sư phạm Anh', '0123456805', '1200205047', '1200205047@gmail.com', NULL),
(1200205048, 'Phạm Thị DD', 'Ngoại ngữ', 'Sư phạm Anh', '0123456806', '1200205048', '1200205048@gmail.com', NULL),
(1200205049, 'Ngô Văn EE', 'Ngoại ngữ', 'Sư phạm Anh', '0123456807', '1200205049', '1200205049@gmail.com', NULL),
(1200206050, 'Đặng Thị FF', 'Ngoại ngữ', 'Ngôn ngữ Anh', '0123456808', '1200206050', '1200206050@gmail.com', NULL),
(1200206051, 'Nguyễn Văn GG', 'Ngoại ngữ', 'Ngôn ngữ Anh', '0123456809', '1200206051', '1200206051@gmail.com', NULL),
(1200206052, 'Trần Thị HH', 'Ngoại ngữ', 'Ngôn ngữ Anh', '0123456810', '1200206052', '1200206052@gmail.com', NULL),
(1200206053, 'Lê Văn II', 'Ngoại ngữ', 'Ngôn ngữ Anh', '0123456811', '1200206053', '1200206053@gmail.com', NULL),
(1200308002, 'Chu Phúc Ngọc', 'SP KHTN', 'Sư phạm Lí', '0911200200', '1200308002', '1200308002@gmail.com', NULL),
(1200309031, 'Nguyễn Văn M', 'SP KHTN', 'Sư phạm Hóa', '0123456801', '1200309031', '1200309031@gmail.com', NULL),
(1200411003, 'Nguyễn Thị Mỹ Ngân', 'SP KHXH', 'Sư phạm Văn', '0911200900', '1200411003', '1200411003@gmail.com', NULL),
(1200411015, 'Nguyễn Mỹ Ngân', 'SP KHXH', 'Sư phạm Văn', '0911220900', '1200411015', '1200411015@gmail.com', NULL),
(1200411016, 'Nguyễn Thủy Triều Ngọc', 'SP KHXH', 'Sư phạm Văn', '0911220900', '1200411016', '1200411016@gmail.com', NULL),
(1200411022, 'Phạm Thị D', 'SP KHXH', 'Sư phạm Văn', '0123456792', '1200411022', '1200411022@gmail.com', NULL),
(1200514001, 'Nguyễn Mai Ngọc', 'Nghệ thuật', 'Sư phạm Âm nhạc', '0911200123', '1200514001', '1200514001@gmail.com', NULL),
(1210103040, 'Nguyễn Văn V', 'CNTT', 'Khoa học máy tính', '0123456790', '1200103040', '1200103040@gmail.com', NULL),
(1210103041, 'Trần Thị W', 'CNTT', 'Khoa học máy tính', '0123456791', '1200103041', '1200103041@gmail.com', NULL),
(1210103042, 'Lê Văn X', 'CNTT', 'Khoa học máy tính', '0123456792', '1200103042', '1200103042@gmail.com', NULL),
(1210205020, 'Trần Thị B', 'Ngoại ngữ', 'Sư phạm Anh', '0123456790', '1210205020', '1210205020@gmail.com', NULL),
(1210206005, 'Nguyễn Ngọc Quỳnh Lực', 'Ngoại Ngữ', 'Ngôn ngữ Anh', '0911203800', '1210206005', '1210206005@gmail.com', NULL),
(1210307021, 'Lê Văn C', 'SP KHTN', 'Sư phạm Toán', '0123456791', '1210307021', '1210307021@gmail.com', NULL),
(1210308026, 'Trần Thị H', 'SP KHTN', 'Sư phạm Lý', '0123456796', '1210308026', '1210308026@gmail.com', NULL),
(1210411004, 'Trần Thị Anh Ngọc', 'SP KHXH', 'Sư phạm Văn', '0911200900', '1210411004', '1210411004@gmail.com', NULL),
(1210412027, 'Lê Văn I', 'SP KHXH', 'Sư phạm Địa', '0123456797', '1210412027', '1210412027@gmail.com', NULL),
(1210413032, 'Trần Thị N', 'SP KHXH', 'Sư phạm Sử', '0123456802', '1210413032', '1210413032@gmail.com', NULL),
(1210514023, 'Ngô Văn E', 'Nghệ thuật', 'Sư phạm Âm nhạc', '0123456793', '1210514023', '1210514023@gmail.com', NULL),
(1210514033, 'Lê Văn O', 'Nghệ thuật', 'Sư phạm Âm nhạc', '0123456803', '1210514033', '1210514033@gmail.com', NULL),
(1210515017, 'Lê Thục Ly', 'Nghệ thuật', 'Sư phạm Mĩ thuật', '0911225300', '1210515017', '1210515017@gmail.com', NULL),
(1210515057, 'Nguyễn Văn M', 'Nghệ thuật', 'Sư phạm Mĩ thuật', '0123456815', '1200515057', '1200515057@gmail.com', NULL),
(1210515058, 'Ngô Văn NN', 'Nghệ thuật', 'Sư phạm Mĩ thuật', '0123456816', '1200515058', '1200515058@gmail.com', NULL),
(1220101019, 'Nguyễn Văn A', 'CNTT', 'Kỹ thuật phần mềm', '0123456789', '1220101019', '1220101019@gmail.com', NULL),
(1220101034, 'Nguyễn Văn P', 'CNTT', 'Kỹ thuật phần mềm', '0123456784', '1200101034', '1200101034@gmail.com', NULL),
(1220101035, 'Trần Thị Q', 'CNTT', 'Kỹ thuật phần mềm', '0123456785', '1200101035', '1200101035@gmail.com', NULL),
(1220101036, 'Lê Văn R', 'CNTT', 'Kỹ thuật phần mềm', '0123456786', '1200101036', '1200101036@gmail.com', NULL),
(1220102008, 'Nguyễn Trần Thái Ngọc', 'CNTT', 'Hệ thống thông tin', '0911204800', '1220102008', '1220102008@gmail.com', NULL),
(1220102024, 'Đặng Thị F', 'CNTT', 'Hệ thống thông tin', '0123456794', '1220102024', '1220102024@gmail.com', NULL),
(1220102037, 'Phạm Thị S', 'CNTT', 'Hệ thống thông tin', '0123456787', '1200102037', '1200102037@gmail.com', NULL),
(1220102038, 'Ngô Văn T', 'CNTT', 'Hệ thống thông tin', '0123456788', '1200102038', '1200102038@gmail.com', NULL),
(1220102039, 'Đặng Thị U', 'CNTT', 'Hệ thống thông tin', '0123456789', '1200102039', '1200102039@gmail.com', NULL),
(1220104009, 'Trần Minh Phúc Ngọc', 'CNTT', 'Kỹ thuật máy tính', '0911204800', '1220104009', '1220104009@gmail.com', NULL),
(1220206025, 'Nguyễn Văn G', 'Ngoại ngữ', 'Ngôn ngữ Anh', '0123456795', '1220206025', '1220206025@gmail.com', NULL),
(1220307007, 'Trần Phạm Ngọc Ly', 'SP KHTN', 'Sư phạm Toán', '0911204800', '1220307007', '1220307007@gmail.com', NULL),
(1230103029, 'Ngô Văn K', 'CNTT', 'Khoa học máy tính', '0123456799', '1230103029', '1230103029@gmail.com', NULL),
(1230205018, 'Nguyễn Gia My', 'Ngoại ngữ', 'Sư phạm Anh', '0911225500', '1230205018', '1230205018@gmail.com', NULL),
(1230205030, 'Đặng Thị L', 'Ngoại ngữ', 'Sư phạm Anh', '0123456800', '1230205030', '1230205030@gmail.com', NULL),
(1230514054, 'Nguyễn Văn JJ', 'Nghệ thuật', 'Sư phạm Âm nhạc', '0123456812', '1200514054', '1200514054@gmail.com', NULL),
(1230514055, 'Trần Thị KK', 'Nghệ thuật', 'Sư phạm Âm nhạc', '0123456813', '1200514055', '1200514055@gmail.com', NULL),
(1240101001, 'Nguyễn Văn Test', 'CNTT', 'Kỹ thuật phần mềm', '0961083148', '1240101001', '1240101001@gmail.com', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thietbi`
--

CREATE TABLE `thietbi` (
  `MaTB` int(10) NOT NULL,
  `TenTB` varchar(100) NOT NULL,
  `MotaTB` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thietbi`
--

INSERT INTO `thietbi` (`MaTB`, `TenTB`, `MotaTB`) VALUES
(120191, 'Micro không dây 01', 'JBL VM 2001'),
(120192, 'Micro có dây 01', 'MCCD 1001'),
(120203, 'Micro không dây 02', 'BCE 9001'),
(120214, 'Micro không dây 03', 'M3001'),
(120235, 'Micro không dây 03', 'BCE UGX12'),
(220191, 'Máy chiếu Panasonic', 'PNSN 031'),
(320201, 'Máy ảnh Fuji', 'FJ 20201'),
(420201, 'Cassette Sony', 'CN 20201'),
(420212, 'Cassette Sony', 'CN 20211'),
(420213, 'Cassette TQ', 'CSTQ 20211'),
(420224, 'Cassette Sony', 'CS 20221'),
(420236, 'Cassette Sony', 'CS 2023'),
(520221, 'Tivi LG', 'TVLG021'),
(520222, 'Tivi Samsung', 'TVSS20221'),
(620231, 'Quạt đứng', 'QD 20231'),
(620232, 'Quạt đứng', 'QD 20232'),
(620233, 'Quạt đứng', 'QD 20233'),
(620234, 'Quạt đứng', 'QD 20234');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongtinsd`
--

CREATE TABLE `thongtinsd` (
  `MaTT` int(10) NOT NULL,
  `MaTV` int(11) NOT NULL,
  `MaTB` int(10) DEFAULT NULL,
  `tgvao` datetime(6) DEFAULT NULL,
  `tgmuon` datetime(6) DEFAULT NULL,
  `tgtra` datetime(6) DEFAULT NULL,
  `tgdatcho` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thongtinsd`
--

INSERT INTO `thongtinsd` (`MaTT`, `MaTV`, `MaTB`, `tgvao`, `tgmuon`, `tgtra`, `tgdatcho`) VALUES
(1, 1190308010, 120191, NULL, '2024-04-01 08:07:36.000000', '2024-04-01 08:09:47.000000', NULL),
(2, 1190308010, 120192, NULL, '2024-04-02 08:07:36.000000', '2024-04-02 10:20:47.000000', NULL),
(3, 1190411014, 120203, NULL, '2024-04-03 08:07:48.000000', '2024-04-03 08:10:00.000000', NULL),
(4, 1190411014, 520221, NULL, '2024-04-04 08:07:48.000000', '2024-04-04 10:22:28.000000', NULL),
(5, 1190412013, 320201, NULL, '2024-04-05 08:07:55.000000', '2024-04-05 08:10:05.000000', NULL),
(6, 1190413011, 520222, NULL, '2024-04-06 08:08:05.000000', '2024-04-06 08:10:09.000000', NULL),
(7, 1190413011, 620233, NULL, '2024-04-07 08:08:05.000000', '2024-04-07 08:10:14.000000', NULL),
(8, 1190413011, 620232, NULL, '2024-04-08 08:08:05.000000', '2024-04-08 08:10:12.000000', NULL),
(9, 1190413012, 120214, NULL, '2024-04-09 08:08:18.000000', '2024-04-09 10:50:48.000000', NULL),
(10, 1190413012, 220191, NULL, '2024-04-10 08:08:18.000000', '2024-04-10 10:50:48.000000', NULL),
(11, 1190413012, 420212, NULL, '2024-04-11 08:08:18.000000', '2024-04-11 10:50:48.000000', NULL),
(12, 1190413012, 420213, NULL, '2024-04-13 08:08:18.000000', '2024-04-19 10:50:48.000000', NULL),
(13, 1200104043, 420224, NULL, '2024-04-13 08:08:25.000000', '2024-04-13 08:10:39.000000', NULL),
(14, 1200104044, 420236, NULL, '2024-04-13 08:08:33.000000', '2024-04-13 08:10:44.000000', NULL),
(15, 1200104045, 620234, NULL, '2024-04-13 08:08:45.000000', '2024-04-13 08:10:50.000000', NULL),
(16, 1200104045, 420201, NULL, '2024-04-13 08:08:53.000000', '2024-04-13 08:10:48.000000', NULL),
(17, 1200205046, 620231, NULL, '2024-04-13 08:09:00.000000', '2024-04-13 08:10:55.000000', NULL),
(18, 1200205047, 120235, NULL, '2024-04-13 08:09:09.000000', '2024-04-13 08:11:00.000000', NULL),
(19, 1200104044, 120235, NULL, '2024-04-13 08:18:31.000000', NULL, NULL),
(20, 1200104044, 120214, NULL, '2024-04-13 08:18:31.000000', '2024-04-25 09:02:28.000000', NULL),
(21, 1210103042, 220191, NULL, '2024-04-13 08:19:01.000000', NULL, NULL),
(22, 1190308010, NULL, '2024-04-06 06:14:11.000000', NULL, NULL, NULL),
(23, 1190308010, NULL, '2024-04-10 06:23:22.000000', NULL, NULL, NULL),
(24, 1190413012, NULL, '2024-04-09 06:23:28.000000', NULL, NULL, NULL),
(26, 1200205047, NULL, '2024-04-01 06:24:27.000000', NULL, NULL, NULL),
(27, 1200205047, NULL, '2024-04-01 06:24:27.000000', NULL, NULL, NULL),
(28, 1210103040, NULL, '2024-04-02 06:24:27.000000', NULL, NULL, NULL),
(32, 1230514055, NULL, '2024-04-12 06:24:27.000000', NULL, NULL, NULL),
(33, 1230514054, NULL, '2024-02-06 06:24:27.000000', NULL, NULL, NULL),
(34, 1230205030, NULL, '2024-03-07 06:24:27.000000', NULL, NULL, NULL),
(35, 1230205018, NULL, '2024-02-08 06:24:27.000000', NULL, NULL, NULL),
(36, 1230103029, NULL, '2024-02-09 06:24:27.000000', NULL, NULL, NULL),
(37, 1220102008, NULL, '2024-04-19 06:30:36.000000', NULL, NULL, NULL),
(38, 1220102024, NULL, '2024-04-19 06:30:49.000000', NULL, NULL, NULL),
(39, 1220102037, NULL, '2024-04-19 06:30:55.000000', NULL, NULL, NULL),
(40, 1220102038, NULL, '2024-04-19 06:31:01.000000', NULL, NULL, NULL),
(41, 1220102039, NULL, '2024-04-19 06:31:08.000000', NULL, NULL, NULL),
(42, 1190308010, 120191, NULL, '2024-04-19 10:20:40.000000', '2024-04-19 10:20:51.000000', NULL),
(43, 1190411014, 320201, NULL, '2024-04-19 10:21:57.000000', '2024-04-19 10:22:35.000000', NULL),
(44, 1190411014, 120203, NULL, '2024-04-19 10:21:57.000000', '2024-04-19 10:22:39.000000', NULL),
(45, 1190411014, 420201, NULL, '2024-04-19 10:21:57.000000', '2024-04-19 10:22:37.000000', NULL),
(46, 1200104045, 420201, NULL, '2024-04-19 10:24:35.000000', '2024-04-19 10:24:48.000000', NULL),
(47, 1200104045, 420213, NULL, '2024-04-19 10:24:35.000000', '2024-04-19 10:24:55.000000', NULL),
(48, 1200104045, 620233, NULL, '2024-04-19 10:24:35.000000', '2024-04-19 10:24:53.000000', NULL),
(49, 1190413012, 420212, NULL, '2024-04-19 10:50:48.000000', '2024-04-19 10:59:59.000000', NULL),
(50, 1190413012, 420201, NULL, '2024-04-19 11:00:05.000000', '2024-04-19 11:00:10.000000', NULL),
(51, 1190413012, 120203, NULL, '2024-04-19 11:00:05.000000', '2024-04-19 11:00:10.000000', NULL),
(52, 1200206050, 120192, NULL, '2024-04-19 11:10:30.000000', NULL, NULL),
(53, 1200206050, 320201, NULL, '2024-04-19 11:10:30.000000', '2024-04-25 09:17:59.000000', NULL),
(54, 1200206050, 420212, NULL, '2024-04-19 11:10:30.000000', '2024-04-25 09:17:57.000000', NULL),
(55, 1200205049, 420224, NULL, '2024-04-19 11:11:38.000000', '2024-04-19 11:11:46.000000', NULL),
(56, 1200205049, 420201, NULL, '2024-04-19 11:11:38.000000', '2024-04-19 11:11:40.000000', NULL),
(57, 1200205049, 420236, NULL, '2024-04-19 11:11:38.000000', '2024-04-19 11:11:43.000000', NULL),
(58, 1200104043, 620234, NULL, '2024-04-19 16:03:29.000000', '2024-04-25 09:17:54.000000', NULL),
(59, 1200104043, 620232, NULL, '2024-04-19 16:03:29.000000', '2024-04-25 09:02:20.000000', NULL),
(60, 1200104043, 620231, NULL, '2024-04-19 16:03:29.000000', '2024-04-25 08:57:43.000000', NULL),
(61, 1200205049, 420213, NULL, '2024-04-19 17:43:34.000000', '2024-04-25 08:56:03.000000', NULL),
(62, 1200205049, 420224, NULL, '2024-04-19 17:43:34.000000', '2024-04-19 17:44:06.000000', NULL),
(63, 1190413011, 120191, NULL, '2024-04-25 09:33:30.000000', NULL, NULL),
(64, 1200411016, 520221, NULL, '2024-04-25 09:35:42.000000', '2024-04-25 09:36:03.000000', NULL),
(65, 1190413011, 420236, NULL, '2024-04-24 00:00:00.000000', '0000-00-00 00:00:00.000000', NULL),
(66, 1190413011, 120203, NULL, '2024-04-23 00:00:00.000000', NULL, NULL),
(67, 1190413011, 420236, NULL, '2024-04-24 00:00:00.000000', '0000-00-00 00:00:00.000000', NULL),
(68, 1190413011, 120203, NULL, '2024-04-23 00:00:00.000000', NULL, NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xuly`
--

CREATE TABLE `xuly` (
  `MaXL` int(10) NOT NULL,
  `MaTV` int(11) NOT NULL,
  `HinhthucXL` varchar(250) DEFAULT NULL,
  `Sotien` int(100) DEFAULT NULL,
  `ngayxl` datetime(6) DEFAULT NULL,
  `TrangthaiXL` int(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `xuly`
--

INSERT INTO `xuly` (`MaXL`, `MaTV`, `HinhthucXL`, `Sotien`, `ngayxl`, `TrangthaiXL`) VALUES
(1, 1190308010, 'Khóa thẻ 1 tháng', NULL, '2024-02-01 09:00:00.000000', 0),
(2, 1190411014, 'Khóa thẻ 2 tháng', NULL, '2024-02-02 10:00:00.000000', 0),
(3, 1190412013, 'Khóa thẻ vĩnh viễn', NULL, '2024-02-03 11:00:00.000000', 0),
(4, 1190413011, 'Bồi thường', 300000, '2024-02-04 12:00:00.000000', 0),
(5, 1200104043, 'Khóa thẻ 1 tháng và bồi thường', 500000, '2024-02-05 13:00:00.000000', 0),
(6, 1200104044, 'Khóa thẻ 1 tháng', NULL, '2024-02-06 14:00:00.000000', 0),
(7, 1200104045, 'Khóa thẻ 2 tháng', NULL, '2024-02-07 15:00:00.000000', 0),
(8, 1200205046, 'Khóa thẻ vĩnh viễn', NULL, '2024-02-08 16:00:00.000000', 0),
(9, 1200205047, 'Bồi thường', 400000, '2024-02-09 17:00:00.000000', 0),
(11, 1210103040, 'Khóa thẻ 1 tháng', NULL, '2024-02-11 09:00:00.000000', 0),
(12, 1210103041, 'Khóa thẻ 2 tháng', NULL, '2024-02-12 10:00:00.000000', 0),
(13, 1210103042, 'Khóa thẻ vĩnh viễn', NULL, '2024-02-13 11:00:00.000000', 0),
(14, 1210205020, 'Bồi thường', 450000, '2024-02-14 12:00:00.000000', 0),
(15, 1220101036, 'Khóa thẻ 1 tháng và bồi thường', 700000, '2024-02-15 13:00:00.000000', 0),
(16, 1220102008, 'Khóa thẻ 1 tháng', NULL, '2024-02-16 14:00:00.000000', 0),
(17, 1220102024, 'Khóa thẻ 2 tháng', NULL, '2024-02-17 15:00:00.000000', 0),
(18, 1230103029, 'Khóa thẻ vĩnh viễn', NULL, '2024-02-18 16:00:00.000000', 0),
(19, 1230205018, 'Bồi thường', 250000, '2024-02-19 17:00:00.000000', 0),
(20, 1230205030, 'Khóa thẻ 1 tháng và bồi thường', 350000, '2024-02-20 18:00:00.000000', 0),
(21, 1220102008, 'Bồi thường', 400000, '2024-04-26 00:00:00.000000', 0),
(22, 1240101001, 'Khóa thẻ 1 tháng', NULL, '2024-10-08 10:42:16.000000', 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `passwordresettoken`
--
ALTER TABLE `passwordresettoken`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK613tfos7g531kw2r0mym4p3b2` (`memberID`);

--
-- Chỉ mục cho bảng `thanhvien`
--
ALTER TABLE `thanhvien`
  ADD PRIMARY KEY (`MaTV`);

--
-- Chỉ mục cho bảng `thietbi`
--
ALTER TABLE `thietbi`
  ADD PRIMARY KEY (`MaTB`);

--
-- Chỉ mục cho bảng `thongtinsd`
--
ALTER TABLE `thongtinsd`
  ADD PRIMARY KEY (`MaTT`),
  ADD KEY `MaTV` (`MaTV`,`MaTB`),
  ADD KEY `MaTB` (`MaTB`);

--
-- Chỉ mục cho bảng `xuly`
--
ALTER TABLE `xuly`
  ADD PRIMARY KEY (`MaXL`),
  ADD KEY `MaTV` (`MaTV`),
  ADD KEY `MaTV_2` (`MaTV`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `passwordresettoken`
--
ALTER TABLE `passwordresettoken`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `thongtinsd`
--
ALTER TABLE `thongtinsd`
  MODIFY `MaTT` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT cho bảng `xuly`
--
ALTER TABLE `xuly`
  MODIFY `MaXL` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `passwordresettoken`
--
ALTER TABLE `passwordresettoken`
  ADD CONSTRAINT `FK613tfos7g531kw2r0mym4p3b2` FOREIGN KEY (`memberID`) REFERENCES `thanhvien` (`MaTV`);

--
-- Các ràng buộc cho bảng `thongtinsd`
--
ALTER TABLE `thongtinsd`
  ADD CONSTRAINT `thongtinsd_ibfk_2` FOREIGN KEY (`MaTB`) REFERENCES `thietbi` (`MaTB`),
  ADD CONSTRAINT `thongtinsd_ibfk_3` FOREIGN KEY (`MaTV`) REFERENCES `thanhvien` (`MaTV`);

--
-- Các ràng buộc cho bảng `xuly`
--
ALTER TABLE `xuly`
  ADD CONSTRAINT `xuly_ibfk_1` FOREIGN KEY (`MaTV`) REFERENCES `thanhvien` (`MaTV`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
