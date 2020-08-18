
use CLOSET;

INSERT INTO `BOARD` VALUES (76,'a','codi001.jpg','/download/board?imageFileName=codi001.jpg','\n #JIPSY #캐주얼','2020-08-16 07:29:03'),(77,'a','codi002.jpg','/download/board?imageFileName=codi002.jpg','실키 레이어드 화이트 셔츠\n #JIPSY #화이트_클래식/드레스셔츠','2020-08-16 07:43:39'),(78,'a','codi003.jpg','/download/board?imageFileName=codi003.jpg','\n #JIPSY #화이트_클래식/드레스셔츠','2020-08-16 07:46:38'),(79,'a','codi004.jpg','/download/board?imageFileName=codi004.jpg','헤비스웨트 데일리 팬츠\n #JIPSY #캐주얼 #블랙_트레이닝바지','2020-08-16 07:52:24');
INSERT INTO `RELATION_BOARD_CLO` VALUES (1,76,7),(2,76,14),(5,77,14),(6,78,14),(7,79,3),(8,79,16);

show tables;
ALTER DATABASE CLOSET COLLATE 'utf8_general_ci';
commit;

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

select * from CLOTHES;
DELETE FROM BOARD where boardNo='75';

ALTER TABLE `CLOTHES` CHANGE `size` `cloSize` varchar(45);
ALTER TABLE `CLOTHES` DROP `buyDate`;
ALTER TABLE `CLOTHES` DROP `regDate`;

ALTER TABLE `CLOTHES` ADD `type` varchar(45) AFTER `cloNo`;
ALTER TABLE `CLOTHES` ADD `regDate` TIMESTAMP DEFAULT current_timestamp;

SHOW CREATE TABLE CLOTHES;
-- Truncate CLOTHES;
-- Truncate BOARD;
select * from BOARD;
DELETE FROM BOARD WHERE userID='stark';

ALTER TABLE `RELATION_CLO_CODI` DROP foreign key `RELATION_CLO_CODI_CLO`;
DROP TABLE RELATION_CLO_CODI;
select * from information_schema.table_constraints;

select * from RELATION_BOARD_CLO;

delete from `USER` where userID="cat";


insert into CLOTHES values(null,"public","상의","맨투맨","맨투맨","블랙","맨투맨_블랙","겨울","JIPSY","M","2019-05-05","맨투맨_블랙.jpg","/download/clothes?imageFileName=맨투맨_블랙.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","하의","청바지","청바지","블랙","청바지_블랙","가을",NULL,NULL,"2019-05-05","청바지_블랙.jpg","/download/clothes?imageFileName=청바지_블랙.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","하의","트레이닝바지","트레이닝바지","블랙","트레이닝바지_블랙","여름","나이키","M","2020-01-05","트레이닝바지_블랙.jpg","/download/clothes?imageFileName=트레이닝바지_블랙.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","상의","맨투맨","맨투맨","화이트","맨투맨_화이트","겨울","JIPSY","M","2019-05-05","맨투맨_화이트.jpg","/download/clothes?imageFileName=맨투맨_화이트.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","상의","맨투맨","맨투맨","블루","맨투맨_블루","겨울","JIPSY","M","2019-05-05","맨투맨_블루.jpg","/download/clothes?imageFileName=맨투맨_블루.jpg","yes","captain","default",null);
insert into CLOTHES values(null,"public","상의","맨투맨","맨투맨","그레이","맨투맨_그레이","겨울","JIPSY","M","2019-05-05","맨투맨_그레이.jpg","/download/clothes?imageFileName=맨투맨_그레이.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","액세서리","모자","캡모자","블랙","캡모자_블랙","가을",NULL,NULL,"2019-07-05","캡모자_블랙.jpg","/download/clothes?imageFileName=캡모자_블랙.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","하의","청바지","청바지","블랙","청바지_스카이블루","가을",NULL,NULL,"2019-05-05","청바지_스카이블루.jpg","/download/clothes?imageFileName=청바지_스카이블루.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","상의","맨투맨","맨투맨","그레이","맨투맨_오렌지","겨울","JIPSY","M","2019-05-05","맨투맨_오렌지.jpg","/download/clothes?imageFileName=맨투맨_오렌지.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","신발","스니커즈","스니커즈","블랙","스니커즈_블랙","겨울","반스","240","2019-05-05","스니커즈_블랙.jpg","/download/clothes?imageFileName=스니커즈_블랙.jpg","yes","a","default",null);
insert into CLOTHES values(null,"public","상의","셔츠","클래식／드레스셔츠","화이트","클래식／드레스셔츠_화이트","겨울","JIPSY","M","2020-03-01","클래식／드레스셔츠_화이트.jpg","/download/clothes?imageFileName=클래식／드레스셔츠_화이트.jpg","yes","captain","default",null);
insert into CLOTHES values(null,"public","하의","트레이닝바지","트레이닝바지","블랙","트레이닝바지_블랙","봄","JIPSY","s","2020-07-10","트레이닝바지_블랙2.jpg","/download/clothes?imageFileName=트레이닝바지_블랙2.jpg","no","a","default",null);

