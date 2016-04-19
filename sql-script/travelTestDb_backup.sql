-- MySQL dump 10.13  Distrib 5.7.10, for Win64 (x86_64)
--
-- Host: localhost    Database: traveltestdb
-- ------------------------------------------------------
-- Server version	5.7.10-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
--
-- Table structure for table `tadresy`
--

DROP TABLE IF EXISTS `tadresy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tadresy` (
  `IDAdresu` int(11) NOT NULL AUTO_INCREMENT,
  `Adres` varchar(512) CHARACTER SET cp1250 NOT NULL,
  `Panstwo` int(11) NOT NULL,
  `Region` varchar(128) CHARACTER SET cp1250 NOT NULL,
  `Miasto` varchar(128) CHARACTER SET cp1250 NOT NULL,
  `Kod` varchar(32) CHARACTER SET cp1250 NOT NULL,
  `Telefon` varchar(32) CHARACTER SET cp1250 NOT NULL,
  `Faks` varchar(32) CHARACTER SET cp1250 DEFAULT NULL,
  PRIMARY KEY (`IDAdresu`),
  KEY `FK_tAdresy_tPanstwa` (`Panstwo`),
  CONSTRAINT `FK_tAdresy_tPanstwa` FOREIGN KEY (`Panstwo`) REFERENCES `tpanstwa` (`IDPanstwa`)
) ENGINE=InnoDB AUTO_INCREMENT=263 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tadresy`
--

LOCK TABLES `tadresy` WRITE;
/*!40000 ALTER TABLE `tadresy` DISABLE KEYS */;
INSERT INTO `tadresy` VALUES (261,'ul. Wolska',217,'region','City10','31-100','102030','2101020'),(262,'ul. Wolska',218,'region','City10','31-100','102030','2101020');
/*!40000 ALTER TABLE `tadresy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tatrakcjehistoria`
--

DROP TABLE IF EXISTS `tatrakcjehistoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tatrakcjehistoria` (
  `IDAtrakcjiUslugi` int(11) NOT NULL AUTO_INCREMENT,
  `Nazwa` varchar(50) CHARACTER SET cp1250 NOT NULL,
  `iLiczbaOsob` int(11) NOT NULL,
  `mCena` decimal(15,2) NOT NULL,
  `IDOferty` int(11) NOT NULL,
  `Opis` varchar(4026) CHARACTER SET cp1250 DEFAULT NULL,
  PRIMARY KEY (`IDAtrakcjiUslugi`),
  KEY `FK_tAtrakcjeHistoria_tHistoriaOfert` (`IDOferty`),
  CONSTRAINT `FK_tAtrakcjeHistoria_tHistoriaOfert` FOREIGN KEY (`IDOferty`) REFERENCES `thistoriaofert` (`IDOferty`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tatrakcjehistoria`
--

