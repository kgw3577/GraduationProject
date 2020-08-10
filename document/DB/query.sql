
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
  CONSTRAINT `HEART_BOARD` FOREIGN KEY (`boardNo`) REFERENCES `BOARD` (`boardNo`),
  CONSTRAINT `HEART_USER` FOREIGN KEY (`hearterID`) REFERENCES `USER` (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='게시글 하트';

show create table `HEART`;
desc `HEART`;
select * from `HEART`;
insert into HEART values (37, 'a', null);

alter table HEART drop heartNo;
alter table HEART add PRIMARY KEY(boardNo, hearterID);


select * from BOARD;
DELETE from BOARD where boardNo='39';

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

select * from HEART;



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
              (SELECT followerID FROM FOLLOW 
				  where FOLLOW.followedID = "captain" AND 
				  FOLLOW.followerID in 
				  (
					select * from (
						(SELECT ifnull(followedID,"") FROM FOLLOW where FOLLOW.followerID = "a") as tmp)
					) ORDER BY RAND() DESC LIMIT 1
				) following
              -- (SELECT userID from `USER` where `USER`.userID = following_friends.followerID) followig_friendsID, -- x를 팔로우하는 친구의 ID
              -- (SELECT nickname from `USER` where `USER`.userID = following_friends.followerID) followig_friendsName,   -- x를 팔로우하는 친구의 닉네임
              -- (SELECT pfImagePath from `USER` where `USER`.userID = following_friends.followerID) followig_friendsImgPath   -- x를 팔로우하는 친구의 프사  
 		FROM `USER` U

 		WHERE U.userID = "captain";

SELECT 
	CASE
		WHEN !isnull((SELECT followedID FROM FOLLOW where FOLLOW.followerID = "11" LIMIT 1)  )
		THEN (SELECT followerID FROM FOLLOW 
				  where FOLLOW.followedID = "11" AND 
				  FOLLOW.followerID in 
				  (
					select * from (
						(SELECT ifnull(followedID,"") FROM FOLLOW where FOLLOW.followerID = "a") as tmp)
					) ORDER BY RAND() DESC LIMIT 1
				)
		ELSE null
	END AS hero_type
FROM FOLLOW LIMIT 1;


 -- 내가 팔로우한 사람이면서 x를 팔로우한 사람
SELECT followerID, regDate FROM FOLLOW 
				  where FOLLOW.followedID = "captain" AND 
				  FOLLOW.followerID in 
				  (
					select * from (
						(SELECT followedID FROM FOLLOW where FOLLOW.followerID = "a") as tmp
									)
				)
                ORDER BY regDate DESC;
                
SELECT ifnull(followedID,"a") followedID FROM FOLLOW 
				  where FOLLOW.followedID = "captain" AND 
				  FOLLOW.followerID in 
				  (
					select * from (
						(SELECT ifnull(followedID,"") followedID FROM FOLLOW where FOLLOW.followerID = "a") as tmp)
					);             
                
                
                SELECT COALESCE(followedID,"f") as f FROM FOLLOW where FOLLOW.followerID = "11";
case (조건 또는 값)
          when 값1 then 표시값
          when 값2 then 표시값
        else 표시값
        end
					


SELECT * from FOLLOW where followerID = 'a' ORDER BY regDate DESC;

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
