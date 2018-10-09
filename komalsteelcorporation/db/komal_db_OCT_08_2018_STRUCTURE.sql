-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: komal_db
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `admin_login_dtls`
--

DROP TABLE IF EXISTS `admin_login_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin_login_dtls` (
  `ADMIN_LOGIN_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMAIL_ID` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `USER_TYPE` varchar(45) NOT NULL,
  `CNTC_NUM` varchar(15) NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ADMIN_LOGIN_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart_dlvry_dtls`
--

DROP TABLE IF EXISTS `cart_dlvry_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart_dlvry_dtls` (
  `CART_DLVRY_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SHIPPING_ADDRESS_ID` int(11) NOT NULL,
  `EXP_DLVRY_DT` date DEFAULT NULL,
  `ACTUAL_DLVRY_DT` date DEFAULT NULL,
  `DLVRY_BY_TRACK_ID` char(36) DEFAULT NULL,
  `DLVRY_TYPE` varchar(50) DEFAULT NULL,
  `ALTERNATE_CNTC` varchar(45) DEFAULT NULL,
  `COURIER_NM` varchar(150) DEFAULT NULL,
  `DOCATE_NO` varchar(50) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CART_DLVRY_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 COMMENT='Stores the delivery details of an order';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart_dtls`
--

DROP TABLE IF EXISTS `cart_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart_dtls` (
  `CART_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CART_DLVRY_DTLS_ID` int(11) NOT NULL,
  `CART_PRICE` decimal(10,2) NOT NULL,
  `TRACK_ID` char(36) NOT NULL,
  `CART_NOTES` varchar(300) DEFAULT NULL,
  `CART_STATUS` enum('Booked','Pending','Ordered','Dispatched','Cancelled','Completed','Packed') NOT NULL DEFAULT 'Pending',
  `INVOICE_DTLS_ID` int(11) DEFAULT NULL,
  `PAYMENT_DTLS_ID` char(36) DEFAULT NULL,
  `IS_OFFER_APPLD` enum('true','false') NOT NULL,
  `OFFER_DTLS_ID` int(11) DEFAULT NULL,
  `CREATED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LR_NO` varchar(100) DEFAULT NULL,
  `LR_NO_DATE` date DEFAULT NULL,
  `NO_OF_CARTON_LOADED` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CART_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 COMMENT='Stores the order related details of a user';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cart_item_dtls`
--

DROP TABLE IF EXISTS `cart_item_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cart_item_dtls` (
  `CART_ITEM_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CART_DTLS_ID` int(11) NOT NULL,
  `ITEM_QTY` int(11) NOT NULL,
  `ITEM_MASTER_DTLS_ID` int(11) NOT NULL,
  `ITEM_PRICE` decimal(10,4) DEFAULT NULL,
  `TRACK_ID` char(36) NOT NULL,
  `OFFER_DTLS_ID` int(11) DEFAULT NULL,
  `IS_OFFER_APPLD` enum('true','false') DEFAULT 'false',
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CART_ITEM_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=899 DEFAULT CHARSET=utf8 COMMENT='Stores the product details added to a cart and associted with a order';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category_master`
--

DROP TABLE IF EXISTS `category_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_master` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) NOT NULL,
  `CAT_DESC` varchar(300) DEFAULT NULL,
  `PARANT_ID` int(11) NOT NULL,
  `URL` varchar(2000) NOT NULL,
  `CREATED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cmpny_address_dtls`
--

DROP TABLE IF EXISTS `cmpny_address_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmpny_address_dtls` (
  `ADDRESS_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CMPNY_INFO_ID` char(36) NOT NULL,
  `ST_ADDRESS_1` varchar(100) NOT NULL,
  `ST_ADDRESS_2` varchar(100) DEFAULT NULL,
  `ST_ADDRESS_3` varchar(100) DEFAULT NULL,
  `ADDRESS_TYPE` enum('Residential','Permanent','Branch','HQ') NOT NULL,
  `CITY` varchar(50) NOT NULL,
  `STATE` varchar(50) NOT NULL,
  `COUNTRY` varchar(20) NOT NULL,
  `POSTAL_CODE` int(11) DEFAULT NULL,
  `LONGITUDE` decimal(10,5) DEFAULT NULL,
  `LATITUDE` decimal(10,5) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  PRIMARY KEY (`ADDRESS_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cmpny_info`
--

DROP TABLE IF EXISTS `cmpny_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cmpny_info` (
  `CMPNY_INFO_ID` char(36) NOT NULL,
  `CMPNY_NAME` varchar(100) NOT NULL,
  `CMPNY_LOGO` blob,
  `CMPNY_DESCRIPTION` varchar(500) DEFAULT NULL,
  `CMPNY_TITLE` varchar(50) DEFAULT NULL,
  `CMPNY_ACC_NO` varchar(50) DEFAULT NULL,
  `CMPNY_BANK` varchar(100) DEFAULT NULL,
  `CMPNY_CHECK_REF` varchar(100) DEFAULT NULL,
  `CMPNY_BANK_IFSC` varchar(20) DEFAULT NULL,
  `CMPNY_PAN_NO` varchar(20) NOT NULL,
  `CMPNY_VAT_NO` varchar(20) NOT NULL,
  `CMPNY_SERVICE_TAX_NO` varchar(20) NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CMPNY_REG` varchar(10) NOT NULL,
  PRIMARY KEY (`CMPNY_INFO_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `configuration`
--

DROP TABLE IF EXISTS `configuration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `configuration` (
  `CONFIG_NAME` varchar(200) NOT NULL,
  `CONFIG_VAL` varchar(500) DEFAULT NULL,
  `CONFIG_DESC` varchar(500) DEFAULT NULL,
  `CONFIG_IMG` blob,
  `CRAETED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CONFIG_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact_dtls`
--

DROP TABLE IF EXISTS `contact_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_dtls` (
  `CONTACT_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONTACT_NAME` char(36) DEFAULT NULL,
  `CONTACT_NUMBER` varchar(45) DEFAULT NULL,
  `SHOP_NAME` varchar(300) DEFAULT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CREATED_BY` char(45) DEFAULT NULL,
  `MODIFIED_BY` char(45) DEFAULT NULL,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`CONTACT_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `courier_master`
--

DROP TABLE IF EXISTS `courier_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `courier_master` (
  `COURIER_MASTER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COURIER_NM` varchar(500) NOT NULL,
  `TRACKING_URL` varchar(1000) NOT NULL,
  `IS_ACTIVE` tinyint(1) NOT NULL DEFAULT '1',
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`COURIER_MASTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `distributor_details`
--

DROP TABLE IF EXISTS `distributor_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `distributor_details` (
  `DISTRIBUTOR_DETAILS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRACK_ID` char(36) NOT NULL,
  `ADDRESS_DTLS_ID` int(11) NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`DISTRIBUTOR_DETAILS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `enquiry_dtls`
--

DROP TABLE IF EXISTS `enquiry_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enquiry_dtls` (
  `ENQUIRY_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHN_NM` varchar(15) NOT NULL,
  `CITY_ID` int(11) NOT NULL,
  `STATE_ID` int(11) NOT NULL,
  `EMAIL_ID` varchar(100) NOT NULL,
  `ENQUIRY_TYPE` enum('Contact','Catalogue') NOT NULL,
  `FIRST_NM` varchar(15) NOT NULL,
  `LAST_NM` varchar(15) DEFAULT NULL,
  `MESSAGE` varchar(500) DEFAULT NULL,
  `PINNO` varchar(10) DEFAULT NULL,
  `COMPANY_NAME` varchar(300) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ENQUIRY_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hsn_dtls`
--

DROP TABLE IF EXISTS `hsn_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hsn_dtls` (
  `HSN_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `HSN_NO` int(11) NOT NULL,
  `IGST` float NOT NULL DEFAULT '0',
  `SGST` float NOT NULL DEFAULT '0',
  `CGST` float NOT NULL DEFAULT '0',
  `IS_ACTIVE` enum('Y','N') NOT NULL DEFAULT 'Y',
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`HSN_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `inventory_refill_dtls`
--

DROP TABLE IF EXISTS `inventory_refill_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory_refill_dtls` (
  `INVENTORY_REFILL_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REFILL_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `RECEIVED_QTY` decimal(10,4) NOT NULL,
  `REJECTED_SCRAP_QTY` decimal(10,4) DEFAULT NULL,
  `REJECTED_REWORK_QTY` decimal(10,4) DEFAULT NULL,
  `VENDOR_ID` int(11) DEFAULT NULL,
  `REFILL_PRICE` decimal(10,4) DEFAULT NULL,
  `ITEM_MASTER_DTLS_ID` int(11) NOT NULL,
  `PER_UNIT_COST_PRICE` decimal(10,4) DEFAULT NULL,
  `MRP` decimal(10,4) NOT NULL,
  `COMMENTS` varchar(300) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  `CMPNY_INFO_ID` varchar(45) NOT NULL,
  PRIMARY KEY (`INVENTORY_REFILL_DTLS_ID`),
  KEY `fk_item_master_dtl_inventory_refils_dtl_idx` (`ITEM_MASTER_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `invoice_dtls`
--

DROP TABLE IF EXISTS `invoice_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_dtls` (
  `INVOICE_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SUB_TOTAL` decimal(10,2) NOT NULL,
  `DISCOUNT` decimal(10,2) NOT NULL,
  `DISCOUNT_VALUE` decimal(10,2) NOT NULL,
  `SERVICE_TAX` decimal(10,2) NOT NULL,
  `SERVICE_TAX_VALUE` decimal(10,2) NOT NULL,
  `VAT` decimal(10,2) NOT NULL,
  `VAT_VALUE` decimal(10,2) NOT NULL,
  `SHIPPING_CHARGES` decimal(10,2) NOT NULL,
  `MISC_CHARGES` decimal(10,2) DEFAULT NULL,
  `GRAND_TOTAL` decimal(10,2) NOT NULL,
  `AMOUNT_PAID` decimal(10,2) NOT NULL,
  `AMOUNT_BAL` decimal(10,2) NOT NULL,
  `TRACK_ID` char(36) NOT NULL,
  `CREATED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`INVOICE_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores the invoice details associated with every order';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `item_master_dtls`
--

DROP TABLE IF EXISTS `item_master_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `item_master_dtls` (
  `ITEM_MASTER_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `HSN_DTLS_ID` int(11) NOT NULL DEFAULT '0',
  `CMPNY_INFO_ID` char(36) NOT NULL,
  `ITEM_NM` varchar(100) NOT NULL,
  `ITEM_CATEGORY` int(11) NOT NULL,
  `ITEM_SUB_CATEGORY` int(11) DEFAULT NULL,
  `ITEM_DESC` varchar(300) DEFAULT NULL,
  `ITEM_CONTENT_INFO` varchar(300) DEFAULT NULL,
  `ITEM_PCKG_TYPE` varchar(20) DEFAULT NULL,
  `UOM` varchar(300) NOT NULL,
  `ITEM_MANUFACTURER` varchar(100) NOT NULL,
  `OFFER_DTLS_ID` int(11) DEFAULT NULL,
  `ITEM_IMAGE` longblob NOT NULL,
  `ITEMS_IN_MASTER_CARTON` int(11) NOT NULL,
  `MASTER_CARTON_PRICE` float DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  `MASTER_CARTON_QTY_RANGE` varchar(15) NOT NULL,
  `MASTER_CARTON_QTY_INC_VAL` varchar(5) NOT NULL,
  `ITEM_NO` varchar(10) DEFAULT NULL,
  `IS_ACTIVE` enum('Active','Inactive') DEFAULT 'Active',
  `PER_UNIT_PRICE` float NOT NULL DEFAULT '0',
  PRIMARY KEY (`ITEM_MASTER_DTLS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `items_inventory_dtls`
--

DROP TABLE IF EXISTS `items_inventory_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items_inventory_dtls` (
  `ITEMS_INVENTORY_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ITEM_MASTER_DTLS_ID` int(11) NOT NULL,
  `CMPNY_INFO_ID` char(36) DEFAULT NULL,
  `AVL_QTY` decimal(10,4) DEFAULT NULL,
  `BOOKED_QTY` decimal(10,4) DEFAULT NULL,
  `THRHLD_VAL` decimal(10,4) DEFAULT NULL,
  `MRP` decimal(10,4) DEFAULT NULL,
  `SHELF_CODE` varchar(20) DEFAULT NULL,
  `LAST_REFILL_DT` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  `is_out_of_stock` enum('true','false') NOT NULL DEFAULT 'false',
  PRIMARY KEY (`ITEMS_INVENTORY_DTLS_ID`),
  KEY `fk_item_master_dtl_id_items_inventory_dtl_idx` (`ITEM_MASTER_DTLS_ID`),
  KEY `fk_cmpny_info_id_items_inventory_dtl` (`CMPNY_INFO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `location_dtls`
--

DROP TABLE IF EXISTS `location_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location_dtls` (
  `LOCATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOCATION_NAME` varchar(500) NOT NULL,
  `LOCATION_DESC` varchar(1000) DEFAULT NULL,
  `LOCATION_PARENT_ID` int(11) NOT NULL DEFAULT '0',
  `IS_ACTIVE` enum('Active','Inactive') NOT NULL DEFAULT 'Active',
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`LOCATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4094 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `offer_dtls`
--

DROP TABLE IF EXISTS `offer_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `offer_dtls` (
  `OFFER_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CMPNY_INFO_ID` char(36) NOT NULL,
  `OFFER_NM` varchar(100) NOT NULL,
  `OFFER_CODE` varchar(10) NOT NULL,
  `OFFER_DESC` varchar(300) NOT NULL,
  `OFFER_START_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `OFFER_END_TS` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `OFFER_STATUS` enum('Active','Inactive') NOT NULL DEFAULT 'Active',
  `OFFER_DISCOUNT` decimal(10,3) NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  PRIMARY KEY (`OFFER_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `other_address_details`
--

DROP TABLE IF EXISTS `other_address_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `other_address_details` (
  `OTHER_ADDRESS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADDRESS_TYPE` enum('Shipping','Default Shipping') DEFAULT NULL,
  `CITY` varchar(45) DEFAULT NULL,
  `COUNTRY` varchar(45) DEFAULT NULL,
  `LATITUDE` decimal(10,5) DEFAULT NULL,
  `LONGITUDE` decimal(10,5) DEFAULT NULL,
  `POSTAL_CODE` varchar(45) DEFAULT NULL,
  `ST_ADDRESS_1` varchar(700) DEFAULT NULL,
  `ST_ADDRESS_2` varchar(100) DEFAULT NULL,
  `ST_ADDRESS_3` varchar(100) DEFAULT NULL,
  `STATE` varchar(45) DEFAULT NULL,
  `TRACK_ID` char(36) NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TIMESTAMP` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `MARK` varchar(45) DEFAULT NULL,
  `DESTINATION` varchar(100) DEFAULT NULL,
  `TINNO` varchar(50) DEFAULT NULL,
  `TRAN_NM` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`OTHER_ADDRESS_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `otp_details`
--

DROP TABLE IF EXISTS `otp_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `otp_details` (
  `OTP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CELLNUMBER` varchar(15) NOT NULL,
  `DEVICE_INFO` varchar(100) NOT NULL DEFAULT '" "',
  `NUM_OF_ATTEMPTS` int(11) NOT NULL DEFAULT '0',
  `OTP` int(11) NOT NULL,
  `CREATED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`OTP_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `package_master`
--

DROP TABLE IF EXISTS `package_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_master` (
  `PACKAGE_MASTER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PACKAGE_NAME` varchar(45) NOT NULL,
  `DESC` varchar(300) DEFAULT NULL,
  `IS_FOR_OPD` enum('Y','N') NOT NULL DEFAULT 'N',
  `IS_FOR_IPD` enum('Y','N') NOT NULL DEFAULT 'N',
  `IMAGE` longblob,
  `IS_ACTIVE` enum('Y','N') NOT NULL DEFAULT 'Y',
  `START_DATE` date NOT NULL,
  `END_DATE` date NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PACKAGE_MASTER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `packaging_info`
--

DROP TABLE IF EXISTS `packaging_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `packaging_info` (
  `PACKAGING_INFO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PACKAGING_INFO` varchar(500) NOT NULL,
  `PACKAGING_DESCRIPTION` varchar(500) NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PACKAGING_INFO_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_dtls`
--

DROP TABLE IF EXISTS `payment_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_dtls` (
  `PAYMENT_DTLS_ID` char(36) NOT NULL,
  `TXN_REF_NO` varchar(45) DEFAULT NULL,
  `PAYMENT_TS` timestamp NULL DEFAULT NULL,
  `PAYMENT_MODE` enum('Online','COD','Cash') DEFAULT NULL,
  `PAYMENT_STATUS` varchar(100) DEFAULT NULL,
  `PAYMENT_GATEWAY` varchar(100) DEFAULT NULL,
  `BANK_REF_NUM` varchar(50) DEFAULT NULL,
  `TXN_DATA` text,
  `PAYMENT_AMOUNT` decimal(10,4) DEFAULT NULL,
  `CREATED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`PAYMENT_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores details related to payment for anything ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sms_dtls`
--

DROP TABLE IF EXISTS `sms_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sms_dtls` (
  `SMS_HISTORY_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CONTACT_DTLS_ID` int(11) NOT NULL,
  `SMS_CONTENT` varchar(1000) DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`SMS_HISTORY_ID`),
  KEY `fk_smsdt_cdi_idx` (`CONTACT_DTLS_ID`),
  CONSTRAINT `fk_smsdt_cdi` FOREIGN KEY (`CONTACT_DTLS_ID`) REFERENCES `contact_dtls` (`CONTACT_DTLS_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `idtest` int(11) NOT NULL AUTO_INCREMENT,
  `testcol` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idtest`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transportation_master`
--

DROP TABLE IF EXISTS `transportation_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transportation_master` (
  `TRANSPORTATION_MASTER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(50) NOT NULL,
  `DESCRIPTION` varchar(300) DEFAULT NULL,
  `IS_ACTIVE` int(11) NOT NULL DEFAULT '1',
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`TRANSPORTATION_MASTER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_address_dtls`
--

DROP TABLE IF EXISTS `user_address_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_address_dtls` (
  `ADDRESS_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRACK_ID` char(36) NOT NULL,
  `ST_ADDRESS_1` varchar(100) NOT NULL,
  `ST_ADDRESS_2` varchar(100) DEFAULT NULL,
  `ST_ADDRESS_3` varchar(100) DEFAULT NULL,
  `ADDRESS_TYPE` enum('Residential','Permanent') NOT NULL DEFAULT 'Residential',
  `CITY` varchar(6) NOT NULL,
  `STATE` varchar(6) NOT NULL,
  `COUNTRY` varchar(6) NOT NULL,
  `POSTAL_CODE` int(11) DEFAULT NULL,
  `LONGITUDE` decimal(10,5) DEFAULT NULL,
  `LATITUDE` decimal(10,5) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  PRIMARY KEY (`ADDRESS_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_distributors_list`
--

DROP TABLE IF EXISTS `user_distributors_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_distributors_list` (
  `USER_DISTRIBUTION_LIST_ID` int(11) NOT NULL AUTO_INCREMENT,
  `DEALER_TRACK_ID` char(36) NOT NULL,
  `DISTRIBUTOR_TRACK_ID` char(36) NOT NULL,
  `CREATED_TS` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TIMESTAMP` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`USER_DISTRIBUTION_LIST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Stores the details about the distributors that a user is asscociated with';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_dtls`
--

DROP TABLE IF EXISTS `user_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_dtls` (
  `TRACK_ID` char(36) NOT NULL,
  `FIRST_NAME` varchar(50) NOT NULL,
  `MIDDLE_NAME` varchar(50) DEFAULT NULL,
  `LAST_NAME` varchar(50) DEFAULT NULL,
  `VAT_TIN_NO` varchar(50) DEFAULT NULL,
  `PAN_NO` varchar(50) DEFAULT NULL,
  `DOB` date DEFAULT NULL,
  `GENDER` enum('Male','Female','Other') DEFAULT NULL,
  `CNTC_NUM` varchar(20) NOT NULL,
  `CMPNY_INFO_ID` char(36) NOT NULL DEFAULT '56',
  `DISPLAY_NAME` varchar(200) DEFAULT NULL,
  `GSTNO` varchar(50) DEFAULT NULL,
  `DISCOUNT` float NOT NULL DEFAULT '0',
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  PRIMARY KEY (`TRACK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_group_dtls`
--

DROP TABLE IF EXISTS `user_group_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group_dtls` (
  `GROUP_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `GROUP_NAME` varchar(100) NOT NULL,
  `GROUP_DESC` varchar(100) NOT NULL,
  `CMPNY_INFO_ID` char(36) NOT NULL,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`GROUP_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_group_master_dtls`
--

DROP TABLE IF EXISTS `user_group_master_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group_master_dtls` (
  `USER_GROUP_MASTER_DTLS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `TRACK_ID` char(36) NOT NULL,
  `GROUP_DTLS_ID` int(11) NOT NULL,
  `CMPNY_INFO_ID` char(36) NOT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  PRIMARY KEY (`USER_GROUP_MASTER_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_login_dtls`
--

DROP TABLE IF EXISTS `user_login_dtls`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_login_dtls` (
  `USER_LOGIN_DTLS_ID` char(36) NOT NULL,
  `TRACK_ID` char(36) NOT NULL,
  `LOGIN_ID` varchar(300) NOT NULL,
  `PASSWORD` varchar(300) NOT NULL,
  `IS_EMAIL_VERIFIED` enum('true','false') NOT NULL DEFAULT 'false',
  `IS_PHONE_VERIFIED` enum('true','false') NOT NULL,
  `CHANGE_PWD` enum('true','false') NOT NULL DEFAULT 'true',
  `STATUS` enum('Active','Inactive') NOT NULL,
  `RECEIVE_OFFERS` enum('true','false') NOT NULL DEFAULT 'true',
  `TERMS_ACCEPTED` enum('true','false') NOT NULL DEFAULT 'true',
  `TERMS_ACCEPTED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `CMPNY_INFO_ID` char(36) NOT NULL,
  `USER_TYPE` enum('Dealer','Distributor','Admin') DEFAULT NULL,
  `ACTIVATION_CODE` char(36) DEFAULT NULL,
  `CREATED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `MODIFIED_TS` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `CREATED_BY` char(36) DEFAULT NULL,
  `MODIFIED_BY` char(36) DEFAULT NULL,
  PRIMARY KEY (`USER_LOGIN_DTLS_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'komal_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-08 18:09:45
