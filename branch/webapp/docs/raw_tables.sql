SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `mydb`;

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
ENGINE = InnoDB;


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
  CONSTRAINT `FKA30D5F5231F52DC7`
    FOREIGN KEY (`mail_id` )
    REFERENCES `mydb`.`emails` (`mail_id` ),
  CONSTRAINT `FKA30D5F52A40D68C7`
    FOREIGN KEY (`adresses_id` )
    REFERENCES `mydb`.`adresses` (`adresses_id` ))
ENGINE = InnoDB;


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
-- Table `mydb`.`Errortypess`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Errortypess` (
  `Errortypes_id` BIGINT NOT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`Errortypes_id`) ,
  INDEX FK62B08B7176B7F57 (`fieldvalues_id` ASC) ,
  CONSTRAINT `FK62B08B7176B7F57`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`errorvalues`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`errorvalues` (
  `errorvalues_id` BIGINT NOT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NULL DEFAULT NULL ,
  `Errortypes_id` BIGINT NULL DEFAULT NULL ,
  PRIMARY KEY (`errorvalues_id`) ,
  INDEX FKF576C92A76B7F57 (`fieldvalues_id` ASC) ,
  INDEX FKF576C92A7942E9DD (`Errortypes_id` ASC) ,
  CONSTRAINT `FKF576C92A76B7F57`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ),
  CONSTRAINT `FKF576C92A7942E9DD`
    FOREIGN KEY (`Errortypes_id` )
    REFERENCES `mydb`.`Errortypess` (`Errortypes_id` ))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Fieldlanguage`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Fieldlanguage` (
  `language_id` BIGINT NOT NULL ,
  `name` VARCHAR(255) NULL DEFAULT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`language_id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Fieldlanguagesvalues`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `mydb`.`Fieldlanguagesvalues` (
  `Fieldlanguagesvalues_id` BIGINT NOT NULL ,
  `starttime` DATETIME NULL DEFAULT NULL ,
  `updatetime` DATETIME NULL DEFAULT NULL ,
  `deleted` VARCHAR(255) NULL DEFAULT NULL ,
  `fieldvalues_id` BIGINT NOT NULL ,
  `language_id` BIGINT NOT NULL ,
  `value` VARCHAR(255) NULL DEFAULT NULL ,
  PRIMARY KEY (`Fieldlanguagesvalues_id`) ,
  INDEX FK3BFBC0C376B7F57 (`fieldvalues_id` ASC) ,
  CONSTRAINT `FK3BFBC0C376B7F57`
    FOREIGN KEY (`fieldvalues_id` )
    REFERENCES `mydb`.`fieldvalues` (`fieldvalues_id` ))
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
  INDEX fk_level_id (`level_id` ASC) ,
  CONSTRAINT `FK6A68E08A40D68C7`
    FOREIGN KEY (`adresses_id` )
    REFERENCES `mydb`.`adresses` (`adresses_id` ),
  CONSTRAINT `fk_level_id`
    FOREIGN KEY (`level_id` )
    REFERENCES `mydb`.`userlevel` (`level_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
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
-- Table `mydb`.`objecttype`
-- -----------------------------------------------------
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



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
