CREATE database IF NOT EXISTS `goldennode`;

-- Database: `goldennode`
--
use goldennode;

-- CREATE USER 'goldennode'@'localhost' IDENTIFIED BY 'goldennode';
-- CREATE USER 'goldennode'@'%' IDENTIFIED BY 'goldennode';
-- GRANT ALL PRIVILEGES ON *.* TO 'goldennode'@'localhost' WITH GRANT OPTION;
-- GRANT ALL PRIVILEGES ON *.* TO 'goldennode'@'%' WITH GRANT OPTION;
-- --------------------------------------------------------

--
-- Table structure for table `authorities`
--

CREATE TABLE IF NOT EXISTS `authorities` (
  `id` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `enabled` int(11) NOT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(50) DEFAULT NULL,
  `apikey` varchar(50) DEFAULT NULL,
  `secretkey` varchar(50) DEFAULT NULL

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------
--
-- Indexes for table `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_userid_authority` (`userid`,`authority`);

-- --------------------------------------------------------
--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_email` (`email`),
  ADD UNIQUE KEY `UK_username` (`username`),
  ADD UNIQUE KEY `UK_apikey` (`apikey`);
  -- --------------------------------------------------------

