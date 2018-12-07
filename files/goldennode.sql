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
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `enabled` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------
--
-- Indexes for table `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK2uf74smucdwf9qal2n67m2343` (`username`,`authority`);

-- --------------------------------------------------------
--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);
-- --------------------------------------------------------

