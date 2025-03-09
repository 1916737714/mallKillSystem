-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_seckill
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `cart_id` int NOT NULL AUTO_INCREMENT COMMENT '购物车id',
  `user_id` int NOT NULL COMMENT '用户id',
  `goods_id` int NOT NULL COMMENT '商品id',
  `goods_num` int NOT NULL COMMENT '商品数量',
  PRIMARY KEY (`cart_id`),
  KEY `goods_id` (`goods_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cart_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `cart_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`),
  CONSTRAINT `cart_chk_1` CHECK ((`goods_num` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `goods_id` int NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `description` varchar(50) NOT NULL COMMENT '商品描述',
  `unit` varchar(10) NOT NULL COMMENT '计件单位',
  `picture` varchar(200) NOT NULL COMMENT '商品图片',
  `price` decimal(10,2) NOT NULL COMMENT '商品价格',
  `stock` int NOT NULL COMMENT '商品库存',
  PRIMARY KEY (`goods_id`),
  CONSTRAINT `goods_chk_1` CHECK ((`price` > 0)),
  CONSTRAINT `goods_chk_2` CHECK ((`stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,'鲜美远味远不散相信！面饼85克','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/0a271e2b-36ac-4690-8567-943e21435f87.jpg',5.99,0),(2,'使用约1颗新晶番茄','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/36a99e9e-8a8e-4dcd-a62f-c3747d3b799e.jpg',3.99,135),(3,'新纯白清香型 净含量：115克','块','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/5acb0e89-9a30-42e7-8062-66ad43296ee2.jpg',2.49,185),(4,'绿茶+甘草 净含量：200ml','瓶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/7e2d29ca-2513-4df7-9752-422ac2fc6675.jpg',4.99,100),(5,'薰衣馨舞 普罗旺斯薰衣草·清新世界香氛 净含量：400克','瓶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/8ac1d8e4-f1f1-4b1b-8639-21812f623b7a.jpg',6.99,65),(6,'造型圆润 快速沥水 时尚美观 材质：PP','个','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/8ecee59c-b9cf-417f-a2d3-9de9007e5d7d.jpg',1.99,285),(7,'可湿水面巾120抽*3，柔软亲肤','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/00d897e4-3ca9-4193-a4ab-d0f5ae6eb440.jpg',12.99,100),(8,'柔软吸水，舒适耐用','条','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/1ea6c9db-8b3b-4bc7-afaa-f7603065653d.jpg',8.99,150),(9,'经典八宝粥，营养美味','罐','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/9a6c464c-aba3-470f-8c89-f4af04138a74.jpg',3.99,200),(10,'随时脉动回来，补充能量','瓶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/403c18c6-5a81-419e-a124-7277a6f0c07a.jpg',2.49,105),(11,'桂圆莲子八宝粥，滋补养颜','罐','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/0634f838-f920-445c-b2e3-6dee94541b02.jpg',4.99,80),(12,'坚固耐用，家用必备','个','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/823e4114-3a15-4292-8bcf-f2ca2430d375.jpg',9.99,300),(13,'护龈健齿，清新口气','支','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/4850e287-0f96-474d-8ea8-d809be6ef07f.jpg',5.99,250),(14,'黑米粥，健康美味','罐','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/5843c756-e597-44c0-8d15-bb7dd718db74.jpg',3.49,160),(15,'丝质顺滑，滋养修护','瓶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/9256b56a-cd71-4b1e-9e75-08c028d49569.jpg',7.99,70),(16,'脆皮软心糖，酸甜可口','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/9318e8a6-1098-4c51-bb38-ca7d9abc53a5.jpg',1.99,180),(17,'经典巧克力派，香甜松软','盒','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/81576975-de14-41bf-a36a-64b173b05f74.jpg',6.99,150),(18,'酸奶夹心，醇正美味','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/a9c6c057-5cd3-4ce3-a40d-8280a60bd6ef.jpg',4.99,100),(19,'简约设计，耐用防漏','个','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/aaabc432-d542-4390-b530-8c7f1ed39902.jpg',8.99,100),(20,'柔软纸巾，亲肤无刺激','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/c4eb2c37-7d95-4b10-8dc9-708b4e2333b9.jpg',2.99,300),(21,'5号电池，持久电力','盒','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/c066ee28-1743-473b-9e1a-51a435c15ddc.jpg',9.99,250),(22,'考试专用，清晰音质','个','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/c461e7f4-b50a-4d61-95a0-16761afae338.jpg',19.99,80),(23,'双重薄荷，清新口气','支','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/c1052cb5-6792-448f-89f3-8b9035547ab9.jpg',3.99,200),(24,'浓郁汤底，日式风味','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/d11b187f-854c-44e1-b31f-ef5bd030369b.jpg',5.99,120),(25,'高速传输，耐用线材','条','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/e0e10d21-c664-4622-88b4-625f42ccb97d.jpg',7.99,150),(26,'薰衣草香，清新怡人','瓶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/e1a92505-7784-4966-b1c6-254c237e8fc2.jpg',6.99,80),(27,'香浓奶茶，经典原味','瓶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/e6d5bd5d-5621-464c-bf75-132f0cf9cf3e.jpg',3.99,160),(28,'鸡汁番茄味，酥脆可口','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/e220a121-d399-4ed2-8109-f43dad07326a.jpg',2.99,200),(29,'亲肤纸巾，温和无刺激','包','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/f823b468-3123-497e-8902-954ccec0289d.jpg',3.49,250),(30,'耐用衣夹，方便实用','袋','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/f1481be2-1ba4-4bdf-84d7-a453fd6a21cc.jpg',4.99,280),(31,'麻辣牛肉面，劲道爽口','桶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/f2957fb9-a4e9-48c7-b5f6-aa2cb236919b.jpg',5.99,120),(32,'深层洁净，去屑止痒','瓶','https://umlimagesave.oss-cn-shenzhen.aliyuncs.com/f890916f-6517-45cc-bc16-7d43e71268cf.jpg',8.99,130);
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `order_item_id` int NOT NULL AUTO_INCREMENT COMMENT '订单项id',
  `order_id` int NOT NULL COMMENT '订单id',
  `goods_id` int NOT NULL COMMENT '商品id',
  `goods_num` int NOT NULL COMMENT '商品数量',
  `pay_price` decimal(10,2) NOT NULL COMMENT '支付价格',
  `is_sec_kill` int DEFAULT NULL COMMENT '是否为秒杀活动，如果是则值为sec_kill_id，否则为空',
  PRIMARY KEY (`order_item_id`),
  KEY `goods_id` (`goods_id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `order_item_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `order_item_ibfk_2` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`),
  CONSTRAINT `order_item_chk_1` CHECK ((`goods_num` > 0)),
  CONSTRAINT `order_item_chk_2` CHECK ((`pay_price` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,4,5,4.99,NULL);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `number` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` int NOT NULL COMMENT '用户id',
  `total_price` decimal(10,2) NOT NULL COMMENT '总金额',
  `status` int NOT NULL COMMENT '订单状态,1已下单,2已支付,3已取消',
  `order_time` datetime NOT NULL COMMENT '下单时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `number` (`number`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `orders_chk_1` CHECK ((`total_price` > 0)),
  CONSTRAINT `orders_chk_2` CHECK ((`status` in (1,2,3)))
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'445a47f1-c66e-4a24-bd87-4449db6e3cdd',2,24.95,2,'2025-03-09 15:23:11','2025-03-09 15:23:12');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sec_kill`
--

DROP TABLE IF EXISTS `sec_kill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sec_kill` (
  `sec_kill_id` int NOT NULL AUTO_INCREMENT COMMENT '秒杀活动id',
  `goods_id` int NOT NULL COMMENT '商品id',
  `sec_kill_price` decimal(10,2) NOT NULL COMMENT '秒杀价',
  `start_time` datetime NOT NULL COMMENT '秒杀开始时间',
  `end_time` datetime NOT NULL COMMENT '秒杀结束时间',
  `limit_num` int NOT NULL COMMENT '限购数',
  `sec_kill_stock` int NOT NULL COMMENT '秒杀商品库存',
  PRIMARY KEY (`sec_kill_id`),
  KEY `goods_id` (`goods_id`),
  CONSTRAINT `sec_kill_ibfk_1` FOREIGN KEY (`goods_id`) REFERENCES `goods` (`goods_id`),
  CONSTRAINT `sec_kill_chk_1` CHECK ((`sec_kill_price` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sec_kill`
