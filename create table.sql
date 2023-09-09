# ————————————————————————————department表(部门表)——————————————————————————————

CREATE TABLE `department`(
	`d_id` INT NOT NULL AUTO_INCREMENT,
	`name` VARCHAR(20) UNIQUE NOT NULL,
	`principal` VARCHAR(20) NOT NULL COMMENT '负责人',
	`managecode` VARCHAR(50) UNIQUE NOT NULL COMMENT '准入码',
	`time` int NOT NULL DEFAULT 1,
	PRIMARY KEY d_id(d_id),
	INDEX `idx_ms_did`(d_id)
)ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
-- 8mb4_0900_ai_ci来说,0900代表Unicode 9.0的规范，ai表示accent insensitivity，也就是“不区分音调”，而ci表示case insensitivity，也就是“不区分大小写”。

# ————————————————————————————USER表(员工表)——————————————————————————————

CREATE TABLE `user`(
	`u_id` INT NOT NULL AUTO_INCREMENT,
	`d_id` INT NOT NULL,
	`name` VARCHAR(20) UNIQUE NOT NULL,
	`password` VARCHAR(40) NOT NULL,
	`position`VARCHAR(20),
	`email` VARCHAR(30) UNIQUE NOT NULL,
	PRIMARY KEY u_id(u_id),
  INDEX idx_ms_uid(u_id),
	FOREIGN KEY (d_id) REFERENCES `department`(d_id) ON UPDATE RESTRICT ON DELETE RESTRICT
)ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE `department` ADD CONSTRAINT FOREIGN KEY (`principal`) REFERENCES `user`(`name`) ON UPDATE CASCADE ON DELETE CASCADE

# ————————————————————————————total_pt表(个人任务总表)——————————————————————————————

