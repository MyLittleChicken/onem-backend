
-- url_shortener database definition

CREATE DATABASE `url_shortener` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

-- url_shortener.short_url definition

CREATE TABLE `short_url` (
                             `shorten_url` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
                             `origin_url` varchar(2050) COLLATE utf8mb4_general_ci NOT NULL,
                             `is_deleted` tinyint(1) NOT NULL DEFAULT '0',
                             `created_at` datetime NOT NULL,
                             `expired_at` datetime DEFAULT NULL,
                             PRIMARY KEY (`shorten_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;