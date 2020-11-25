CREATE TABLE member(
   id varchar2(12) primary key,
   pw varchar2(20),
   addr varchar2(100),
   tel varchar2(100)
)


SELECT * from member
drop table member