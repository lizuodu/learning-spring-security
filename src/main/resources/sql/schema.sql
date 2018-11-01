-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        8.0.13 - MySQL Community Server - GPL
-- 服务器操作系统:                      Linux
-- HeidiSQL 版本:                  9.5.0.5295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 security 的数据库结构
DROP DATABASE IF EXISTS `security`;
CREATE DATABASE IF NOT EXISTS `security` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `security`;

-- 导出  表 security.t_sys_permission 结构
DROP TABLE IF EXISTS `t_sys_permission`;
CREATE TABLE IF NOT EXISTS `t_sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `res_type` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  UNIQUE KEY `url_method` (`url`,`method`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- 正在导出表  security.t_sys_permission 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `t_sys_permission` DISABLE KEYS */;
INSERT INTO `t_sys_permission` (`id`, `code`, `description`, `res_type`, `url`, `pid`, `method`) VALUES
	(1, 'home', '', 'menu', '/manage/home.html', 0, 'GET'),
	(2, 'module1', 'module1页面', 'menu', '/manage/module1.html', 0, 'GET'),
	(3, 'module2', 'module2页面', 'menu', '/manage/module2.html', 0, 'GET'),
	(5, 'home:add', NULL, 'button', '/account/add', 0, 'POST'),
	(6, 'home:delete', NULL, 'button', '/account/delete', 0, 'POST');
/*!40000 ALTER TABLE `t_sys_permission` ENABLE KEYS */;

-- 导出  表 security.t_sys_permission_role 结构
DROP TABLE IF EXISTS `t_sys_permission_role`;
CREATE TABLE IF NOT EXISTS `t_sys_permission_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 正在导出表  security.t_sys_permission_role 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `t_sys_permission_role` DISABLE KEYS */;
INSERT INTO `t_sys_permission_role` (`id`, `role_id`, `permission_id`) VALUES
	(1, 1, 1),
	(2, 1, 2),
	(3, 1, 3),
	(4, 1, 4),
	(5, 1, 5),
	(6, 1, 6),
	(7, 2, 1),
	(8, 2, 3);
/*!40000 ALTER TABLE `t_sys_permission_role` ENABLE KEYS */;

-- 导出  表 security.t_sys_role 结构
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE IF NOT EXISTS `t_sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 正在导出表  security.t_sys_role 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `t_sys_role` DISABLE KEYS */;
INSERT INTO `t_sys_role` (`id`, `name`, `desc`) VALUES
	(1, 'ROLE_ADMIN', ' '),
	(2, 'ROLE_USER', 'test');
/*!40000 ALTER TABLE `t_sys_role` ENABLE KEYS */;

-- 导出  表 security.t_sys_user 结构
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE IF NOT EXISTS `t_sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  security.t_sys_user 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `t_sys_user` DISABLE KEYS */;
INSERT INTO `t_sys_user` (`id`, `username`, `password`) VALUES
	(1, 'admin', 'admin'),
	(2, 'aa', '123');
/*!40000 ALTER TABLE `t_sys_user` ENABLE KEYS */;

-- 导出  表 security.t_sys_user_role 结构
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE IF NOT EXISTS `t_sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- 正在导出表  security.t_sys_user_role 的数据：~3 rows (大约)
/*!40000 ALTER TABLE `t_sys_user_role` DISABLE KEYS */;
INSERT INTO `t_sys_user_role` (`id`, `user_id`, `role_id`) VALUES
	(1, 1, 1),
	(2, 2, 2);
/*!40000 ALTER TABLE `t_sys_user_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
