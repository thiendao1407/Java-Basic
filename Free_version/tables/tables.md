CREATE TABLE `user` (
  `user_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `permission` varchar(45) NOT NULL,
  PRIMARY KEY (`user_name`)
)

CREATE TABLE `book` (
  `book_id` varchar(45) NOT NULL,
  `book_name` varchar(45) NOT NULL,
  `author` varchar(45) DEFAULT NULL,
  `publish_year` int(11) DEFAULT NULL,
  `number_of_books` int(11) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
)

CREATE TABLE `borrowedbooks` (
  `book_id` varchar(45) NOT NULL,
  `user_name` varchar(45) NOT NULL,
  `number_of_borrowed_books` int(11) NOT NULL,
  PRIMARY KEY (`book_id`,`user_name`)
)