
use CLOSET;
show tables;
ALTER DATABASE CLOSET COLLATE 'utf8_general_ci';

ALTER TABLE CODI
   ALTER favorite SET DEFAULT 'miuhn' ;
ALTER TABLE CODI
   modify favorite varchar(45) null;

desc USER;
desc CLOTHES;
desc CODI;
desc BOARD;
desc `COMMENT`;
desc GOOD;



-- USER Table Create SQL
CREATE TABLE BOARD
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
insert into BOARD values (null, 'codi', 'a', null, null, '코디10' ,'안녕하세요? 게시글 내용입니다.', null, 0);
SET foreign_key_checks = 1;
DELETE FROM BOARD WHERE boardNo=8;
select * from BOARD;

ALTER TABLE BOARD
   modify numHeart int DEFAULT 0 NULL;

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
SET foreign_key_checks = 1;
DROP TABLE HEARTED_CODI_USER;


ALTER TABLE `BOARD` DROP `boardType`;
ALTER TABLE `BOARD` DROP `numHeart`;
ALTER TABLE `BOARD` DROP `subject`;



-- USER Table Create SQL
CREATE TABLE `COMMENT` (
  `commentNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '댓글 고유번호',
  `boardNo` int(11) NOT NULL COMMENT '게시글 고유번호-옷 게시판 외래키',
  `writerID` varchar(45) NOT NULL COMMENT '작성자 아이디-유저 외래키',
  `contents` text COMMENT '댓글 내용',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`commentNo`),
  KEY `COMMENT_BOARD` (`boardNo`),
  KEY `COMMENT_USER` (`writerID`),
  CONSTRAINT `COMMENT_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`),
  CONSTRAINT `COMMENT_USER` FOREIGN KEY (`writerID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='옷 게시판 댓글';

desc `COMMENT`;
select * from `COMMENT`;
insert into COMMENT values (null, 38, 'a', '시계 댓글', null);

-- 중복 테이블 삭제
SET foreign_key_checks = 1;
DROP TABLE COMMENT_BOARD_CODI;


CREATE TABLE `HEART` (
  `boardNo` int(11) NOT NULL COMMENT '게시글 고유번호-게시판 외래키',
  `hearterID` varchar(45) NOT NULL COMMENT '하트한 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`boardNo`,`hearterID`),
  KEY `HEART_BOARD` (`boardNo`),
  KEY `HEART_USER` (`hearterID`),
  CONSTRAINT `HEART_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`),
  CONSTRAINT `HEART_USER` FOREIGN KEY (`hearterID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시글 하트';

show create table `HEART`;
desc `HEART`;
select * from `HEART`;
insert into HEART values (37, 'a', null);

alter table HEART drop heartNo;
alter table HEART add PRIMARY KEY(boardNo, hearterID);




CREATE TABLE `GOOD` (
  `commentNo` int(11) NOT NULL COMMENT '댓글 고유번호- 댓글 외래키',
  `gooderID` varchar(45) NOT NULL COMMENT '댓글 좋아요한 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 좋아요 등록일',
  PRIMARY KEY (`commentNo`,`gooderID`),
  KEY `GOOD_COMMENT` (`commentNo`),
  KEY `GOOD_USER` (`gooderID`),
  CONSTRAINT `GOOD_COMMENT` FOREIGN KEY (`commentNo`) REFERENCES `COMMENT` (`commentNo`),
  CONSTRAINT `GOOD_USER` FOREIGN KEY (`gooderID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='댓글 좋아요';



alter table GOOD drop goodNo;
alter table GOOD add PRIMARY KEY(commentNo, gooderID);
show create table `GOOD`;

desc `GOOD`;
select * from `GOOD`;
insert into GOOD values (3, 'bcde', null);


-- 메인 피드 쿼리 만들기 (최신글)
 SELECT U.userID writerID, U.nickname writerName, U.pfImagePath pfImagePath,
              (SELECT COUNT(*) FROM HEART where HEART.boardNo = B.boardNo) numHeart,
              (SELECT COUNT(*) FROM `COMMENT` where `COMMENT`.boardNo = B.boardNo) numComment,
              B.boardNo boardNo, B.filePath imagePath, B.contents contents, B.regDate regDate
 FROM `USER` U, `BOARD` B
 WHERE U.userID = B.userID
 ORDER BY regDate DESC;

-- 해당 게시글의 - 댓글 상세 쿼리 만들기
SELECT U.userID commenterID, U.nickname commenterName, U.pfImagePath PfImagePath,
              (SELECT COUNT(*) FROM GOOD where GOOD.commentNo = C.commentNo) numGood,
              C.commentNo commentNo, C.contents contents, C.regDate regDate
 FROM `USER` U, `COMMENT` C
 WHERE C.boardNo = '36' AND U.userID = C.writerID
 ORDER BY regDate DESC;




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



DESC USER;
ALTER TABLE USER CHANGE userName nickname  VARCHAR(50)  NULL;
ALTER TABLE USER CHANGE age birth  DATE  NULL;

ALTER TABLE USER ADD pfImageName VARCHAR(50) NULL        COMMENT '프로필 이미지 이름'; 
SHOW CREATE TABLE `USER`;
