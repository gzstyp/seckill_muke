/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.3.166_MySQL5.7.25
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 192.168.3.166:3307
 Source Schema         : miaosha

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 03/04/2019 16:06:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for item
-- ----------------------------
DROP TABLE IF EXISTS `item`;
CREATE TABLE `item`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品的id主键',
  `title` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '商品价格',
  `description` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品描述',
  `sales` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品销量,当用户发生交易行为后，异步修改销量+1,从而不影响下单主链路',
  `img_url` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item
-- ----------------------------
INSERT INTO `item` VALUES (1, 'iphone99', 800.00, '最好用的苹果手机', 99, 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1331582529,2936579635&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES (2, 'iphone8', 600.00, '第二好用的苹果手机', 81, 'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1651068237,2097249471&fm=26&gp=0.jpg');
INSERT INTO `item` VALUES (3, 'iphoneMax', 1200.50, 'iphoneMax苹果手机', 11, 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1834104521,1228205913&fm=11&gp=0.jpg');

-- ----------------------------
-- Table structure for item_stock
-- ----------------------------
DROP TABLE IF EXISTS `item_stock`;
CREATE TABLE `item_stock`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '库存id主键',
  `stock` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品库存量',
  `item_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品库存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of item_stock
-- ----------------------------
INSERT INTO `item_stock` VALUES (1, 100, 1);
INSERT INTO `item_stock` VALUES (2, 82, 2);
INSERT INTO `item_stock` VALUES (3, 63, 3);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单主键编号',
  `user_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '用户信息的id',
  `item_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品的id',
  `item_price` double(16, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '下单的商品金额',
  `amount` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '购买数量',
  `order_price` double(16, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '对应的商品总价',
  `promo_id` int(11) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否为秒杀[值为0时表示不是以秒杀的下单的]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品订单|用户交易领域模型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES ('2019040200000001', 1, 2, 0.00, 1, 0.00, 0);
INSERT INTO `order_info` VALUES ('2019040200000101', 1, 2, 0.00, 1, 0.00, 0);
INSERT INTO `order_info` VALUES ('2019040200000201', 1, 2, 600.00, 1, 600.00, 0);
INSERT INTO `order_info` VALUES ('2019040200000301', 1, 2, 600.50, 1, 600.02, 0);
INSERT INTO `order_info` VALUES ('2019040200000401', 1, 2, 600.05, 1, 600.00, 0);
INSERT INTO `order_info` VALUES ('2019040200000501', 1, 2, 600.00, 1, 600.50, 0);
INSERT INTO `order_info` VALUES ('2019040300000601', 2, 3, 1200.50, 1, 1200.50, 0);
INSERT INTO `order_info` VALUES ('2019040300000701', 1, 3, 1200.50, 2, 2401.00, 0);

-- ----------------------------
-- Table structure for promo
-- ----------------------------
DROP TABLE IF EXISTS `promo`;
CREATE TABLE `promo`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '秒杀活动的id',
  `promo_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '秒杀活动名称',
  `start_date` datetime(0) NOT NULL COMMENT '秒杀活动开始时间',
  `item_id` int(10) UNSIGNED NOT NULL DEFAULT 0 COMMENT '商品id,活动的商品信息',
  `promo_item_price` decimal(11, 2) UNSIGNED NOT NULL DEFAULT 0.00 COMMENT '商品活动的价格',
  `end_date` datetime(0) NOT NULL COMMENT '秒杀活动结束时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品秒杀活动表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of promo
-- ----------------------------
INSERT INTO `promo` VALUES (1, 'iphone4S抢购', '2019-04-03 17:00:17', 2, 100.00, '2019-04-03 17:30:17');
INSERT INTO `promo` VALUES (2, 'iphoneX秒杀', '2019-04-03 15:20:00', 1, 102.25, '2019-04-03 15:50:00');

-- ----------------------------
-- Table structure for sequence_info
-- ----------------------------
DROP TABLE IF EXISTS `sequence_info`;
CREATE TABLE `sequence_info`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'sequence的名字',
  `current_value` bigint(20) UNSIGNED NOT NULL DEFAULT 0 COMMENT '当前值',
  `step` tinyint(255) UNSIGNED NOT NULL DEFAULT 0 COMMENT '步长',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '模拟oracle的自增序列号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sequence_info
-- ----------------------------
INSERT INTO `sequence_info` VALUES ('order_info', 8, 1);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名|账号',
  `gender` tinyint(1) UNSIGNED NOT NULL COMMENT '性别',
  `age` mediumint(3) UNSIGNED NOT NULL DEFAULT 0 COMMENT '年龄',
  `telphone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `register_mode` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '注册方式|手机号|微信|支付宝',
  `third_party_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '第三方注册的id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_telphone`(`telphone`) USING BTREE COMMENT '手机号注册不能重复注册'
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表_不含密码_在企业级应用中_密码是不写在主表里的,而是分开存放' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '田应平', 1, 34, '13765121695', 'bytelpone', '');
INSERT INTO `user_info` VALUES (2, '黄桂兰', 2, 36, '13765090931', 'bytelpone', '');
INSERT INTO `user_info` VALUES (3, '田卓智', 1, 2, '13765090930', 'bytelpone', '');
INSERT INTO `user_info` VALUES (4, '武则天', 1, 2, '13888888888', 'bytelpone', '');

-- ----------------------------
-- Table structure for user_password
-- ----------------------------
DROP TABLE IF EXISTS `user_password`;
CREATE TABLE `user_password`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `encrpt_password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码_要和账号表分开存储',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT '账号id主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户密码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_password
-- ----------------------------
INSERT INTO `user_password` VALUES (1, 'a31c5edd5c8e9f515d00c52c61e0da34f4644e07', 1);
INSERT INTO `user_password` VALUES (2, '132e2b8e7209f13df93c10b1139542f8e0f21eb9', 2);
INSERT INTO `user_password` VALUES (3, 'b9559979ecba80ba73a7003f68df1a7f58801e45', 3);
INSERT INTO `user_password` VALUES (4, '8944107dd3331ab998ab2fbc0219a797bd748c2b', 4);

SET FOREIGN_KEY_CHECKS = 1;