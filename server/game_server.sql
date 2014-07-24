CREATE TABLE `color` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `color` smallint(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_color` (`player_id`, `color`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `gender` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `gender` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_gender` (`player_id`, `gender`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `type` enum('inventory','equipment','bank') NOT NULL,
  `slot` smallint(6) NOT NULL,
  `item` smallint(6) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_type_slot` (`player_id`,`type`,`slot`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(12) NOT NULL,
  `password` char(60) NOT NULL,
  `rights` tinyint(4) NOT NULL,
  `members` tinyint(1) NOT NULL,
  `x` smallint(6) NOT NULL,
  `y` smallint(6) NOT NULL,
  `height` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_username` (`id`, `username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `setting` enum('designed_character') NOT NULL,
  `value` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_setting` (`player_id`,`setting`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sanctions` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(12) NOT NULL,
  `type` enum('disabled','muted') NOT NULL,
  `punisher_username` varchar(12) NOT NULL,
  `address` varchar(15) DEFAULT NULL,
  `issue` datetime NOT NULL,
  `expire` datetime DEFAULT NULL,
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `reason` varchar(256) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_type` (`username`,`type`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `skills` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `current_level` int(11) NOT NULL,
  `experience` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_level_experience` (`player_id`,`current_level`,`experience`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `style` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `style` smallint(6) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_style` (`player_id`, `style`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

CREATE TABLE `failed_logins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `username` varchar(12) NOT NULL,
  `issue` datetime NOT NULL,
  `expire` datetime DEFAULT 'NOW() + INTERVAL 5 MINUTE'
  `active` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_username` (`player_id`, `username`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;