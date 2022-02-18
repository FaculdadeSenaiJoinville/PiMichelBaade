CREATE DATABASE  IF NOT EXISTS `bdsonhos` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `bdsonhos`;
-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: bdsonhos
-- ------------------------------------------------------
-- Server version	8.0.18

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
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Neste campo será armazenado o id da categoria.',
  `nome` varchar(25) NOT NULL COMMENT 'Neste campo será armazenado o nome da categoria.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idCategoria_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,'refrigerantes'),(2,'pães'),(3,'doces'),(4,'frios');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `produtos` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Neste campo será armazenado o id do produto.',
  `nome` varchar(100) NOT NULL COMMENT 'Neste campo será armazenado o nome do produto.',
  `valor` decimal(9,2) unsigned NOT NULL COMMENT 'Neste campo será armazenado qual o valor pago por produto. No caso de o produto ser por kg, será o valor do kg.',
  `isvalorunidade` tinyint(1) unsigned NOT NULL COMMENT 'Neste campo será armazenado se o produto é vendido por kg ou unidade. Para os produtos vendidos por unidade será atribuido valor 1 e por kg o valor 2.',
  `categorias_id` int(10) unsigned NOT NULL COMMENT 'Neste campo ficará armazenado o id da categoria do produto.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idProduto_UNIQUE` (`id`),
  KEY `fk_produtos_categorias_idx` (`categorias_id`),
  CONSTRAINT `fk_produtos_categorias` FOREIGN KEY (`categorias_id`) REFERENCES `categorias` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` VALUES (1,'coca-cola',10.00,1,1),(2,'pão francês',20.00,2,2),(3,'cuca',5.00,2,3),(4,'presunto',15.00,1,4),(5,'sprite',50.00,1,1);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `login` varchar(45) NOT NULL COMMENT 'Este campo será responsável por armazenar o login que os usuários utilziarão para acessar o sistema e salvar qual o usuário que realizou a venda.',
  `senha` char(32) NOT NULL COMMENT 'Este campo armazenará a senha de cada usuário, sendo utilziado para entrar no sistema.',
  `nivel_usuario` tinyint(1) unsigned NOT NULL COMMENT 'Este campo armazenará o nível de usuário que irá distinguir os acessos de dentro do sistema. Serão 3 níveis de de usuário: 1- Administrador 2- Caixa 3- Estoquista',
  `data_nasc` date NOT NULL COMMENT 'Este campo armazenará a data de nascimento de cada usuário.',
  `cpf` bigint(11) unsigned zerofill NOT NULL COMMENT 'Neste campo será armazenado o cpf de cada usuário.',
  `nome` varchar(255) NOT NULL COMMENT 'Neste campo será armazenado o nome completo do usuário.',
  `email` varchar(100) NOT NULL COMMENT 'Neste campo será armazenado o e-mail do usuário.',
  `telefone` bigint(13) unsigned zerofill DEFAULT NULL COMMENT 'Neste campo será armazenado o telefone do usuário.',
  PRIMARY KEY (`login`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES ('teste','1817baed7670edc97916d2e36336db06',1,'2021-12-12',99484435963,'teste','mibaade.mb@gmail.com',0047996015808),('testecaixa','545805af3993a955610ebbff4eec05cc',2,'2021-12-12',99484435955,'testecaixa','baade@gmail.com',0047996015811),('testeestoquista','24de1aebdaca4ed734796f5a68bfc49b',3,'2021-12-12',99484437755,'testeestoquista','teste@gmail.com',0047955015811);
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas`
--

DROP TABLE IF EXISTS `vendas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Neste campo será armazenado o id da venda.',
  `data_venda` datetime NOT NULL COMMENT 'Neste campo será armazenado a data/hora na qual ocorreu a venda.',
  `usuarios_login` varchar(45) NOT NULL COMMENT 'Neste campo será armazenado o login do usuário que realizou a venda.',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idVendas_UNIQUE` (`id`),
  KEY `fk_vendas_usuarios1_idx` (`usuarios_login`),
  CONSTRAINT `fk_vendas_usuarios1` FOREIGN KEY (`usuarios_login`) REFERENCES `usuarios` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas`
--

LOCK TABLES `vendas` WRITE;
/*!40000 ALTER TABLE `vendas` DISABLE KEYS */;
INSERT INTO `vendas` VALUES (1,'2022-02-18 18:31:00','teste'),(2,'2022-02-18 18:31:06','teste'),(3,'2022-02-18 18:32:21','teste'),(4,'2022-02-18 18:32:27','teste'),(5,'2022-02-18 18:32:37','teste'),(6,'2022-02-18 19:24:50','teste');
/*!40000 ALTER TABLE `vendas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vendas_has_produtos`
--

DROP TABLE IF EXISTS `vendas_has_produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vendas_has_produtos` (
  `id_vendas_has` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `vendas_id` int(10) unsigned NOT NULL COMMENT 'Neste campo será armazenado o id da venda.',
  `produtos_id` int(10) NOT NULL COMMENT 'Neste campo será armazenado o id do produto vendido.',
  `valor` decimal(9,2) unsigned NOT NULL COMMENT 'Neste campo será armazenado o valor do produto vendido.',
  `quantidade` float unsigned NOT NULL COMMENT 'Neste campo será armazenado a quantidade do produto vendido.',
  PRIMARY KEY (`id_vendas_has`),
  KEY `fk_vendas_has_produtos_produtos1_idx` (`produtos_id`),
  KEY `fk_vendas_has_produtos_vendas1_idx` (`vendas_id`),
  CONSTRAINT `fk_vendas_has_produtos_vendas1` FOREIGN KEY (`vendas_id`) REFERENCES `vendas` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vendas_has_produtos`
--

LOCK TABLES `vendas_has_produtos` WRITE;
/*!40000 ALTER TABLE `vendas_has_produtos` DISABLE KEYS */;
INSERT INTO `vendas_has_produtos` VALUES (7,1,1,30.00,3),(8,2,5,250.00,5),(9,3,2,100.00,5),(10,4,3,35.00,7),(11,5,4,150.00,10),(12,6,5,150.00,3);
/*!40000 ALTER TABLE `vendas_has_produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'bdsonhos'
--

--
-- Dumping routines for database 'bdsonhos'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-02-18 20:54:34
