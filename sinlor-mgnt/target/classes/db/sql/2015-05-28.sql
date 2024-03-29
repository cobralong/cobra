 CREATE TABLE IF NOT EXISTS `mgnt_client_upgradehistory` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `file_path` varchar(255) NOT NULL,
  `bundle_id` varchar(255) DEFAULT '',
  `sign` varchar(255) DEFAULT '',
  `ipa_channel` varchar(255) DEFAULT '',
  `support_device_flag` int(11) COMMENT 'BIT 111 means ipad/itouch/iphone',
  `support_min_os` varchar(50) DEFAULT '',
  `version` varchar(50) DEFAULT '',
  `short_version` int(11) COMMENT '系统版本值，值小的往值大的进行更新',
  `test` int(11) NOT NULL DEFAULT '1' COMMENT '0 means product upgrade or test upgrade',
  `plist` varchar(255) DEFAULT '' COMMENT 'plist文件地址',
  `upgrade_info` varchar(500) DEFAULT '' COMMENT '更新说明',
  `release_date` datetime DEFAULT ''DEFAULT '0000-00-00 00:00:00',
  `ipa_md5` CHAR(32) DEFAULT '',
  `test_ipa_status` int(11) DEFAULT '0' COMMENT '0 等待测试 -1 未通知测试 1通过测试，可进行上架',
  `info` text NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0有效-1无效记录',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `create_time` datetime NOT NULL COMMENT '记录创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用汇客户端上传黑历史列表';