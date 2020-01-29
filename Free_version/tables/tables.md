CREATE TABLE `user` (
  `user_name` varchar(45) COLLATE utf8_bin NOT NULL,
  `password` varchar(45) COLLATE utf8_bin NOT NULL,
  `permission` varchar(45) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_name`)
)

CREATE TABLE `book` (
  `book_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `book_name` varchar(45) COLLATE utf8_bin NOT NULL,
  `author` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `publish_year` int(11) DEFAULT NULL,
  `number_of_books` int(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
)

CREATE TABLE `borrowedbooks` (
  `book_id` varchar(45) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(45) COLLATE utf8_bin NOT NULL,
  `number_of_borrowed_books` int(11) NOT NULL,
  PRIMARY KEY (`book_id`,`user_name`)
)