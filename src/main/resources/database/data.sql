create table sites (
   id varchar(255) not null,
   title varchar(255),
   url varchar(255),
   primary key (id)
) ENGINE=InnoDB CHARSET=utf8;

create table locations (
   id varchar(255) not null,
   location tinytext(),
   siteid varchar(255),
   ositeid varchar(255),
   version datetime(6),
   primary key (id)
) ENGINE=InnoDB CHARSET=utf8;

alter table locations 
add constraint site_fk 
foreign key (siteid) 
references sites (id);