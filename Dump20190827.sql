-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: yuehe
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
-- Table structure for table `beautifyskinitem`
--

DROP TABLE IF EXISTS `beautifyskinitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `beautifyskinitem` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `price` bigint(11) DEFAULT NULL,
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
INSERT INTO `beautifyskinitem` VALUES ('xm001','表皮斑',4150,'一般是24900 6次，单价算下来为4150'),('xm002','真皮斑',4967,'一般是29800 6次，单价算下来为4967'),('xm003','代谢',4150,'一般是24900 6次，单价算下来为4150'),('xm004','混合斑',4967,'一般是29800 6次，单价算下来为4967，混合斑即为表皮斑+真皮斑'),('xm005','花青素',1000,'花青素属于产品，也可算作美肤项目的一种'),('xm006','水光',2419,'水光的价格应该和表皮斑差不多'),('xm007','线雕',2343,'就是水光'),('xm008','疣',300,'一般是包年'),('xm009','点痣',3432,'包年为主'),('xm010','面膜',200,'补水保湿'),('xm011','无针水光',10000,'补水保湿'),('xm012','黄褐斑',4967,'黄褐斑为活性斑建议长期管理'),('xm013','黄褐斑代谢',3300,'黄褐斑代谢建议长期保养'),('xm014','至尊水雕',3300,'有利于细胞活力与色素代谢'),('xm015','黄气暗哑',3300,'此项目可做日常保养，辅助各类斑的治疗'),('xm016','汗管瘤',19800,'价格因量的多少来定少量19800，中量29800，大量59800不等');
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
  `name` varchar(20) CHARACTER SET gbk COLLATE gbk_bin NOT NULL,
  `age` int(11) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `symptom` varchar(100) DEFAULT NULL,
  `client_id` bigint(20) NOT NULL,
  `client_questionare` tinyblob,
  `questionare_client` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `client-shop_idx` (`shop_id`),
  KEY `FKpvy022125ljems4gfnlluwtdb` (`questionare_client`),
  CONSTRAINT `FKpvy022125ljems4gfnlluwtdb` FOREIGN KEY (`questionare_client`) REFERENCES `client_questionare` (`id`),
  CONSTRAINT `client-shop` FOREIGN KEY (`shop_id`) REFERENCES `cosmeticshop` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('kh00001','mr001','戴棠',45,'f','表皮斑',0,NULL,NULL),('kh00002','mr001','邱希玟',50,'f','混合斑',0,NULL,NULL),('kh00003','mr001','于京京',52,'f','黑眼圈（表皮斑）黄气 ',0,NULL,NULL),('kh00004','mr001','陆妍洁',54,'f','表皮斑 黄气 黑眼圈',0,NULL,NULL),('kh00005','mr001','王建霞',55,'f','真皮斑+黑眼圈,汗管瘤',0,NULL,NULL),('kh00006','mr001','施静',57,'f','真皮斑+黑眼圈+黄气+循环代谢',0,NULL,NULL),('kh00007','mr001','官媛媛',43,'f','黄气',0,NULL,NULL),('kh00008','mr006','祝燕',30,'f','雀斑',0,NULL,NULL),('kh00009','mr003','钟悦旻',34,'f','test',0,NULL,NULL),('kh00010','mr004','黄锦燕',38,'f','表皮斑',0,NULL,NULL),('kh00011','mr004','王秀娟',32,'f','修复',0,NULL,NULL),('kh00012','mr004','李燕',24,'f','黄褐斑',0,NULL,NULL),('kh00013','mr004','顾昕怡',28,'f','表皮斑',0,NULL,NULL),('kh00014','mr005','陈晓芳',38,'f','表皮斑',0,NULL,NULL),('kh00015','mr005','樊玉琴',23,'f','扁平疣少量',0,NULL,NULL),('kh00016','mr005','陈建林',65,'f','痣包年',0,NULL,NULL),('kh00017','mr003','司红芳',35,'f','表皮斑，真皮斑，混合斑',0,NULL,NULL),('kh00018','mr002','陆倩慧',34,'f','混合斑',0,NULL,NULL),('kh00019','mr002','郭鸣',37,'f','表皮斑',0,NULL,NULL),('kh00020','mr005','徐继英',32,'f','黄褐斑',0,NULL,NULL),('kh00021','mr001','于得水',32,'f','黄褐斑',0,NULL,NULL),('kh00022','mr007','韩国玉',40,'m','黄褐斑',0,NULL,NULL),('kh00023','mr007','李四',32,'f','黄褐斑',0,NULL,NULL),('kh00024','mr003','祝燕子',40,'f','黄褐斑',0,NULL,NULL),('kh00025','mr003','钱钟书',32,'m','黄褐斑',0,NULL,NULL),('kh00026','mr003','李祝钟',40,'m','黄褐斑',0,NULL,NULL),('kh00027','mr004','杨晓波',47,'f','表皮斑',0,NULL,NULL),('kh00028','mr009','许小芊',33,'f','表皮斑，黄气',0,NULL,NULL),('kh00029','mr001','火金俐',45,'f','黄褐斑',0,NULL,NULL),('kh00030','mr004','蒋文仙',42,'f','真皮斑',0,NULL,NULL),('kh00031','mr004','何建琴',45,'f','黄褐斑敏感',0,NULL,NULL);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_questionare`
