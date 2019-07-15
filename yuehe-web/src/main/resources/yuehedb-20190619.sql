-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: yuehedb
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `account` (
  `id` int(11) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  `created` varchar(45) DEFAULT NULL COMMENT '用于登陆悦和网站',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='系统账户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'user','demo','ROLE_USER',NULL),(2,'admin','admin','ROLE_ADMIN',NULL);
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `beautifyskinitem`
--

DROP TABLE IF EXISTS `beautifyskinitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `beautifyskinitem` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='美肤项目表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `beautifyskinitem`
--

LOCK TABLES `beautifyskinitem` WRITE;
/*!40000 ALTER TABLE `beautifyskinitem` DISABLE KEYS */;
INSERT INTO `beautifyskinitem` VALUES ('xm001','表皮斑',4150,'一般是24900 6次，单价算下来为4150'),('xm002','真皮斑',4967,'一般是29800 6次，单价算下来为4967'),('xm003','代谢',4150,'一般是24900 6次，单价算下来为4150'),('xm004','混合斑',4967,'一般是29800 6次，单价算下来为4967，混合斑即为表皮斑+真皮斑'),('xm005','花青素',1000,'花青素属于产品，也可算作美肤项目的一种'),('xm006','水光',2419,'水光的价格应该和表皮斑差不多');
/*!40000 ALTER TABLE `beautifyskinitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `client` (
  `id` varchar(7) NOT NULL,
  `shop_id` varchar(5) NOT NULL,
  `name` varchar(20) NOT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `symptom` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `client-shop_idx` (`shop_id`),
  CONSTRAINT `client-shop` FOREIGN KEY (`shop_id`) REFERENCES `cosmeticshop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('kh00001','mr001','戴棠',50,'女性','表皮斑'),('kh00002','mr001','邱希玟',50,'女性','混合斑'),('kh00003','mr001','于京京',52,'女性','黑眼圈（表皮斑）黄气 '),('kh00004','mr001','陆妍洁',54,'女性','表皮斑 黄气 黑眼圈'),('kh00005','mr001','王建霞',55,'女性','真皮斑+黑眼圈,汗管瘤'),('kh00006','mr001','施静',57,'女性','真皮斑+黑眼圈+黄气+循环代谢'),('kh00007','mr001','官媛媛',43,'女性','黄气');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cosmeticshop`
--

DROP TABLE IF EXISTS `cosmeticshop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cosmeticshop` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) NOT NULL,
  `owner` varchar(45) NOT NULL,
  `contact_method` varchar(11) DEFAULT NULL,
  `location` varchar(10) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='美容院表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cosmeticshop`
--

LOCK TABLES `cosmeticshop` WRITE;
/*!40000 ALTER TABLE `cosmeticshop` DISABLE KEYS */;
INSERT INTO `cosmeticshop` VALUES ('mr001','熙泉','A女士','11323423333','江苏南京',500,0.4,'累积总额达50万需要返点5万给店家'),('mr002','肤语港','B女士','13232232332','江苏南京',400,0.4,NULL),('mr003','舒心厅','华星','13234232214','江苏无锡',600,0.4,NULL);
/*!40000 ALTER TABLE `cosmeticshop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `resigned` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('yg001','钟悦旻',10000,'1983-11-14','悦和创始人','n'),('yg002','陈康健',10000,'1981-01-01','悦和合伙人','n'),('yg003','石小兰',10000,'1987-01-01','悦和合伙人','n'),('yg004','李明亚',10000,'1990-01-01','悦和合伙人','n'),('yg005','钟翼翔',3500,'1986-06-18','悦和打工仔','n'),('yg006','钟鑫',4000,'1990-01-01','悦和之前的操作人，医生','y'),('yg007','曾文',4000,'1986-04-01','悦和之前的操作人，医生','y'),('yg008','杨波',3500,'1990-01-01','悦和之前的销售','y');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation`
--

DROP TABLE IF EXISTS `operation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `operation` (
  `id` varchar(8) NOT NULL,
  `sale_id` varchar(7) NOT NULL,
  `operator_id` varchar(5) NOT NULL,
  `tool_id` varchar(5) NOT NULL,
  `operation_date` date DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `operation-sale_idx` (`sale_id`),
  KEY `operation-operator_idx` (`operator_id`),
  KEY `operation-tool_idx` (`tool_id`),
  CONSTRAINT `operation-operator` FOREIGN KEY (`operator_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `operation-sale` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`),
  CONSTRAINT `operation-tool` FOREIGN KEY (`tool_id`) REFERENCES `tool` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='操作表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation`
--

LOCK TABLES `operation` WRITE;
/*!40000 ALTER TABLE `operation` DISABLE KEYS */;
INSERT INTO `operation` VALUES ('cz000001','xs00001','yg006','gj002','2016-10-25','钟鑫操作'),('cz000002','xs00001','yg006','gj002','2016-11-22','钟鑫操作'),('cz000003','xs00001','yg006','gj002','2017-03-05','钟鑫操作'),('cz000004','xs00001','yg006','gj002','2017-04-10','钟鑫操作'),('cz000005','xs00001','yg006','gj002','2017-05-05','钟鑫操作'),('cz000006','xs00002','yg001','gj002','2016-10-25','钟悦旻操作'),('cz000007','xs00002','yg001','gj002','2016-11-23','钟悦旻操作'),('cz000008','xs00002','yg006','gj002','2016-12-30','钟鑫操作');
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--
DROP TABLE IF EXISTS `duty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `duty` (
	`id` varchar(5) NOT NULL,
  `employee_id` varchar(5) NOT NULL,
  `role_id` varchar(5) NOT NULL,
  `welfare` int(11) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee-role_idx` (`role_id`),
  CONSTRAINT `employee-role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role-employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='职责分工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `duty` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `duty` VALUES ('zz001','yg001','js001','','悦总为公司创始人'),('zz002','yg001','js003','','悦总同时也是公司销售专家'),('zz003','yg002','js002','','陈康健为公司合伙人'),('zz004','yg002','js003','','陈康健为公司销售专家'),('zz005','yg002','js007','','陈康健为公司司机'),('zz006','yg003','js002','','石小兰为公司合伙人'),('zz007','yg003','js004','','石小兰为公司医生，操作人'),('zz008','yg004','js002','','李明亚为公司合伙人'),('zz009','yg004','js004','','李明亚为公司医生，操作人'),('zz010','yg005','js005','','钟翼翔为公司财务'),('zz011','yg005','js006','','钟翼翔为公司技术');
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `profile` (
  `sale_id` varchar(7) NOT NULL,
  `rest_item_amount` int(11) NOT NULL,
  `create_profile_date` date DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`sale_id`),
  CONSTRAINT `profile-sale` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='档案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
INSERT INTO `profile` VALUES ('xs00001',0,'2016-10-25','这里作为档案表的一部分，完整部分可在前台构建'),('xs00002',0,'2016-10-25',NULL),('xs00003',0,'2016-10-25',NULL);
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `role` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `responsibility` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES ('js001','创始人','管理公司','创业老板'),('js002','合伙人','运营管理日常事务','合伙人'),('js003','专家','销售美肤项目','做业绩，美肤方面的专家，制定销售策略，指导美容店员工专业知识'),('js004','操作人','操作各种美肤仪器','皮肤领域专家，培训店内员工，操作客人'),('js005','财务','管理公司财务支出','各项财务相关数据汇总管理'),('js006','技术','公司网站，文案，广告','办公室杂项，技术相关'),('js007','司机','驾驶公司车辆，运送仪器，公司员工，维护车辆','维护车辆各项事宜');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sale`
--

DROP TABLE IF EXISTS `sale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sale` (
  `id` varchar(7) NOT NULL,
  `client_id` varchar(7) NOT NULL,
  `item_id` varchar(5) NOT NULL,
  `seller_id` varchar(5) NOT NULL,
  `item_number` int(11) NOT NULL,
  `discount` float DEFAULT '1',
  `employee_premium` float DEFAULT NULL,
  `shop_premium` float DEFAULT NULL,
  `received_amount` int(11) NOT NULL,
  `create_card_date` date NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `sale-seller_idx` (`seller_id`),
  KEY `sale-item_idx` (`item_id`),
  KEY `sale-client_idx` (`client_id`),
  CONSTRAINT `sale-client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `sale-item` FOREIGN KEY (`item_id`) REFERENCES `beautifyskinitem` (`id`),
  CONSTRAINT `sale-seller` FOREIGN KEY (`seller_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES ('xs00001','kh00001','xm001','yg001',5,0.81,0,0,16800,'2016-10-25','这里的折扣为计算来的，开卡5次16800，而表皮斑的单次价格为4150，折扣=16800/4150*5'),('xs00002','kh00002','xm004','yg001',6,0.66,0,0,19800,'2016-10-25','开卡4次，赠送2次，之前的开卡次数耗完了，效果不好，然后免费再给她做的售后'),('xs00003','kh00002','xm005','yg001',1,1,0,0,1000,'2016-10-25','销售的为产品花青素，同样需要与店家分成，按折扣计算后返回400');
/*!40000 ALTER TABLE `sale` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tool`
--

DROP TABLE IF EXISTS `tool`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tool` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `major` varchar(50) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `buy_date` date DEFAULT NULL,
  `buy_from` varchar(20) DEFAULT NULL,
  `operate_expense` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='仪器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tool`
--

LOCK TABLES `tool` WRITE;
/*!40000 ALTER TABLE `tool` DISABLE KEYS */;
INSERT INTO `tool` VALUES ('gj001','c6','表皮斑，真皮斑',300000,'2017-01-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次30'),('gj002','皮秒','各种皮肤斑',200000,'2016-12-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次30'),('gj003','超声刀','紧凑皮肤',50000,'2016-12-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次50'),('gj004','水光针','皮肤补水',20000,'2016-12-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次30'),('gj005','二氧化碳仪','疣，痣',10000,'2016-12-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次30');
/*!40000 ALTER TABLE `tool` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-19 17:50:01