--

LOCK TABLES `sec_kill` WRITE;
/*!40000 ALTER TABLE `sec_kill` DISABLE KEYS */;
INSERT INTO `sec_kill` VALUES (6,14,2.50,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(7,15,2.50,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(8,16,1.80,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(9,18,1.80,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(10,30,4.00,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(11,32,8.00,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(12,27,2.00,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(13,26,6.80,'2025-03-10 00:00:00','2025-03-11 00:00:00',5,20),(14,2,3.00,'2025-03-12 00:00:00','2025-03-13 00:00:00',1,15),(15,3,1.60,'2025-03-12 00:00:00','2025-03-13 00:00:00',1,15),(16,4,1.60,'2025-03-12 00:00:00','2025-03-13 00:00:00',1,15),(17,5,3.60,'2025-03-12 00:00:00','2025-03-13 00:00:00',1,15),(18,6,1.60,'2025-03-12 00:00:00','2025-03-13 00:00:00',1,15),(19,10,2.00,'2025-03-12 00:00:00','2025-03-13 00:00:00',1,15);
/*!40000 ALTER TABLE `sec_kill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `tel` varchar(11) NOT NULL COMMENT '手机号',
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `role` int NOT NULL COMMENT '角色,1管理员,2普通用户',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `tel` (`tel`),
  CONSTRAINT `user_chk_1` CHECK ((`role` in (1,2)))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'19116737714','2200310312','e10adc3949ba59abbe56e057f20f883e','null',1),(2,'19116737715','2200310312','e10adc3949ba59abbe56e057f20f883e',NULL,2),(3,'19116737716','2200310313','e10adc3949ba59abbe56e057f20f883e',NULL,1);
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

-- Dump completed on 2025-03-09 15:39:37
