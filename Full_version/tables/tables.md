CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL,
  `account_status` enum('ACTIVE','BLACKLISTED','LOCKED','REQUESTED') COLLATE utf8_bin NOT NULL,
  `account_type` enum('LIBRARIAN','MEMBER') COLLATE utf8_bin NOT NULL,
  `name` varchar(45) COLLATE utf8_bin NOT NULL,
  `address` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `phone` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `title` varchar(45) COLLATE utf8_bin NOT NULL,
  `author` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `publish_date` date DEFAULT NULL,
  `number_of_books` int(11) NOT NULL,
  `book_status` enum('AVAILABLE','RESERVED') COLLATE utf8_bin NOT NULL,
  `subject` enum('DICTIONARY','LITERATURE','SCIENCE_AND_TECHNOLOGY') COLLATE utf8_bin NOT NULL,
  `rental_fee` double NOT NULL,
  PRIMARY KEY (`id`)
)

CREATE TABLE `transaction` (
  `transaction_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `book_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `transaction_date` date NOT NULL,
  `issued_books` int(11) NOT NULL,
  `unreturned_books` int(11) NOT NULL,
  PRIMARY KEY (`transaction_id`)
)

CREATE TABLE `bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `bill_date` date NOT NULL,
  `transaction_date` date NOT NULL,
  `returned_books` int(11) NOT NULL,
  `rental_fee` double NOT NULL,
  `amount` double NOT NULL,
  `bill_status` enum('UNPAID','PAID') COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`bill_id`)
)