insert into BOARD values (null, 'a', 'codi001.jpg', '/download/board?imageFileName=codi001.jpg', "\n #JIPSY #캐주얼",null);
insert into BOARD values (null, 'a', 'codi002.jpg', '/download/board?imageFileName=codi002.jpg', "실키 레이어드 화이트 셔츠\n #JIPSY #클래식／드레스셔츠",null);
insert into BOARD values (null, 'a', 'codi003.jpg', '/download/board?imageFileName=codi003.jpg', "\n #JIPSY #클래식／드레스셔츠",null);
insert into BOARD values (null, 'a', 'codi004.jpg', '/download/board?imageFileName=codi004.jpg', "헤비스웨트 데일리 팬츠\n #JIPSY #캐주얼 #트레이닝바지",null);

desc RELATION_BOARD_CLO;
insert into RELATION_BOARD_CLO values (null, 76, 7);  
insert into RELATION_BOARD_CLO values (null, 76, 14);
insert into RELATION_BOARD_CLO values (null, 77, 14);
insert into RELATION_BOARD_CLO values (null, 78, 14);
insert into RELATION_BOARD_CLO values (null, 79, 3);
insert into RELATION_BOARD_CLO values (null, 79, 16);

UPDATE CLOTHES SET identifier="브라운_미디엄스커트",detailCategory="미디엄스커트" where identifier="브라운_미디엄 스커트";

-- 특정 종류의 옷(identifier)들을 반드시 포함하는 게시물 찾기 

-- 해당 유저가 가진 옷 종류로 할 수 있는 코디(게시물) 찾기
select *
     from BOARD
    where boardNo not in
           ( select R.boardNo
               from RELATION_BOARD_CLO R, CLOTHES C
              where R.cloNo = C.cloNo AND C.identifier not in
                     ( select identifier
                         from CLOTHES
                         where userID='a'
                     )
           )
           LIMIT 4
;

-- 특정 옷 종류들로 할 수 있는 코디(게시물) 찾기
select *
     from BOARD
    where boardNo not in
           ( select R.boardNo
               from RELATION_BOARD_CLO R, CLOTHES C
              where R.cloNo = C.cloNo AND C.identifier not in
                     ("베이지_롱스커트","아이보리_스니커즈","베이지_털모자") -- 여기에 배열로 입력
           )
;

ALTER TABLE `CLOTHES` CHANGE `class` `kind` varchar(45);
desc `CLOTHES`;
update CLOTHES set identifier="클래식/드레스셔츠_화이트" where identifier="클래식／드레스셔츠_화이트";


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
        REFERENCES USER (userID) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE BOARD_CLO
    ADD CONSTRAINT BOARD_CLO_CLOTHES FOREIGN KEY (cloNo)
        REFERENCES CLOTHES (cloNo) ON DELETE RESTRICT ON UPDATE RESTRICT;

