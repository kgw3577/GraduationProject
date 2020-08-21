

-- 해당 유저가 가진 옷 종류로 할 수 있는 코디(게시물) 찾기 (LIMIT n RANDOM 해야함)
select U.userID userID, U.nickname userName, U.pfImagePath userPfImagePath, U.pfContents userPfContents,
			(SELECT COUNT(*) FROM BOARD where BOARD.userID = U.userID) userNumBoard,
              (SELECT COUNT(*) FROM FOLLOW where FOLLOW.followedID = U.userID) userNumFollower,
              (SELECT COUNT(*) FROM FOLLOW where FOLLOW.followerID = U.userID) userNumFollowing,
			IF(
              (SELECT COUNT(*) FROM FOLLOW where FOLLOW.followerID = 'a' AND FOLLOW.followedID = U.userID)>0
              ,"following","not_following"
              ) user_if_following,       -- 게시자 팔로잉 여부
               @following_friendsID := 
               (SELECT followerID FROM FOLLOW 
				  where FOLLOW.followedID = U.userID AND 
				  FOLLOW.followerID in 
				  (
					select * from (
						(SELECT followedID FROM FOLLOW where FOLLOW.followerID = "a") as tmp)
					) ORDER BY RAND() DESC LIMIT 1
				) user_following_friendsID,     -- 내(a)가 팔로우하는 사람 중, 대상을 팔로우하고 있는 사람의 아이디를 랜덤으로 선택
              (SELECT nickname from `USER` where `USER`.userID = @following_friendsID) user_followig_friendsName,   -- x를 팔로우하는 친구의 닉네임
              (SELECT pfImagePath from `USER` where `USER`.userID = @following_friendsID) user_followig_friendsImgPath,   -- x를 팔로우하는 친구의 프사  
              IF(
              (SELECT COUNT(*) FROM HEART where HEART.hearterID = 'a' AND HEART.boardNo = B.boardNo)>0
              ,"hearting","not_hearting"
              ) board_if_hearting,       -- 이 게시물 하트 여부
              (SELECT COUNT(*) FROM HEART where HEART.boardNo = B.boardNo) board_numHeart,
              (SELECT COUNT(*) FROM `COMMENT` where `COMMENT`.boardNo = B.boardNo) board_numComment,
              B.boardNo boardNo, B.filePath boardImagePath, B.contents boardContents, B.regDate boardRegDate,
              C.cloNo cloNo, C.location cloLocation, C.kind cloKind, C.category cloCategory, 
              C.detailCategory cloDetailCategory, C.color cloColor, C.identifier cloIdentifier, 
              C.season cloSeason, C.brand cloBrand, C.filePath cloImgPath, C.userID cloUserID
     from `USER` U, RELATION_BOARD_CLO R, CLOTHES C, `BOARD` B
    where B.boardNo not in
           ( select R.boardNo
               from RELATION_BOARD_CLO R, CLOTHES C
              where R.cloNo = C.cloNo AND C.identifier not in
                     ( select identifier
                         from CLOTHES
                         where userID='a' AND location='public'
                     )
           )
           AND B.userID = U.userID
                AND R.boardNo = B.boardNo AND R.cloNo = C.cloNo
                AND B.contents LIKE '%%'
                ORDER BY RAND() LIMIT {};
                ;
                -- 
                -- 