update mgnt_menu set id = id - (parent - 9000)*10 where parent is not null and parent<9010;
insert into mgnt_menu values(900830, '授权服务列表', 9008, '/auth/authorizer/pc/server/info/list', 0, now(), now());
insert into mgnt_menu values(900831, '授权虚拟机器信息列表', 9008, '/auth/authorizer/pc/machine/info/list', 0, now(), now());


insert into mgnt_menu values(900842, '授权用户设备信息列表', 9008, '/auth/appleaccount/authorizer/auth/device/info/list', 0, now(), now());

insert into mgnt_menu values(900820, '授权账号已购应用列表', 9008, '/auth/appleaccount/authorizer/buy/app/info/list', 0, now(), now());
insert into mgnt_menu values(900821, '授权账号已购应用下载列表', 9008, '/auth/appleaccount/authorizer/buy/app/download/info/list', 0, now(), now());