--

DROP TABLE IF EXISTS `client_questionare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `client_questionare` (
  `id` bigint(20) NOT NULL,
  `client_id` varchar(7) NOT NULL,
  `usual_beautify_item` varchar(50) DEFAULT NULL,
  `if_alergic_body` varchar(1) DEFAULT NULL,
  `if_alergic_skin` varchar(1) DEFAULT NULL,
  `alergic_source` varchar(50) DEFAULT NULL,
  `medicine_name` varchar(15) DEFAULT NULL,
  `if_healthy` varchar(1) DEFAULT NULL,
  `if_had_medicine` varchar(1) DEFAULT NULL,
  `if_pregnant_or_breast_feeding` varchar(1) DEFAULT NULL,
  `if_used_whitening_product` varchar(1) DEFAULT NULL,
  `if_sun_exposure` varchar(1) DEFAULT NULL,
  `if_sun_protection` varchar(1) DEFAULT NULL,
  `if_sun_burn_recently` varchar(1) DEFAULT NULL,
  `if_scab_body` varchar(1) DEFAULT NULL,
  `eating_situation` varchar(50) DEFAULT NULL,
  `sleep_situation` varchar(50) DEFAULT NULL,
  `digest_situation` varchar(50) DEFAULT NULL,
  `incretion_situation` varchar(50) DEFAULT NULL,
  `practise_situation` varchar(50) DEFAULT NULL,
  `working_env` varchar(50) DEFAULT NULL,
  `common_used_skin_care_products` varchar(45) DEFAULT NULL,
  `practise_methods` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `questionare_client_idx` (`client_id`),
  CONSTRAINT `questionare_client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户皮肤情况问卷调查表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_questionare`
--

LOCK TABLES `client_questionare` WRITE;
/*!40000 ALTER TABLE `client_questionare` DISABLE KEYS */;
INSERT INTO `client_questionare` VALUES (15,'kh00002','黄褐斑','1','1','powder;food','发','0','1','0','1','0',NULL,'0','1','meat','dream','constipation','gynecologyIssue','0','SunExposure','花青素','发'),(16,'kh00001','黄褐斑代谢代谢2','1','1','powder;food','青霉素','1','1','1','1','1','1','1','1','vegetable;meat;fruit;fry','jetleg;dream;late','average;constipation','abnormal;gynecologyIssue','1','computer','花青素','发发发'),(18,'kh00018','黄褐斑,代谢','0','1','accesory;powder;skinCareProduct','青霉素','0','1','0','1','0',NULL,'0','0','vegetable;fruit;fry','jetleg;late','average;constipation','abnormal;gynecologyIssue','0','sunExposure;businessTrip','面膜','跳绳'),(19,'kh00019','黄褐斑,代谢','1','1','powder;skinCareProduct;food;medicine','青霉素','1','1','1','1','1','1','1','1','vegetable;meat','jetleg','average;constipation','gynecologyIssue','1','sunExposure;businessTrip','花青素','羽毛球');
/*!40000 ALTER TABLE `client_questionare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cosmeticshop`
--

DROP TABLE IF EXISTS `cosmeticshop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cosmeticshop` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) CHARACTER SET gbk COLLATE gbk_bin NOT NULL,
  `owner` varchar(45) NOT NULL,
  `contact_method` varchar(11) DEFAULT NULL,
  `location` varchar(10) DEFAULT NULL,
  `size` varchar(20) DEFAULT NULL,
  `member_number` int(11) DEFAULT NULL,
  `discount` float DEFAULT NULL,
  `shop_premium` float DEFAULT NULL COMMENT '例如肤语港，总业绩超过50万时，需要返还5万给店家',
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
INSERT INTO `cosmeticshop` VALUES ('mr001','熙泉','A女士','11323423333','江苏南京','800平',500,0.4,NULL,'累积总额达50万需要返点5万给店家'),('mr002','肤语港','B女士','13232232332','江苏南京','200平',400,0.4,NULL,'早期的店家，算是启蒙店'),('mr003','舒心厅','华星','13234232214','江苏无锡','600',600,0.4,0,'佛系美容院老板，人很善良正直有信仰'),('mr004','柳叶','叶显帧','13634151701','浙江杭州新安江','800',300,0.4,NULL,'柳叶香薰是近20年的老店，客户及团队相对都稳定'),('mr005','至尊丽人','C女士','13243234232','浙江桐乡','1000',1000,0.4,0,'之前钟云根跑的店，店的质量还可以'),('mr006','菲梵','D女士','1232324324','江苏常州','1000',1000,0.5,0,'之前钟鑫开发的店，店老板和悦总关系不错，楼下经营一家服装设计店'),('mr007','真女人','E女士','13402934832','江苏苏州','300',300,0.4,NULL,'张家港新店'),('mr008','蕊曼','F女士','1320402348','江苏常州','300',300,0.4,0,'常州新店'),('mr009','金陵世玺','魏老师','233223423','江苏南京','500',500,0.45,0,'新店');
/*!40000 ALTER TABLE `cosmeticshop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `duty`
--

