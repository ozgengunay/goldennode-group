CREATE database IF NOT EXISTS `thingabled`;

-- Database: `thingabled`
--
use thingabled;

-- CREATE USER 'thingabled'@'localhost' IDENTIFIED BY 'thingabled';
-- CREATE USER 'thingabled'@'%' IDENTIFIED BY 'thingabled';
-- GRANT ALL PRIVILEGES ON *.* TO 'thingabled'@'localhost' WITH GRANT OPTION;
-- GRANT ALL PRIVILEGES ON *.* TO 'thingabled'@'%' WITH GRANT OPTION;
-- --------------------------------------------------------

--
-- Table structure for table `application`
--

CREATE TABLE IF NOT EXISTS `application` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `authorities`
--

CREATE TABLE IF NOT EXISTS `authorities` (
  `id` varchar(50) NOT NULL,
  `authority` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `friendship`
--

CREATE TABLE IF NOT EXISTS `friendship` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `tags` text,
  `userid` varchar(50) NOT NULL,
  `useridfriend` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ifttt_action`
--

CREATE TABLE IF NOT EXISTS `ifttt_action` (
  `id` varchar(50) NOT NULL,
  `ifttt_source_id` varchar(50) DEFAULT NULL,
  `ifttt_source_url` varchar(255) DEFAULT NULL,
  `data` varchar(5000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `processed` int(11) NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `user_timezone` varchar(255) DEFAULT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `ifttt_trigger`
--

CREATE TABLE IF NOT EXISTS `ifttt_trigger` (
  `id` varchar(50) NOT NULL,
  `data` varchar(5000) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `timestamp` bigint(20) NOT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `location`
--

CREATE TABLE IF NOT EXISTS `location` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `name` varchar(255) NOT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `meta`
--

CREATE TABLE IF NOT EXISTS `meta` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `metavalue`
--

CREATE TABLE IF NOT EXISTS `metavalue` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `fieldid` varchar(50) NOT NULL,
  `metaid` varchar(50) NOT NULL,
  `value` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `oauth_access_token`
--

CREATE TABLE IF NOT EXISTS `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `oauth_client_details`
--

CREATE TABLE IF NOT EXISTS `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` integer DEFAULT NULL,
  `refresh_token_validity` integer DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `oauth_client_details`
--

INSERT INTO `oauth_client_details` (`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`) VALUES
('ifttt', NULL, 'secret', 'ifttt', 'authorization_code,refresh_token', NULL, 'ROLE_CLIENT', 86400, 2592000, NULL, 'true');

-- --------------------------------------------------------

--
-- Table structure for table `oauth_client_token`
--

CREATE TABLE IF NOT EXISTS `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` BLOB,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `oauth_code`
--

CREATE TABLE IF NOT EXISTS `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `oauth_refresh_token`
--

CREATE TABLE IF NOT EXISTS `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------
--
-- Table structure for table `socialgroup`
--

CREATE TABLE IF NOT EXISTS `socialgroup` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `socialgroupcontext`
--

CREATE TABLE IF NOT EXISTS `socialgroupcontext` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `socialgroupid` varchar(50) NOT NULL,
  `thingcontextid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `socialgroupmember`
--

CREATE TABLE IF NOT EXISTS `socialgroupmember` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `socialgroupid` varchar(50) NOT NULL,
  `useridmember` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `thing`
--

CREATE TABLE IF NOT EXISTS `thing` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `publickey` varchar(50) DEFAULT NULL,
  `secretkey` varchar(50) DEFAULT NULL,
  `thingcontextid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `thingcontext`
--

CREATE TABLE IF NOT EXISTS `thingcontext` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `parent` varchar(50) DEFAULT NULL,
  `type` varchar(20) NOT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `thingownership`
--

CREATE TABLE IF NOT EXISTS `thingownership` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `thingid` varchar(50) NOT NULL,
  `userid` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `thingpoint`
--

CREATE TABLE IF NOT EXISTS `thingpoint` (
  `id` varchar(50) NOT NULL,
  `creation_time` datetime NOT NULL,
  `modification_time` datetime NOT NULL,
  `status` varchar(20) NOT NULL,
  `internalid` varchar(50) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `permission` varchar(20) NOT NULL,
  `thingid` varchar(255) NOT NULL,
  `type` varchar(20) NOT NULL,
  `unit` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `UserConnection`
--

CREATE TABLE IF NOT EXISTS `UserConnection` (
  `userId` varchar(255) NOT NULL,
  `providerId` varchar(255) NOT NULL,
  `providerUserId` varchar(255) NOT NULL,
  `rank` int(11) NOT NULL,
  `displayName` varchar(255) DEFAULT NULL,
  `profileUrl` varchar(512) DEFAULT NULL,
  `imageUrl` varchar(512) DEFAULT NULL,
  `accessToken` varchar(512) NOT NULL,
  `secret` varchar(512) DEFAULT NULL,
  `refreshToken` varchar(512) DEFAULT NULL,
  `expireTime` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `enabled` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `password_` varchar(255) DEFAULT NULL,
  `sign_in_provider` varchar(20) DEFAULT NULL,
  `username` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `application`
--
ALTER TABLE `application`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `authorities`
--
ALTER TABLE `authorities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK2uf74smucdwf9qal2n67m2343` (`username`,`authority`);

--
-- Indexes for table `friendship`
--
ALTER TABLE `friendship`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKj29s66wdnmtar4d0y2axepvu8` (`useridfriend`,`userid`);

--
-- Indexes for table `ifttt_action`
--
ALTER TABLE `ifttt_action`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `ifttt_trigger`
--
ALTER TABLE `ifttt_trigger`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `meta`
--
ALTER TABLE `meta`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `metavalue`
--
ALTER TABLE `metavalue`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKjo63dfo79s6ow8mtrulp6etjn` (`fieldid`,`metaid`);

--
-- Indexes for table `oauth_client_details`
--
ALTER TABLE `oauth_client_details`
  ADD PRIMARY KEY (`client_id`);

--
-- Indexes for table `socialgroup`
--
ALTER TABLE `socialgroup`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_tgdontcj5noa0airtwk79ra5e` (`name`);

--
-- Indexes for table `socialgroupcontext`
--
ALTER TABLE `socialgroupcontext`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKfvl2s3sprwhrdslyjl221ko96` (`thingcontextid`,`socialgroupid`);

--
-- Indexes for table `socialgroupmember`
--
ALTER TABLE `socialgroupmember`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKas9jkngpcnry2sfaij5uxvmbg` (`useridmember`,`socialgroupid`);

--
-- Indexes for table `thing`
--
ALTER TABLE `thing`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ngnbweywq99tvrbrsu6k456r` (`publickey`);

--
-- Indexes for table `thingcontext`
--
ALTER TABLE `thingcontext`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `thingownership`
--
ALTER TABLE `thingownership`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `thingpoint`
--
ALTER TABLE `thingpoint`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKkchrtxv04joscyqojcaw822nd` (`internalid`,`thingid`),
  ADD KEY `FKkuu0mtc5j59atw3st3id4auce` (`thingid`);

--
-- Indexes for table `UserConnection`
--
ALTER TABLE `UserConnection`
  ADD PRIMARY KEY (`userId`,`providerId`,`providerUserId`),
  ADD UNIQUE KEY `UserConnectionRank` (`userId`,`providerId`,`rank`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  ADD UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `thingpoint`
--
ALTER TABLE `thingpoint`
  ADD CONSTRAINT `FKkuu0mtc5j59atw3st3id4auce` FOREIGN KEY (`thingid`) REFERENCES `thing` (`id`);

