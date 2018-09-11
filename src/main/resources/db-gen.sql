-- MySQL Workbench Synchronization
-- Generated: 2018-08-25 20:48
-- Model: New Model
-- Version: 1.0
-- Project: SailorsMarketplace

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `smarket` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin ;

CREATE TABLE IF NOT EXISTS `smarket`.`users` (
  `user_id` BIGINT(11) GENERATED ALWAYS AS () VIRTUAL,
  `email` VARCHAR(25) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `salt` VARCHAR(45) NOT NULL,
  `enabled` TINYINT(1) NOT NULL,
  `telephone` VARCHAR(12) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `username_UNIQUE` (`email` ASC) VISIBLE,
  UNIQUE INDEX `telephone_UNIQUE` (`telephone` ASC) VISIBLE,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `smarket`.`users_profiles` (
  `user_id` BIGINT(11) NOT NULL,
  `firstname` VARCHAR(45) NULL DEFAULT NULL,
  `lastname` VARCHAR(45) NULL DEFAULT NULL,
  `birthdate` DATE NULL DEFAULT NULL,
  `gender` ENUM('male', 'female') NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  `organization` VARCHAR(45) NULL DEFAULT NULL,
  `avatar` BLOB NULL DEFAULT NULL,
  `telegram_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE INDEX `telegram_id_UNIQUE` (`telegram_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_profiles_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `smarket`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `smarket`.`admins_roles` (
  `role_id` INT(11) NOT NULL AUTO_INCREMENT,
  `role_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `smarket`.`organizations` (
  `org_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `owner_id` BIGINT(11) NOT NULL,
  PRIMARY KEY (`org_id`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE,
  INDEX `org_owner_fkey_idx` (`owner_id` ASC) VISIBLE,
  CONSTRAINT `org_owner_fkey`
    FOREIGN KEY (`owner_id`)
    REFERENCES `smarket`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `smarket`.`events` (
  `event_id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `description` VARCHAR(45) NULL DEFAULT NULL,
  `charge_user_id` BIGINT(11) NOT NULL,
  `date_start` DATE NULL DEFAULT NULL,
  `date_end` DATE NULL DEFAULT NULL,
  `place` BLOB NULL DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  INDEX `event_charge_user_fkey_idx` (`charge_user_id` ASC) VISIBLE,
  CONSTRAINT `event_charge_user_fkey`
    FOREIGN KEY (`charge_user_id`)
    REFERENCES `smarket`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `smarket`.`participations` (
  `event_id` INT(11) NOT NULL,
  `user_id` BIGINT(11) NOT NULL,
  UNIQUE INDEX `event_participation_index` (`event_id` ASC, `user_id` ASC) VISIBLE,
  INDEX `participation_user_key_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `participation_event_key`
    FOREIGN KEY (`event_id`)
    REFERENCES `smarket`.`events` (`event_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `participation_user_key`
    FOREIGN KEY (`user_id`)
    REFERENCES `smarket`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `smarket`.`telegram_connections` (
  `user_id` BIGINT(11) NOT NULL,
  `telegram_id` INT(11) NOT NULL,
  `verified` TINYINT(1) NOT NULL,
  PRIMARY KEY (`user_id`, `telegram_id`),
  CONSTRAINT `fk_telegram_connections_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `smarket`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `smarket`.`verification_codes` (
  `source_system` ENUM('EMail', 'Telegram') NOT NULL,
  `verification_code` VARCHAR(7) NOT NULL,
  `valid_till` DATE NOT NULL,
  `target_id` VARCHAR(45) NOT NULL,
  `target_user_id` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`source_system`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
