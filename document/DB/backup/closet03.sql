-- MySQL dump 10.13  Distrib 5.7.31, for Linux (x86_64)
--
-- Host: localhost    Database: CLOSET
-- ------------------------------------------------------
-- Server version	5.7.31-0ubuntu0.18.04.1

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
-- Table structure for table `BOARD`
--

DROP TABLE IF EXISTS `BOARD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BOARD` (
  `boardNo` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(45) NOT NULL COMMENT '작성자 아이디-유저 외래키',
  `fileName` varchar(45) DEFAULT NULL COMMENT '파일이름',
  `filePath` varchar(100) DEFAULT NULL COMMENT '파일경로',
  `contents` text COMMENT '게시글 내용',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`boardNo`),
  KEY `BOARD_CLO_USER` (`userID`),
  CONSTRAINT `BOARD_CLO_USER` FOREIGN KEY (`userID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=utf8 COMMENT='옷 공유 게시판';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BOARD`
--

LOCK TABLES `BOARD` WRITE;
/*!40000 ALTER TABLE `BOARD` DISABLE KEYS */;
INSERT INTO `BOARD` VALUES (76,'a','codi001.jpg','/download/board?imageFileName=codi001.jpg','\n #JIPSY #캐주얼','2020-08-15 22:29:03'),(77,'a','codi002.jpg','/download/board?imageFileName=codi002.jpg','실키 레이어드 화이트 셔츠\n #JIPSY #화이트_클래식/드레스셔츠','2020-08-15 22:43:39'),(78,'a','codi003.jpg','/download/board?imageFileName=codi003.jpg','\n #JIPSY #화이트_클래식/드레스셔츠','2020-08-15 22:46:38'),(79,'a','codi004.jpg','/download/board?imageFileName=codi004.jpg','헤비스웨트 데일리 팬츠\n #JIPSY #캐주얼 #블랙_트레이닝바지','2020-08-15 22:52:24'),(90,'a','a_1597703448520.jpg','/download/board?imageFileName=a_1597703448520.jpg','\n #아이보리_스니커즈 #베이지_롱 스커트 #베이지_털모자','2020-08-17 22:30:48'),(91,'a','a_1597704498325.jpg','/download/board?imageFileName=a_1597704498325.jpg','\n #베이지_바캉스모자 #화이트_민소매원피스 #브라운_슬리퍼','2020-08-17 22:48:18'),(92,'a','a_1597704780128.jpg','/download/board?imageFileName=a_1597704780128.jpg','\n #아이보리_양털점퍼','2020-08-17 22:53:00'),(94,'a','a_1597749695461.jpg','/download/board?imageFileName=a_1597749695461.jpg','상큼발랄\n #옐로우_라운드넥반팔티셔츠 #스카이블루_청바지 #화이트_스니커즈','2020-08-18 11:21:35'),(95,'a','a_1597749774946.jpg','/download/board?imageFileName=a_1597749774946.jpg','쿠키 또 먹고싶다\n #스카이블루_브이넥반팔티셔츠 #블랙_숏/미니스커트 #블랙_샌들 #블랙_가방','2020-08-18 11:22:54'),(96,'a','a_1597749859156.jpg','/download/board?imageFileName=a_1597749859156.jpg','일상룩 \n#화이트_헨리넥반팔티셔츠 #블랙_슬랙스 #블랙_슬리퍼','2020-08-18 11:24:19'),(97,'a','a_1597749956029.jpg','/download/board?imageFileName=a_1597749956029.jpg','오늘은 차분하게\n #화이트_헨리넥긴팔티셔츠 #아이보리_롱스커트 #베이지_슬리퍼 #블랙_시계 #브라운_가방','2020-08-18 11:25:56'),(98,'a','a_1597750271372.jpg','/download/board?imageFileName=a_1597750271372.jpg','색조합 맘에 든다\n #브라운_카라반팔티셔츠 #아이보리_슬랙스 #브라운_가방','2020-08-18 11:31:11'),(99,'a','a_1597750375024.jpg','/download/board?imageFileName=a_1597750375024.jpg','날씨가 핫해\n\n #블랙_민소매티셔츠 #화이트_가디건 #스카이블루_반바지 #블랙_샌들 #블랙_캡모자 #실버_목걸이','2020-08-18 11:32:55'),(107,'cat','cat_1597754111782.jpg','/download/board?imageFileName=cat_1597754111782.jpg','날씨가 좋다\n #블랙_가죽자켓 #블랙_라운드넥반팔티셔츠 #브라운_미디엄스커트 #블랙_가방 #블랙_구두','2020-08-18 12:35:11'),(108,'cat','cat_1597754200452.jpg','/download/board?imageFileName=cat_1597754200452.jpg','모던시크\n\n #브라운_블레이저 #브라운_라운드넥반팔티셔츠 #화이트_면바지 #블랙_워커/부츠 #블랙_가방','2020-08-18 12:36:40'),(119,'boy','boy_1597922139098.jpg','/download/board?imageFileName=boy_1597922139098.jpg','캠퍼스룩\n\n #블랙_야구점퍼 #아이보리_맨투맨 #블랙_면바지 #화이트_운동화','2020-08-20 11:15:39'),(120,'boy','boy_1597922207487.jpg','/download/board?imageFileName=boy_1597922207487.jpg','나와, 집 앞이야 룩\n #블랙_항공점퍼 #그레이_후드티셔츠','2020-08-20 11:16:47'),(121,'boy','boy_1597922263545.jpg','/download/board?imageFileName=boy_1597922263545.jpg','가끔은 캐주얼하게\n\n #블랙_데님/청자켓 #그레이_후드집업 #블랙_라운드넥긴팔티셔츠 #네이비_청바지','2020-08-20 11:17:43'),(124,'boy','boy_1597925020617.jpg','/download/board?imageFileName=boy_1597925020617.jpg','출장 가기 싫다..\n#남자코디 #포멀  #아이보리_트렌치코트 #화이트_클래식/드레스셔츠 #네이비_넥타이 #네이비_블레이저 #네이비_슬랙스 #브라운_구두 #블랙_가방','2020-08-20 12:03:40'),(125,'cat','cat_1597926343110.jpg','/download/board?imageFileName=cat_1597926343110.jpg','내 최애 무스탕 코디\n#여자코디 #캐주얼  #블랙_무스탕 #아이보리_라운드넥니트 #블랙_면바지 #와인_시계 #스카이블루_가방 #스카이블루_운동화','2020-08-20 12:25:43'),(128,'cat','cat_1597926645457.jpg','/download/board?imageFileName=cat_1597926645457.jpg','겨울이어도 가을 느낌\n#여자코디 #캐주얼 \n #브라운_무스탕 #네이비_긴팔원피스 #블랙_레깅스 #와인_시계 #브라운_워커/부츠 #핑크_가방','2020-08-20 12:30:45'),(129,'cat','cat_1597926704249.jpg','/download/board?imageFileName=cat_1597926704249.jpg','오늘은 귀엽게\n#여자코디 #캐주얼 \n #브라운_무스탕 #화이트_라운드넥긴팔티셔츠 #그레이_숏/미니스커트 #블랙_워커/부츠 #블랙_가방','2020-08-20 12:31:44'),(131,'cat','cat_1597927073757.jpg','/download/board?imageFileName=cat_1597927073757.jpg','가디건에 갈색 로퍼로 뽀인뜨\n#여자코디 #캐주얼 \n #그레이_가디건 #블랙_라운드넥긴팔티셔츠 #네이비_청바지 #블랙_가방 #브라운_구두','2020-08-20 12:37:53'),(132,'cat','cat_1597927302412.jpg','/download/board?imageFileName=cat_1597927302412.jpg','가을엔 레이어드\n#여자코디 #캐주얼 \n #올리브그린_야상점퍼 #블랙_가디건 #화이트_라운드넥긴팔티셔츠 #그레이_레깅스 #블랙_스니커즈','2020-08-20 12:41:42'),(133,'cat','cat_1597927564939.jpg','/download/board?imageFileName=cat_1597927564939.jpg','여성스러운게 조아\n#여성코디 #비즈니스캐주얼 \n #베이지_가디건 #와인_긴팔원피스 #블랙_워커/부츠 #블랙_시계 #와인_가방','2020-08-20 12:46:04');
/*!40000 ALTER TABLE `BOARD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLOSET`
--

DROP TABLE IF EXISTS `CLOSET`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLOSET` (
  `userID` varchar(45) NOT NULL COMMENT '유저아이디',
  `closetName` varchar(45) NOT NULL COMMENT '옷장이름',
  `closetLocation` varchar(45) DEFAULT NULL COMMENT '옷장위치',
  `closetMemo` varchar(45) DEFAULT NULL COMMENT '옷장메모',
  `closetOpen` varchar(45) DEFAULT NULL COMMENT '공개여부',
  PRIMARY KEY (`userID`,`closetName`),
  CONSTRAINT `FK_CLOSET_userID_USER_userID` FOREIGN KEY (`userID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='옷장';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLOSET`
--

LOCK TABLES `CLOSET` WRITE;
/*!40000 ALTER TABLE `CLOSET` DISABLE KEYS */;
INSERT INTO `CLOSET` VALUES ('1','default',NULL,NULL,'no'),('11','default',NULL,NULL,'no'),('1111','default',NULL,NULL,'no'),('5','default',NULL,NULL,'no'),('a','default',NULL,NULL,'no'),('aa','default',NULL,NULL,'no'),('abc','default',NULL,NULL,'no'),('air','default',NULL,NULL,'no'),('apple','default',NULL,NULL,'no'),('bbb','default',NULL,NULL,'no'),('bcde','default',NULL,NULL,'no'),('boy','default',NULL,NULL,'no'),('buri','default',NULL,NULL,'no'),('captain','default',NULL,NULL,'no'),('cat','default',NULL,NULL,'no'),('clo','default',NULL,NULL,'no'),('g','default',NULL,NULL,'no'),('grape','default',NULL,NULL,'no'),('hello','default',NULL,NULL,'no'),('joshua','default',NULL,NULL,'no'),('l','default',NULL,NULL,'no'),('lemon','default',NULL,NULL,'no'),('lemonade','default',NULL,NULL,'no'),('min','default',NULL,NULL,'no'),('mm','default',NULL,NULL,'no'),('nm','default',NULL,NULL,'no'),('r','default',NULL,NULL,'no'),('stark','default',NULL,NULL,'no'),('uu','default',NULL,NULL,'no'),('x','default',NULL,NULL,'no');
/*!40000 ALTER TABLE `CLOSET` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CLOTHES`
--

DROP TABLE IF EXISTS `CLOTHES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CLOTHES` (
  `cloNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '옷번호(대리키)',
  `location` varchar(45) DEFAULT NULL,
  `kind` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `detailCategory` varchar(45) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `identifier` varchar(91) DEFAULT NULL,
  `season` varchar(45) DEFAULT NULL,
  `brand` varchar(45) DEFAULT NULL,
  `cloSize` varchar(45) DEFAULT NULL,
  `buyDate` date DEFAULT NULL,
  `fileName` varchar(45) DEFAULT NULL COMMENT '파일이름',
  `filePath` varchar(100) DEFAULT NULL COMMENT '파일경로',
  `favorite` varchar(45) DEFAULT NULL,
  `userID` varchar(45) NOT NULL COMMENT '유저아이디',
  `closetName` varchar(45) NOT NULL COMMENT '옷장이름',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`cloNo`),
  KEY `FK_CLOTHES_closetName_CLOSET_closetName` (`userID`,`closetName`),
  CONSTRAINT `FK_CLOTHES_closetName_CLOSET_closetName` FOREIGN KEY (`userID`, `closetName`) REFERENCES `CLOSET` (`userID`, `closetName`)
) ENGINE=InnoDB AUTO_INCREMENT=126 DEFAULT CHARSET=utf8 COMMENT='옷';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CLOTHES`
--

LOCK TABLES `CLOTHES` WRITE;
/*!40000 ALTER TABLE `CLOTHES` DISABLE KEYS */;
INSERT INTO `CLOTHES` VALUES (2,'public','하의','청바지','청바지','블랙','블랙_청바지','가을',NULL,NULL,'2019-05-05','청바지_블랙.jpg','/download/clothes?imageFileName=청바지_블랙.jpg','yes','a','default','2020-08-16 06:23:03'),(3,'public','상의','맨투맨','맨투맨','블랙','블랙_맨투맨','겨울','JIPSY','M','2019-05-05','맨투맨_블랙.jpg','/download/clothes?imageFileName=맨투맨_블랙.jpg','yes','a','default','2020-08-16 06:23:47'),(4,'public','하의','트레이닝바지','트레이닝바지','블랙','블랙_트레이닝바지','여름','나이키','M','2020-01-05','트레이닝바지_블랙.jpg','/download/clothes?imageFileName=트레이닝바지_블랙.jpg','yes','a','default','2020-08-16 06:26:24'),(5,'public','상의','맨투맨','맨투맨','화이트','화이트_맨투맨','겨울','JIPSY','M','2019-05-05','맨투맨_화이트.jpg','/download/clothes?imageFileName=맨투맨_화이트.jpg','yes','a','default','2020-08-16 06:27:25'),(7,'public','상의','맨투맨','맨투맨','블루','블루_맨투맨','겨울','JIPSY','M','2019-05-05','맨투맨_블루.jpg','/download/clothes?imageFileName=맨투맨_블루.jpg','yes','a','default','2020-08-16 06:28:04'),(8,'public','상의','맨투맨','맨투맨','그레이','그레이_맨투맨','겨울','JIPSY','M','2019-05-05','맨투맨_그레이.jpg','/download/clothes?imageFileName=맨투맨_그레이.jpg','yes','a','default','2020-08-16 06:28:04'),(9,'public','상의','맨투맨','맨투맨','오렌지','오렌지_맨투맨','겨울','JIPSY','M','2019-05-05','맨투맨_오렌지.jpg','/download/clothes?imageFileName=맨투맨_오렌지.jpg','yes','a','default','2020-08-16 06:28:58'),(10,'public','액세서리','모자','캡모자','블랙','블랙_캡모자','가을',NULL,NULL,'2019-07-05','캡모자_블랙.jpg','/download/clothes?imageFileName=캡모자_블랙.jpg','no','a','default','2020-08-16 06:31:29'),(11,'public','하의','청바지','청바지','블랙','스카이블루_청바지','가을',NULL,NULL,'2019-05-05','청바지_스카이블루.jpg','/download/clothes?imageFileName=청바지_스카이블루.jpg','no','a','default','2020-08-16 06:32:21'),(13,'public','신발','스니커즈','스니커즈','블랙','블랙_스니커즈','겨울','반스','240','2019-05-05','스니커즈_블랙.jpg','/download/clothes?imageFileName=스니커즈_블랙.jpg','yes','a','default','2020-08-16 06:34:56'),(14,'public','상의','셔츠','클래식/드레스셔츠','화이트','화이트_클래식/드레스셔츠','겨울','JIPSY','M','2020-03-01','클래식／드레스셔츠_화이트.jpg','/download/clothes?imageFileName=클래식／드레스셔츠_화이트.jpg','no','a','default','2020-08-16 07:19:17'),(16,'public','하의','트레이닝바지','트레이닝바지','블랙','블랙_트레이닝바지','봄','JIPSY','s','2020-07-10','트레이닝바지_블랙2.jpg','/download/clothes?imageFileName=트레이닝바지_블랙2.jpg','no','a','default','2020-08-16 07:51:17'),(27,'public','액세서리','팔찌','팔찌','골드','골드_팔찌',NULL,'','',NULL,'uu_1597620352742.jpg','/download/clothes?imageFileName=uu_1597620352742.jpg','yes','uu','default','2020-08-16 23:25:52'),(31,'public','하의','반바지','반바지','스카이블루','스카이블루_반바지','여름','','',NULL,'a_1597623753442.jpg','/download/clothes?imageFileName=a_1597623753442.jpg',NULL,'a','default','2020-08-17 00:22:33'),(33,'private','외투','점퍼','양털점퍼','아이보리','아이보리_양털점퍼',NULL,'','',NULL,'a_1597626630698.jpg','/download/clothes?imageFileName=a_1597626630698.jpg','yes','a','default','2020-08-17 01:10:30'),(34,'public','외투','점퍼','양털점퍼','아이보리','아이보리_양털점퍼',NULL,'','',NULL,'a_1597626699478.jpg','/download/clothes?imageFileName=a_1597626699478.jpg','yes','a','default','2020-08-17 01:11:39'),(35,'public','상의','반팔티셔츠','라운드넥반팔티셔츠','옐로우','옐로우_라운드넥반팔티셔츠',NULL,'','',NULL,'a_1597629831570.jpg','/download/clothes?imageFileName=a_1597629831570.jpg',NULL,'a','default','2020-08-17 02:03:51'),(36,'public','하의','청바지','청바지','스카이블루','스카이블루_청바지',NULL,'','',NULL,'a_1597629879237.jpg','/download/clothes?imageFileName=a_1597629879237.jpg',NULL,'a','default','2020-08-17 02:04:39'),(37,'public','신발','스니커즈','스니커즈','화이트','화이트_스니커즈',NULL,'','',NULL,'a_1597629942859.jpg','/download/clothes?imageFileName=a_1597629942859.jpg',NULL,'a','default','2020-08-17 02:05:42'),(38,'public','상의','반팔티셔츠','브이넥반팔티셔츠','스카이블루','스카이블루_브이넥반팔티셔츠',NULL,'','',NULL,'a_1597630250778.jpg','/download/clothes?imageFileName=a_1597630250778.jpg',NULL,'a','default','2020-08-17 02:10:50'),(39,'public','하의','스커트','숏/미니스커트','블랙','블랙_숏/미니스커트',NULL,'','',NULL,'a_1597630284832.jpg','/download/clothes?imageFileName=a_1597630284832.jpg',NULL,'a','default','2020-08-17 02:11:24'),(40,'public','신발','샌들','샌들','블랙','블랙_샌들',NULL,'','',NULL,'a_1597630315392.jpg','/download/clothes?imageFileName=a_1597630315392.jpg',NULL,'a','default','2020-08-17 02:11:55'),(41,'public','가방','가방','가방','블랙','블랙_가방',NULL,'','',NULL,'a_1597630391613.jpg','/download/clothes?imageFileName=a_1597630391613.jpg',NULL,'a','default','2020-08-17 02:13:11'),(42,'public','상의','긴팔티셔츠','헨리넥긴팔티셔츠','화이트','화이트_헨리넥긴팔티셔츠',NULL,'','',NULL,'a_1597630785171.jpg','/download/clothes?imageFileName=a_1597630785171.jpg',NULL,'a','default','2020-08-17 02:19:45'),(43,'public','하의','스커트','롱스커트','아이보리','아이보리_롱스커트',NULL,'','',NULL,'a_1597630823424.jpg','/download/clothes?imageFileName=a_1597630823424.jpg',NULL,'a','default','2020-08-17 02:20:23'),(44,'public','액세서리','시계','시계','블랙','블랙_시계',NULL,'','',NULL,'a_1597630854499.jpg','/download/clothes?imageFileName=a_1597630854499.jpg',NULL,'a','default','2020-08-17 02:20:54'),(45,'public','가방','가방','가방','브라운','브라운_가방',NULL,'','',NULL,'a_1597630914232.jpg','/download/clothes?imageFileName=a_1597630914232.jpg',NULL,'a','default','2020-08-17 02:21:54'),(46,'public','상의','반팔티셔츠','헨리넥반팔티셔츠','화이트','화이트_헨리넥반팔티셔츠',NULL,'','',NULL,'a_1597631569453.jpg','/download/clothes?imageFileName=a_1597631569453.jpg',NULL,'a','default','2020-08-17 02:32:49'),(47,'public','하의','슬랙스','슬랙스','블랙','블랙_슬랙스',NULL,'','',NULL,'a_1597631615350.jpg','/download/clothes?imageFileName=a_1597631615350.jpg',NULL,'a','default','2020-08-17 02:33:35'),(48,'public','신발','슬리퍼','슬리퍼','블랙','블랙_슬리퍼',NULL,'','',NULL,'a_1597631653689.jpg','/download/clothes?imageFileName=a_1597631653689.jpg',NULL,'a','default','2020-08-17 02:34:13'),(49,'public','신발','슬리퍼','슬리퍼','베이지','베이지_슬리퍼',NULL,'','',NULL,'a_1597631711103.jpg','/download/clothes?imageFileName=a_1597631711103.jpg',NULL,'a','default','2020-08-17 02:35:11'),(50,'public','상의','반팔티셔츠','카라반팔티셔츠','브라운','브라운_카라반팔티셔츠',NULL,'','',NULL,'a_1597632391370.jpg','/download/clothes?imageFileName=a_1597632391370.jpg',NULL,'a','default','2020-08-17 02:46:31'),(51,'public','하의','슬랙스','슬랙스','아이보리','아이보리_슬랙스',NULL,'','',NULL,'a_1597632435925.jpg','/download/clothes?imageFileName=a_1597632435925.jpg',NULL,'a','default','2020-08-17 02:47:15'),(52,'public','가방','가방','가방','브라운','브라운_가방',NULL,'','',NULL,'a_1597637090260.jpg','/download/clothes?imageFileName=a_1597637090260.jpg',NULL,'a','default','2020-08-17 04:04:50'),(53,'public','상의','반팔티셔츠','민소매티셔츠','블랙','블랙_민소매티셔츠',NULL,'','',NULL,'a_1597638264271.jpg','/download/clothes?imageFileName=a_1597638264271.jpg',NULL,'a','default','2020-08-17 04:24:24'),(54,'public','상의','가디건','가디건','화이트','화이트_가디건',NULL,'','',NULL,'a_1597638313265.jpg','/download/clothes?imageFileName=a_1597638313265.jpg',NULL,'a','default','2020-08-17 04:25:13'),(55,'public','하의','반바지','반바지','스카이블루','스카이블루_반바지',NULL,'','',NULL,'a_1597638351149.jpg','/download/clothes?imageFileName=a_1597638351149.jpg',NULL,'a','default','2020-08-17 04:25:51'),(56,'public','액세서리','목걸이','목걸이','실버','실버_목걸이',NULL,'','',NULL,'a_1597638386505.jpg','/download/clothes?imageFileName=a_1597638386505.jpg',NULL,'a','default','2020-08-17 04:26:26'),(57,'public','액세서리','모자','캡모자','블랙','블랙_캡모자',NULL,'','',NULL,'a_1597638417180.jpg','/download/clothes?imageFileName=a_1597638417180.jpg',NULL,'a','default','2020-08-17 04:26:57'),(58,'public','외투','점퍼','야구점퍼','블랙','블랙_야구점퍼',NULL,'','',NULL,'a_1597639124465.jpg','/download/clothes?imageFileName=a_1597639124465.jpg',NULL,'a','default','2020-08-17 04:38:44'),(59,'public','상의','맨투맨','맨투맨','아이보리','아이보리_맨투맨','가을','','',NULL,'a_1597639171013.jpg','/download/clothes?imageFileName=a_1597639171013.jpg',NULL,'a','default','2020-08-17 04:39:31'),(60,'public','하의','면바지','면바지','블랙','블랙_면바지',NULL,'','',NULL,'a_1597639201853.jpg','/download/clothes?imageFileName=a_1597639201853.jpg',NULL,'a','default','2020-08-17 04:40:01'),(61,'public','신발','운동화','운동화','화이트','화이트_운동화',NULL,'','',NULL,'a_1597639244239.jpg','/download/clothes?imageFileName=a_1597639244239.jpg',NULL,'a','default','2020-08-17 04:40:44'),(62,'public','외투','점퍼','항공점퍼','블랙','블랙_항공점퍼',NULL,'','',NULL,'a_1597639343384.jpg','/download/clothes?imageFileName=a_1597639343384.jpg','yes','a','default','2020-08-17 04:42:23'),(63,'public','상의','후디','후드티셔츠','그레이','그레이_후드티셔츠',NULL,'','',NULL,'a_1597639370674.jpg','/download/clothes?imageFileName=a_1597639370674.jpg',NULL,'a','default','2020-08-17 04:42:50'),(64,'public','외투','자켓','데님/청자켓','블랙','블랙_데님/청자켓',NULL,'','',NULL,'a_1597640353939.jpg','/download/clothes?imageFileName=a_1597640353939.jpg',NULL,'a','default','2020-08-17 04:59:13'),(65,'public','상의','후디','후드집업','그레이','그레이_후드집업',NULL,'','',NULL,'a_1597640393436.jpg','/download/clothes?imageFileName=a_1597640393436.jpg',NULL,'a','default','2020-08-17 04:59:53'),(66,'public','상의','긴팔티셔츠','라운드넥긴팔티셔츠','블랙','블랙_라운드넥긴팔티셔츠',NULL,'','',NULL,'a_1597640440544.jpg','/download/clothes?imageFileName=a_1597640440544.jpg',NULL,'a','default','2020-08-17 05:00:40'),(67,'public','하의','청바지','청바지','네이비','네이비_청바지',NULL,'','',NULL,'a_1597640473632.jpg','/download/clothes?imageFileName=a_1597640473632.jpg',NULL,'a','default','2020-08-17 05:01:13'),(68,'public','외투','자켓','가죽자켓','블랙','블랙_가죽자켓',NULL,'','',NULL,'a_1597640976011.jpg','/download/clothes?imageFileName=a_1597640976011.jpg','yes','a','default','2020-08-17 05:09:36'),(69,'public','상의','반팔티셔츠','라운드넥반팔티셔츠','블랙','블랙_라운드넥반팔티셔츠',NULL,'','',NULL,'a_1597641005179.jpg','/download/clothes?imageFileName=a_1597641005179.jpg',NULL,'a','default','2020-08-17 05:10:05'),(70,'public','하의','스커트','미디엄스커트','브라운','브라운_미디엄스커트',NULL,'','',NULL,'a_1597641047963.jpg','/download/clothes?imageFileName=a_1597641047963.jpg',NULL,'a','default','2020-08-17 05:10:47'),(72,'public','가방','가방','가방','블랙','블랙_가방',NULL,'','',NULL,'a_1597641090063.jpg','/download/clothes?imageFileName=a_1597641090063.jpg',NULL,'a','default','2020-08-17 05:11:30'),(73,'public','신발','구두','구두','블랙','블랙_구두',NULL,'','',NULL,'a_1597641117049.jpg','/download/clothes?imageFileName=a_1597641117049.jpg',NULL,'a','default','2020-08-17 05:11:57'),(74,'public','외투','자켓','블레이저','브라운','브라운_블레이저',NULL,'','',NULL,'a_1597642627737.jpg','/download/clothes?imageFileName=a_1597642627737.jpg',NULL,'a','default','2020-08-17 05:37:07'),(75,'public','상의','반팔티셔츠','라운드넥반팔티셔츠','브라운','브라운_라운드넥반팔티셔츠',NULL,'','',NULL,'a_1597642660017.jpg','/download/clothes?imageFileName=a_1597642660017.jpg',NULL,'a','default','2020-08-17 05:37:40'),(76,'public','하의','면바지','면바지','화이트','화이트_면바지',NULL,'','',NULL,'a_1597642685233.jpg','/download/clothes?imageFileName=a_1597642685233.jpg',NULL,'a','default','2020-08-17 05:38:05'),(77,'public','신발','워커/부츠','워커/부츠','블랙','블랙_워커/부츠',NULL,'','',NULL,'a_1597642753805.jpg','/download/clothes?imageFileName=a_1597642753805.jpg',NULL,'a','default','2020-08-17 05:39:13'),(78,'public','가방','가방','가방','블랙','블랙_가방',NULL,'','',NULL,'a_1597642787409.jpg','/download/clothes?imageFileName=a_1597642787409.jpg',NULL,'a','default','2020-08-17 05:39:47'),(79,'public','액세서리','모자','바캉스모자','베이지','베이지_바캉스모자',NULL,'','',NULL,'a_1597643840260.jpg','/download/clothes?imageFileName=a_1597643840260.jpg',NULL,'a','default','2020-08-17 05:57:20'),(80,'public','한벌옷','원피스','민소매원피스','화이트','화이트_민소매원피스',NULL,'','',NULL,'a_1597643869664.jpg','/download/clothes?imageFileName=a_1597643869664.jpg',NULL,'a','default','2020-08-17 05:57:49'),(81,'public','신발','슬리퍼','슬리퍼','브라운','브라운_슬리퍼','여름','','',NULL,'a_1597643900605.jpg','/download/clothes?imageFileName=a_1597643900605.jpg',NULL,'a','default','2020-08-17 05:58:20'),(82,'public','액세서리','모자','털모자','베이지','베이지_털모자',NULL,'','',NULL,'a_1597644335561.jpg','/download/clothes?imageFileName=a_1597644335561.jpg',NULL,'a','default','2020-08-17 06:05:35'),(83,'public','하의','스커트','롱스커트','베이지','베이지_롱스커트',NULL,'','',NULL,'a_1597644377085.jpg','/download/clothes?imageFileName=a_1597644377085.jpg',NULL,'a','default','2020-08-17 06:06:17'),(84,'public','신발','스니커즈','스니커즈','아이보리','아이보리_스니커즈',NULL,'','',NULL,'a_1597644400428.jpg','/download/clothes?imageFileName=a_1597644400428.jpg',NULL,'a','default','2020-08-17 06:06:40'),(85,'public','외투','코트','트렌치코트','아이보리','아이보리_트렌치코트',NULL,'','',NULL,'boy_1597924045131.jpg','/download/clothes?imageFileName=boy_1597924045131.jpg',NULL,'boy','default','2020-08-20 11:47:25'),(86,'public','외투','코트','트렌치코트','베이지','베이지_트렌치코트',NULL,'','',NULL,'boy_1597924078602.jpg','/download/clothes?imageFileName=boy_1597924078602.jpg',NULL,'boy','default','2020-08-20 11:47:58'),(87,'public','외투','코트','트렌치코트','블랙','블랙_트렌치코트',NULL,'','',NULL,'boy_1597924116043.jpg','/download/clothes?imageFileName=boy_1597924116043.jpg',NULL,'boy','default','2020-08-20 11:48:36'),(88,'public','외투','코트','트렌치코트','브라운','브라운_트렌치코트',NULL,'','',NULL,'boy_1597924164477.jpg','/download/clothes?imageFileName=boy_1597924164477.jpg',NULL,'boy','default','2020-08-20 11:49:24'),(89,'public','상의','셔츠','클래식/드레스셔츠','화이트','화이트_클래식/드레스셔츠',NULL,'','',NULL,'boy_1597924197053.jpg','/download/clothes?imageFileName=boy_1597924197053.jpg',NULL,'boy','default','2020-08-20 11:49:57'),(90,'private','액세서리','넥타이','넥타이','네이비','네이비_넥타이',NULL,'','',NULL,'boy_1597924326228.jpg','/download/clothes?imageFileName=boy_1597924326228.jpg',NULL,'boy','default','2020-08-20 11:52:06'),(91,'public','액세서리','넥타이','넥타이','네이비','네이비_넥타이',NULL,'','',NULL,'boy_1597924352543.jpg','/download/clothes?imageFileName=boy_1597924352543.jpg',NULL,'boy','default','2020-08-20 11:52:32'),(92,'public','외투','자켓','블레이저','네이비','네이비_블레이저',NULL,'','',NULL,'boy_1597924465088.jpg','/download/clothes?imageFileName=boy_1597924465088.jpg',NULL,'boy','default','2020-08-20 11:54:25'),(93,'public','하의','슬랙스','슬랙스','네이비','네이비_슬랙스',NULL,'','',NULL,'boy_1597924488770.jpg','/download/clothes?imageFileName=boy_1597924488770.jpg',NULL,'boy','default','2020-08-20 11:54:48'),(94,'public','신발','구두','구두','브라운','브라운_구두',NULL,'','',NULL,'boy_1597924658849.jpg','/download/clothes?imageFileName=boy_1597924658849.jpg',NULL,'boy','default','2020-08-20 11:57:38'),(95,'public','가방','가방','가방','블랙','블랙_가방',NULL,'','',NULL,'boy_1597924714645.jpg','/download/clothes?imageFileName=boy_1597924714645.jpg',NULL,'boy','default','2020-08-20 11:58:34'),(96,'public','외투','코트','무스탕','브라운','브라운_무스탕',NULL,'','',NULL,'cat_1597925609607.jpg','/download/clothes?imageFileName=cat_1597925609607.jpg',NULL,'cat','default','2020-08-20 12:13:29'),(97,'public','상의','긴팔티셔츠','라운드넥긴팔티셔츠','화이트','화이트_라운드넥긴팔티셔츠',NULL,'','',NULL,'cat_1597925627769.jpg','/download/clothes?imageFileName=cat_1597925627769.jpg',NULL,'cat','default','2020-08-20 12:13:47'),(98,'public','하의','스커트','숏/미니스커트','그레이','그레이_숏/미니스커트',NULL,'','',NULL,'cat_1597925649659.jpg','/download/clothes?imageFileName=cat_1597925649659.jpg',NULL,'cat','default','2020-08-20 12:14:09'),(99,'public','신발','워커/부츠','워커/부츠','블랙','블랙_워커/부츠',NULL,'','',NULL,'cat_1597925670424.jpg','/download/clothes?imageFileName=cat_1597925670424.jpg',NULL,'cat','default','2020-08-20 12:14:30'),(100,'public','외투','코트','무스탕','브라운','브라운_무스탕',NULL,'','',NULL,'cat_1597925755021.jpg','/download/clothes?imageFileName=cat_1597925755021.jpg',NULL,'cat','default','2020-08-20 12:15:55'),(101,'public','한벌옷','원피스','긴팔원피스','네이비','네이비_긴팔원피스',NULL,'','',NULL,'cat_1597925796212.jpg','/download/clothes?imageFileName=cat_1597925796212.jpg',NULL,'cat','default','2020-08-20 12:16:36'),(102,'public','액세서리','시계','시계','와인','와인_시계',NULL,'','',NULL,'cat_1597925832101.jpg','/download/clothes?imageFileName=cat_1597925832101.jpg',NULL,'cat','default','2020-08-20 12:17:12'),(103,'public','신발','워커/부츠','워커/부츠','브라운','브라운_워커/부츠',NULL,'','',NULL,'cat_1597925855602.jpg','/download/clothes?imageFileName=cat_1597925855602.jpg',NULL,'cat','default','2020-08-20 12:17:35'),(104,'public','가방','가방','가방','핑크','핑크_가방',NULL,'','',NULL,'cat_1597925875992.jpg','/download/clothes?imageFileName=cat_1597925875992.jpg',NULL,'cat','default','2020-08-20 12:17:55'),(105,'public','하의','면바지','면바지','블랙','블랙_면바지',NULL,'','',NULL,'cat_1597926047509.jpg','/download/clothes?imageFileName=cat_1597926047509.jpg',NULL,'cat','default','2020-08-20 12:20:47'),(106,'public','가방','가방','가방','스카이블루','스카이블루_가방',NULL,'','',NULL,'cat_1597926073838.jpg','/download/clothes?imageFileName=cat_1597926073838.jpg',NULL,'cat','default','2020-08-20 12:21:13'),(107,'public','가방','가방','가방','핑크','핑크_가방','봄','프라다','',NULL,'cat_1597926120279.jpg','/download/clothes?imageFileName=cat_1597926120279.jpg',NULL,'cat','default','2020-08-20 12:22:00'),(108,'public','신발','운동화','운동화','스카이블루','스카이블루_운동화',NULL,'아디다스','',NULL,'cat_1597926150521.jpg','/download/clothes?imageFileName=cat_1597926150521.jpg',NULL,'cat','default','2020-08-20 12:22:30'),(109,'public','외투','코트','무스탕','블랙','블랙_무스탕',NULL,'','',NULL,'cat_1597926287101.jpg','/download/clothes?imageFileName=cat_1597926287101.jpg',NULL,'cat','default','2020-08-20 12:24:47'),(110,'public','상의','니트','라운드넥니트','아이보리','아이보리_라운드넥니트',NULL,'','',NULL,'cat_1597926308649.jpg','/download/clothes?imageFileName=cat_1597926308649.jpg',NULL,'cat','default','2020-08-20 12:25:08'),(111,'public','하의','레깅스','레깅스','블랙','블랙_레깅스',NULL,'','',NULL,'cat_1597926391340.jpg','/download/clothes?imageFileName=cat_1597926391340.jpg',NULL,'cat','default','2020-08-20 12:26:31'),(112,'public','가방','가방','가방','블랙','블랙_가방',NULL,'','',NULL,'cat_1597926512659.jpg','/download/clothes?imageFileName=cat_1597926512659.jpg',NULL,'cat','default','2020-08-20 12:28:32'),(113,'public','상의','가디건','가디건','그레이','그레이_가디건',NULL,'','',NULL,'cat_1597926872976.jpg','/download/clothes?imageFileName=cat_1597926872976.jpg',NULL,'cat','default','2020-08-20 12:34:32'),(114,'public','상의','긴팔티셔츠','라운드넥긴팔티셔츠','블랙','블랙_라운드넥긴팔티셔츠',NULL,'','',NULL,'cat_1597926894774.jpg','/download/clothes?imageFileName=cat_1597926894774.jpg',NULL,'cat','default','2020-08-20 12:34:54'),(115,'public','하의','청바지','청바지','네이비','네이비_청바지',NULL,'','',NULL,'cat_1597926943269.jpg','/download/clothes?imageFileName=cat_1597926943269.jpg',NULL,'cat','default','2020-08-20 12:35:43'),(116,'public','신발','구두','구두','브라운','브라운_구두',NULL,'','',NULL,'cat_1597927013454.jpg','/download/clothes?imageFileName=cat_1597927013454.jpg',NULL,'cat','default','2020-08-20 12:36:53'),(117,'public','외투','점퍼','야상점퍼','올리브그린','올리브그린_야상점퍼',NULL,'','',NULL,'cat_1597927104845.jpg','/download/clothes?imageFileName=cat_1597927104845.jpg',NULL,'cat','default','2020-08-20 12:38:24'),(118,'public','상의','셔츠','블라우스','블랙','블랙_블라우스',NULL,'','',NULL,'cat_1597927136882.jpg','/download/clothes?imageFileName=cat_1597927136882.jpg',NULL,'cat','default','2020-08-20 12:38:56'),(119,'public','하의','레깅스','레깅스','그레이','그레이_레깅스',NULL,'','',NULL,'cat_1597927159837.jpg','/download/clothes?imageFileName=cat_1597927159837.jpg',NULL,'cat','default','2020-08-20 12:39:19'),(120,'public','상의','긴팔티셔츠','라운드넥긴팔티셔츠','화이트','화이트_라운드넥긴팔티셔츠',NULL,'','',NULL,'cat_1597927184590.jpg','/download/clothes?imageFileName=cat_1597927184590.jpg',NULL,'cat','default','2020-08-20 12:39:44'),(121,'public','상의','가디건','가디건','블랙','블랙_가디건','가을','','',NULL,'cat_1597927216305.jpg','/download/clothes?imageFileName=cat_1597927216305.jpg',NULL,'cat','default','2020-08-20 12:40:16'),(122,'public','신발','스니커즈','스니커즈','블랙','블랙_스니커즈','봄','컨버스','',NULL,'cat_1597927241966.jpg','/download/clothes?imageFileName=cat_1597927241966.jpg',NULL,'cat','default','2020-08-20 12:40:41'),(123,'public','상의','가디건','가디건','베이지','베이지_가디건',NULL,'','',NULL,'cat_1597927383089.jpg','/download/clothes?imageFileName=cat_1597927383089.jpg',NULL,'cat','default','2020-08-20 12:43:03'),(124,'public','한벌옷','원피스','긴팔원피스','와인','와인_긴팔원피스',NULL,'','',NULL,'cat_1597927462352.jpg','/download/clothes?imageFileName=cat_1597927462352.jpg',NULL,'cat','default','2020-08-20 12:44:22'),(125,'public','가방','가방','가방','와인','와인_가방',NULL,'','',NULL,'cat_1597927481541.jpg','/download/clothes?imageFileName=cat_1597927481541.jpg',NULL,'cat','default','2020-08-20 12:44:41');
/*!40000 ALTER TABLE `CLOTHES` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CODI`
--

DROP TABLE IF EXISTS `CODI`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CODI` (
  `codiNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '코디번호(대리키)',
  `codiName` varchar(45) DEFAULT NULL COMMENT '이름',
  `season` varchar(45) DEFAULT NULL COMMENT '계절',
  `place` varchar(45) DEFAULT NULL COMMENT '장소',
  `buyDate` date DEFAULT NULL COMMENT '구입 날짜',
  `comment` varchar(1000) DEFAULT NULL COMMENT '코멘트 500자',
  `favorite` varchar(45) DEFAULT NULL,
  `fileName` varchar(50) DEFAULT NULL COMMENT '파일이름',
  `filePath` varchar(80) DEFAULT NULL COMMENT '파일경로',
  `addedDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '추가 날짜',
  `userID` varchar(30) NOT NULL COMMENT '유저아이디',
  PRIMARY KEY (`codiNo`),
  KEY `FK_CODI_name_CLOSET_name` (`userID`),
  CONSTRAINT `FK_CODI_name_CLOSET_name` FOREIGN KEY (`userID`) REFERENCES `CLOSET` (`userID`)
) ENGINE=InnoDB AUTO_INCREMENT=77 DEFAULT CHARSET=utf8 COMMENT='코디';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CODI`
--

LOCK TABLES `CODI` WRITE;
/*!40000 ALTER TABLE `CODI` DISABLE KEYS */;
INSERT INTO `CODI` VALUES (25,NULL,NULL,'daily',NULL,NULL,'yes','a_1593469596809.jpg','/download/codi?imageFileName=a_1593469596809.jpg','2020-06-29 22:26:36','a'),(26,NULL,NULL,'special',NULL,NULL,NULL,'a_1593469661736.jpg','/download/codi?imageFileName=a_1593469661736.jpg','2020-06-29 22:27:41','a'),(27,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593473058065.jpg','/download/codi?imageFileName=a_1593473058065.jpg','2020-06-29 23:24:18','a'),(28,NULL,NULL,NULL,NULL,NULL,'yes','a_1593474036925.jpg','/download/codi?imageFileName=a_1593474036925.jpg','2020-06-29 23:40:36','a'),(29,NULL,'summer',NULL,NULL,NULL,'yes','a_1593474268894.jpg','/download/codi?imageFileName=a_1593474268894.jpg','2020-06-29 23:44:28','a'),(30,NULL,'winter',NULL,NULL,NULL,NULL,'a_1593483123100.jpg','/download/codi?imageFileName=a_1593483123100.jpg','2020-06-30 02:12:03','a'),(32,NULL,'fall',NULL,NULL,NULL,NULL,'a_1593488669149.jpg','/download/codi?imageFileName=a_1593488669149.jpg','2020-06-30 03:44:29','a'),(33,NULL,NULL,'daily',NULL,NULL,NULL,'a_1593488763491.jpg','/download/codi?imageFileName=a_1593488763491.jpg','2020-06-30 03:46:03','a'),(34,NULL,'fall',NULL,NULL,NULL,NULL,'a_1593489663030.jpg','/download/codi?imageFileName=a_1593489663030.jpg','2020-06-30 04:01:03','a'),(35,NULL,NULL,'formal',NULL,NULL,NULL,'a_1593489747528.jpg','/download/codi?imageFileName=a_1593489747528.jpg','2020-06-30 04:02:27','a'),(37,NULL,'spring',NULL,NULL,NULL,NULL,'a_1593490419773.jpg','/download/codi?imageFileName=a_1593490419773.jpg','2020-06-30 04:13:39','a'),(39,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593491013652.jpg','/download/codi?imageFileName=a_1593491013652.jpg','2020-06-30 04:23:33','a'),(40,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593491330846.jpg','/download/codi?imageFileName=a_1593491330846.jpg','2020-06-30 04:28:50','a'),(41,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593492910354.jpg','/download/codi?imageFileName=a_1593492910354.jpg','2020-06-30 04:55:10','a'),(42,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593493927718.jpg','/download/codi?imageFileName=a_1593493927718.jpg','2020-06-30 05:12:07','a'),(43,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593510816837.jpg','/download/codi?imageFileName=a_1593510816837.jpg','2020-06-30 09:53:36','a'),(44,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593531579414.jpg','/download/codi?imageFileName=a_1593531579414.jpg','2020-06-30 15:39:39','a'),(45,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593531633234.jpg','/download/codi?imageFileName=a_1593531633234.jpg','2020-06-30 15:40:33','a'),(46,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593531754027.jpg','/download/codi?imageFileName=a_1593531754027.jpg','2020-06-30 15:42:34','a'),(47,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593532476140.jpg','/download/codi?imageFileName=a_1593532476140.jpg','2020-06-30 15:54:36','a'),(48,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593534992018.jpg','/download/codi?imageFileName=a_1593534992018.jpg','2020-06-30 16:36:32','a'),(50,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593560757860.jpg','/download/codi?imageFileName=a_1593560757860.jpg','2020-06-30 23:45:57','a'),(51,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593581171679.jpg','/download/codi?imageFileName=a_1593581171679.jpg','2020-07-01 05:26:11','a'),(52,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593582604762.jpg','/download/codi?imageFileName=a_1593582604762.jpg','2020-07-01 05:50:04','a'),(53,NULL,NULL,NULL,NULL,NULL,NULL,'a_1593639041280.jpg','/download/codi?imageFileName=a_1593639041280.jpg','2020-07-01 21:30:41','a'),(54,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594366274868.jpg','/download/codi?imageFileName=a_1594366274868.jpg','2020-07-10 07:31:14','a'),(55,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594554824721.jpg','/download/codi?imageFileName=a_1594554824721.jpg','2020-07-12 11:53:44','a'),(56,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594554904727.jpg','/download/codi?imageFileName=a_1594554904727.jpg','2020-07-12 11:55:04','a'),(57,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594555142554.jpg','/download/codi?imageFileName=a_1594555142554.jpg','2020-07-12 11:59:02','a'),(58,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594555491053.jpg','/download/codi?imageFileName=a_1594555491053.jpg','2020-07-12 12:04:51','a'),(59,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594568132296.jpg','/download/codi?imageFileName=a_1594568132296.jpg','2020-07-12 15:35:32','a'),(60,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594572248303.jpg','/download/codi?imageFileName=a_1594572248303.jpg','2020-07-12 16:44:08','a'),(61,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594572345293.jpg','/download/codi?imageFileName=a_1594572345293.jpg','2020-07-12 16:45:45','a'),(62,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594572527282.jpg','/download/codi?imageFileName=a_1594572527282.jpg','2020-07-12 16:48:47','a'),(63,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594572795186.jpg','/download/codi?imageFileName=a_1594572795186.jpg','2020-07-12 16:53:15','a'),(64,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594573056557.jpg','/download/codi?imageFileName=a_1594573056557.jpg','2020-07-12 16:57:36','a'),(65,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594573075469.jpg','/download/codi?imageFileName=a_1594573075469.jpg','2020-07-12 16:57:55','a'),(66,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594573159955.jpg','/download/codi?imageFileName=a_1594573159955.jpg','2020-07-12 16:59:19','a'),(67,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594576735489.jpg','/download/codi?imageFileName=a_1594576735489.jpg','2020-07-12 17:58:55','a'),(68,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594706870877.jpg','/download/codi?imageFileName=a_1594706870877.jpg','2020-07-14 06:07:50','a'),(69,NULL,NULL,NULL,NULL,NULL,NULL,'a_1594720165103.jpg','/download/codi?imageFileName=a_1594720165103.jpg','2020-07-14 09:49:25','a'),(70,NULL,NULL,NULL,NULL,NULL,NULL,'a_1595507256017.jpg','/download/codi?imageFileName=a_1595507256017.jpg','2020-07-23 12:27:36','a'),(71,NULL,NULL,NULL,NULL,NULL,NULL,'stark_1595670567290.jpg','/download/codi?imageFileName=stark_1595670567290.jpg','2020-07-25 09:49:27','stark'),(72,NULL,NULL,NULL,NULL,NULL,NULL,'a_1596942691949.jpg','/download/codi?imageFileName=a_1596942691949.jpg','2020-08-09 03:11:31','a'),(73,NULL,NULL,NULL,NULL,NULL,NULL,'a_1596942716840.jpg','/download/codi?imageFileName=a_1596942716840.jpg','2020-08-09 03:11:56','a'),(74,NULL,NULL,NULL,NULL,NULL,NULL,'1_1597190953883.jpg','/download/codi?imageFileName=1_1597190953883.jpg','2020-08-12 00:09:13','1'),(75,NULL,NULL,NULL,NULL,NULL,NULL,'1_1597191159185.jpg','/download/codi?imageFileName=1_1597191159185.jpg','2020-08-12 00:12:39','1'),(76,NULL,NULL,NULL,NULL,NULL,NULL,'a_1597507059570.jpg','/download/codi?imageFileName=a_1597507059570.jpg','2020-08-15 15:57:39','a');
/*!40000 ALTER TABLE `CODI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COMMENT`
--

DROP TABLE IF EXISTS `COMMENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `COMMENT` (
  `commentNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '댓글 고유번호',
  `boardNo` int(11) NOT NULL COMMENT '게시글 고유번호-옷 게시판 외래키',
  `writerID` varchar(45) NOT NULL COMMENT '작성자 아이디-유저 외래키',
  `contents` text COMMENT '댓글 내용',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`commentNo`),
  KEY `COMMENT_BOARD` (`boardNo`),
  KEY `COMMENT_USER` (`writerID`),
  CONSTRAINT `COMMENT_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `COMMENT_USER` FOREIGN KEY (`writerID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='옷 게시판 댓글';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COMMENT`
--

LOCK TABLES `COMMENT` WRITE;
/*!40000 ALTER TABLE `COMMENT` DISABLE KEYS */;
INSERT INTO `COMMENT` VALUES (2,94,'stark','굳!','2020-08-18 12:12:17');
/*!40000 ALTER TABLE `COMMENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `FOLLOW`
--

DROP TABLE IF EXISTS `FOLLOW`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FOLLOW` (
  `followerID` varchar(45) NOT NULL COMMENT '팔로워 아이디 - 유저 외래키',
  `followedID` varchar(45) NOT NULL COMMENT '팔로우된 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`followerID`,`followedID`),
  KEY `FOLLOWER_USER` (`followerID`),
  KEY `FOLLOWED_USER` (`followedID`),
  CONSTRAINT `FOLLOWED_USER` FOREIGN KEY (`followedID`) REFERENCES `USER` (`userID`),
  CONSTRAINT `FOLLOWER_USER` FOREIGN KEY (`followerID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='팔로우';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `FOLLOW`
--

LOCK TABLES `FOLLOW` WRITE;
/*!40000 ALTER TABLE `FOLLOW` DISABLE KEYS */;
INSERT INTO `FOLLOW` VALUES ('1','captain','2020-08-09 02:01:52'),('1','x','2020-08-09 02:02:07'),('a','1','2020-08-15 20:26:14'),('a','bcde','2020-08-06 14:24:08'),('a','captain','2020-08-09 05:08:11'),('a','cat','2020-08-20 08:29:33'),('a','clo','2020-08-06 14:26:46'),('a','stark','2020-08-09 03:13:41'),('a','x','2020-08-08 22:57:50'),('buri','x','2020-08-06 15:20:41'),('captain','a','2020-08-18 21:53:58'),('captain','cat','2020-08-18 18:32:12'),('captain','nm','2020-08-08 22:59:13'),('captain','stark','2020-08-08 22:59:05'),('captain','x','2020-08-06 14:11:22'),('clo','x','2020-08-06 14:28:28'),('stark','x','2020-08-06 14:11:16'),('uu','a','2020-08-16 16:36:52'),('x','captain','2020-08-10 07:14:19'),('x','stark','2020-08-10 07:13:38');
/*!40000 ALTER TABLE `FOLLOW` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GOOD`
--

DROP TABLE IF EXISTS `GOOD`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GOOD` (
  `commentNo` int(11) NOT NULL COMMENT '댓글 고유번호- 댓글 외래키',
  `gooderID` varchar(45) NOT NULL COMMENT '댓글 좋아요한 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 좋아요 등록일',
  PRIMARY KEY (`commentNo`,`gooderID`),
  KEY `GOOD_COMMENT` (`commentNo`),
  KEY `GOOD_USER` (`gooderID`),
  CONSTRAINT `GOOD_COMMENT` FOREIGN KEY (`commentNo`) REFERENCES `COMMENT` (`commentNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `GOOD_USER` FOREIGN KEY (`gooderID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='댓글 좋아요';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GOOD`
--

LOCK TABLES `GOOD` WRITE;
/*!40000 ALTER TABLE `GOOD` DISABLE KEYS */;
/*!40000 ALTER TABLE `GOOD` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `HEART`
--

DROP TABLE IF EXISTS `HEART`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `HEART` (
  `boardNo` int(11) NOT NULL COMMENT '게시글 고유번호-게시판 외래키',
  `hearterID` varchar(45) NOT NULL COMMENT '하트한 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`boardNo`,`hearterID`),
  KEY `HEART_BOARD` (`boardNo`),
  KEY `HEART_USER` (`hearterID`),
  CONSTRAINT `HEART_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `HEART_USER` FOREIGN KEY (`hearterID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시글 하트';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `HEART`
--

LOCK TABLES `HEART` WRITE;
/*!40000 ALTER TABLE `HEART` DISABLE KEYS */;
INSERT INTO `HEART` VALUES (90,'captain','2020-08-18 21:40:18'),(91,'captain','2020-08-18 17:51:47'),(92,'a','2020-08-18 21:04:24'),(92,'captain','2020-08-18 19:29:38'),(94,'captain','2020-08-18 12:12:08'),(95,'captain','2020-08-18 17:51:38'),(96,'a','2020-08-18 18:52:46'),(97,'a','2020-08-19 10:27:58'),(97,'captain','2020-08-18 18:49:19'),(98,'a','2020-08-18 21:03:50'),(99,'1','2020-08-18 16:06:25'),(99,'captain','2020-08-18 18:49:36'),(99,'stark','2020-08-18 14:39:41'),(107,'a','2020-08-18 17:38:34'),(107,'captain','2020-08-18 18:32:01'),(108,'a','2020-08-18 21:15:07');
/*!40000 ALTER TABLE `HEART` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RELATION`
--

DROP TABLE IF EXISTS `RELATION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RELATION` (
  `relationUserID` varchar(45) NOT NULL COMMENT '주사람ID',
  `relatedUserID` varchar(45) NOT NULL COMMENT '부사람ID',
  `type` varchar(45) DEFAULT NULL COMMENT '관계',
  PRIMARY KEY (`relationUserID`,`relatedUserID`),
  KEY `FK_RELATION_relatedUserID_USER_userID` (`relatedUserID`),
  CONSTRAINT `FK_RELATION_relatedUserID_USER_userID` FOREIGN KEY (`relatedUserID`) REFERENCES `USER` (`userID`),
  CONSTRAINT `FK_RELATION_relationUserID_USER_userID` FOREIGN KEY (`relationUserID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='유저관계';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RELATION`
--

LOCK TABLES `RELATION` WRITE;
/*!40000 ALTER TABLE `RELATION` DISABLE KEYS */;
/*!40000 ALTER TABLE `RELATION` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RELATION_BOARD_CLO`
--

DROP TABLE IF EXISTS `RELATION_BOARD_CLO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RELATION_BOARD_CLO` (
  `relNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '관계 고유번호',
  `boardNo` int(11) NOT NULL COMMENT '게시물 고유번호-게시물 외래키',
  `cloNo` int(11) NOT NULL COMMENT '옷 고유번호-옷 외래키',
  PRIMARY KEY (`relNo`),
  KEY `RELATION_TO_BOARD` (`boardNo`),
  KEY `RELATION_TO_CLO` (`cloNo`),
  CONSTRAINT `RELATION_TO_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `RELATION_TO_CLO` FOREIGN KEY (`cloNo`) REFERENCES `CLOTHES` (`cloNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=164 DEFAULT CHARSET=utf8 COMMENT='게시물-옷 관계';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RELATION_BOARD_CLO`
--

LOCK TABLES `RELATION_BOARD_CLO` WRITE;
/*!40000 ALTER TABLE `RELATION_BOARD_CLO` DISABLE KEYS */;
INSERT INTO `RELATION_BOARD_CLO` VALUES (1,76,7),(2,76,14),(5,77,14),(6,78,14),(7,79,3),(8,79,16),(19,90,84),(20,90,83),(21,90,82),(22,91,79),(23,91,80),(24,91,81),(25,92,34),(29,94,35),(30,94,36),(31,94,37),(32,95,38),(33,95,39),(34,95,40),(35,95,41),(36,96,46),(37,96,47),(38,96,48),(39,97,42),(40,97,43),(41,97,49),(42,97,44),(43,97,45),(44,98,50),(45,98,51),(46,98,52),(47,99,53),(48,99,54),(49,99,55),(50,99,40),(51,99,57),(52,99,56),(65,107,68),(66,107,69),(67,107,70),(68,107,72),(69,107,73),(70,108,74),(71,108,75),(72,108,76),(73,108,77),(74,108,78),(103,119,58),(104,119,59),(105,119,60),(106,119,61),(107,120,62),(108,120,63),(109,121,64),(110,121,65),(111,121,66),(112,121,67),(114,124,85),(115,124,89),(116,124,91),(117,124,92),(118,124,93),(119,124,94),(120,124,95),(121,125,109),(122,125,110),(123,125,105),(124,125,102),(125,125,106),(126,125,108),(138,128,100),(139,128,101),(140,128,111),(141,128,102),(142,128,103),(143,128,107),(144,129,96),(145,129,97),(146,129,98),(147,129,99),(148,129,112),(149,131,113),(150,131,114),(151,131,115),(152,131,78),(153,131,116),(154,132,117),(155,132,121),(156,132,120),(157,132,119),(158,132,122),(159,133,123),(160,133,124),(161,133,99),(162,133,44),(163,133,125);
/*!40000 ALTER TABLE `RELATION_BOARD_CLO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `userID` varchar(45) NOT NULL COMMENT '유저아이디',
  `nickname` varchar(50) DEFAULT NULL,
  `email` varchar(70) DEFAULT NULL,
  `pwd` varchar(45) DEFAULT NULL,
  `gender` varchar(10) DEFAULT NULL,
  `birth` date DEFAULT NULL,
  `emailChecked` varchar(45) DEFAULT NULL COMMENT '유저메일체크',
  `pfImageName` varchar(50) DEFAULT NULL COMMENT '프로필 이미지 이름',
  `pfImagePath` varchar(80) DEFAULT NULL COMMENT '프로필 이미지 경로',
  `pfContents` varchar(300) DEFAULT NULL COMMENT '프로필 소개글',
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='유저';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES ('1','레드','on@naver.com','1','여자','2015-02-11','no','default.png','/download/profile?imageFileName=default01.png','캐주얼 좋아합니다'),('11','레드','on@naver.com','1','여자','2015-02-11','no','default.png','/download/profile?imageFileName=default02.png','캐주얼 좋아합니다'),('1111','블루','on@naver.com','1','남자','2010-01-01','no','default.png','/download/profile?imageFileName=default03.png','하하하'),('5','젤리','on@naver.com','1','남자','2009-05-05','no','default04.png','/download/profile?imageFileName=default04.png','하하하'),('a','레드','on@naver.com','1','여자','2015-02-11','no','default.png','/download/profile?imageFileName=default00.png','캐주얼 좋아합니다'),('aa','포도','on@naver.com','1','남자','1990-01-05','no','default05.png','/download/profile?imageFileName=default05.png','프로그래머'),('abc','재즈','on@naver.com','1','남자','1980-01-05','no','default06.png','/download/profile?imageFileName=default06.png','취미 : 가든스케이프'),('air','에어',NULL,'1','여자','1999-04-01','no','air_1597924461844.jpg','/download/profile?imageFileName=air_1597924461844.jpg',NULL),('apple','사과',NULL,'1111','여자',NULL,'no','default9.png','/download/profile?imageFileName=default9.png',NULL),('bbb','bee',NULL,'1',NULL,NULL,'no','bbb_1597924611441.jpg','/download/profile?imageFileName=bbb_1597924611441.jpg','ㅎㅎㅎ\n인스타 @bee'),('bcde','바람의 나라','on@naver.com','1','여자','1977-01-05','no','default07.png','/download/profile?imageFileName=default07.png','인스타 @bcde 친추 부탁'),('boy','아메카지',NULL,'1','여자','2007-01-23','no','boy_1597922028128.jpg','/download/profile?imageFileName=boy_1597922028128.jpg',NULL),('buri','너구리',NULL,'1',NULL,NULL,'no','buri_1595785844996.jpg','/download/profile?imageFileName=buri_1595785844996.jpg',NULL),('captain','캡틴',NULL,'1',NULL,NULL,'no','captain_1595785552619.jpg','/download/profile?imageFileName=captain_1595785552619.jpg',NULL),('cat','고앵이',NULL,'1','여자','1996-10-21','no','cat_1597754032484.jpg','/download/profile?imageFileName=cat_1597754032484.jpg',NULL),('clo','클로',NULL,'1111','여자','2010-01-05','no','default9.png','/download/profile?imageFileName=default9.png',NULL),('g','ㅇㅇㅇ',NULL,'1','ㅁ',NULL,'no','g_1597753409391.jpg','/download/profile?imageFileName=g_1597753409391.jpg',NULL),('grape','포도',NULL,'11','남자','2010-08-21','no','grape_1595672044172.jpg','/download/profile?imageFileName=grape_1595672044172.jpg',NULL),('hello','hello',NULL,'1',NULL,NULL,'no','default1.png','/download/profile?imageFileName=default1.png',NULL),('joshua','조슈아',NULL,'11','남자','2020-01-01','no','joshua_1595670243063.jpg','/download/profile?imageFileName=joshua_1595670243063.jpg',NULL),('l','엘',NULL,'1111','남자','1990-01-01','no','default2.png','/download/profile?imageFileName=default2.png',NULL),('lemon','unknown',NULL,'1',NULL,NULL,'no','default5.png','/download/profile?imageFileName=default5.png',NULL),('lemonade','레모네이드',NULL,'1','여자','1996-10-21','no','lemonade_1595764432852.jpg','/download/profile?imageFileName=lemonade_1595764432852.jpg',NULL),('min','민',NULL,'1','남자','1999-09-09','no','default11.png','/download/profile?imageFileName=default11.png',NULL),('mm','mm',NULL,'1',NULL,NULL,'no','default12.png','/download/profile?imageFileName=default12.png',NULL),('nm','깃헙',NULL,'5555','여자','1979-12-29','no','nm_1595746768435.jpg','/download/profile?imageFileName=nm_1595746768435.jpg',NULL),('r','R',NULL,'1',NULL,NULL,'no','default3.png','/download/profile?imageFileName=default3.png',NULL),('stark','스타크',NULL,'11','남자','1980-02-01','no','stark_1595670511141.jpg','/download/profile?imageFileName=stark_1595670511141.jpg',NULL),('uu','u_u',NULL,'333',NULL,NULL,'no','uu_1597595731624.jpg','/download/profile?imageFileName=uu_1597595731624.jpg',NULL),('x','X',NULL,'1','남자',NULL,'no','x_1595745504586.jpg','/download/profile?imageFileName=x_1595745504586.jpg',NULL);
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-20 22:02:00