LOCK TABLES `tatrakcjehistoria` WRITE;
/*!40000 ALTER TABLE `tatrakcjehistoria` DISABLE KEYS */;
INSERT INTO `tatrakcjehistoria` VALUES (10,'Zwiedzanie zamku',2,5.00,10,NULL);
/*!40000 ALTER TABLE `tatrakcjehistoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tatrakcjeuslugi`
--

DROP TABLE IF EXISTS `tatrakcjeuslugi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tatrakcjeuslugi` (
  `IDAtrakcjiUslugi` int(11) NOT NULL AUTO_INCREMENT,
  `Nazwa` varchar(50) CHARACTER SET cp1250 NOT NULL,
  `iLiczbaOsob` int(11) NOT NULL,
  `mCena` decimal(15,2) NOT NULL,
  `IDOferty` int(11) NOT NULL,
  `Opis` varchar(4096) CHARACTER SET cp1250 DEFAULT NULL,
  PRIMARY KEY (`IDAtrakcjiUslugi`),
  KEY `FK_tAtrakcjeUslugi_tOferta` (`IDOferty`),
  CONSTRAINT `FK_tAtrakcjeUslugi_tOferta` FOREIGN KEY (`IDOferty`) REFERENCES `toferta` (`IDOferty`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tatrakcjeuslugi`
--

LOCK TABLES `tatrakcjeuslugi` WRITE;
/*!40000 ALTER TABLE `tatrakcjeuslugi` DISABLE KEYS */;
/*!40000 ALTER TABLE `tatrakcjeuslugi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tfirmy`
--

DROP TABLE IF EXISTS `tfirmy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tfirmy` (
  `IDFirmy` int(11) NOT NULL AUTO_INCREMENT,
  `NazwaFirmy` varchar(128) CHARACTER SET cp1250 NOT NULL,
  `IDAdresu` int(11) NOT NULL,
  `IDKlienta` int(11) NOT NULL,
  `NIP` char(13) CHARACTER SET cp1250 NOT NULL,
  `REGON` char(15) CHARACTER SET cp1250 NOT NULL,
  PRIMARY KEY (`IDFirmy`),
  KEY `FK_tFirmy_tAdresy` (`IDAdresu`),
  KEY `FK_tFirmy_tKlient` (`IDKlienta`),
  CONSTRAINT `FK_tFirmy_tAdresy` FOREIGN KEY (`IDAdresu`) REFERENCES `tadresy` (`IDAdresu`),
  CONSTRAINT `FK_tFirmy_tKlient` FOREIGN KEY (`IDKlienta`) REFERENCES `tklient` (`IDKlienta`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tfirmy`
--

LOCK TABLES `tfirmy` WRITE;
/*!40000 ALTER TABLE `tfirmy` DISABLE KEYS */;
/*!40000 ALTER TABLE `tfirmy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `thistoriaofert`
--

DROP TABLE IF EXISTS `thistoriaofert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `thistoriaofert` (
  `IDOferty` int(11) NOT NULL AUTO_INCREMENT,
  `bWyjazd` int(11) NOT NULL,
  `mCena` decimal(15,2) NOT NULL,
  `iLiczbaOsob` int(11) NOT NULL,
  `IDPanstwa` int(11) NOT NULL,
  `Miasto` varchar(128) CHARACTER SET cp1250 NOT NULL,
  `MiejsceWyjazdu` varchar(64) CHARACTER SET cp1250 NOT NULL,
  `LiczbaDniTrwania` int(11) NOT NULL,
  `DataWyjazdu` datetime NOT NULL,
  `Opis` varchar(4096) CHARACTER SET cp1250 DEFAULT NULL,
  PRIMARY KEY (`IDOferty`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `thistoriaofert`
--

LOCK TABLES `thistoriaofert` WRITE;
/*!40000 ALTER TABLE `thistoriaofert` DISABLE KEYS */;
INSERT INTO `thistoriaofert` VALUES (10,0,10.00,2,218,'Paris','Kraków',3,'2016-04-12 13:22:57',NULL);
/*!40000 ALTER TABLE `thistoriaofert` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tklienciatrakcje`
--

DROP TABLE IF EXISTS `tklienciatrakcje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tklienciatrakcje` (
  `IDKlienta` int(11) NOT NULL,
  `IDAtrakcjiUslugi` int(11) NOT NULL,
  `IDOsobyAtrakcji` int(11) NOT NULL,
  `DataRezerwacji` datetime NOT NULL,
  `SposobZaplaty` char(1) CHARACTER SET cp1250 DEFAULT NULL,
  `DataZaplaty` datetime DEFAULT NULL,
  `ZaplaconaKwota` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`IDAtrakcjiUslugi`,`IDOsobyAtrakcji`),
  KEY `FK_tKlienciAtrakcje_tKlient1` (`IDKlienta`),
  KEY `FK_tKlienciAtrakcje_tOsoby` (`IDOsobyAtrakcji`),
  CONSTRAINT `FK_tKlienciAtrakcje_tAtrakcjeUslugi` FOREIGN KEY (`IDAtrakcjiUslugi`) REFERENCES `tatrakcjeuslugi` (`IDAtrakcjiUslugi`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tKlienciAtrakcje_tKlient1` FOREIGN KEY (`IDKlienta`) REFERENCES `tklient` (`IDKlienta`),
  CONSTRAINT `FK_tKlienciAtrakcje_tOsoby` FOREIGN KEY (`IDOsobyAtrakcji`) REFERENCES `tosoby` (`IDOsoby`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tklienciatrakcje`
--

LOCK TABLES `tklienciatrakcje` WRITE;
/*!40000 ALTER TABLE `tklienciatrakcje` DISABLE KEYS */;
/*!40000 ALTER TABLE `tklienciatrakcje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tklienciatrakcjehistoria`
--

DROP TABLE IF EXISTS `tklienciatrakcjehistoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tklienciatrakcjehistoria` (
  `IDKlienta` int(11) NOT NULL,
  `IDAtrakcjiUslugi` int(11) NOT NULL,
  `IDOsobyAtrakcji` int(11) NOT NULL,
  `SposobZaplaty` char(7) CHARACTER SET cp1250 NOT NULL,
  `DataZaplaty` datetime NOT NULL,
  `ZaplaconaKwota` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`IDAtrakcjiUslugi`,`IDOsobyAtrakcji`),
  KEY `FK_tKlienciAtrakcjeHistoria_tKlient1` (`IDKlienta`),
  KEY `FK_tKlienciAtrakcjeHistoria_tOsoby` (`IDOsobyAtrakcji`),
  CONSTRAINT `FK_tKlienciAtrakcjeHistoria_tAtrakcjeHistoria` FOREIGN KEY (`IDAtrakcjiUslugi`) REFERENCES `tatrakcjehistoria` (`IDAtrakcjiUslugi`),
  CONSTRAINT `FK_tKlienciAtrakcjeHistoria_tKlient1` FOREIGN KEY (`IDKlienta`) REFERENCES `tklient` (`IDKlienta`),
  CONSTRAINT `FK_tKlienciAtrakcjeHistoria_tOsoby` FOREIGN KEY (`IDOsobyAtrakcji`) REFERENCES `tosoby` (`IDOsoby`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tklienciatrakcjehistoria`
--

LOCK TABLES `tklienciatrakcjehistoria` WRITE;
/*!40000 ALTER TABLE `tklienciatrakcjehistoria` DISABLE KEYS */;
INSERT INTO `tklienciatrakcjehistoria` VALUES (68,10,68,'P','2016-04-12 13:22:57',NULL);
/*!40000 ALTER TABLE `tklienciatrakcjehistoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tkliencioferty`
--

DROP TABLE IF EXISTS `tkliencioferty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tkliencioferty` (
  `IDKlienta` int(11) NOT NULL,
  `IDOferty` int(11) NOT NULL,
  `IDOsobyImprezy` int(11) NOT NULL,
  `DataRezerwacji` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `SposobZaplaty` char(1) CHARACTER SET cp1250 DEFAULT NULL,
  `DataZaplaty` datetime DEFAULT NULL,
  `ZaplaconaKwota` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`IDOferty`,`IDOsobyImprezy`),
  KEY `FK_tKlienciOferty_tKlient` (`IDKlienta`),
  KEY `FK_tKlienciOferty_tOsoby` (`IDOsobyImprezy`),
  CONSTRAINT `FK_tKlienciOferty_tKlient` FOREIGN KEY (`IDKlienta`) REFERENCES `tklient` (`IDKlienta`),
  CONSTRAINT `FK_tKlienciOferty_tOferta` FOREIGN KEY (`IDOferty`) REFERENCES `toferta` (`IDOferty`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tKlienciOferty_tOsoby` FOREIGN KEY (`IDOsobyImprezy`) REFERENCES `tosoby` (`IDOsoby`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tkliencioferty`
--

LOCK TABLES `tkliencioferty` WRITE;
/*!40000 ALTER TABLE `tkliencioferty` DISABLE KEYS */;
/*!40000 ALTER TABLE `tkliencioferty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tklienciofertyhistoria`
--

DROP TABLE IF EXISTS `tklienciofertyhistoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tklienciofertyhistoria` (
  `IDOferty` int(11) NOT NULL,
  `IDKlienta` int(11) NOT NULL,
  `IDOsoby` int(11) NOT NULL,
  `SposobZaplaty` char(7) CHARACTER SET cp1250 NOT NULL,
  `DataZaplaty` datetime NOT NULL,
  `ZaplaconaKwota` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`IDOferty`,`IDOsoby`),
  KEY `FK_tKlienciOfertyHistoria_tOsoby` (`IDOsoby`),
  CONSTRAINT `FK_tKlienciOfertyHistoria_tHistoriaOfert` FOREIGN KEY (`IDOferty`) REFERENCES `thistoriaofert` (`IDOferty`),
  CONSTRAINT `FK_tKlienciOfertyHistoria_tOsoby` FOREIGN KEY (`IDOsoby`) REFERENCES `tosoby` (`IDOsoby`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tklienciofertyhistoria`
--

LOCK TABLES `tklienciofertyhistoria` WRITE;
/*!40000 ALTER TABLE `tklienciofertyhistoria` DISABLE KEYS */;
INSERT INTO `tklienciofertyhistoria` VALUES (10,68,68,'P','2016-04-12 13:22:57',NULL);
/*!40000 ALTER TABLE `tklienciofertyhistoria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tklient`
--

DROP TABLE IF EXISTS `tklient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tklient` (
  `IDKlienta` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(64) CHARACTER SET cp1250 NOT NULL,
  `haslo` varchar(34) CHARACTER SET cp1250 NOT NULL,
  `bFirma` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`IDKlienta`),
  UNIQUE KEY `NONCLUSTERED` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tklient`
--

LOCK TABLES `tklient` WRITE;
/*!40000 ALTER TABLE `tklient` DISABLE KEYS */;
INSERT INTO `tklient` VALUES (68,'alice@ta.com','abc123','\0');
/*!40000 ALTER TABLE `tklient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `toferta`
--

DROP TABLE IF EXISTS `toferta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `toferta` (
  `IDOferty` int(11) NOT NULL AUTO_INCREMENT,
  `bWyjazd` bit(1) NOT NULL,
  `mCena` decimal(15,2) NOT NULL,
  `iLiczbaOsob` int(11) NOT NULL,
  `IDPanstwa` int(11) NOT NULL,
  `Miasto` varchar(128) CHARACTER SET cp1250 NOT NULL,
  `MiejsceWyjazdu` varchar(64) CHARACTER SET cp1250 NOT NULL,
  `LiczbaDniTrwania` int(11) NOT NULL,
  `DataWyjazdu` datetime NOT NULL,
  `Opis` varchar(4096) CHARACTER SET cp1250 DEFAULT NULL,
  PRIMARY KEY (`IDOferty`),
  KEY `FK_tOferta_tPanstwa` (`IDPanstwa`),
  CONSTRAINT `FK_tOferta_tPanstwa` FOREIGN KEY (`IDPanstwa`) REFERENCES `tpanstwa` (`IDPanstwa`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `toferta`
--

LOCK TABLES `toferta` WRITE;
/*!40000 ALTER TABLE `toferta` DISABLE KEYS */;
/*!40000 ALTER TABLE `toferta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tosoby`
--

DROP TABLE IF EXISTS `tosoby`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tosoby` (
  `IDOsoby` int(11) NOT NULL AUTO_INCREMENT,
  `IDKlienta` int(11) NOT NULL,
  `IDAdresu` int(11) DEFAULT NULL,
  `Imie` varchar(32) CHARACTER SET cp1250 NOT NULL,
  `Nazwisko` varchar(50) CHARACTER SET cp1250 NOT NULL,
  `bPracownik` bit(1) NOT NULL DEFAULT b'0',
  `NIP` char(13) CHARACTER SET cp1250 DEFAULT NULL,
  PRIMARY KEY (`IDOsoby`),
  KEY `FK_tOsoby_tAdresy` (`IDAdresu`),
  KEY `FK_tOsoby_tKlient` (`IDKlienta`),
  CONSTRAINT `FK_tOsoby_tAdresy` FOREIGN KEY (`IDAdresu`) REFERENCES `tadresy` (`IDAdresu`),
  CONSTRAINT `FK_tOsoby_tKlient` FOREIGN KEY (`IDKlienta`) REFERENCES `tklient` (`IDKlienta`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tosoby`
--

LOCK TABLES `tosoby` WRITE;
/*!40000 ALTER TABLE `tosoby` DISABLE KEYS */;
INSERT INTO `tosoby` VALUES (68,68,262,'Jack','Smith','\0','186-190-01-20');
/*!40000 ALTER TABLE `tosoby` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tpanstwa`
--

DROP TABLE IF EXISTS `tpanstwa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tpanstwa` (
  `IDPanstwa` int(11) NOT NULL AUTO_INCREMENT,
  `NazwaPanstwa` varchar(128) CHARACTER SET cp1250 NOT NULL,
  PRIMARY KEY (`IDPanstwa`)
) ENGINE=InnoDB AUTO_INCREMENT=219 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tpanstwa`
--

LOCK TABLES `tpanstwa` WRITE;
/*!40000 ALTER TABLE `tpanstwa` DISABLE KEYS */;
INSERT INTO `tpanstwa` VALUES (217,'Polska'),(218,'Polska');
/*!40000 ALTER TABLE `tpanstwa` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-15  6:18:58
