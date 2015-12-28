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
