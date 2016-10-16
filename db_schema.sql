create table minq_msgs (id varchar(256) primary key, description longblob, ts timestamp);
create table minq_msgs_acks (id varchar(256) primary key, description longblob);
