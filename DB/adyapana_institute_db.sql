-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.33 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.0.0.6468
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for adyapana_institute_db
CREATE DATABASE IF NOT EXISTS `adyapana_institute_db` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `adyapana_institute_db`;

-- Dumping structure for table adyapana_institute_db.admin
CREATE TABLE IF NOT EXISTS `admin` (
  `id` int NOT NULL AUTO_INCREMENT,
  `typpe` varchar(45) DEFAULT NULL,
  `uname` varchar(45) DEFAULT NULL,
  `pass` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.admin: ~2 rows (approximately)
REPLACE INTO `admin` (`id`, `typpe`, `uname`, `pass`) VALUES
	(1, 'Admin', 'Janaka123', '123456'),
	(2, 'Admin', '12345', '12345');

-- Dumping structure for table adyapana_institute_db.attendence_record
CREATE TABLE IF NOT EXISTS `attendence_record` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `class_classNo` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_attendence_record_class1_idx` (`class_classNo`),
  CONSTRAINT `fk_attendence_record_class1` FOREIGN KEY (`class_classNo`) REFERENCES `class` (`classNo`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.attendence_record: ~2 rows (approximately)
REPLACE INTO `attendence_record` (`id`, `date`, `class_classNo`) VALUES
	(1, '2023-07-13', 1),
	(2, '2023-07-13', 4);

-- Dumping structure for table adyapana_institute_db.city
CREATE TABLE IF NOT EXISTS `city` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.city: ~6 rows (approximately)
REPLACE INTO `city` (`id`, `name`) VALUES
	(1, 'Anuradhapura'),
	(2, 'Colombo'),
	(3, 'Polonnaruwa'),
	(4, 'Gampaha'),
	(5, 'Kurunegala'),
	(6, 'Kandy');

-- Dumping structure for table adyapana_institute_db.class
CREATE TABLE IF NOT EXISTS `class` (
  `classNo` int NOT NULL AUTO_INCREMENT,
  `subjects_Subno` int NOT NULL,
  `teachers_Tno` varchar(15) NOT NULL,
  `timeLots_id` int NOT NULL,
  `class_name` varchar(45) DEFAULT NULL,
  `al_year` year DEFAULT NULL,
  `intake` int DEFAULT NULL,
  `class_type_id` int NOT NULL,
  `fee` double DEFAULT NULL,
  PRIMARY KEY (`classNo`),
  KEY `fk_class_timeLots1_idx` (`timeLots_id`),
  KEY `fk_class_subjects1_idx` (`subjects_Subno`),
  KEY `fk_class_teachers1_idx` (`teachers_Tno`),
  KEY `fk_class_class_type1_idx` (`class_type_id`),
  CONSTRAINT `fk_class_class_type1` FOREIGN KEY (`class_type_id`) REFERENCES `class_type` (`id`),
  CONSTRAINT `fk_class_subjects1` FOREIGN KEY (`subjects_Subno`) REFERENCES `subjects` (`Subno`),
  CONSTRAINT `fk_class_teachers1` FOREIGN KEY (`teachers_Tno`) REFERENCES `teachers` (`Tno`),
  CONSTRAINT `fk_class_timeLots1` FOREIGN KEY (`timeLots_id`) REFERENCES `timelots` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.class: ~9 rows (approximately)
REPLACE INTO `class` (`classNo`, `subjects_Subno`, `teachers_Tno`, `timeLots_id`, `class_name`, `al_year`, `intake`, `class_type_id`, `fee`) VALUES
	(1, 2, 'TE_2_6779', 7, '2023/AL/PH/TE_2_6779/CO/IN1', '2023', 1, 1, 666),
	(2, 2, 'TE_2_6779', 8, '2023/AL/PH/TE_2_6779/RE/IN1', '2023', 1, 2, 3000),
	(3, 3, 'TE_3_9173', 9, '2023/AL/CH/TE_3_9173/RE/IN1', '2023', 1, 2, 2500),
	(4, 3, 'TE_3_9173', 10, '2022/AL/CH/TE_3_9173/PA/IN1', '2022', 1, 3, 2500),
	(5, 3, 'TE_4_5259', 11, '2024/AL/CH/TE_4_5259/PA/IN1', '2024', 1, 3, 1500),
	(6, 3, 'TE_4_5259', 12, '2024/AL/CH/TE_4_5259/RE/IN1', '2024', 1, 2, 2500),
	(7, 1, 'TE_2_6779', 13, '2024/AL/PH/TE_2_6779/CO/IN2', '2024', 2, 1, 3000),
	(8, 3, 'TE_4_5259', 14, '2022/AL/CH/TE_4_5259/CO/IN2', '2022', 2, 1, 3000),
	(9, 1, 'TE_3_2772', 15, '2024/AL/PH/TE_3_2772/PA/IN1', '2024', 1, 3, 3000);

-- Dumping structure for table adyapana_institute_db.class_type
CREATE TABLE IF NOT EXISTS `class_type` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.class_type: ~3 rows (approximately)
REPLACE INTO `class_type` (`id`, `type`) VALUES
	(1, 'Common'),
	(2, 'Revision'),
	(3, 'Paper');

-- Dumping structure for table adyapana_institute_db.day
CREATE TABLE IF NOT EXISTS `day` (
  `id` int NOT NULL AUTO_INCREMENT,
  `day_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.day: ~7 rows (approximately)
REPLACE INTO `day` (`id`, `day_name`) VALUES
	(1, 'MONDAY'),
	(2, 'TUESDAY'),
	(3, 'WEDNESDAY'),
	(4, 'THURSDAY'),
	(5, 'FRIDAY'),
	(6, 'SATURDAY'),
	(7, 'SUNDAY');

-- Dumping structure for table adyapana_institute_db.gender
CREATE TABLE IF NOT EXISTS `gender` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gen` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.gender: ~2 rows (approximately)
REPLACE INTO `gender` (`id`, `gen`) VALUES
	(1, 'Male'),
	(2, 'Female');

-- Dumping structure for table adyapana_institute_db.invoice
CREATE TABLE IF NOT EXISTS `invoice` (
  `id` int NOT NULL AUTO_INCREMENT,
  `students_Sno` varchar(15) NOT NULL,
  `teachers_Tno` varchar(15) NOT NULL,
  `class_classNo` int NOT NULL,
  `month` varchar(20) NOT NULL,
  `value` double NOT NULL,
  `paid_date` date NOT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_invoice_students1_idx` (`students_Sno`),
  KEY `fk_invoice_teachers1_idx` (`teachers_Tno`),
  KEY `fk_invoice_status1_idx` (`status_id`),
  KEY `fk_invoice_class1_idx` (`class_classNo`),
  CONSTRAINT `fk_invoice_class1` FOREIGN KEY (`class_classNo`) REFERENCES `class` (`classNo`),
  CONSTRAINT `fk_invoice_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `fk_invoice_students1` FOREIGN KEY (`students_Sno`) REFERENCES `students` (`Sno`),
  CONSTRAINT `fk_invoice_teachers1` FOREIGN KEY (`teachers_Tno`) REFERENCES `teachers` (`Tno`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.invoice: ~27 rows (approximately)
REPLACE INTO `invoice` (`id`, `students_Sno`, `teachers_Tno`, `class_classNo`, `month`, `value`, `paid_date`, `status_id`) VALUES
	(1, 'ST_2_3901', 'TE_2_6779', 1, 'MARCH', 300, '2023-07-13', 1),
	(8, 'ST_3_3689', 'TE_2_6779', 1, 'AUGUST', 666, '2023-07-14', 1),
	(9, 'ST_3_3689', 'TE_3_9173', 3, 'MARCH', 2500, '2023-07-14', 1),
	(10, 'ST_1_8478', 'TE_4_5259', 5, 'JUNE', 1500, '2023-07-14', 1),
	(11, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-14', 1),
	(12, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-14', 1),
	(13, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-14', 1),
	(14, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-14', 1),
	(15, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-14', 1),
	(16, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-14', 1),
	(17, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-14', 1),
	(18, 'ST_1_8478', 'TE_3_9173', 3, 'SEPTEMBER', 2500, '2023-07-14', 2),
	(19, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-14', 1),
	(20, 'ST_1_8478', 'TE_2_6779', 1, 'Select', 666, '2023-07-14', 1),
	(21, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-14', 1),
	(22, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-14', 1),
	(23, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-14', 1),
	(24, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-14', 1),
	(25, 'ST_2_3901', 'TE_2_6779', 1, 'Select', 666, '2023-07-14', 1),
	(26, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-15', 1),
	(27, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-15', 1),
	(28, 'ST_1_8478', 'TE_2_6779', 1, 'Select', 666, '2023-07-15', 1),
	(29, 'ST_3_3689', 'TE_2_6779', 1, 'Select', 666, '2023-07-19', 1),
	(30, 'ST_3_3689', 'TE_3_9173', 3, 'Select', 2500, '2023-07-19', 1),
	(31, 'ST_1_8478', 'TE_4_5259', 6, 'Select', 2500, '2023-07-19', 1),
	(32, 'ST_1_8478', 'TE_4_5259', 5, 'Select', 1500, '2023-07-19', 1),
	(33, 'ST_1_8478', 'TE_3_9173', 3, 'Select', 2500, '2023-07-19', 1);

-- Dumping structure for table adyapana_institute_db.status
CREATE TABLE IF NOT EXISTS `status` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.status: ~2 rows (approximately)
REPLACE INTO `status` (`id`, `type`) VALUES
	(1, 'Paid'),
	(2, 'Unpaid');

-- Dumping structure for table adyapana_institute_db.students
CREATE TABLE IF NOT EXISTS `students` (
  `Sno` varchar(15) NOT NULL,
  `stNic` varchar(45) DEFAULT NULL,
  `stFname` varchar(45) DEFAULT NULL,
  `stLname` varchar(45) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `stMobile` varchar(10) DEFAULT NULL,
  `stEmail` varchar(45) DEFAULT NULL,
  `stGuardName` varchar(45) DEFAULT NULL,
  `stGuardOccu` varchar(45) DEFAULT NULL,
  `stGuardMobile` varchar(10) DEFAULT NULL,
  `stGuradEmail` varchar(45) DEFAULT NULL,
  `gender_id` int NOT NULL,
  `reg_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`Sno`),
  KEY `fk_students_gender1_idx` (`gender_id`),
  CONSTRAINT `fk_students_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.students: ~6 rows (approximately)
REPLACE INTO `students` (`Sno`, `stNic`, `stFname`, `stLname`, `dob`, `stMobile`, `stEmail`, `stGuardName`, `stGuardOccu`, `stGuardMobile`, `stGuradEmail`, `gender_id`, `reg_datetime`) VALUES
	('ST_1_8478', '200212303574', 'Janaka', 'Sangeeth', '2002-05-02', '0762228848', 'janakasangeethjava@gmail.com', 'Kalyani', 'House Wife', '0762046812', '', 1, '2023-07-12 20:04:04'),
	('ST_2_3901', '200402323232', 'Ben', 'Ten', '2004-05-27', '0765263232', 'ben@gmail.com', 'Tenisan', 'Space Police', '0765456765', 'tensian@gmail.com', 1, '2023-07-12 20:05:20'),
	('ST_3_3689', '200167237232', 'Guwen', 'Tenisan', '2001-09-18', '0752162531', 'guwen@gmail.com', 'Tenisan', 'Space Police', '0765342673', 'tensian@gmail.com', 2, '2023-07-12 20:06:39'),
	('ST_4_6594', '2004232321121', 'Upek ', 'Hasanjana', '2004-03-08', '0763534256', 'upek@gmail.com', 'Parent', '', '0765434567', '', 1, '2023-07-14 10:06:55'),
	('ST_5_7201', '200223734833', 'Jhon', 'Smith', '1999-02-11', '0765236578', 'jhonsmith@gmail.com', 'Jhonsan', 'Teacher', '076543456', 'jhonsan@gmail.com', 1, '2023-07-19 17:08:46'),
	('ST_6_9073', '200236436343', 'Hasindu', 'Herath', '2002-07-16', '0765352435', 'hasindu@gmail.com', 'Priyantha', 'Teacher', '076545678', 'priyantha@gmail.com', 1, '2023-07-19 18:14:08');

-- Dumping structure for table adyapana_institute_db.student_address
CREATE TABLE IF NOT EXISTS `student_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `line1` text,
  `line2` text,
  `city_id` int NOT NULL,
  `students_Sno` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_address_city1_idx` (`city_id`),
  KEY `fk_student_address_students1_idx` (`students_Sno`),
  CONSTRAINT `fk_student_address_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_student_address_students1` FOREIGN KEY (`students_Sno`) REFERENCES `students` (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.student_address: ~6 rows (approximately)
REPLACE INTO `student_address` (`id`, `line1`, `line2`, `city_id`, `students_Sno`) VALUES
	(16, '72/4A, ', 'Yakkala RD', 4, 'ST_1_8478'),
	(17, 'Kalattewa', 'Kurundankulma', 1, 'ST_2_3901'),
	(18, 'kalattewa	', 'Kurundankulama', 1, 'ST_3_3689'),
	(19, 'Thewatta Basilika', 'Ragama', 4, 'ST_4_6594'),
	(21, 'No50, Yakkala Rd', 'Gampaha', 4, 'ST_5_7201'),
	(22, 'Jaffna Junction', 'Anuradhapura', 1, 'ST_6_9073');

-- Dumping structure for table adyapana_institute_db.student_enrolment_class
CREATE TABLE IF NOT EXISTS `student_enrolment_class` (
  `id` int NOT NULL AUTO_INCREMENT,
  `enrol_date` datetime DEFAULT NULL,
  `students_Sno` varchar(15) NOT NULL,
  `class_classNo` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_enrolment_class_students1_idx` (`students_Sno`),
  KEY `fk_student_enrolment_class_class1_idx` (`class_classNo`),
  CONSTRAINT `fk_student_enrolment_class_class1` FOREIGN KEY (`class_classNo`) REFERENCES `class` (`classNo`),
  CONSTRAINT `fk_student_enrolment_class_students1` FOREIGN KEY (`students_Sno`) REFERENCES `students` (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.student_enrolment_class: ~14 rows (approximately)
REPLACE INTO `student_enrolment_class` (`id`, `enrol_date`, `students_Sno`, `class_classNo`) VALUES
	(1, '2023-07-12 20:36:02', 'ST_1_8478', 1),
	(4, '2023-07-12 23:19:26', 'ST_3_3689', 3),
	(5, '2023-07-12 23:27:24', 'ST_3_3689', 1),
	(6, '2023-07-13 00:41:35', 'ST_1_8478', 3),
	(7, '2023-07-13 16:32:11', 'ST_2_3901', 1),
	(8, '2023-07-14 10:27:20', 'ST_1_8478', 5),
	(9, '2023-07-19 18:10:32', 'ST_1_8478', 6),
	(10, '2023-07-19 18:11:30', 'ST_3_3689', 6),
	(11, '2023-07-19 18:26:50', 'ST_2_3901', 8),
	(12, '2023-07-19 18:27:05', 'ST_3_3689', 8),
	(13, '2023-07-19 18:27:18', 'ST_1_8478', 8),
	(14, '2023-08-16 22:57:26', 'ST_3_3689', 1),
	(15, '2023-08-16 23:02:43', 'ST_4_6594', 7),
	(16, '2023-08-21 14:27:17', 'ST_1_8478', 2);

-- Dumping structure for table adyapana_institute_db.stu_images
CREATE TABLE IF NOT EXISTS `stu_images` (
  `path` varchar(100) NOT NULL,
  `students_Sno` varchar(15) NOT NULL,
  PRIMARY KEY (`path`),
  KEY `fk_stu_images_students1_idx` (`students_Sno`),
  CONSTRAINT `fk_stu_images_students1` FOREIGN KEY (`students_Sno`) REFERENCES `students` (`Sno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.stu_images: ~0 rows (approximately)

-- Dumping structure for table adyapana_institute_db.st_attendance
CREATE TABLE IF NOT EXISTS `st_attendance` (
  `id` int NOT NULL AUTO_INCREMENT,
  `students_Sno` varchar(15) NOT NULL,
  `marked_time` time DEFAULT NULL,
  `attendence_record_id` int NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_st_attendance_students1_idx` (`students_Sno`),
  KEY `fk_st_attendance_attendence_record1_idx` (`attendence_record_id`),
  CONSTRAINT `fk_st_attendance_attendence_record1` FOREIGN KEY (`attendence_record_id`) REFERENCES `attendence_record` (`id`),
  CONSTRAINT `fk_st_attendance_students1` FOREIGN KEY (`students_Sno`) REFERENCES `students` (`Sno`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.st_attendance: ~3 rows (approximately)
REPLACE INTO `st_attendance` (`id`, `students_Sno`, `marked_time`, `attendence_record_id`, `status`) VALUES
	(1, 'ST_1_8478', '14:34:43', 1, 1),
	(2, 'ST_3_3689', '15:07:07', 2, 1),
	(4, 'ST_2_3901', '16:32:43', 1, 1);

-- Dumping structure for table adyapana_institute_db.subjects
CREATE TABLE IF NOT EXISTS `subjects` (
  `Subno` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `Description` text,
  `Price` double DEFAULT NULL,
  PRIMARY KEY (`Subno`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.subjects: ~5 rows (approximately)
REPLACE INTO `subjects` (`Subno`, `name`, `Description`, `Price`) VALUES
	(1, 'Physics', 'physics', 3000),
	(2, 'Bio', 'physics-Chemestry', 2500),
	(3, 'Chemestry', 'All the sylabus\n', 3000),
	(6, 'Combined Maths', 'With Pure and Applied', 3000),
	(8, 'ICT', 'Information Comunication Technology', 2000);

-- Dumping structure for table adyapana_institute_db.teachers
CREATE TABLE IF NOT EXISTS `teachers` (
  `Tno` varchar(15) NOT NULL,
  `teFname` varchar(45) DEFAULT NULL,
  `teLname` varchar(45) DEFAULT NULL,
  `te_NIC` varchar(20) DEFAULT NULL,
  `teDob` date DEFAULT NULL,
  `gender_id` int NOT NULL,
  `teMobile` varchar(10) DEFAULT NULL,
  `teEmail` varchar(45) DEFAULT NULL,
  `teEduQualifi` text,
  `teProfQualifi` text,
  `subjects_Subno` int NOT NULL,
  `reg_datetime` datetime DEFAULT NULL,
  PRIMARY KEY (`Tno`),
  KEY `fk_teachers_gender1_idx` (`gender_id`),
  KEY `fk_teachers_subjects1_idx` (`subjects_Subno`),
  CONSTRAINT `fk_teachers_gender1` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  CONSTRAINT `fk_teachers_subjects1` FOREIGN KEY (`subjects_Subno`) REFERENCES `subjects` (`Subno`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.teachers: ~7 rows (approximately)
REPLACE INTO `teachers` (`Tno`, `teFname`, `teLname`, `te_NIC`, `teDob`, `gender_id`, `teMobile`, `teEmail`, `teEduQualifi`, `teProfQualifi`, `subjects_Subno`, `reg_datetime`) VALUES
	('TE_2_6779', 'Sumudu', 'Kumarasinghe', '19867364363', '1986-03-04', 1, '076253415', 'sumdu@gmail.com', 'BSc ', 'software', 1, '2023-07-12 00:16:34'),
	('TE_3_2772', 'Prasanna', 'Sumudu', '1972552266v', '1973-05-03', 1, '0762534125', 'prasanna@gmail.com', 'BSc. SE', 'Sofware engineer', 1, '2023-07-14 10:10:58'),
	('TE_3_9173', 'asasa', 'sasasa', '23232', '2023-07-12', 2, '1211212', 'addsdsds', 'sdsdsd', 'sdsds', 3, '2023-07-12 01:36:51'),
	('TE_4_5259', 'Indrajith', 'Chathuranga', '19862535342', '1986-07-23', 1, '0764353245', 'chathu@gmail.com', 'Bsc', 'Teacher', 3, '2023-07-14 10:16:21'),
	('TE_5_3663', 'Thissa', 'Jananayake', '197256732v', '1986-07-23', 1, '0772121222', 'thissa@gmail.com', 'Masters', 'Teacher', 2, '2023-07-14 10:17:58'),
	('TE_6_5075', 'Thilini', 'Imasha', '19896255232', '1989-07-10', 2, '0714253652', 'thilini@gmail.com', 'Bsc', 'school Teacher', 6, '2023-07-14 10:22:08'),
	('TE_7_6351', 'Prasanna ', 'Sumudu', '19763456v', '1976-03-09', 1, '0762364567', 'prasanna@gmail.com', 'Bsc Hons', 'Software engineer', 8, '2023-07-19 18:16:55');

-- Dumping structure for table adyapana_institute_db.teacher_address
CREATE TABLE IF NOT EXISTS `teacher_address` (
  `id` int NOT NULL AUTO_INCREMENT,
  `line1` text,
  `line2` text,
  `city_id` int NOT NULL,
  `teachers_Tno` varchar(15) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_teacher_address_city1_idx` (`city_id`),
  KEY `fk_teacher_address_teachers1_idx` (`teachers_Tno`),
  CONSTRAINT `fk_teacher_address_city1` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `fk_teacher_address_teachers1` FOREIGN KEY (`teachers_Tno`) REFERENCES `teachers` (`Tno`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.teacher_address: ~6 rows (approximately)
REPLACE INTO `teacher_address` (`id`, `line1`, `line2`, `city_id`, `teachers_Tno`) VALUES
	(2, 'LIne1', 'Line2', 1, 'TE_2_6779'),
	(5, 'Airforce RD', '', 1, 'TE_3_2772'),
	(6, 'kalattewa', 'Kurundankulama', 1, 'TE_4_5259'),
	(7, 'line1', '', 2, 'TE_5_3663'),
	(8, 'Abaya Mawatha', 'Nelumkulama', 1, 'TE_6_5075'),
	(9, '73/3A', 'Airport RD', 1, 'TE_7_6351');

-- Dumping structure for table adyapana_institute_db.timelots
CREATE TABLE IF NOT EXISTS `timelots` (
  `id` int NOT NULL AUTO_INCREMENT,
  `time` time DEFAULT NULL,
  `day_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_timeLots_day1_idx` (`day_id`),
  CONSTRAINT `fk_timeLots_day1` FOREIGN KEY (`day_id`) REFERENCES `day` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;

-- Dumping data for table adyapana_institute_db.timelots: ~9 rows (approximately)
REPLACE INTO `timelots` (`id`, `time`, `day_id`) VALUES
	(7, '04:21:00', 4),
	(8, '08:00:00', 7),
	(9, '07:30:00', 2),
	(10, '03:30:00', 4),
	(11, '03:00:00', 5),
	(12, '18:00:00', 2),
	(13, '08:00:00', 7),
	(14, '08:30:00', 3),
	(15, '04:00:00', 1);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
