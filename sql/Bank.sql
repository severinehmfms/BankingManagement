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
	NumBankAccount			varchar(12) 	PRIMARY KEY,
	Holder					varchar(30)		NOT NULL,
	Balance 				DECIMAL(10,2) 	NOT NULL DEFAULT 0,
	MaximumBalance			decimal(10,2)	NOT NULL
) ENGINE = InnoDB;

CREATE TABLE Operations(
	IdOperation 			int(4) 			PRIMARY KEY AUTO_INCREMENT,
	DateOperation 			DATETIME 		NOT NULL DEFAULT CURRENT_TIMESTAMP,
	AmountTransaction		decimal(10,2)	NOT NULL,
	TypeOperation			smallint		NOT NULL,
	NumBankAccount      	varchar(12) 	NOT NULL,
	NumBankAccountDestination varchar(12) 	NULL,
	KEY NumBankAccount (NumBankAccount),
	KEY NumBankAccountDestination (NumBankAccountDestination)
 ) ENGINE = InnoDB;

ALTER TABLE Operations
  ADD CONSTRAINT `operations_ibfk_1` FOREIGN KEY (`NumBankAccount`) REFERENCES `Bank_Account` (`NumBankAccount`),
  ADD CONSTRAINT `operations_ibfk_2` FOREIGN KEY (`NumBankAccountDestination`) REFERENCES `Bank_Account` (`NumBankAccount`)
