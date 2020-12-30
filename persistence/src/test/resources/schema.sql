-- -----------------------------------------------------
-- Schema gift-certificates-db
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS "gift-certificates-db" AUTHORIZATION sa;
CREATE SEQUENCE IF NOT EXISTS "gift-certificates-db"."tag" START WITH 1 INCREMENT BY 1;
-- -----------------------------------------------------
-- Table gift-certificates-db.tag
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tag
(
    id   BIGINT(19)   NOT NULL AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL

);


-- -----------------------------------------------------
-- Table gift-certificates-db.gift_certificate
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               BIGINT(19)    NOT NULL AUTO_INCREMENT,
    name             VARCHAR(100)  NOT NULL,
    description      VARCHAR(2000) NOT NULL,
    price            DECIMAL       NOT NULL,
    create_date      TIMESTAMP     NOT NULL,
    last_update_date TIMESTAMP     NOT NULL,
    duration         INT           NOT NULL,
    PRIMARY KEY (id)
);


-- -----------------------------------------------------
-- Table gift-certificates-db.gift_certificate_has_tag
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificate_has_tag
(
    gift_certificate_id BIGINT(19) NOT NULL,
    tag_id              BIGINT(19) NOT NULL,
    PRIMARY KEY (gift_certificate_id, tag_id),
    CONSTRAINT fk_gift_certificate_has_tag_gift_certificate
        FOREIGN KEY (gift_certificate_id)
            REFERENCES gift_certificate (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_gift_certificate_has_tag_tag1
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);


CREATE TABLE IF NOT EXISTS user
(
    id    BIGINT(19)   NOT NULL AUTO_INCREMENT,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(254) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS orders
(
    id            BIGINT(19) NOT NULL AUTO_INCREMENT,
    purchase_date TIMESTAMP  NOT NULL,
    user_id       BIGINT(19) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_user1
        FOREIGN KEY (user_id)
            REFERENCES user (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);


-- -----------------------------------------------------
-- Table gift_certificates_db.order_details
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificates_db.order_details
(
    id                  BIGINT(19) NOT NULL AUTO_INCREMENT,
    order_id            BIGINT(19) NOT NULL,
    gift_certificate_id BIGINT(19) NOT NULL,
    cost                DECIMAL    NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_details_order1
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_order_details_gift_certificate1
        FOREIGN KEY (gift_certificate_id)
            REFERENCES gift_certificate (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);



-- MySQL Workbench Forward Engineering
--
-- SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
-- SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
-- SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema gift_certificates_db
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema gift_certificates_db
-- -----------------------------------------------------
-- CREATE SCHEMA IF NOT EXISTS `gift_certificates_db` DEFAULT CHARACTER SET utf8 ;
-- USE `gift_certificates_db` ;
--
-- -- -----------------------------------------------------
-- -- Table `gift_certificates_db`.`tag`
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `gift_certificates_db`.`tag` (
--                                                             `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
--                                                             `Name` VARCHAR(100) NOT NULL,
--                                                             PRIMARY KEY (`id`),
--                                                             UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
--                                                             UNIQUE INDEX `Name_UNIQUE` (`Name` ASC) VISIBLE)
--     ENGINE = InnoDB;
--
--
-- -- -----------------------------------------------------
-- -- Table `gift_certificates_db`.`gift_certificate`
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `gift_certificates_db`.`gift_certificate` (
--                                                                          `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
--                                                                          `name` VARCHAR(100) NOT NULL,
--                                                                          `description` VARCHAR(2000) NOT NULL,
--                                                                          `price` DECIMAL NOT NULL,
--                                                                          `create_date` TIMESTAMP NOT NULL,
--                                                                          `last_update_date` TIMESTAMP NOT NULL,
--                                                                          `duration` INT NOT NULL,
--                                                                          `deleted` TINYINT NOT NULL,
--                                                                          PRIMARY KEY (`id`),
--                                                                          UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
--     ENGINE = InnoDB;
--
--
-- -- -----------------------------------------------------
-- -- Table `gift_certificates_db`.`gift_certificate_has_tag`
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `gift_certificates_db`.`gift_certificate_has_tag` (
--                                                                                  `gift_certificate_id` BIGINT(19) NOT NULL,
--                                                                                  `tag_id` BIGINT(19) NOT NULL,
--                                                                                  PRIMARY KEY (`gift_certificate_id`, `tag_id`),
--                                                                                  INDEX `fk_gift_certificate_has_tag_tag1_idx` (`tag_id` ASC) VISIBLE,
--                                                                                  INDEX `fk_gift_certificate_has_tag_gift_certificate_idx` (`gift_certificate_id` ASC) VISIBLE,
--                                                                                  CONSTRAINT `fk_gift_certificate_has_tag_gift_certificate`
--                                                                                      FOREIGN KEY (`gift_certificate_id`)
--                                                                                          REFERENCES `gift_certificates_db`.`gift_certificate` (`id`)
--                                                                                          ON DELETE NO ACTION
--                                                                                          ON UPDATE NO ACTION,
--                                                                                  CONSTRAINT `fk_gift_certificate_has_tag_tag1`
--                                                                                      FOREIGN KEY (`tag_id`)
--                                                                                          REFERENCES `gift_certificates_db`.`tag` (`id`)
--                                                                                          ON DELETE NO ACTION
--                                                                                          ON UPDATE NO ACTION)
--     ENGINE = InnoDB;
--
--
-- -- -----------------------------------------------------
-- -- Table `gift_certificates_db`.`user`
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `gift_certificates_db`.`user` (
--                                                              `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
--                                                              `name` VARCHAR(100) NOT NULL,
--                                                              `email` VARCHAR(254) NOT NULL,
--                                                              PRIMARY KEY (`id`),
--                                                              UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
--                                                              UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
--     ENGINE = InnoDB;
--
--
-- -- -----------------------------------------------------
-- -- Table `gift_certificates_db`.`orders`
-- -- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `gift_certificates_db`.`orders` (
--                                                                `id` BIGINT(19) NOT NULL AUTO_INCREMENT,
--                                                                `purchase_date` TIMESTAMP NOT NULL,
--                                                                `user_id` BIGINT(19) NOT NULL,
--                                                                `gift_certificate_id` BIGINT(19) NOT NULL,
--                                                                `cost` DECIMAL NOT NULL,
--                                                                PRIMARY KEY (`id`, `user_id`),
--                                                                INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
--                                                                UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
--                                                                INDEX `fk_orders_gift_certificate1_idx` (`gift_certificate_id` ASC) VISIBLE,
--                                                                CONSTRAINT `fk_order_user1`
--                                                                    FOREIGN KEY (`user_id`)
--                                                                        REFERENCES `gift_certificates_db`.`user` (`id`)
--                                                                        ON DELETE NO ACTION
--                                                                        ON UPDATE NO ACTION,
--                                                                CONSTRAINT `fk_orders_gift_certificate1`
--                                                                    FOREIGN KEY (`gift_certificate_id`)
--                                                                        REFERENCES `gift_certificates_db`.`gift_certificate` (`id`)
--                                                                        ON DELETE NO ACTION
--                                                                        ON UPDATE NO ACTION)
--     ENGINE = InnoDB;
--
--
-- SET SQL_MODE=@OLD_SQL_MODE;
-- SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
-- SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
