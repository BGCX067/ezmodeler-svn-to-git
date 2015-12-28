SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;
USE `mydb`;

-- -----------------------------------------------------
-- Table `mydb`.`fieldvalues`
-- -----------------------------------------------------
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
CREATE  TABLE IF NOT EXISTS `mydb`.`error_type` (
  `errortype_id` BIGINT NOT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`errortype_id`) ,
  INDEX FK62B08B7176B7F57 (`fieldvalues_id` ASC) ,
  CONSTRAINT `fk_fieldvalues_id`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`error_values`
-- -----------------------------------------------------
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
  CONSTRAINT `Ffk_fieldvalues_id`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ),
  CONSTRAINT `fk_error_type_id`
    FOREIGN KEY (`errortype_id` )
    REFERENCES `mydb`.`error_type` (`errortype_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`objecttype`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`objecttype` (
  `objecttype_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `description` TEXT NULL ,
  `updatedby` BIGINT NULL ,
  `insertedby` BIGINT NULL ,
  `updated` DATETIME NULL ,
  `inserted` DATETIME NULL ,
  PRIMARY KEY (`objecttype_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`baseobject`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`baseobject` (
  `baseobject_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `comment` TEXT NULL ,
  `objecttype_id` BIGINT NULL ,
  `inserted` BIGINT NULL ,
  `updated` BIGINT NULL ,
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
-- Table `mydb`.`categories`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`categories` (
  `categories_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `insertedby` BIGINT NULL ,
  `updatedby` BIGINT NULL ,
  `inserted` DATETIME NULL ,
  `updated` DATETIME NULL ,
  `description` TEXT NULL ,
  INDEX category_id () ,
  PRIMARY KEY (`categories_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`object`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`object` (
  `object_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `baseobject_id` BIGINT NULL ,
  `deleted` VARCHAR(45) NULL ,
  `updatedBy` BIGINT NULL ,
  `insertedby` BIGINT NULL ,
  `updated` DATETIME NULL ,
  `inserted` DATETIME NULL ,
  `category_id` BIGINT NULL ,
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
    REFERENCES `mydb`.`categories` (`categories_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`diagramrevision`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`diagramrevision` (
  `diagramrevision_id` BIGINT NOT NULL ,
  `insertedby` BIGINT NULL ,
  `inserted` DATETIME NULL ,
  `comment` VARCHAR(255) NULL ,
  `updatedby` BIGINT NULL ,
  `updated` DATETIME NULL ,
  `deleted` VARCHAR(45) NULL ,
  PRIMARY KEY (`diagramrevision_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`diagram`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`diagram` (
  `diagram_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `description` TEXT NULL ,
  `updatedby` BIGINT NULL ,
  `insertedby` BIGINT NULL ,
  `updated` DATETIME NULL ,
  `inserted` DATETIME NULL ,
  `revision_number` BIGINT NULL ,
  `diagramrevision_id` BIGINT NULL ,
  `deleted` VARCHAR(45) NULL ,
  `parent_diagram_id` BIGINT NULL ,
  `diagram_no` BIGINT NULL ,
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
CREATE  TABLE IF NOT EXISTS `mydb`.`markercategory` (
  `markercategory_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `comment` TEXT NULL ,
  `insertedby` BIGINT NULL ,
  `updatedby` BIGINT NULL ,
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
CREATE  TABLE IF NOT EXISTS `mydb`.`objectmarker` (
  `objectmarker_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL ,
  `comment` TEXT NULL ,
  `priority` BIGINT NULL ,
  `markercategory_id` BIGINT NULL ,
  `insertedby` BIGINT NULL ,
  `updatedby` BIGINT NULL ,
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
CREATE  TABLE IF NOT EXISTS `mydb`.`diagram_object` (
  `diagram_object_id` BIGINT NOT NULL ,
  `diagram_id` BIGINT NULL ,
  `marker_id` BIGINT NULL ,
  `deleted` VARCHAR(45) NULL ,
  `objectmarker_id` BIGINT NULL ,
  `inserted` DATETIME NULL ,
  `insertedby` BIGINT NULL ,
  `object_id` BIGINT NULL ,
  `name` VARCHAR(255) NULL ,
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
-- Table `mydb`.`states`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`states` (
  `state_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`state_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`adresses`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`adresses` (
  `adresses_id` BIGINT NOT NULL ,
  `additionalname` VARCHAR(255) NULL DEFAULT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `fax` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `state_id` BIGINT NULL DEFAULT NULL ,
  `street` VARCHAR(255) NULL DEFAULT NULL ,
  `town` VARCHAR(255) NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `zip` VARCHAR(255) NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`adresses_id`) ,
  INDEX FKCE8EE5A467466E58 (`state_id` ASC) ,
  CONSTRAINT `FKCE8EE5A467466E58`
    FOREIGN KEY (`state_id` )
    REFERENCES `mydb`.`states` (`state_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`adresses_emails`
-- -----------------------------------------------------
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
  CONSTRAINT `fk_email_id`
    FOREIGN KEY (`mail_id` )
    REFERENCES `mydb`.`emails` (`mail_id` ),
  CONSTRAINT `fk_adresses_id`
    FOREIGN KEY (`adresses_id` )
    REFERENCES `mydb`.`adresses` (`adresses_id` ))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


-- -----------------------------------------------------
-- Table `mydb`.`phones`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`phones` (
  `phone_id` BIGINT NOT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `phonevalue` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`phone_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`adresses_phones`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`adresses_phones` (
  `adresses_phone_id` BIGINT NOT NULL ,
  `adresses_id` BIGINT NULL DEFAULT NULL ,
  `phone_id` BIGINT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`adresses_phone_id`) ,
  INDEX fk_adresses_id (`adresses_id` ASC) ,
  INDEX fk_phones_id (`phone_id` ASC) ,
  CONSTRAINT `fk_adresses_id`
    FOREIGN KEY (`adresses_id` )
    REFERENCES `mydb`.`adresses` (`adresses_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_phones_id`
    FOREIGN KEY (`phone_id` )
    REFERENCES `mydb`.`phones` (`phone_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`configuration`
-- -----------------------------------------------------
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
-- Table `mydb`.`organisation`
-- -----------------------------------------------------
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
-- Table `mydb`.`userlevel`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`userlevel` (
  `level_id` BIGINT NOT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `statuscode` INT NULL DEFAULT NULL ,
  PRIMARY KEY (`level_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`salutations`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`salutations` (
  `salutations_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`salutations_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users`
-- -----------------------------------------------------
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
  INDEX fk_userlevel_id (`level_id` ASC) ,
  INDEX fk_salutations_id (`title_id` ASC) ,
  CONSTRAINT `fk_adresses_id`
    FOREIGN KEY (`adresses_id` )
    REFERENCES `mydb`.`adresses` (`adresses_id` ),
  CONSTRAINT `fk_userlevel_id`
    FOREIGN KEY (`level_id` )
    REFERENCES `mydb`.`userlevel` (`level_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_salutations_id`
    FOREIGN KEY (`title_id` )
    REFERENCES `mydb`.`salutations` (`salutations_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`usergroups`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`usergroups` (
  `usergroup_id` BIGINT NOT NULL ,
  `level_id` BIGINT NULL DEFAULT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `description` VARCHAR(255) NULL DEFAULT NULL ,
  `user_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`usergroup_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`users_usergroups`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`users_usergroups` (
  `users_usergroups_id` BIGINT NOT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `user_id` BIGINT NULL DEFAULT NULL ,
  `usergroup_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`users_usergroups_id`) ,
  INDEX fk_users_id (`user_id` ASC) ,
  INDEX fk_usergroups_id (`usergroup_id` ASC) ,
  CONSTRAINT `fk_users_id`
    FOREIGN KEY (`user_id` )
    REFERENCES `mydb`.`users` (`user_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usergroups_id`
    FOREIGN KEY (`usergroup_id` )
    REFERENCES `mydb`.`usergroups` (`usergroup_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`userdata`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`userdata` (
  `data_id` BIGINT NOT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  `data` VARCHAR(255) NULL DEFAULT NULL ,
  `data_key` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `user_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`data_id`) ,
  INDEX fk_user_id (`user_id` ASC) ,
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id` )
    REFERENCES `mydb`.`users` (`user_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`sessiondata`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`sessiondata` (
  `id` BIGINT NOT NULL ,
  `refresh_time` DATETIME NULL DEFAULT NULL ,
  `session_id` VARCHAR(255) NULL DEFAULT NULL ,
  `starttermin_time` DATETIME NULL DEFAULT NULL ,
  `user_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`organisation_users`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`organisation_users` (
  `organisation_users_id` BIGINT NOT NULL ,
  `organisation_id` BIGINT NULL DEFAULT NULL ,
  `user_id` BIGINT NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `comment` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`organisation_users_id`) ,
  INDEX FKA4E4666381ED7911 (`user_id` ASC) ,
  INDEX FKA4E46663E2E18827 (`organisation_id` ASC) ,
  CONSTRAINT `FKA4E4666381ED7911`
    FOREIGN KEY (`user_id` )
    REFERENCES `mydb`.`users` (`user_id` ),
  CONSTRAINT `FKA4E46663E2E18827`
    FOREIGN KEY (`organisation_id` )
    REFERENCES `mydb`.`organisation` (`organisation_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`fieldlanguagesvalues`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`fieldlanguagesvalues` (
  `fieldlanguagesvalues_id` BIGINT NOT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NOT NULL ,
  `language_id` BIGINT NOT NULL ,
  `value` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`fieldlanguagesvalues_id`) ,
  INDEX FK3BFBC0C376B7F57 (`fieldvalues_id` ASC) ,
  INDEX fk_language_id (`language_id` ASC) ,
  CONSTRAINT `FK3BFBC0C376B7F57`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ),
  CONSTRAINT `fk_language_id`
    FOREIGN KEY (`language_id` )
    REFERENCES `mydb`.`field_language` (`language_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
