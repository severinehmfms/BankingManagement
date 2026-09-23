-- ------------------------------------------------------------------------------
-- - Reconstruction de la base de données                                     ---
-- ------------------------------------------------------------------------------
DROP DATABASE IF EXISTS Bank;
CREATE DATABASE Bank;
USE Bank;

-- -----------------------------------------------------------------------------
-- - Construction des TABLESPACE						                     ---
-- -----------------------------------------------------------------------------
CREATE TABLE Bank_Account (
	NumBankAccount		varchar(12) PRIMARY KEY,
	Titulaire			varchar(30)	NOT NULL
) ENGINE = InnoDB;

CREATE TABLE Operations(
	IdOperation 		int(4) 		PRIMARY KEY AUTO_INCREMENT,
	DateOperation 		DATE		NOT NULL DEFAULT NOW(),
	AmountTransaction	float(4)	NOT NULL,
	TypeOperation		smallint	NOT NULL,
	NumBankAccount      varchar(12) NOT NULL,
	KEY NumBankAccount (NumBankAccount)
 ) ENGINE = InnoDB;

ALTER TABLE Operations
  ADD CONSTRAINT `operations_ibfk_1` FOREIGN KEY (`NumBankAccount`) REFERENCES `Bank_Account` (`NumBankAccount`);
