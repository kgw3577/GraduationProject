
use CLOSET;
show tables;
ALTER DATABASE CLOSET COLLATE 'utf8_general_ci';



UPDATE CODI SET favorite='yes' WHERE codiNo=29;

UPDATE CODI SET season='winter' WHERE codiNo=30;
UPDATE CODI SET place='daily' WHERE codiNo=2;

UPDATE CODI SET season='fall' WHERE codiNo=34;
UPDATE CODI SET place='special' WHERE codiNo=26;

UPDATE CODI SET season='summer' WHERE codiNo=29;
UPDATE CODI SET place='formal' WHERE codiNo=35;

ALTER TABLE CODI
   ALTER favorite SET DEFAULT 'miuhn' ;
   
desc CODI;

ALTER TABLE CODI
   modify favorite varchar(45) null;
   
insert into CODI values (null, null, null, null ,null, null, null,null,null,null,'a');
insert into CODI values (null, null, null, null ,null, null, null,null,null,null,'a');

insert into CODI values (null, '한글 제발', null, null ,null, null, null,null,null,null,'a');


desc USER;
desc CLOTHES;
desc CODI;


-- USER Table Create SQL
CREATE TABLE BOARD_CLO
(
    `boardNo`           INT             NOT NULL    COMMENT '게시글 고유번호'	AUTO_INCREMENT, 
    `userID`          VARCHAR(45)    NOT NULL    COMMENT '작성자 아이디-유저 외래키', 
    `cloNo`           INT             NOT NULL	COMMENT '옷 고유번호-옷 외래키', 
    `subject`      VARCHAR(45)    NOT NULL    COMMENT '게시글 제목',
    `contents`      TEXT    NULL    COMMENT '게시글 내용',
    `regDate`      TIMESTAMP     NOT NULL	DEFAULT CURRENT_TIMESTAMP        COMMENT '등록일', 
    `numHeart`      INT     NULL	DEFAULT 0        COMMENT '하트 개수', 
    PRIMARY KEY (boardNo)
);

ALTER TABLE BOARD_CLO COMMENT '옷 공유 게시판';