DROP TABLE IF EXISTS `duty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `duty` (
  `id` varchar(5) NOT NULL,
  `employee_id` varchar(5) NOT NULL,
  `role_id` varchar(5) NOT NULL,
  `welfare` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `employee-role_idx` (`role_id`),
  KEY `role-employee` (`employee_id`),
  CONSTRAINT `employee-role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role-employee` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='职责分工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `duty`
--

LOCK TABLES `duty` WRITE;
/*!40000 ALTER TABLE `duty` DISABLE KEYS */;
INSERT INTO `duty` VALUES ('zz001','yg001','js001',100,'悦总为公司创始人'),('zz002','yg001','js003',1000,'悦总同时也是公司销售专家'),('zz003','yg002','js002',100,'陈康健为公司合伙人'),('zz004','yg002','js003',100,'陈康健为公司销售专家'),('zz005','yg002','js007',2000,'陈康健为公司司机'),('zz006','yg003','js002',100,'石小兰为公司合伙人'),('zz007','yg003','js004',100,'石小兰为公司医生，操作人'),('zz008','yg004','js002',100,'李明亚为公司合伙人'),('zz009','yg004','js004',100,'李明亚为公司医生，操作人'),('zz010','yg005','js005',100,'钟翼翔为公司财务'),('zz011','yg005','js006',100,'钟翼翔为公司技术'),('zz012','yg005','js007',300,'测试数据'),('zz013','yg001','js007',1000,'分两条线走，悦总为司机'),('zz014','yg003','js003',0,''),('zz015','yg006','js004',0,'之前的操作医生，已离职'),('zz016','yg007','js004',0,'之前的操作医生，已离职'),('zz017','yg008','js004',0,'之前的操作医生，已离职');
/*!40000 ALTER TABLE `duty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `employee` (
  `id` varchar(5) NOT NULL,
  `name` varchar(20) CHARACTER SET gbk COLLATE gbk_bin DEFAULT NULL,
  `salary` int(11) DEFAULT NULL,
  `birthday` varchar(11) DEFAULT NULL,
  `resigned` varchar(1) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('yg001','钟悦旻',10000,'1983-11-14','n','悦和创始人'),('yg002','陈康健',10000,'1981-01-01','n','悦和合伙人'),('yg003','石小兰',10000,'1987-01-01','n','悦和合伙人'),('yg004','李明亚',10000,'1990-01-01','n','悦和合伙人'),('yg005','钟翼翔',3500,'1986-06-18','n','悦和打工仔'),('yg006','钟鑫',4000,'1990-01-01','y','悦和之前的操作人，医生'),('yg007','曾文',4000,'1986-04-01','y','悦和之前的操作人，医生'),('yg008','张波',3500,'1990-01-01','y','悦和之前的销售'),('yg009','祝燕',3000,'1988-02-16','y','悦和之前的助理');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (20);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
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
  `operation_date` varchar(20) DEFAULT NULL,
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
INSERT INTO `operation` VALUES ('cz000001','xs00001','yg006','gj002','2016-10-25','钟鑫操作'),('cz000002','xs00001','yg006','gj002','2016-11-22','钟鑫操作'),('cz000003','xs00001','yg006','gj002','2017-03-05','钟鑫操作'),('cz000004','xs00001','yg006','gj002','2017-04-10','钟鑫操作'),('cz000005','xs00001','yg006','gj002','2017-05-05','钟鑫操作'),('cz000006','xs00002','yg001','gj002','2016-10-25','钟悦旻操作'),('cz000007','xs00002','yg001','gj002','2016-11-23','钟悦旻操作'),('cz000008','xs00002','yg006','gj002','2016-12-30','钟鑫操作'),('cz000009','xs00004','yg004','gj001','2019-03-20','测试数据'),('cz000010','xs00004','yg003','gj001','2019-04-22','测试数据'),('cz000011','xs00004','yg004','gj001','2019-05-22','测试数据'),('cz000012','xs00004','yg003','gj001','2019-06-22','测试数据'),('cz000013','xs00004','yg004','gj001','2019-06-25','测试数据'),('cz000014','xs00006','yg004','gj002','2019-04-12','测试数据'),('cz000015','xs00006','yg004','gj002','2019-05-12','测试数据'),('cz000016','xs00009','yg003','gj001','2019-05-13','测试数据'),('cz000017','xs00007','yg004','gj002','2019-05-12','测试数据'),('cz000018','xs00010','yg003','gj002','2019-04-12','测试数据'),('cz000019','xs00009','yg004','gj002','2019-05-12','测试数据'),('cz000020','xs00008','yg004','gj002','2019-04-12','测试数据'),('cz000021','xs00001','yg003','gj001','2019-07-01','test'),('cz000022','xs00008','yg003','gj002','2019-07-02','test'),('cz000023','xs00006','yg004','gj002','2019-06-04','test'),('cz000024','xs00008','yg004','gj003','2019-05-07','test'),('cz000025','xs00011','yg004','gj004','2019-05-21','test'),('cz000026','xs00002','yg003','gj003','2019-05-15','test'),('cz000027','xs00008','yg003','gj002','2019-06-03','test'),('cz000029','xs00015','yg003','gj004','2019-08-15','ggyg'),('cz000030','xs00035','yg004','gj001','2018-10-19','体验'),('cz000031','xs00035','yg004','gj001','2018-11-30',''),('cz000032','xs00035','yg004','gj007','2019-01-10','黄褐斑为活性斑建议长期管理'),('cz000033','xs00035','yg004','gj007','2019-03-08','无'),('cz000034','xs00035','yg004','gj007','2019-04-17','无'),('cz000035','xs00035','yg004','gj001','2019-05-24','无'),('cz000036','xs00036','yg004','gj008','2019-05-24','包括体验卡的一次'),('cz000037','xs00037','yg004','gj001','2019-06-28','5月份活动色素管控体验卡赠送'),('cz000038','xs00038','yg006','gj002','2017-05-05','2016开卡时做的黑眼圈不算卡耗'),('cz000039','xs00038','yg006','gj002','2017-06-08','此混合斑（包括表皮斑黑眼圈黄气）'),('cz000040','xs00038','yg006','gj002','2017-07-14','此混合斑（包括表皮斑黑眼圈黄气）'),('cz000041','xs00038','yg006','gj002','2017-08-17','此混合斑（包括表皮斑黑眼圈黄气）'),('cz000042','xs00038','yg006','gj002','2017-10-13','此混合斑为综合卡'),('cz000043','xs00040','yg006','gj002','2017-03-26','无'),('cz000044','xs00040','yg006','gj002','2017-04-23','无'),('cz000045','xs00042','yg006','gj002','2017-04-23','此卡为开真皮斑混合斑赠送'),('cz000046','xs00042','yg006','gj002','2017-05-30','背部扫黄为赠送项目'),('cz000047','xs00042','yg006','gj002','2017-06-17','背部扫黄为赠送项目'),('cz000048','xs00040','yg006','gj002','2017-06-17','此真皮斑在档案上标为混合斑'),('cz000049','xs00040','yg006','gj002','2017-08-25','此真皮斑在档案上标为混合斑'),('cz000050','xs00040','yg006','gj002','2017-09-24','此真皮斑在档案上标为混合斑'),('cz000051','xs00040','yg006','gj002','2017-10-29','此真皮斑在档案上标为混合斑'),('cz000052','xs00040','yg006','gj002','2018-05-20','18年3月份有送1次代谢不算耗卡'),('cz000053','xs00040','yg004','gj001','2018-10-13','此真皮斑在档案上标为混合斑'),('cz000054','xs00040','yg004','gj001','2018-11-18','此真皮斑在档案上标为混合斑'),('cz000055','xs00040','yg004','gj001','2018-12-15','此真皮斑在档案上标为混合斑'),('cz000056','xs00040','yg004','gj001','2019-04-13','同时还送了一次水雕'),('cz000057','xs00040','yg003','gj001','2019-05-11','此卡已做完'),('cz000058','xs00043','yg004','gj007','2019-01-18','此方案效果为角质层修复为正常，黄褐斑淡化6-8成，具体进度要根据客人补水防晒配合来决定'),('cz000059','xs00043','yg003','gj001','2019-03-19','此次做的斑'),('cz000060','xs00043','yg003','gj008','2019-04-13','修复换为水雕体验'),('cz000061','xs00043','yg003','gj001','2019-06-09','无'),('cz000062','xs00043','yg003','gj001','2019-07-12','无'),('cz000063','xs00043','yg003','gj001','2019-08-10','建议长期补水'),('cz000064','xs00005','yg004','gj001','2018-10-12','李老师指导严老师操作的'),('cz000065','xs00005','yg004','gj001','2018-11-24','李老师指导严老师操作的'),('cz000066','xs00005','yg004','gj001','2008-12-14','李老师指导严老师操作的'),('cz000067','xs00005','yg004','gj001','2019-01-19','无'),('cz000068','xs00016','yg006','gj001','2018-03-24','5次卡里包含了1次体验卡里的'),('cz000069','xs00016','yg006','gj001','2018-04-08','无'),('cz000070','xs00016','yg006','gj001','2018-05-19','无'),('cz000071','xs00016','yg004','gj001','2018-06-28','无'),('cz000072','xs00016','yg004','gj001','2018-09-02','无'),('cz000073','xs00016','yg003','gj008','2019-08-10','4月份活动680卡店里项目换'),('cz000074','xs00033','yg003','gj001','2019-08-11','皮肤肤色白，底层有'),('cz000075','xs00033','yg003','gj001','2019-08-11','皮肤肤色白，从未做过仪器保养，两颧部有黄褐斑');
/*!40000 ALTER TABLE `operation` ENABLE KEYS */;
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
  `rest_card_amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`sale_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='档案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
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
INSERT INTO `role` VALUES ('js001','创始人','管理公司','创业老板'),('js002','合伙人','运营管理日常事务','合伙人'),('js003','专家','销售美肤项目','做业绩，美肤方面的专家，制定销售策略，指导美容店员工专业知识'),('js004','操作人','操作各种美肤仪器','皮肤领域专家，培训店内员工，操作客人'),('js005','财务','管理公司财务支出','各项财务相关数据汇总管理'),('js006','技术','公司网站，文案，广告','办公室杂项，技术相关'),('js007','司机','驾驶公司车辆，运送仪器，公司员工，维护车辆','维护车辆各项事宜'),('js008','文案','宣传推广','运营公众号，网站');
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
  `beautify_skin_item_id` varchar(5) NOT NULL,
  `seller_id` varchar(5) NOT NULL,
  `item_number` int(11) NOT NULL,
  `create_card_total_amount` bigint(11) DEFAULT '1',
  `employee_premium` float DEFAULT NULL,
  `shop_premium` float DEFAULT NULL COMMENT '不同店家计算回扣方法不一样，比如柳叶每一笔销售都要扣除1%，而肤语港是超过50万返还5万，用此表来计算不妥，所以在cosmeticShop表中加了1列shop_premium',
  `received_amount` bigint(11) NOT NULL,
  `received_earned_amount` bigint(11) DEFAULT NULL COMMENT '用来表示店家给悦和的实际回款',
  `create_card_date` varchar(25) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `sale-seller_idx` (`seller_id`),
  KEY `sale-item_idx` (`beautify_skin_item_id`),
  KEY `sale-client_idx` (`client_id`),
  CONSTRAINT `sale-client` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  CONSTRAINT `sale-item` FOREIGN KEY (`beautify_skin_item_id`) REFERENCES `beautifyskinitem` (`id`),
  CONSTRAINT `sale-seller` FOREIGN KEY (`seller_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sale`
--

LOCK TABLES `sale` WRITE;
/*!40000 ALTER TABLE `sale` DISABLE KEYS */;
INSERT INTO `sale` VALUES ('xs00001','kh00001','xm001','yg002',3,16800,12,0,16800,2000,'2016-10-25','这里的折扣为计算来的，开卡5次16800，而表皮斑的单次价格为4150，折扣=16800/4150*5'),('xs00002','kh00002','xm004','yg003',8,22800,0,0,19800,2000,'2016-10-25','开卡4次，赠送2次，之前的开卡次数耗完了，效果不好，然后免费再给她做的售后'),('xs00003','kh00002','xm003','yg001',3,2000,0,0,1000,200,'2016-10-25','销售的为产品花青素，同样需要与店家分成，按折扣计算后返回400'),('xs00004','kh00008','xm001','yg002',6,14000,100,0,8000,2000,'2017-12-31','测试数据'),('xs00005','kh00010','xm001','yg001',5,17780,177,0,17780,7112,'2018-10-10','无'),('xs00006','kh00017','xm004','yg001',5,29900,0,0,29900,2000,'2017-04-11','测试数据'),('xs00007','kh00019','xm001','yg002',6,26800,0,0,24800,2000,'2018-04-22','测试数据'),('xs00008','kh00018','xm004','yg001',13,34800,0,0,30000,2000,'2017-11-17','测试数据'),('xs00009','kh00018','xm003','yg001',6,10000,0,0,10000,2000,'2018-04-20','测试数据'),('xs00010','kh00014','xm001','yg001',6,20000,0,0,20000,2000,'2018-9-26','测试数据'),('xs00011','kh00018','xm002','yg001',6,19800,100,100,3000,3000,'2019-07-01','test'),('xs00012','kh00017','xm002','yg002',6,29800,200,200,5000,5000,'2019-06-10','test'),('xs00013','kh00015','xm002','yg001',6,19800,100,200,10000,3000,'2019-03-05','test'),('xs00014','kh00003','xm002','yg002',6,29800,100,200,10000,3000,'2019-05-14','test'),('xs00015','kh00015','xm002','yg003',3,19800,100,100,10000,3000,'2019-07-01','test'),('xs00016','kh00013','xm001','yg001',5,20580,205,0,20580,8232,'2018-03-24','test'),('xs00017','kh00011','xm003','yg002',3,19800,150,100,10000,5000,'2019-07-10','test'),('xs00018','kh00017','xm002','yg001',3,19800,150,200,10000,3000,'2019-07-09','test'),('xs00019','kh00018','xm002','yg002',9,19800,150,200,10000,3000,'2019-07-07','test'),('xs00020','kh00016','xm004','yg003',3,19800,150,200,10000,5000,'2019-07-01','test'),('xs00021','kh00020','xm004','yg001',7,29800,150,200,15000,5000,'2019-07-01','test'),('xs00023','kh00017','xm002','yg001',8,19800,100,100,2000,1000,'2019-07-08','test'),('xs00024','kh00011','xm004','yg002',6,14000,100,100,8800,1000,'2019-07-09','test'),('xs00025','kh00015','xm003','yg001',8,19800,100,100,8800,5000,'2019-07-08','test'),('xs00027','kh00009','xm002','yg003',5,29800,100,100,20000,5000,'2019-07-08','test'),('xs00028','kh00019','xm003','yg001',5,20000,100,100,20000,5000,'2019-07-15','test'),('xs00029','kh00022','xm002','yg001',5,29800,100,100,20000,5000,'2019-07-23','test'),('xs00030','kh00024','xm003','yg003',8,14000,100,100,10000,3000,'2019-07-09','test'),('xs00031','kh00025','xm001','yg002',8,19800,100,100,8800,5000,'2019-08-01','常州新店'),('xs00032','kh00026','xm003','yg002',3,19800,100,100,8800,5000,'2019-08-12','常州新店'),('xs00033','kh00027','xm001','yg001',8,28000,280,0,28000,11200,'2019-08-11','是叶老师兰心会的朋友，卡里8次里包含送1次水雕和1次代谢'),('xs00034','kh00028','xm001','yg001',4,15000,0,0,15000,3750,'08/02/2019','test'),('xs00035','kh00029','xm013','yg001',13,29800,0,0,29800,11920,'2018-10-19','无'),('xs00036','kh00029','xm014','yg001',11,19800,0,0,19800,7920,'2019-05-24','此卡为特惠卡11次包括680卡里送的1次'),('xs00037','kh00029','xm013','yg001',1,0,0,0,0,0,'2019-05-24','5月份活动色素管控体验卡赠送'),('xs00038','kh00003','xm004','yg001',5,22800,0,0,22800,9120,'2016-10-25','此混合斑（包括表皮斑黑眼圈黄气）'),('xs00039','kh00003','xm005','yg001',1,1000,0,0,1000,400,'2016-10-25','无'),('xs00040','kh00030','xm002','yg001',12,49800,498,0,49800,11920,'2017-03-26','送了6次后背'),('xs00041','kh00030','xm010','yg001',1,498,0,0,498,299,'2017-03-26','面膜是5:5分'),('xs00042','kh00030','xm015','yg001',6,0,0,0,0,0,'2017-03-26','此卡为开真皮斑混合斑赠送'),('xs00043','kh00031','xm013','yg002',12,30000,300,0,30000,12000,'2019-01-18','12次里包含了修复项目'),('xs00044','kh00012','xm013','yg001',22,27500,100,0,10000,5500,'2018-04-05','此卡实际金额为69800，叶老师承担50000，余款按55/45分成，为方便入系统里默认为4/6分将19800，改为27500，这个22次公司也只是收成本'),('xs00045','kh00012','xm008','yg001',1,2500,25,0,2500,1000,'2018-11-17','此卡均为特价卡包括汗管瘤');
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
  `buy_date` varchar(11) DEFAULT NULL,
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
INSERT INTO `tool` VALUES ('gj001','c6','表皮斑，真皮斑',300000,'2017-01-01','广州某厂家',30,'操作费现在不计算，但以后有可能会，之前操作一次30'),('gj002','皮秒','各种皮肤斑',200000,'2016-12-01','广州某厂家',30,'操作费现在不计算，但以后有可能会，之前操作一次30'),('gj003','超声刀','紧凑皮肤',50000,'2016-12-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次50'),('gj004','水光针','皮肤补水',20000,'2016-12-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次30'),('gj005','二氧化碳仪','疣，痣',10000,'2016-12-01','广州某厂家',0,'操作费现在不计算，但以后有可能会，之前操作一次30'),('gj006','面膜','补水',200,'2017-04-12','广州',0,'这算是耗材'),('gj007','射频','代谢 修复',8000,'2018-03-02','广州',0,'代谢 修复'),('gj008','水雕仪','补水营养代谢',10000,'2018-08-02','广州',0,'补水营养代谢');
/*!40000 ALTER TABLE `tool` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` varchar(5) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('yh001','admin','$2a$10$wQa7Ndvc/DJ5a5eXZ5iuNOOKWWf.W5CStdLAvRzDjk7mYeXrRyy36','ROLE_ADMIN'),('yh002','soveran','$2a$10$C/J5InYN8R.QzM7bxk9y2.73P5NPVwo0T3UlvCz5OcnojKbWbP24C','ROLE_ADMIN'),('yh003','yuehe','$2a$10$KthHNTDNFRHpwSkT9BaKbOxSTfCDqe52MSil2rFQtxq5cwWeXPH8e','ROLE_USER'),('yh004','yuangu','$2a$10$1Vxlsn6P2eH./hB7tp1fau1UMvNh.U6xKkoXLEyNxTMW8AG03aOAi','ROLE_USER');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-27 16:35:23
