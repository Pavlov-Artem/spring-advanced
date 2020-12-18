insert into gift_certificate (name,description,price,create_date,last_update_date,duration)
values('vip_certificate','very important certificate', 10, current_timestamp(), current_timestamp(), 10);
insert into gift_certificate (name,description,price,create_date,last_update_date,duration)
values('simple certificate','give some bonus somewhere', 5, current_timestamp(), current_timestamp(), 20);
insert into gift_certificate (name,description,price,create_date,last_update_date,duration)
values('journey certificate','to move somewhere ', 100, current_timestamp(), current_timestamp(), 30);
insert into gift_certificate (name,description,price,create_date,last_update_date,duration)
values('fuel certificate','gives free 100 litres of any fuel in any gas station ', 90, current_timestamp(), current_timestamp(), 30);
insert into gift_certificate (name,description,price,create_date,last_update_date,duration)
values('hair cut certificate','hair cut 50% discount on hair cut ', 100, current_timestamp(), current_timestamp(), 30);

insert into tag (name) values ('cheap');
insert into tag (name) values ('vip');
insert into tag (name) values ('discount');
insert into tag (name) values ('gift');

insert into gift_certificate_has_tag (gift_certificate_id,tag_id) values (1,2);
insert into gift_certificate_has_tag (gift_certificate_id,tag_id) values (2,2);
insert into gift_certificate_has_tag (gift_certificate_id,tag_id) values (2,3);
insert into gift_certificate_has_tag (gift_certificate_id,tag_id) values (3,1);

 insert into user (id, name, email) values (1, 'Mirabelle', 'mbenne0@amazon.co.uk');
insert into user (id, name, email) values (2, 'Westleigh', 'wswindell1@tripadvisor.com');
insert into user (id, name, email) values (3, 'Gasparo', 'gbream2@wikia.com');
insert into user (id, name, email) values (4, 'Harvey', 'hcoskerry3@paginegialle.it');
insert into user (id, name, email) values (5, 'Bran', 'bpersey4@amazon.com');
insert into user (id, name, email) values (6, 'Fara', 'fgiveen5@umn.edu');
insert into user (id, name, email) values (7, 'Raimund', 'rkohrt6@gov.uk');
insert into user (id, name, email) values (8, 'Ailina', 'afoley7@soundcloud.com');
insert into user (id, name, email) values (9, 'Hartley', 'hunderhill8@tripod.com');
insert into user (id, name, email) values (10, 'Ashien', 'aauchterlony9@psu.edu');