CREATE TABLE `total_pt`(
	`pt_id` INT NOT NULL AUTO_INCREMENT,
	`d_id` INT NOT NULL,
	`title` VARCHAR(40) NOT NULL,
	`briefing` VARCHAR(100) NOT NULL COMMENT '任务简介',
	`detail` LONGTEXT,
	`state` INT NOT NULL,
	`start_time` DATETIME,
	`finish_time` DATETIME,
	PRIMARY KEY pt_id(pt_id),
	INDEX `idx_ms_ptid`(pt_id),
	FOREIGN KEY (d_id) REFERENCES `department`(d_id) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# ————————————————————————————p_task表(个人任务表)——————————————————————————————

CREATE TABLE `p_task`(
	`u_id` INT NOT NULL,
	`pt_id` INT NOT NULL,
	`state` INT NOT NULL,
	PRIMARY KEY (u_id,pt_id),
	INDEX `idx_ms_uid_ptid`(u_id,pt_id),
	FOREIGN KEY (u_id) REFERENCES `user`(u_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (pt_id) REFERENCES `total_pt`(pt_id) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# ————————————————————————————total_dt表(任务总表)——————————————————————————————

CREATE TABLE `total_dt`(
	`dt_id` INT NOT NULL AUTO_INCREMENT,
	`title` VARCHAR(40) NOT NULL,
	`briefing` VARCHAR(100) NOT NULL COMMENT '任务简介',
	`detail` LONGTEXT,
	`state` INT NOT NULL,
	`start_time` DATETIME,
	`finish_time` DATETIME,
	PRIMARY KEY dt_id(dt_id),
	INDEX `idx_ms_dtid`(dt_id)
)ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# ————————————————————————————d_task表(部门任务表)——————————————————————————————
CREATE TABLE `d_task`(
	`d_id` INT NOT NULL,
	`dt_id` INT NOT NULL,
	`state` INT NOT NULL,
	PRIMARY KEY (d_id,dt_id),
	INDEX `idx_ms_did_dtid`(d_id,dt_id),
	FOREIGN KEY (d_id) REFERENCES `department`(d_id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY (dt_id) REFERENCES `total_dt`(dt_id) ON UPDATE CASCADE ON DELETE CASCADE
)ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# ————————————————————————————notice表(公告表)——————————————————————————————

CREATE TABLE `notice`(
	`id` INT NOT NULL AUTO_INCREMENT,
	`time` DATETIME,
	`title` VARCHAR(40) NOT NULL,
	`detail` LONGTEXT,
	PRIMARY KEY (id),
	INDEX `idx_ms_ntid`(id)
)ENGINE=InnoDB CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

# ————————————————————————————添加示例数据——————————————————————————————
-- DROP DATABASE ms
-- CREATE DATABASE ms
-- USE ms

INSERT INTO `department` VALUES 
(1,'总经理','root','key1',1),
(2,'市场部','s1','key2',2),
(3,'研发部','s2','key3',5);

INSERT INTO `USER` VALUES 
(1,1,'root','a976a7761715197401a80dd746132e46','总经理','000@123.com'),			#'000000'
(2,2,'p1','837186dcf708e69ca1b0a3db9d1e2aad','成员','111@123.com'),					#'111111'
(3,2,'p2','22f964b90890e7833a13552f00526299','成员','112@123.com'),					#'111112'
(4,2,'p3','7b65b5fe94588bf29a27bb4b50d0c9e2','成员','113@123.com'),					#'111113'
(5,2,'p4','b696b50305d3318ef9b899de6e6b8c1b','成员','114@123.com'),					#'111114'
(6,2,'s1','6c332b2aa175f467b9521580cd245d82','部门总管','020@123.com'),			#'000020'
(7,2,'p11','22f2f14e74d7cecf6145e2d093281457','成员','121@123.com'),				#'111121'
(8,2,'p12','a931ead1807324f3e084e8d97b4e0b4d','成员','122@123.com'),				#'111122'
(9,2,'p13','6992f8ad27dddb6bccc7b3ec934ceea5','成员','123@123.com'),				#'111123'
(10,2,'p14','91e68271411c9c10d4b7fc47ac22d6f9','成员','124@123.com'),				#'111124'
(11,3,'s2','7f027761bbb9e2ed4559e613adf194c3','部门总管','030@123.com'),		#'000030'
(12,3,'p21','0e59ac259aab53bad49d5292684e2c97','成员','131@123.com'),				#'111131'
(13,3,'p22','ff1d1820d4eb0c6074bca98e245da31b','成员','132@123.com'),				#'111132'
(14,3,'p23','7b821252ec1661c5d6dfebaecfdcaad7','成员','133@123.com'),				#'111133'
(15,3,'p24','2bc7c4c9a50ba5d5f5112db50b1c126d','成员','134@123.com');				#'111134'

INSERT INTO `total_pt`VALUES 
(1,2,'first person task','first task',NULL,0,'2023:08:23 15:09:37','2023:08:23 23:00:00'),
(2,3,'second person task','second task',NULL,0,'2023:08:23 00:00:00','2023:08:25 23:00:00'),
(3,3,'third person task','third task','third task',0,'2023:08:23 00:00:00','2023:08:25 23:00:00'),
(4,2,'forth person task','forth task','forth task',1,'2023:08:23 00:00:00','2023:08:25 23:00:00');

INSERT INTO `total_dt`VALUES 
(1,'first department task','first task',NULL,0,'2023:08:23 15:09:37','023:08:23 23:00:00'),
(2,'second department task','second task',NULL,0,'2023:08:23 00:00:00','2023:08:25 23:00:00'),
(3,'third department task','third task','third task',0,'2023:08:23 00:00:00','2023:08:25 23:00:00'),
(4,'forth department task','forth task','forth task',1,'2023:08:23 00:00:00','2023:08:25 23:00:00');

INSERT INTO `p_task` VALUES
(2,1,0),
(2,2,0),
(2,3,0),
(2,4,1),
(3,1,0),
(4,1,0),
(5,1,0),
(7,2,1),
(8,2,1),
(9,2,1);

INSERT INTO `d_task` VALUES
(2,1,0),
(2,2,0),
(3,3,1),
(3,4,1);

INSERT INTO `notice` VALUES
(1,'2023:08:23 00:00:00','first notice',NULL),
(2,'2023:08:23 00:00:00','second notice','About second notice'),
(3,'2023:08:23 00:00:00','third notice','third');

