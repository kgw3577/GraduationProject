
use CLOSET;
UPDATE CODI SET favorite='no' WHERE favorite=null;

UPDATE CODI SET season='winter' WHERE codiNo=2;
UPDATE CODI SET place='daily' WHERE codiNo=2;

UPDATE CODI SET season='fall' WHERE codiNo=3;
UPDATE CODI SET place='special' WHERE codiNo=3;

UPDATE CODI SET season='spring' WHERE codiNo=4;
UPDATE CODI SET place='formal' WHERE codiNo=4;

ALTER TABLE 테이블이름
   ALTER 필드이름 SET DEFAULT 기본값