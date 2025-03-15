-- ----------------------------
-- Table structure for t_user
-- ----------------------------

DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
                          `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID，自增',
                          `username` varchar(64) NOT NULL COMMENT '用户名',
                          `email` varchar(64) NOT NULL COMMENT '电子邮件地址',
                          `password` varchar(64) NOT NULL COMMENT '密码',
                          `salt` varchar(64) NOT NULL COMMENT '密码盐值',
                          `signature` varchar(256) DEFAULT NULL COMMENT '个性签名',
                          `status` varchar(1) DEFAULT '0' COMMENT '用户状态： 0-在用,1-无效',
                          `avatar_url` varchar(1024) DEFAULT NULL COMMENT '头像',
                          `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `idx_username` (`username`),
                          UNIQUE KEY `idx_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