ALTER TABLE BOARD_CLO
    ADD CONSTRAINT BOARD_CLO_USER FOREIGN KEY (userID)
        REFERENCES USER (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE BOARD_CLO
    ADD CONSTRAINT BOARD_CLO_CLOTHES FOREIGN KEY (cloNo)
        REFERENCES CLOTHES (cloNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

desc BOARD;
insert into BOARD_CLO values (null, 'a', 68, '제목' ,'안녕하세요? 게시글 내용입니다.', null, null);
select * from BOARD_CLO;

ALTER TABLE BOARD_CLO
   modify numHeart int DEFAULT 0 NULL;

ALTER TABLE BOARD_CLO CHANGE boardNo cloBoardNo INT  NOT NULL    AUTO_INCREMENT;


-- 외래키 삭제 하기
SHOW CREATE TABLE BOARD; -- create 문 보기
ALTER TABLE BOARD_CLO DROP FOREIGN KEY BOARD_CLO_CLOTHES;
ALTER TABLE BOARD_CLO DROP `cloNo`;
-- 컬럼 추가하기
ALTER TABLE `BOARD` ADD `boardType`        VARCHAR(20)     NOT NULL       COMMENT '게시판 타입' AFTER `boardNo`;
ALTER TABLE `BOARD_CLO` ADD `fileName`        VARCHAR(45)     NULL       COMMENT '파일이름' AFTER `userID`;
ALTER TABLE `BOARD_CLO` ADD `filePath`        VARCHAR(100)     NULL       COMMENT '파일경로' AFTER `fileName`;

RENAME TABLE BOARD_CLO TO BOARD;
ALTER TABLE BOARD CHANGE cloBoardNo boardNo  INT  NOT NULL    AUTO_INCREMENT;

-- 중복 테이블 삭제
ALTER TABLE BOARD_CODI DROP FOREIGN KEY  BOARD_CODI_USER;
ALTER TABLE BOARD_CODI DROP `userID`;
SET foreign_key_checks = 0;
DROP TABLE BOARD_CODI;





-- USER Table Create SQL
CREATE TABLE COMMENT_BOARD_CLO
(
    `commentNo`           INT             NOT NULL    COMMENT '댓글 고유번호'	AUTO_INCREMENT, 
    `boardNo`           INT             NOT NULL    COMMENT '게시글 고유번호-옷 게시판 외래키', 
    `userID`          VARCHAR(45)    NOT NULL    COMMENT '작성자 아이디-유저 외래키',
    `contents`      TEXT    NULL    COMMENT '댓글 내용',
    `regDate`      TIMESTAMP     NOT NULL	DEFAULT CURRENT_TIMESTAMP        COMMENT '등록일', 
    `numGood`      INT     NULL	DEFAULT 0        COMMENT '좋아요 개수', 
    PRIMARY KEY (commentNo)
);

ALTER TABLE COMMENT_BOARD_CLO COMMENT '옷 게시판 댓글';

ALTER TABLE COMMENT_BOARD_CLO
    ADD CONSTRAINT COMMENT_BOARD_CLO_BOARD FOREIGN KEY (boardNo)
        REFERENCES BOARD_CLO (boardNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE COMMENT_BOARD_CLO
    ADD CONSTRAINT COMMENT_BOARD_CLO_USER FOREIGN KEY (userID)
        REFERENCES USER (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;


desc COMMENT_BOARD_CLO;
insert into COMMENT_BOARD_CLO values (null, 3, 'a', '안녕하세요? 댓글 내용입니다.', null, null);
insert into COMMENT_BOARD_CLO values (null, 5, 'a', '안녕하세요? 댓글 내용입니다.', null, null);
select * from COMMENT_BOARD_CLO;

ALTER TABLE COMMENT_BOARD_CLO CHANGE commentNo cloCommentNo INT  NOT NULL    AUTO_INCREMENT;





-- USER Table Create SQL
CREATE TABLE COMMENT_BOARD_CODI
(
    `commentNo`           INT             NOT NULL    COMMENT '댓글 고유번호'	AUTO_INCREMENT, 
    `boardNo`           INT             NOT NULL    COMMENT '게시글 고유번호-코디 게시판 외래키', 
    `userID`          VARCHAR(45)    NOT NULL    COMMENT '작성자 아이디-유저 외래키',
    `contents`      TEXT    NULL    COMMENT '댓글 내용',
    `regDate`      TIMESTAMP     NOT NULL	DEFAULT CURRENT_TIMESTAMP        COMMENT '등록일', 
    `numGood`      INT     NULL	DEFAULT 0        COMMENT '좋아요 개수', 
    PRIMARY KEY (commentNo)
);

ALTER TABLE COMMENT_BOARD_CODI COMMENT '코디 게시판 댓글';

ALTER TABLE COMMENT_BOARD_CODI
    ADD CONSTRAINT COMMENT_BOARD_CODI_BOARD FOREIGN KEY (boardNo)
        REFERENCES BOARD_CODI (boardNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE COMMENT_BOARD_CODI
    ADD CONSTRAINT COMMENT_BOARD_CODI_USER FOREIGN KEY (userID)
        REFERENCES USER (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;


desc COMMENT_BOARD_CODI;
insert into COMMENT_BOARD_CODI values (null, 3, 'a', '안녕하세요? 댓글 내용입니다.', null, null);
insert into COMMENT_BOARD_CODI values (null, 7, 'a', '안녕하세요? 댓글 내용입니다.', null, null);
select * from COMMENT_BOARD_CODI;


ALTER TABLE COMMENT_BOARD_CODI CHANGE commentNo codiCommentNo INT  NOT NULL    AUTO_INCREMENT;



drop table HEARTED_CLO_USER;



-- USER Table Create SQL
CREATE TABLE HEARTED_CLO_USER
(
    `cloHeartedNo`           INT             NOT NULL    COMMENT '옷 하트-유저 여부 고유번호'	AUTO_INCREMENT, 
    `cloBoardNo`           INT             NOT NULL    COMMENT '게시글 고유번호-옷 게시판 외래키', 
    `userID`          VARCHAR(45)    NOT NULL    COMMENT '작성자 아이디-유저 외래키',
    PRIMARY KEY (cloHeartedNo)
);

ALTER TABLE HEARTED_CLO_USER COMMENT '옷 게시판 하트된 유저(추가 삭제)';

ALTER TABLE HEARTED_CLO_USER
    ADD CONSTRAINT HEARTED_CLO_USER_BOARD FOREIGN KEY (cloBoardNo)
        REFERENCES BOARD_CLO (cloBoardNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HEARTED_CLO_USER
    ADD CONSTRAINT HEARTED_CLO_USER_USER FOREIGN KEY (userID)
        REFERENCES USER (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;

desc HEARTED_CLO_USER;
insert into HEARTED_CLO_USER values (null, 6, 'a');
delete from HEARTED_CLO_USER where cloHeartedNo=2;
select * from HEARTED_CLO_USER;





-- USER Table Create SQL
CREATE TABLE HEARTED_CODI_USER
(
    `codiHeartedNo`           INT             NOT NULL    COMMENT '코디 하트-유저 여부 고유번호'	AUTO_INCREMENT, 
    `codiBoardNo`           INT             NOT NULL    COMMENT '게시글 고유번호-코디 게시판 외래키', 
    `userID`          VARCHAR(45)    NOT NULL    COMMENT '작성자 아이디-유저 외래키',
    PRIMARY KEY (codiHeartedNo)
);

ALTER TABLE HEARTED_CODI_USER COMMENT '코디 게시판 하트된 유저(추가 삭제)';

ALTER TABLE HEARTED_CODI_USER
    ADD CONSTRAINT HEARTED_CODI_USER_BOARD FOREIGN KEY (codiBoardNo)
        REFERENCES BOARD_CODI (codiBoardNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE HEARTED_CODI_USER
    ADD CONSTRAINT HEARTED_CODI_USER_USER FOREIGN KEY (userID)
        REFERENCES USER (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;

desc HEARTED_CODI_USER;
insert into HEARTED_CODI_USER values (null, 7, 'a');
delete from HEARTED_CODI_USER where cloHeartedNo=2;
select * from HEARTED_CODI_USER;








-- USER Table Create SQL
CREATE TABLE GOOD_CLOCOMMENT_USER
(
    `cloCommentGoodNo`           INT             NOT NULL    COMMENT ' 좋아요 옷코멘트-유저 여부 고유번호'	AUTO_INCREMENT, 
    `cloCommentNo`           INT             NOT NULL    COMMENT '옷 댓글 고유번호-옷 댓글 외래키', 
    `userID`          VARCHAR(45)    NOT NULL    COMMENT '유저아이디-유저 외래키',
    PRIMARY KEY (cloCommentGoodNo)
);

ALTER TABLE GOOD_CLOCOMMENT_USER COMMENT '옷코멘트 좋아요한 유저(추가 삭제)';

ALTER TABLE GOOD_CLOCOMMENT_USER
    ADD CONSTRAINT GOOD_CLOCOMMENT_USER_COMMENT FOREIGN KEY (cloCommentNo)
        REFERENCES COMMENT_BOARD_CLO (cloCommentNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE GOOD_CLOCOMMENT_USER
    ADD CONSTRAINT GOOD_CLOCOMMENT_USER_USER FOREIGN KEY (userID)
        REFERENCES USER (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;

desc GOOD_CLOCOMMENT_USER;
insert into GOOD_CLOCOMMENT_USER values (null, 5, 'a');
delete from GOOD_CLOCOMMENT_USER where cloHeartedNo=2;
select * from GOOD_CLOCOMMENT_USER;





-- USER Table Create SQL
CREATE TABLE GOOD_CODICOMMENT_USER
(
    `codiCommentGoodNo`           INT             NOT NULL    COMMENT ' 좋아요 코디코멘트-유저 여부 고유번호'	AUTO_INCREMENT, 
    `codiCommentNo`           INT             NOT NULL    COMMENT '코디 댓글 고유번호-코디 댓글 외래키', 
    `userID`          VARCHAR(45)    NOT NULL    COMMENT '유저아이디-유저 외래키',
    PRIMARY KEY (codiCommentGoodNo)
);

ALTER TABLE GOOD_CODICOMMENT_USER COMMENT '코디코멘트 좋아요한 유저(추가 삭제)';

ALTER TABLE GOOD_CODICOMMENT_USER
    ADD CONSTRAINT GOOD_CODICOMMENT_USER_COMMENT FOREIGN KEY (codiCommentNo)
        REFERENCES COMMENT_BOARD_CODI (codiCommentNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE GOOD_CODICOMMENT_USER
    ADD CONSTRAINT GOOD_CODICOMMENT_USER_USER FOREIGN KEY (userID)
        REFERENCES USER (userID) ON DELETE RESTRICT ON UPDATE RESTRICT;

desc GOOD_CODICOMMENT_USER;
insert into GOOD_CODICOMMENT_USER values (null, 5, 'b');
delete from GOOD_CODICOMMENT_USER where cloHeartedNo=2;
select * from GOOD_CODICOMMENT_USER;




-- USER Table Create SQL
CREATE TABLE RELATION_CLO_CODI
(
    `relationCloCodiNo`           INT             NOT NULL    COMMENT ' 옷-코디 관계 고유번호'	AUTO_INCREMENT, 
    `cloNo`          INT    NOT NULL    COMMENT '옷 고유번호-옷 외래키',    
    `codiNo`           INT             NOT NULL    COMMENT '코디 고유번호-코디 외래키', 
    PRIMARY KEY (relationCloCodiNo)
);

ALTER TABLE RELATION_CLO_CODI COMMENT '옷-코디 관계';

ALTER TABLE RELATION_CLO_CODI
    ADD CONSTRAINT RELATION_CLO_CODI_CLO FOREIGN KEY (cloNo)
        REFERENCES CLOTHES (cloNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE RELATION_CLO_CODI
    ADD CONSTRAINT RELATION_CLO_CODI_CODI FOREIGN KEY (codiNo)
        REFERENCES CODI (codiNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

desc RELATION_CLO_CODI;
insert into RELATION_CLO_CODI values (null, 76, 33);
select * from RELATION_CLO_CODI;




DESC RELATION;
SHOW CREATE TABLE `RELATION`;
