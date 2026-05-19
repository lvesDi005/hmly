/*==============================================================*/
/* DBMS name:      MySQL 5.7                                    */
/* Created on:     2026/5/19 09:10:29                           */
/*==============================================================*/
set names utf8;
SET sql_mode = 'NO_ENGINE_SUBSTITUTION';

drop table if exists tab_favorite;
drop table if exists tab_route_img;
drop table if exists tab_route;
drop table if exists tab_category;
drop table if exists tab_seller;
drop table if exists tab_user;

/*==============================================================*/
/* Table: tab_category                                          */
/*==============================================================*/
create table tab_category
(
   cid                  int not null auto_increment,
   cname                varchar(100) not null,
   primary key (cid),
   unique key AK_nq_categoryname (cname)
);

/*==============================================================*/
/* Table: tab_favorite                                          */
/*==============================================================*/
create table tab_favorite
(
   rid                  int not null,
   `date`                 date not null,
   uid                  int not null,
   primary key (rid, uid)
);

/*==============================================================*/
/* Table: tab_route                                             */
/*==============================================================*/
create table tab_route
(
   rid                  int not null auto_increment,
   rname                varchar(500) not null,
   price                double not null,
   routeIntroduce       varchar(1000),
   rflag                char(1) not null,
   rdate                varchar(19),
   isThemeTour          char(1) not null,
   count                int default 0,
   cid                  int not null,
   rimage               varchar(200),
   sid                  int,
   sourceId             varchar(50),
   primary key (rid),
   unique key AK_nq_sourceId (sourceId)
);

/*==============================================================*/
/* Table: tab_route_img                                         */
/*==============================================================*/
create table tab_route_img
(
   rgid                 int not null auto_increment,
   rid                  int not null,
   bigPic               varchar(200) not null,
   smallPic             varchar(200),
   primary key (rgid)
);

/*==============================================================*/
/* Table: tab_seller                                            */
/*==============================================================*/
create table tab_seller
(
   sid                  int not null auto_increment,
   sname                varchar(200) not null,
   consphone            varchar(20) not null,
   address              varchar(200),
   primary key (sid),
   unique key AK_Key_2 (sname)
);

/*==============================================================*/
/* Table: tab_user                                              */
/*==============================================================*/
create table tab_user
(
   uid                  int not null auto_increment,
   username             varchar(100) not null,
   password             varchar(32) not null,
   name                 varchar(100),
   birthday             date,
   sex                  char(1),
   telephone            varchar(11),
   email                varchar(100),
   status               char(1) ,
   code					varchar(50),

   primary key (uid),
   unique key AK_nq_username (username),
   unique key AK_nq_code (code)
);

alter table tab_favorite add constraint FK_route_favorite foreign key (rid)
      references tab_route (rid) on delete restrict on update restrict;

alter table tab_favorite add constraint FK_user_favorite foreign key (uid)
      references tab_user (uid) on delete restrict on update restrict;

alter table tab_route add constraint FK_category_route foreign key (cid)
      references tab_category (cid) on delete restrict on update restrict;

alter table tab_route add constraint FK_seller_route foreign key (sid)
      references tab_seller (sid) on delete restrict on update restrict;

alter table tab_route_img add constraint FK_route_routeimg foreign key (rid)
      references tab_route (rid) on delete restrict on update restrict;


insert  into `tab_category`(`cid`,`cname`) values (8,'全球自由行'),(5,'国内游'),(4,'处境游'),(7,'抱团定制'),(6,'港澳游'),(2,'酒店'),(1,'门票'),(3,'香港车票');
insert  into `tab_seller`(`sid`,`sname`,`consphone`,`address`) values (1,'黑马程序员','12345678901','传智播客javaEE学院');


/*Data for the table `tab_route` */

insert  into `tab_route`(`rid`,`rname`,`price`,`routeIntroduce`,`rflag`,`rdate`,`isThemeTour`,`count`,`cid`,`rimage`,`sid`,`sourceId`) values (1,'【旅展 半价特惠 重走丝路•漫游宁夏 双飞4天】银川西部影视城 穆民新村 中卫沙坡头【品美酒 回族学唱花儿 感悟民俗】',999,'走进【宁夏沙坡头】，感受西北大漠风情、体会"大漠孤烟直，长河落日圆"的塞上风光！','1','2018-02-09 01:13:16','0',0,5,'img/product/small/m304b69a4c8328f7d6b8d5dadef020fe07.jpg',1,'23677'),(2,'【官网专享 立减¥500 张家界天门山+大峡谷+凤凰古城+玻璃栈道+玻璃桥 高铁4天 无自费5钻】印象鲵宴-赶年宴+2晚蓝湾博格酒店',1799,'官网专线，顶级品质！全程超豪华住宿，2晚入住超豪华铂金-蓝湾博格国际酒店，独家尊享金马VIP贵宾专用楼层。','1','2018-02-09 01:13:17','0',990,5,'img/product/small/m34866f055de8630e94e25c40f277a79ba.jpg',1,'22066');


-- 创建管理员表
CREATE TABLE `admin` (
                         `id` INT NOT NULL AUTO_INCREMENT,
                         `username` VARCHAR(100) NOT NULL,
                         `name` VARCHAR(100),
                         `password` VARCHAR(32) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `AK_nq_username` (`username`)
);

-- 插入默认管理员账户
INSERT INTO `admin` (`id`, `username`, `name`, `password`)
VALUES (1, 'admin', '系统管理员', 'admin');

INSERT INTO `tab_user` (`uid`, `username`, `password`, `name`, `birthday`, `sex`, `telephone`, `email`, `status`, `code`)
VALUES (1, 'test', '12345678', '测试用户', '1990-01-01', '男', '13800138000', 'test@example.com', 'Y', 'TEST_CODE_001');