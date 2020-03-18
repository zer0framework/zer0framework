-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema zer0framework
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema zer0framework
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `zer0framework` DEFAULT CHARACTER SET utf8 ;
USE `zer0framework` ;

-- -----------------------------------------------------
-- Table `zer0framework`.`person`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zer0framework`.`person` (
  `cd_person` INT NOT NULL AUTO_INCREMENT,
  `nm_person` VARCHAR(120) NOT NULL,
  `dt_birthdate` DATE NOT NULL,
  `ds_job` VARCHAR(45) NULL,
  `cd_person_manager` INT NULL,
  `dh_created` DATETIME NULL,
  PRIMARY KEY (`cd_person`),
  INDEX `fk_person_person1_idx` (`cd_person_manager` ASC) VISIBLE,
  CONSTRAINT `fk_person_person1`
    FOREIGN KEY (`cd_person_manager`)
    REFERENCES `zer0framework`.`person` (`cd_person`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `zer0framework`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zer0framework`.`user` (
  `cd_user` INT NOT NULL AUTO_INCREMENT,
  `ds_username` VARCHAR(20) NOT NULL,
  `ds_password` VARCHAR(45) NOT NULL,
  `cd_person` INT NOT NULL,
  `dh_created` DATETIME NULL,
  PRIMARY KEY (`cd_user`),
  INDEX `fk_user_person1_idx` (`cd_person` ASC) VISIBLE,
  CONSTRAINT `fk_user_person1`
    FOREIGN KEY (`cd_person`)
    REFERENCES `zer0framework`.`person` (`cd_person`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `zer0framework`.`post`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `zer0framework`.`post` (
  `cd_post` INT NOT NULL AUTO_INCREMENT,
  `cd_user` INT NOT NULL,
  `ds_title` VARCHAR(120) NOT NULL,
  `ds_body` VARCHAR(255) NOT NULL,
  `dh_created` DATETIME NULL,
  PRIMARY KEY (`cd_post`),
  INDEX `fk_post_user_idx` (`cd_user` ASC) VISIBLE,
  CONSTRAINT `fk_post_user`
    FOREIGN KEY (`cd_user`)
    REFERENCES `zer0framework`.`user` (`cd_user`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
