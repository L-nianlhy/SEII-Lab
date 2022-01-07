use `courselearning`;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coupon
-- ----------------------------
DROP TABLE IF EXISTS `prereq`;
DROP TABLE IF EXISTS `coupon`;
DROP TABLE IF EXISTS `section`;
DROP TABLE IF EXISTS `teaches`;
DROP TABLE IF EXISTS `takes`;
CREATE TABLE `coupon`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `metadata` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `is_valid` tinyint(1) NOT NULL DEFAULT 1,
  `is_sharable` tinyint(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NOT NULL,
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `cost` int(11) NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `teacher_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_user_course`(`teacher_id`) USING BTREE,
  CONSTRAINT `fk_user_course` FOREIGN KEY (`teacher_id`) REFERENCES `user_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '软件工程与计算 I', '中级', '通过Scheme、Python和Java语言，让你分别体会函数式编程范式、结构化编程范式和面向对象编程范式', 'course1.png', '南京大学', '2020-12-20 10:00:00', NULL, 1, 100, '刘钦');
INSERT INTO `course` VALUES (2, '软件工程与计算 II', '中级', '基于DevOps培养团队开发中小规模软件系统的能力', 'course2.png', '南京大学', '2020-12-20 10:00:00', NULL, 1, 200, '刘钦');

-- ----------------------------
-- Table structure for course_likes
-- ----------------------------
DROP TABLE IF EXISTS `course_likes`;
CREATE TABLE `course_likes`  (
  `course_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`course_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_order
-- ----------------------------
DROP TABLE IF EXISTS `course_order`;
CREATE TABLE `course_order`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `origin` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `course_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  `user_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_course_order`(`course_id`) USING BTREE,
  INDEX `fk_user_order`(`user_id`) USING BTREE,
  CONSTRAINT `fk_course_order` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_user_order` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_order_coupon
-- ----------------------------
DROP TABLE IF EXISTS `course_order_coupon`;
CREATE TABLE `course_order_coupon`  (
  `order_id` int(11) NOT NULL,
  `coupon_id` int(11) NOT NULL,
  PRIMARY KEY (`order_id`, `coupon_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course_ware
-- ----------------------------
DROP TABLE IF EXISTS `course_ware`;
CREATE TABLE `course_ware`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `number` int(11) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `file_size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `free_flag` tinyint(1) NOT NULL DEFAULT 0,
  `download_flag` tinyint(1) NOT NULL DEFAULT 1,
  `upload_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `fk_course_ware`(`course_id`) USING BTREE,
  CONSTRAINT `fk_course_ware` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 198 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course_ware
-- ----------------------------
INSERT INTO `course_ware` VALUES (2, 1, 1, '00 - Overview', NULL, NULL, NULL, 0, 1, '2020-12-18 10:00:00');
INSERT INTO `course_ware` VALUES (3, 1, 2, '01 - 编程语言概述', NULL, NULL, NULL, 0, 1, '2020-12-18 10:00:00');

-- ----------------------------
-- Table structure for recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `recharge_order`;
CREATE TABLE `recharge_order`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `value` int(11) NOT NULL,
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`order_id`) USING BTREE,
  INDEX `fk_user_recharge`(`user_id`) USING BTREE,
  CONSTRAINT `fk_user_recharge` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_coupon
-- ----------------------------
DROP TABLE IF EXISTS `user_coupon`;
CREATE TABLE `user_coupon`  (
  `user_id` int(11) NOT NULL,
  `coupon_id` int(11) NOT NULL,
  `nums` int(11) NOT NULL,
  PRIMARY KEY (`user_id`, `coupon_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `balance` int(11) NOT NULL,
  `user_role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '刘钦', '10112345678', '123456', NULL, 0, 'TEACHER', '2020-12-18 10:00:00');
INSERT INTO `user_info` VALUES (2, '小明', '10212345678', '123456', NULL, 0, 'STUDENT', '2020-12-18 10:00:00');
INSERT INTO `user_info` VALUES (3, '小红', '10212345678', '123456', NULL, 0, 'STUDENT', '2020-12-18 10:00:00');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `create_time` datetime(0) NOT NULL,
    `theme` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL,
    `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL,
    `course_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    PRIMARY KEY (`id`) using BTREE,
    INDEX `fk_course_post`(`course_id`) USING BTREE,
    INDEX `fk_user_post`(`user_id`) USING BTREE,
    CONSTRAINT FOREIGN KEY (`course_id`) REFERENCES `course`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user_info`(`id`) ON DELETE NO ACTION ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `post` VALUES (1,'2021-07-02 18:47:50','背不完了','这门课好难',1,2);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `create_time` datetime(0) NOT NULL,
    `content` varchar(255) NOT NULL,
    `post_id` int(11) NOT NULL,
    `user_id` int(11) NOT NULL,
    `replied_id` int(11) NULL DEFAULT NULL,
    `have_read` boolean NOT NULL DEFAULT false,
    PRIMARY KEY (`id`) using BTREE,
    INDEX `fk_user_comment`(`user_id`) USING BTREE,
    INDEX `fk_post_comment`(`post_id`) USING BTREE,
    INDEX `fk_comment_comment`(`replied_id`) USING BTREE,
    CONSTRAINT FOREIGN KEY (`user_id`) REFERENCES `user_info`(`id`) ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (`post_id`) REFERENCES `post`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (`replied_id`) REFERENCES  `comment`(`id`) ON DELETE NO ACTION ON UPDATE CASCADE
)ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `comment` VALUES (1,'2021-07-02 19:05:55','我觉得这门课可以开卷考试',1,3,NULL,false);
INSERT INTO `comment` VALUES (2,'2021-07-02 19:05:55','你不会还是不会',1,1,1,false);

SET FOREIGN_KEY_CHECKS = 1;
show tables;