SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`fieldvalues`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`fieldvalues` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`fieldvalues` (
  `fieldvalues_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`fieldvalues_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`error_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`error_type` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`error_type` (
  `errortype_id` BIGINT NOT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`errortype_id`) ,
  INDEX FK62B08B7176B7F57 (`fieldvalues_id` ASC) ,
  CONSTRAINT `FK62B08B7176B7F57`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`error_values`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`error_values` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`error_values` (
  `errorvalues_id` BIGINT NOT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NULL DEFAULT NULL ,
  `errortype_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`errorvalues_id`) ,
  INDEX FKF576C92A76B7F57 (`fieldvalues_id` ASC) ,
  INDEX FKF576C92A7942E9DD (`errortype_id` ASC) ,
  CONSTRAINT `FKF576C92A76B7F57`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ),
  CONSTRAINT `FKF576C92A7942E9DD`
    FOREIGN KEY (`errortype_id` )
    REFERENCES `mydb`.`error_type` (`errortype_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`field_language`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`field_language` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`field_language` (
  `language_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`language_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`objecttype`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`objecttype` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`objecttype` (
  `objecttype_id` INT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `description` TEXT NULL ,
  `updatedby` INT NULL ,
  `insertedby` INT NULL ,
  `updated` DATETIME NULL ,
  `inserted` DATETIME NULL ,
  PRIMARY KEY (`objecttype_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`baseobject`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`baseobject` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`baseobject` (
  `baseobject_id` INT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `comment` TEXT NULL ,
  `objecttype_id` INT NULL ,
  `inserted` INT NULL ,
  `updated` INT NULL ,
  `insertedby` DATETIME NULL ,
  `updatedby` DATETIME NULL ,
  `deleted` VARCHAR(45) NULL ,
  PRIMARY KEY (`baseobject_id`) ,
  INDEX fk_objecttype_id (`objecttype_id` ASC) ,
  CONSTRAINT `fk_objecttype_id`
    FOREIGN KEY (`objecttype_id` )
    REFERENCES `mydb`.`objecttype` (`objecttype_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'These are the Base-Objects';


-- -----------------------------------------------------
-- Table `mydb`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`category` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`category` (
  `category_id` INT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `insertedby` INT NULL ,
  `updatedby` INT NULL ,
  `inserted` DATETIME NULL ,
  `updated` DATETIME NULL ,
  `description` TEXT NULL ,
  INDEX category_id () ,
  PRIMARY KEY (`category_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`object` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`object` (
  `object_id` INT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `baseobject_id` INT NULL ,
  `deleted` VARCHAR(45) NULL ,
  `updatedBy` INT NULL ,
  `insertedby` INT NULL ,
  `updated` DATETIME NULL ,
  `inserted` DATETIME NULL ,
  `category_id` INT NULL ,
  PRIMARY KEY (`object_id`) ,
  INDEX fk_baseobject_id (`baseobject_id` ASC) ,
  INDEX fk_category_id (`category_id` ASC) ,
  CONSTRAINT `fk_baseobject_id`
    FOREIGN KEY (`baseobject_id` )
    REFERENCES `mydb`.`baseobject` (`baseobject_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_category_id`
    FOREIGN KEY (`category_id` )
    REFERENCES `mydb`.`category` (`category_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`diagramrevision`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`diagramrevision` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`diagramrevision` (
  `diagramrevision_id` INT NOT NULL ,
  `insertedby` INT NULL ,
  `inserted` DATETIME NULL ,
  `comment` VARCHAR(255) NULL ,
  `updatedby` INT NULL ,
  `updated` DATETIME NULL ,
  `deleted` VARCHAR(45) NULL ,
  PRIMARY KEY (`diagramrevision_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`diagram`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`diagram` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`diagram` (
  `diagram_id` INT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `description` TEXT NULL ,
  `updatedby` INT NULL ,
  `insertedby` INT NULL ,
  `updated` DATETIME NULL ,
  `inserted` DATETIME NULL ,
  `revision_number` INT NULL ,
  `diagramrevision_id` INT NULL ,
  `deleted` VARCHAR(45) NULL ,
  `parent_diagram_id` INT NULL ,
  PRIMARY KEY (`diagram_id`) ,
  INDEX fk_diagramrevision_id (`diagramrevision_id` ASC) ,
  CONSTRAINT `fk_diagramrevision_id`
    FOREIGN KEY (`diagramrevision_id` )
    REFERENCES `mydb`.`diagramrevision` (`diagramrevision_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`markercategory`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`markercategory` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`markercategory` (
  `markercategory_id` INT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `comment` TEXT NULL ,
  `insertedby` INT NULL ,
  `updatedby` INT NULL ,
  `inserted` DATETIME NULL ,
  `updated` DATETIME NULL ,
  `deleted` VARCHAR(45) NULL ,
  PRIMARY KEY (`markercategory_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`objectmarker`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`objectmarker` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`objectmarker` (
  `objectmarker_id` INT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `comment` TEXT NULL ,
  `priority` INT NULL ,
  `markercategory_id` INT NULL ,
  `insertedby` INT NULL ,
  `updatedby` INT NULL ,
  `inserted` DATETIME NULL ,
  `updated` DATETIME NULL ,
  `deleted` VARCHAR(45) NULL ,
  PRIMARY KEY (`objectmarker_id`) ,
  INDEX fk_markercategory_id (`markercategory_id` ASC) ,
  CONSTRAINT `fk_markercategory_id`
    FOREIGN KEY (`markercategory_id` )
    REFERENCES `mydb`.`markercategory` (`markercategory_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin
COMMENT = 'Marker-Object';


-- -----------------------------------------------------
-- Table `mydb`.`diagram_object`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`diagram_object` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`diagram_object` (
  `diagram_object_id` INT NOT NULL ,
  `diagram_id` INT NULL ,
  `object_id` INT NULL ,
  `marker_id` INT NULL ,
  `deleted` VARCHAR(45) NULL ,
  `objectmarker_id` INT NULL ,
  `inserted` DATETIME NULL ,
  `insertedby` INT NULL ,
  PRIMARY KEY (`diagram_object_id`) ,
  INDEX fk_diagram_id (`diagram_id` ASC) ,
  INDEX fk_object_id (`object_id` ASC) ,
  INDEX fk_objectmarker_id (`objectmarker_id` ASC) ,
  CONSTRAINT `fk_diagram_id`
    FOREIGN KEY (`diagram_id` )
    REFERENCES `mydb`.`diagram` (`diagram_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_object_id`
    FOREIGN KEY (`object_id` )
    REFERENCES `mydb`.`object` (`object_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_objectmarker_id`
    FOREIGN KEY (`objectmarker_id` )
    REFERENCES `mydb`.`objectmarker` (`objectmarker_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`emails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`emails` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`emails` (
  `mail_id` BIGINT NOT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `email` VARCHAR(255) NULL DEFAULT NULL ,
  `startdate` DATETIME NULL DEFAULT NULL ,
  `updatedate` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`mail_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`adresses`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`adresses` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`adresses` (
  `adresses_id`  NULL );


-- -----------------------------------------------------
-- Table `mydb`.`adresses_emails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`adresses_emails` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`adresses_emails` (
  `adresses_emails_id` BIGINT NOT NULL ,
  `adresses_id` BIGINT NULL DEFAULT NULL ,
  `mail_id` BIGINT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`adresses_emails_id`) ,
  INDEX FKA30D5F5231F52DC7 (`mail_id` ASC) ,
  INDEX FKA30D5F52A40D68C7 (`adresses_id` ASC) ,
  CONSTRAINT `FKA30D5F5231F52DC7`
    FOREIGN KEY (`mail_id` )
    REFERENCES `mydb`.`emails` (`mail_id` ),
  CONSTRAINT `FKA30D5F52A40D68C7`
    FOREIGN KEY (`adresses_id` )
    REFERENCES `mydb`.`adresses` ())
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`adresses_phones`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`adresses_phones` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`adresses_phones` (
  `adresses_phone_id` BIGINT NOT NULL ,
  `adresses_id` BIGINT NULL DEFAULT NULL ,
  `phone_id` BIGINT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`adresses_phone_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`configuration`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`configuration` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`configuration` (
  `configuration_id` BIGINT NOT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `conf_key` VARCHAR(255) NULL DEFAULT NULL ,
  `conf_value` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `user_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`configuration_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`fieldvalues`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`fieldvalues` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`fieldvalues` (
  `fieldvalues_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`fieldvalues_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`organisation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`organisation` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`organisation` (
  `organisation_id` BIGINT NOT NULL ,
  `insertedby` BIGINT NULL DEFAULT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `updatedby` BIGINT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`organisation_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`naviglobal`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`naviglobal` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`naviglobal` (
  `global_id` BIGINT NOT NULL ,
  `action` VARCHAR(255) NULL DEFAULT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `icon` VARCHAR(255) NULL DEFAULT NULL ,
  `isleaf` BIT NULL DEFAULT NULL ,
  `isopen` BIT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `naviorder` INT NULL DEFAULT NULL ,
  `level_id` BIGINT NULL DEFAULT NULL ,
  `label_number` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`global_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`navimain`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`navimain` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`navimain` (
  `main_id` BIGINT NOT NULL ,
  `action` VARCHAR(255) NULL DEFAULT NULL ,
  `level_id` BIGINT NULL DEFAULT NULL ,
  `global_id` BIGINT NULL DEFAULT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `icon` VARCHAR(255) NULL DEFAULT NULL ,
  `isleaf` BIT NULL DEFAULT NULL ,
  `isopen` BIT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `naviorder` INT NULL DEFAULT NULL ,
  `label_number` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`main_id`) ,
  INDEX FK7D543E5F363F971D (`global_id` ASC) ,
  CONSTRAINT `FK7D543E5F363F971D`
    FOREIGN KEY (`global_id` )
    REFERENCES `mydb`.`naviglobal` (`global_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`navisub`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`navisub` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`navisub` (
  `sub_id` BIGINT NOT NULL ,
  `main_id` BIGINT NULL DEFAULT NULL ,
  `level_id` BIGINT NULL DEFAULT NULL ,
  `action` VARCHAR(255) NULL DEFAULT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `icon` VARCHAR(255) NULL DEFAULT NULL ,
  `isleaf` BIT NULL DEFAULT NULL ,
  `isopen` BIT NULL DEFAULT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `naviorder` INT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `label_number` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`sub_id`) ,
  INDEX FK6723D8DA8D368C1D (`main_id` ASC) ,
  CONSTRAINT `FK6723D8DA8D368C1D`
    FOREIGN KEY (`main_id` )
    REFERENCES `mydb`.`navimain` (`main_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`users` (
  `user_id` BIGINT NOT NULL ,
  `adresses_id` BIGINT NULL DEFAULT NULL ,
  `age` DATETIME NULL DEFAULT NULL ,
  `availible` INT NULL DEFAULT NULL ,
  `firstname` VARCHAR(255) NULL DEFAULT NULL ,
  `lastlogin` DATETIME NULL DEFAULT NULL ,
  `lastname` VARCHAR(255) NULL DEFAULT NULL ,
  `lasttrans` BIGINT NULL DEFAULT NULL ,
  `level_id` BIGINT NULL DEFAULT NULL ,
  `login` VARCHAR(255) NULL DEFAULT NULL ,
  `password` VARCHAR(255) NULL DEFAULT NULL ,
  `regdate` DATETIME NULL DEFAULT NULL ,
  `status` INT NULL DEFAULT NULL ,
  `title_id` INT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `pictureuri` VARCHAR(255) NULL DEFAULT NULL ,
  `language_id` BIGINT NULL DEFAULT NULL ,
  `resethash` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`user_id`) ,
  INDEX FK6A68E08A40D68C7 (`adresses_id` ASC) ,
  CONSTRAINT `FK6A68E08A40D68C7`
    FOREIGN KEY (`adresses_id` )
    REFERENCES `mydb`.`adresses` (`adresses_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users_usergroups`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`users_usergroups` ;

CREATE  TABLE IF NOT EXISTS `mydb`.`users_usergroups` (
  `users_usergroups_id` BIGINT NOT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `user_id` BIGINT NULL DEFAULT NULL ,
  `usergroup_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`users_usergroups_id`) )
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
