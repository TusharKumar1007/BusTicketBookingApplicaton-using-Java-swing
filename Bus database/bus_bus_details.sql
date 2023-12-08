-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: bus
-- ------------------------------------------------------
-- Server version	8.0.35

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bus_details`
--

DROP TABLE IF EXISTS `bus_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bus_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bus_no` varchar(100) DEFAULT NULL,
  `bus_source` varchar(100) DEFAULT NULL,
  `bus_dest` varchar(100) DEFAULT NULL,
  `depart_time` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `seat` varchar(45) DEFAULT NULL,
  `movement` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bus_details`
--

LOCK TABLES `bus_details` WRITE;
/*!40000 ALTER TABLE `bus_details` DISABLE KEYS */;
INSERT INTO `bus_details` VALUES (1,'UK07P1546','Delhi','Dehradun','08:00 AM','50.00','30','Morning'),(2,'MH12B7654','Mumbai','Pune','09:00 AM','60.00','40','Morning'),(3,'UP15T8923','Saharanpur','Agra','10:00 AM','70.00','50','Morning'),(4,'RJ01C4512','Bikaner','Jaipur','11:00 AM','80.00','60','Morning'),(5,'DL07D1547','Delhi','Mumbai','12:00 PM','90.00','70','Afternoon'),(6,'UP12A8625','Agra','Mathura','01:00 PM','100.00','80','Afternoon'),(7,'MH20P2365','Pune','Mumbai','02:00 PM','110.00','90','Afternoon'),(8,'DL10T7521','Delhi','Pune','03:00 PM','120.00','100','Afternoon'),(9,'DL05D2589','Delhi','Lucknow','04:00 PM','130.00','110','Afternoon'),(10,'UP17A7524','Agra','Varanasi','05:00 PM','140.00','120','Evening'),(11,'MH22P9564','Mumbai','Goa','06:00 PM','150.00','130','Evening'),(12,'UP21T6547','Saharanpur','Delhi','07:00 PM','160.00','140','Evening');
/*!40000 ALTER TABLE `bus_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-07 23:53:30
