CREATE TABLE IF NOT EXISTS `urgent_online_record`(
	`item_id` int not null,
	`locale` varchar(10) default '',
	`url` varchar(255) default '',
	`root_id` int default null,
	`name` varchar(255) default '',
	`status` int(11) NOT NULL DEFAULT '0' COMMENT '0 ok -1 del',
	`update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
	`create_time` datetime NOT NULL COMMENT '记录创建时间',
	PRIMARY KEY `PK_UOR_ITEMID`(`item_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='编辑紧急上架记录表';