desc BOARD;


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
show CREATE TABLE `COMMENT`;
CREATE TABLE `COMMENT` (
  `commentNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '댓글 고유번호',
  `boardNo` int(11) NOT NULL COMMENT '게시글 고유번호-옷 게시판 외래키',
  `writerID` varchar(45) NOT NULL COMMENT '작성자 아이디-유저 외래키',
  `contents` text COMMENT '댓글 내용',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`commentNo`),
  KEY `COMMENT_BOARD` (`boardNo`),
  KEY `COMMENT_USER` (`writerID`),
  CONSTRAINT `COMMENT_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `COMMENT_USER` FOREIGN KEY (`writerID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=281377 DEFAULT CHARSET=utf8 COMMENT='옷 게시판 댓글';

ALTER TABLE `COMMENT` DROP foreign key `COMMENT_BOARD`;
ALTER TABLE `COMMENT` DROP foreign key `COMMENT_USER`;
ALTER TABLE `COMMENT`
    ADD CONSTRAINT `COMMENT_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `COMMENT`
    ADD CONSTRAINT `COMMENT_USER` FOREIGN KEY (`writerID`) REFERENCES `USER` (`userID`)
    ON DELETE CASCADE ON UPDATE CASCADE;
    
select * FROM `COMMENT`;



select * from BOARD;
UPDATE BOARD SET contents = "돈까스 돈까스 돈까스 돈까스 돈까스 \n돈까스 돈까스 돈까스 돈까스 돈까스\n돈까스 돈까스 돈까스 돈까스 돈까스\n돈까스 돈까스 돈까스 돈까스 돈까스\n돈까스돈까스 돈까스 돈까스돈까스\n돈까스 돈까스 돈까스 돈까스" WHERE boardNo = '59';

desc `COMMENT`;
select * from `COMMENT`;
insert into COMMENT values 
(null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null)
,(null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null), (null, 43, 'a', '댓글', null)
;


-- 중복 테이블 삭제
SET foreign_key_checks = 1;
DELETE FROM USER WHERE userID='';

DROP TABLE COMMENT_BOARD_CODI;


CREATE TABLE `HEART` (
  `boardNo` int(11) NOT NULL COMMENT '게시글 고유번호-게시판 외래키',
  `hearterID` varchar(45) NOT NULL COMMENT '하트한 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`boardNo`,`hearterID`),
  KEY `HEART_BOARD` (`boardNo`),
  KEY `HEART_USER` (`hearterID`),
  CONSTRAINT `HEART_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `HEART_USER` FOREIGN KEY (`hearterID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시글 하트';


TRUNCATE `HEART`;
ALTER TABLE `HEART` DROP foreign key `HEART_BOARD`;
ALTER TABLE `HEART` DROP foreign key `HEART_USER`;
ALTER TABLE `HEART`
    ADD CONSTRAINT `HEART_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `HEART`
    ADD CONSTRAINT `HEART_USER` FOREIGN KEY (`hearterID`) REFERENCES `USER` (`userID`)
    ON DELETE CASCADE ON UPDATE CASCADE;
select * FROM `COMMENT`;

delete from `USER` where userID='cat';
select * from `RELATION_BOARD_CLO`;


show create table `HEART`;
desc `HEART`;
select * from `HEART`;
insert into HEART values (37, 'a', null);

alter table HEART drop heartNo;
alter table HEART add PRIMARY KEY(boardNo, hearterID);


select * from BOARD;
DELETE from BOARD where boardNo='39';

SHOW CREATE TABLE `GOOD`;

CREATE TABLE `GOOD` (
  `commentNo` int(11) NOT NULL COMMENT '댓글 고유번호- 댓글 외래키',
  `gooderID` varchar(45) NOT NULL COMMENT '댓글 좋아요한 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '댓글 좋아요 등록일',
  PRIMARY KEY (`commentNo`,`gooderID`),
  KEY `GOOD_COMMENT` (`commentNo`),
  KEY `GOOD_USER` (`gooderID`),
  CONSTRAINT `GOOD_COMMENT` FOREIGN KEY (`commentNo`) REFERENCES `COMMENT` (`commentNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `GOOD_USER` FOREIGN KEY (`gooderID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='댓글 좋아요';


-- TRUNCATE `GOOD`;
ALTER TABLE `GOOD` DROP foreign key `GOOD_USER`;
ALTER TABLE `GOOD` DROP foreign key `GOOD_COMMENT`;
ALTER TABLE `GOOD`
    ADD CONSTRAINT `GOOD_COMMENT` FOREIGN KEY (`commentNo`) REFERENCES `COMMENT` (`commentNo`)
    ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE `GOOD`
    ADD CONSTRAINT `GOOD_USER` FOREIGN KEY (`gooderID`) REFERENCES `USER` (`userID`)
    ON DELETE CASCADE ON UPDATE CASCADE;
    
select * FROM `COMMENT`;




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
              IF(
              (SELECT COUNT(*) FROM HEART where hearterID = "b" AND boardNo = B.boardNo)>0
              ,"hearting","not_hearting"
              ) if_hearting,       -- 게시물 하트 여부
              B.boardNo boardNo, B.filePath imagePath, B.contents contents, B.regDate regDate
 FROM `USER` U, `BOARD` B
 WHERE U.userID = B.userID
 ORDER BY regDate DESC;

-- 해당 게시글의 - 댓글 상세 쿼리 만들기
SELECT U.userID commenterID, U.nickname commenterName, U.pfImagePath pfImagePath,
              (SELECT COUNT(*) FROM GOOD where GOOD.commentNo = C.commentNo) numGood,
              C.commentNo commentNo, C.contents contents, C.regDate regDate
 FROM `USER` U, `COMMENT` C
 WHERE C.boardNo = '36' AND U.userID = C.writerID
 ORDER BY regDate DESC;




CREATE TABLE `FOLLOW` (
  `followerID` varchar(45) NOT NULL COMMENT '팔로워 아이디 - 유저 외래키',
  `followedID` varchar(45) NOT NULL COMMENT '팔로우된 유저 아이디-유저 외래키',
  `regDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일',
  PRIMARY KEY (`followerID`,`followedID`),
  KEY `FOLLOWER_USER` (`followerID`),
  KEY `FOLLOWED_USER` (`followedID`),
  CONSTRAINT `FOLLOWER_USER` FOREIGN KEY (`followerID`) REFERENCES `USER` (`userID`),
  CONSTRAINT `FOLLOWED_USER` FOREIGN KEY (`followedID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='팔로우';

insert into FOLLOW values ('a','captain',null);
insert into FOLLOW values ('a','bcde',null);
insert into FOLLOW values ('a','q',null);

select * from FOLLOW;

-- 팔로우 피드 쿼리 만들기 (내 게시글 포함)
SELECT U.userID writerID, U.nickname writerName, U.pfImagePath pfImagePath,
              (SELECT COUNT(*) FROM HEART where HEART.boardNo = B.boardNo) numHeart,
              (SELECT COUNT(*) FROM `COMMENT` where `COMMENT`.boardNo = B.boardNo) numComment,
              B.boardNo boardNo, B.filePath imagePath, B.contents contents, B.regDate regDate
 		FROM `USER` U, `BOARD` B
 		WHERE (U.userID in (SELECT followedID FROM FOLLOW WHERE followerID = 'a') OR U.userID = 'a')
				AND B.userID = U.userID
 		ORDER BY regDate DESC;
        -- 하트 여부 포함해서 만들어야 함.
        
        
-- 해당 사용자가 좋아요한 피드 쿼리
SELECT U.userID writerID, U.nickname writerName, U.pfImagePath pfImagePath,
              (SELECT COUNT(*) FROM HEART where HEART.boardNo = B.boardNo) numHeart,
              (SELECT COUNT(*) FROM `COMMENT` where `COMMENT`.boardNo = B.boardNo) numComment,
              B.boardNo boardNo, B.filePath imagePath, B.contents contents, B.regDate regDate 
 		FROM `USER` U, `BOARD` B
		WHERE U.userID = B.userID
			AND B.boardNo in 
            (select boardNo FROM HEART where hearterID = 'm')
 		ORDER BY regDate DESC;



-- 유저 스페이스 쿼리 만들기
-- x의 스페이스를 a가 보는 상황.
SELECT U.userID userID, U.nickname nickname, U.pfImagePath pfImagePath, U.pfContents pfContents,
              (SELECT COUNT(*) FROM BOARD where BOARD.userID = U.userID) numBoard,
              (SELECT COUNT(*) FROM FOLLOW where FOLLOW.followedID = U.userID) numFollower,
              (SELECT COUNT(*) FROM FOLLOW where FOLLOW.followerID = U.userID) numFollowing,
              IF(
              (SELECT COUNT(*) FROM FOLLOW where FOLLOW.followerID = 'a' AND FOLLOW.followedID = U.userID)>0
              ,"following","not_following"
              ) if_following,       -- 팔로잉 여부
               @following_friendsID := 
               (SELECT followerID FROM FOLLOW 
				  where FOLLOW.followedID = "x" AND 
				  FOLLOW.followerID in 
				  (
					select * from (
						(SELECT followedID FROM FOLLOW where FOLLOW.followerID = "a") as tmp)
					) ORDER BY RAND() DESC LIMIT 1
				) following_friendsID,     -- 내(a)가 팔로우하는 사람 중, x를 팔로우하고 있는 사람의 아이디를 랜덤으로 선택
              (SELECT nickname from `USER` where `USER`.userID = @following_friendsID) followig_friendsName,   -- x를 팔로우하는 친구의 닉네임
              (SELECT pfImagePath from `USER` where `USER`.userID = @following_friendsID) followig_friendsImgPath   -- x를 팔로우하는 친구의 프사  
 		FROM `USER` U 
 		WHERE U.userID = "x";



case (조건 또는 값)
          when 값1 then 표시값
          when 값2 then 표시값
        else 표시값
        end
        
        ;

-- USER Table Create SQL
show CREATE TABLE RELATION_BOARD_CLO;
CREATE TABLE `RELATION_BOARD_CLO` (
  `relNo` int(11) NOT NULL AUTO_INCREMENT COMMENT '관계 고유번호',
  `boardNo` int(11) NOT NULL COMMENT '게시물 고유번호-게시물 외래키',
  `cloNo` int(11) NOT NULL COMMENT '옷 고유번호-옷 외래키',
  PRIMARY KEY (`relNo`),
  KEY `RELATION_TO_BOARD` (`boardNo`),
  KEY `RELATION_TO_CLO` (`cloNo`),
  CONSTRAINT `RELATION_TO_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `RELATION_TO_CLO` FOREIGN KEY (`cloNo`) REFERENCES `CLOTHES` (`cloNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시물-옷 관계';


SHOW CREATE TABLE `CLOSET`;
CREATE TABLE `CLOSET` (
  `userID` varchar(45) NOT NULL COMMENT '유저아이디',
  `closetName` varchar(45) NOT NULL COMMENT '옷장이름',
  `closetLocation` varchar(45) DEFAULT NULL COMMENT '옷장위치',
  `closetMemo` varchar(45) DEFAULT NULL COMMENT '옷장메모',
  `closetOpen` varchar(45) DEFAULT NULL COMMENT '공개여부',
  PRIMARY KEY (`userID`,`closetName`),
  CONSTRAINT `FK_CLOSET_userID_USER_userID` FOREIGN KEY (`userID`) REFERENCES `USER` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='옷장';


ALTER TABLE `CLOSET` DROP foreign key `FK_CLOSET_userID_USER_userID`;
ALTER TABLE `CLOSET`
    ADD CONSTRAINT `FK_CLOSET_userID_USER_userID` FOREIGN KEY (`userID`) REFERENCES `USER` (`userID`)
    ON DELETE CASCADE ON UPDATE CASCADE;

select * from CLOSET;
delete from `CLOSET` where userID='w';


-- 게시글 상세 조회 쿼리. 번호 123 게시물을 a가 보고 있는 상황.
SELECT U.userID writerID, U.nickname writerName, U.pfImagePath pfImagePath, U.pfContents pfContents,
			IF(
              (SELECT COUNT(*) FROM FOLLOW where FOLLOW.followerID = 'captain' AND FOLLOW.followedID = U.userID)>0
              ,"following","not_following"
              ) if_following,       -- 게시자 팔로잉 여부
              IF(
              (SELECT COUNT(*) FROM HEART where HEART.hearterID = 'captain' AND HEART.boardNo = B.boardNo)>0
              ,"hearting","not_hearting"
              ) if_hearting,       -- 이 게시물 하트 여부
              (SELECT COUNT(*) FROM HEART where HEART.boardNo = B.boardNo) numHeart,
              (SELECT COUNT(*) FROM `COMMENT` where `COMMENT`.boardNo = B.boardNo) numComment,
              B.boardNo boardNo, B.filePath imagePath, B.contents contents, B.regDate regDate,
              C.cloNo cloNo, C.filePath imagePath, C.identifier identifier, C.brand brand
 		FROM `USER` U, `BOARD` B, RELATION_BOARD_CLO R, CLOTHES C
 		WHERE B.boardNo = "79"
				AND B.userID = U.userID
                AND R.boardNo = B.boardNo AND R.cloNo = C.cloNo;
                
                
DESC RELATION_BOARD_CLO;
                


        DESC CLOTHES;
        insert into HEART values(94,"captain",null);
        insert into `COMMENT` values(null,94,"stark","굳!",null);


        select * from HEART;
        select * from `COMMENT`;
        
        select * from `RELATION_BOARD_CLO`;
        -- 하트 여부 포함해서 만들어야 함.
        






DESC RELATION;
SHOW CREATE TABLE `RELATION`;



DESC USER;
ALTER TABLE USER CHANGE userName nickname  VARCHAR(50)  NULL;
ALTER TABLE USER CHANGE age birth  DATE  NULL;

ALTER TABLE USER ADD pfImageName VARCHAR(50) NULL        COMMENT '프로필 이미지 이름'; 
SHOW CREATE TABLE `USER`;
