INSERT IGNORE INTO `DB`.`user` (`id`, `username`, `password`, `algorithm`) VALUES ('1', 'john', '{bcrypt}$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT IGNORE INTO `DB`.`authority` (`id`, `name`, `user`) VALUES ('1', 'READ', '1');
INSERT IGNORE INTO `DB`.`authority` (`id`, `name`, `user`) VALUES ('2', 'WRITE', '1');

INSERT IGNORE INTO `DB`.`product` (`id`, `name`, `price`, `currency`) VALUES ('1', 'Chocolate', '10', 'USD');