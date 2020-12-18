
-- -----------------------------------------------------
-- Schema gift-certificates-db
-- -----------------------------------------------------

CREATE SCHEMA IF NOT EXISTS "gift-certificates-db" AUTHORIZATION sa;
CREATE SEQUENCE IF NOT EXISTS "gift-certificates-db"."tag" START WITH 1 INCREMENT BY 1;
-- -----------------------------------------------------
-- Table `gift-certificates-db`.`tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS tag
(
    id   BIGINT(19)   NOT NULL AUTO_INCREMENT,
    Name VARCHAR(100) NOT NULL

);


-- -----------------------------------------------------
-- Table `gift-certificates-db`.`gift_certificate`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificate
(
    id               BIGINT(19)    NOT NULL AUTO_INCREMENT,
    name            VARCHAR(100)  NOT NULL,
    description      VARCHAR(2000) NOT NULL,
    price            DECIMAL       NOT NULL,
    create_date      TIMESTAMP     NOT NULL,
    last_update_date TIMESTAMP     NOT NULL,
    duration         INT     NOT NULL,
    PRIMARY KEY (id)
);


-- -----------------------------------------------------
-- Table `gift-certificates-db`.`gift_certificate_has_tag`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS gift_certificate_has_tag (
  gift_certificate_id BIGINT(19) NOT NULL,
  tag_id BIGINT(19) NOT NULL,
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


CREATE TABLE IF NOT EXISTS user (
  id BIGINT(19) NOT NULL AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  email VARCHAR(254) NOT NULL,
  PRIMARY KEY (id)
  );


-- -----------------------------------------------------
-- Table `gift_certificates_db`.`order`
-- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `gift_certificates_db`.`order` (
--   `id` BIGINT(19) NOT NULL,
--   `purchase_date` TIMESTAMP NOT NULL,
--   `user_id` BIGINT(19) NOT NULL,
--   PRIMARY KEY (`id`, `user_id`),
--   INDEX `fk_order_user1_idx` (`user_id` ASC) VISIBLE,
--   CONSTRAINT `fk_order_user1`
--     FOREIGN KEY (`user_id`)
--     REFERENCES `gift_certificates_db`.`user` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `gift_certificates_db`.`order_details`
-- -----------------------------------------------------
-- CREATE TABLE IF NOT EXISTS `gift_certificates_db`.`order_details` (
--   `order_id` BIGINT(19) NOT NULL,
--   `gift_certificate_id` BIGINT(19) NOT NULL,
--   `cost` DECIMAL NOT NULL,
--   PRIMARY KEY (`order_id`, `gift_certificate_id`),
--   INDEX `fk_order_details_gift_certificate1_idx` (`gift_certificate_id` ASC) VISIBLE,
--   CONSTRAINT `fk_order_details_order1`
--     FOREIGN KEY (`order_id`)
--     REFERENCES `gift_certificates_db`.`order` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION,
--   CONSTRAINT `fk_order_details_gift_certificate1`
--     FOREIGN KEY (`gift_certificate_id`)
--     REFERENCES `gift_certificates_db`.`gift_certificate` (`id`)
--     ON DELETE NO ACTION
--     ON UPDATE NO ACTION)
-- ENGINE = InnoDB;





