show databases;
use voucherpartner;
show tables;
drop table vp_voucher_def;
drop table vp_batch;
drop table vp_campaign;
drop table vp_campaign_vouchers;
drop table vp_file_load;
drop table vp_users;
drop table vp_vouchers;

delete  from DATABASECHANGELOG;
SET SQL_SAFE_UPDATES = 0;