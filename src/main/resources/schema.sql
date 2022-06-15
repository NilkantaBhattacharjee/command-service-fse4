-- -----------------------------------------------------
-- Table `skill_tracker`.`associate_profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `skill_tracker`.`skill_lookup` ;
DROP TABLE IF EXISTS `skill_tracker`.`associate_skill_tracker` ;
DROP TABLE IF EXISTS `skill_tracker`.`associate_profile` ;

CREATE TABLE IF NOT EXISTS `skill_tracker`.`associate_profile` (
  `user_id` VARCHAR(200) NOT NULL,
  `associate_id` VARCHAR(100) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `email` VARCHAR(200) NOT NULL,
  `mobile` VARCHAR(10) NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `skill_tracker`.`associate_skill_tracker`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `skill_tracker`.`associate_skill_tracker` (
  `tracker_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` VARCHAR(200) NOT NULL,
  `skill_id` INT NOT NULL,
  `expertise_level` INT NOT NULL,
  `created_by` VARCHAR(100) NOT NULL,
  `created_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_modified_by` VARCHAR(100) NOT NULL,
  `last_modified_on` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`tracker_id`),
  INDEX `fk_associate_profile_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `fk_associate_profile`
    FOREIGN KEY (`user_id`)
    REFERENCES `skill_tracker`.`associate_profile` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `skill_tracker`.`skill_lookup`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `skill_tracker`.`skill_lookup` (
  `skill_id` INT NOT NULL AUTO_INCREMENT,
  `skill_type` VARCHAR(100) NOT NULL,
  `skill_name` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`skill_id`))
ENGINE = InnoDB;