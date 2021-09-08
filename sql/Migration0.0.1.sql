--
-- Table structure for table `accessories`
--

DROP TABLE IF EXISTS `accessories`;
CREATE TABLE `accessories` (
  `accessories_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `sku` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `manufacturer_part_number` bigint DEFAULT NULL,
  `material` varchar(255) DEFAULT NULL,
  `pressure_rating` int DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `target_gender` varchar(255) DEFAULT NULL,
  `tittle` varchar(255) DEFAULT NULL,
  `valve_type` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `cycle_id` bigint DEFAULT NULL,
  PRIMARY KEY (`accessories_id`),
  UNIQUE KEY `UK_fgk18pvbyjc8h2q8hgl2dltbe` (`sku`),
  KEY `FKoqkk7s4b6b4rtjw90w0ndq9k` (`category_id`),
  KEY `FK37xc6yd5ihwp1gm95p7hnhmw9` (`cycle_id`),
  CONSTRAINT `FK37xc6yd5ihwp1gm95p7hnhmw9` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`cycle_id`) ON DELETE CASCADE,
  CONSTRAINT `FKoqkk7s4b6b4rtjw90w0ndq9k` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `accessories`
--



--
-- Table structure for table `acessorie_variations`
--

DROP TABLE IF EXISTS `acessorie_variations`;
CREATE TABLE `acessorie_variations` (
  `accessorie_variations_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `sku` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` float NOT NULL,
  `size` float NOT NULL,
  `stock` int DEFAULT NULL,
  `variations_title` varchar(255) DEFAULT NULL,
  `cycle_id` bigint DEFAULT NULL,
  PRIMARY KEY (`accessorie_variations_id`),
  UNIQUE KEY `UK_o5oyk7qeyo1uflxjhmh8lf12e` (`sku`),
  KEY `FK8ecrn06g697mvbd50h0b5rkq1` (`cycle_id`),
  CONSTRAINT `FK8ecrn06g697mvbd50h0b5rkq1` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`cycle_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `acessorie_variations`
--



--
-- Table structure for table `add_to_cart`
--

DROP TABLE IF EXISTS `add_to_cart`;

CREATE TABLE `add_to_cart` (
  `cart_id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `accessories_id` bigint DEFAULT NULL,
  `cycle_variations_id` bigint DEFAULT NULL,
  `fitness_equipment_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `FKaihrywsqll20jyi3exc4vt5h` (`accessories_id`),
  KEY `FKbh4vvsqv94yhwlnx55lwij9ca` (`fitness_equipment_id`),
  KEY `FKdekd94auhe67yuxxp0q9jxop0` (`user_id`),
  CONSTRAINT `FKaihrywsqll20jyi3exc4vt5h` FOREIGN KEY (`accessories_id`) REFERENCES `accessories` (`accessories_id`) ON DELETE CASCADE,
  CONSTRAINT `FKbh4vvsqv94yhwlnx55lwij9ca` FOREIGN KEY (`fitness_equipment_id`) REFERENCES `fitness_equipment` (`fitness_equipment_id`) ON DELETE CASCADE,
  CONSTRAINT `FKdekd94auhe67yuxxp0q9jxop0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `add_to_cart`
--



--
-- Table structure for table `address_details`
--

DROP TABLE IF EXISTS `address_details`;

CREATE TABLE `address_details` (
  `address_details_id` bigint NOT NULL AUTO_INCREMENT,
  `address_type` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `door_no` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `land_mark` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `pin_code` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`address_details_id`),
  KEY `FKlp9v0d0oxx0jfg18b3crs8s8p` (`user_id`),
  CONSTRAINT `FKlp9v0d0oxx0jfg18b3crs8s8p` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `address_details`
--



--
-- Table structure for table `badge_configuration`
--

DROP TABLE IF EXISTS `badge_configuration`;

CREATE TABLE `badge_configuration` (
  `badge_configuration_id` bigint NOT NULL AUTO_INCREMENT,
  `batch_name` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `range_from` float DEFAULT NULL,
  `range_to` float DEFAULT NULL,
  `badge_reward_conf_id` bigint DEFAULT NULL,
  PRIMARY KEY (`badge_configuration_id`),
  KEY `FKtcii34l1fn57ob6by0aqhv1vx` (`badge_reward_conf_id`),
  CONSTRAINT `FKtcii34l1fn57ob6by0aqhv1vx` FOREIGN KEY (`badge_reward_conf_id`) REFERENCES `badge_reward_conf` (`badge_reward_conf_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1292 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Dumping data for table `badge_configuration`
--



--
-- Table structure for table `badge_reward_conf`
--

DROP TABLE IF EXISTS `badge_reward_conf`;

CREATE TABLE `badge_reward_conf` (
  `badge_reward_conf_id` bigint NOT NULL AUTO_INCREMENT,
  `activity_distance` float DEFAULT NULL,
  `activity_points` int DEFAULT NULL,
  `blog_points` int DEFAULT NULL,
  `challenge_enrolled_per_day` int DEFAULT NULL,
  `challenge_enrolled_point` int DEFAULT NULL,
  `challenge_point` int DEFAULT NULL,
  `event_enrolled_per_day` int DEFAULT NULL,
  `event_enrolled_points` int DEFAULT NULL,
  `event_per_day` int DEFAULT NULL,
  `event_points` int DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `no_of_blogs_per_day` int DEFAULT NULL,
  `no_of_challenges_per_day` int DEFAULT NULL,
  `shop_item_per_day` int DEFAULT NULL,
  `shop_points` int DEFAULT NULL,
  `tnt_event_enrolled_per_day` int DEFAULT NULL,
  `tnt_event_enrolled_points` int DEFAULT NULL,
  PRIMARY KEY (`badge_reward_conf_id`)
) ENGINE=InnoDB AUTO_INCREMENT=344 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--



--
-- Table structure for table `badges_earned`
--

DROP TABLE IF EXISTS `badges_earned`;

CREATE TABLE `badges_earned` (
  `badges_earned_id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `badge_name` varchar(255) DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `module` varchar(45) DEFAULT NULL,
  `range_from` int DEFAULT NULL,
  `range_to` int DEFAULT NULL,
  `earn_point` int DEFAULT NULL,
  PRIMARY KEY (`badges_earned_id`),
  KEY `FKq4ku84xubu488m1d6cqc36xll` (`user_id`),
  CONSTRAINT `FKq4ku84xubu488m1d6cqc36xll` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `badges_reward_configuration`
--

DROP TABLE IF EXISTS `badges_reward_configuration`;
CREATE TABLE `badges_reward_configuration` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `activity_distance` int DEFAULT NULL,
  `activity_duration` int DEFAULT NULL,
  `activity_laps` int DEFAULT NULL,
  `activity_per_month_badges` int DEFAULT NULL,
  `activity_point` int DEFAULT NULL,
  `badge` varchar(255) DEFAULT NULL,
  `blog_point` int DEFAULT NULL,
  `blogs_blogs_per_day` int DEFAULT NULL,
  `blogs_no_of_words` int DEFAULT NULL,
  `blogs_per_month_blogs` int DEFAULT NULL,
  `challenge_point` int DEFAULT NULL,
  `challenges_challenges_completed_per_day` int DEFAULT NULL,
  `challenges_challenges_completed_per_month` int DEFAULT NULL,
  `challenges_no_of_challenges_per_day` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `event_point` int DEFAULT NULL,
  `events_completed_per_day` int DEFAULT NULL,
  `events_completed_per_months` int DEFAULT NULL,
  `events_no_of_events_per_day` int DEFAULT NULL,
  `events_tnt_events` int DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `shop_point` int DEFAULT NULL,
  `shopping_items_purchase_per_day` int DEFAULT NULL,
  `shopping_items_purchase_per_month` int DEFAULT NULL,
  `shopping_shopping_count` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `bike_booking`
--

DROP TABLE IF EXISTS `bike_booking`;
CREATE TABLE `bike_booking` (
  `booking_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `from_date` datetime DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `total_cost` varchar(255) DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `bike_rental_product_id` bigint NOT NULL,
  `store_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `reject_reason` varchar(255) DEFAULT NULL,
  `rejection_date` datetime DEFAULT NULL,
  `return_date` datetime DEFAULT NULL,
  `conform_date` datetime DEFAULT NULL,
  PRIMARY KEY (`booking_id`),
  KEY `FK8wbc494go7h43w8jhdtvsd9se` (`bike_rental_product_id`),
  KEY `FK39q5u5j27r633km79m40yubke` (`store_id`),
  KEY `FKi54jd458vvu9u11pyjbjfekhd` (`user_id`),
  CONSTRAINT `FK39q5u5j27r633km79m40yubke` FOREIGN KEY (`store_id`) REFERENCES `store_detail` (`store_id`) ON DELETE CASCADE,
  CONSTRAINT `FK8wbc494go7h43w8jhdtvsd9se` FOREIGN KEY (`bike_rental_product_id`) REFERENCES `bike_rental_management` (`bike_rental_product_id`) ON DELETE CASCADE,
  CONSTRAINT `FKi54jd458vvu9u11pyjbjfekhd` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `bike_brand`
--

DROP TABLE IF EXISTS `bike_brand`;

CREATE TABLE `bike_brand` (
  `brand_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `bike_filter`
--

DROP TABLE IF EXISTS `bike_filter`;

CREATE TABLE `bike_filter` (
  `size` int NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `price` int DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  PRIMARY KEY (`size`),
  KEY `FKhy9yi4tl2qarl7x8li78qt2sc` (`brand_id`),
  CONSTRAINT `FKhy9yi4tl2qarl7x8li78qt2sc` FOREIGN KEY (`brand_id`) REFERENCES `bike_brand` (`brand_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `bike_model`
--

DROP TABLE IF EXISTS `bike_model`;

CREATE TABLE `bike_model` (
  `model_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  PRIMARY KEY (`model_id`),
  KEY `FKjewwxbs2jwf6imjwk0bfqmkd6` (`brand_id`),
  CONSTRAINT `FKjewwxbs2jwf6imjwk0bfqmkd6` FOREIGN KEY (`brand_id`) REFERENCES `bike_brand` (`brand_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




--
-- Table structure for table `bike_rental_management`
--

DROP TABLE IF EXISTS `bike_rental_management`;

CREATE TABLE `bike_rental_management` (
  `bike_rental_product_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `about` varchar(255) DEFAULT NULL,
  `bottom_bracket` varchar(255) DEFAULT NULL,
  `brake_levers` varchar(255) DEFAULT NULL,
  `brake_rear` varchar(255) DEFAULT NULL,
  `brakes_front` varchar(255) DEFAULT NULL,
  `chain_cover` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `crank` varchar(255) DEFAULT NULL,
  `field_stand` varchar(255) DEFAULT NULL,
  `fork` varchar(255) DEFAULT NULL,
  `frame` varchar(255) DEFAULT NULL,
  `gears` varchar(255) DEFAULT NULL,
  `grips` varchar(255) DEFAULT NULL,
  `handle_bar` varchar(255) DEFAULT NULL,
  `head_set` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `offer` int DEFAULT NULL,
  `pedals` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `product_sku` varchar(255) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `rear_derailleur` varchar(255) DEFAULT NULL,
  `rims` varchar(255) DEFAULT NULL,
  `road_type` varchar(255) DEFAULT NULL,
  `saddle` varchar(255) DEFAULT NULL,
  `seat_clamps` varchar(255) DEFAULT NULL,
  `seat_post` varchar(255) DEFAULT NULL,
  `size` float DEFAULT NULL,
  `spokes` varchar(255) DEFAULT NULL,
  `stem` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `tires` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `website_link` varchar(255) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `model_id` bigint DEFAULT NULL,
  PRIMARY KEY (`bike_rental_product_id`),
  UNIQUE KEY `UKs111u3w7i3957hae8rhaaqlup` (`product_sku`),
  UNIQUE KEY `UK_s111u3w7i3957hae8rhaaqlup` (`product_sku`),
  KEY `FKo991wl6k9f4k9lbflsimhq3pv` (`brand_id`),
  KEY `FKk33bpoqhmqm7ju4fbtd6gffyk` (`model_id`),
  CONSTRAINT `FKk33bpoqhmqm7ju4fbtd6gffyk` FOREIGN KEY (`model_id`) REFERENCES `bike_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `FKo991wl6k9f4k9lbflsimhq3pv` FOREIGN KEY (`brand_id`) REFERENCES `bike_brand` (`brand_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `bike_service`
--

DROP TABLE IF EXISTS `bike_service`;
CREATE TABLE `bike_service` (
  `service_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `cycle_name` varchar(255) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `pickup_drop` bit(1) DEFAULT NULL,
  `pickup_price` double DEFAULT NULL,
  `service_date` datetime DEFAULT NULL,
  `service_price` double DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `model_id` bigint DEFAULT NULL,
  `package_id` bigint DEFAULT NULL,
  `store_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`service_id`),
  KEY `FKadreuu8qb9mjhf2seyrxet9ct` (`brand_id`),
  KEY `FK54unam9t6t5kk245n3bdfmhlt` (`model_id`),
  KEY `FKt7s1y4r8w9up3iv8yyqc94310` (`package_id`),
  KEY `FK2rcey6lv75k3xlx3k7x9bgry` (`store_id`),
  KEY `FKkdvim4qm2w38diuwtqlbahk02` (`user_id`),
  CONSTRAINT `FK2rcey6lv75k3xlx3k7x9bgry` FOREIGN KEY (`store_id`) REFERENCES `store_detail` (`store_id`) ON DELETE CASCADE,
  CONSTRAINT `FK54unam9t6t5kk245n3bdfmhlt` FOREIGN KEY (`model_id`) REFERENCES `bike_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `FKadreuu8qb9mjhf2seyrxet9ct` FOREIGN KEY (`brand_id`) REFERENCES `bike_brand` (`brand_id`) ON DELETE CASCADE,
  CONSTRAINT `FKkdvim4qm2w38diuwtqlbahk02` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKt7s1y4r8w9up3iv8yyqc94310` FOREIGN KEY (`package_id`) REFERENCES `bike_service_package` (`package_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=148 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `bike_service_feature`
--

DROP TABLE IF EXISTS `bike_service_feature`;
CREATE TABLE `bike_service_feature` (
  `feature_id` bigint NOT NULL AUTO_INCREMENT,
  `detail` varchar(255) DEFAULT NULL,
  `package_id` bigint DEFAULT NULL,
  PRIMARY KEY (`feature_id`),
  KEY `FKl422sv9eoa6e6ullcxsrby9q5` (`package_id`),
  CONSTRAINT `FKl422sv9eoa6e6ullcxsrby9q5` FOREIGN KEY (`package_id`) REFERENCES `bike_service_package` (`package_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `bike_service_package`
--

DROP TABLE IF EXISTS `bike_service_package`;
CREATE TABLE `bike_service_package` (
  `package_id` bigint NOT NULL AUTO_INCREMENT,
  `package_name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`package_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
CREATE TABLE `bill` (
  `bill` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `bill_no` varchar(255) DEFAULT NULL,
  `order_no` int DEFAULT NULL,
  `seller_id` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`bill`),
  KEY `FKs78seu7p3wfrnuhulmtpg707u` (`user_id`),
  CONSTRAINT `FKs78seu7p3wfrnuhulmtpg707u` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `bill_items`
--

DROP TABLE IF EXISTS `bill_items`;
CREATE TABLE `bill_items` (
  `bill_items_id` bigint NOT NULL AUTO_INCREMENT,
  `bill_no` bigint DEFAULT NULL,
  `order_no` bigint DEFAULT NULL,
  `product_code` bigint DEFAULT NULL,
  PRIMARY KEY (`bill_items_id`),
  KEY `FK7fthhognpakr9merqf2wp7dfd` (`bill_no`),
  KEY `FKko8c1l4osllyiy6epv1pn66oq` (`order_no`),
  KEY `FKa36nrtc11j07knt9xoj85xlqr` (`product_code`),
  CONSTRAINT `FK7fthhognpakr9merqf2wp7dfd` FOREIGN KEY (`bill_no`) REFERENCES `bill` (`bill`) ON DELETE CASCADE,
  CONSTRAINT `FKa36nrtc11j07knt9xoj85xlqr` FOREIGN KEY (`product_code`) REFERENCES `order_items` (`order_items_id`) ON DELETE CASCADE,
  CONSTRAINT `FKko8c1l4osllyiy6epv1pn66oq` FOREIGN KEY (`order_no`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `challenge`
--

DROP TABLE IF EXISTS `challenge`;
CREATE TABLE `challenge` (
  `challenge_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `age_allowed` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `duration` varchar(255) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `gender_allowed` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `total_kilometers` float DEFAULT NULL,
  `total_members` int DEFAULT NULL,
  `ride_type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`challenge_id`),
  KEY `FK1pmijrxdyqw7vapqhtkju1hyb` (`ride_type_id`),
  KEY `FKq8acspc7u4hsl89y9sfq1hq27` (`user_id`),
  CONSTRAINT `FK1pmijrxdyqw7vapqhtkju1hyb` FOREIGN KEY (`ride_type_id`) REFERENCES `ride_type` (`ride_type_id`) ON DELETE CASCADE,
  CONSTRAINT `FKq8acspc7u4hsl89y9sfq1hq27` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `challenge_count`
--

DROP TABLE IF EXISTS `challenge_count`;
CREATE TABLE `challenge_count` (
  `challenge_id` bigint NOT NULL,
  `joined` bigint DEFAULT NULL,
  PRIMARY KEY (`challenge_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `challenge_group`
--

DROP TABLE IF EXISTS `challenge_group`;
CREATE TABLE `challenge_group` (
  `challenge_group_id` bigint NOT NULL AUTO_INCREMENT,
  `status` int NOT NULL DEFAULT '1',
  `challenge_id` bigint DEFAULT NULL,
  `user_group_id` bigint NOT NULL,
  PRIMARY KEY (`challenge_group_id`),
  KEY `FK7065d68xepw1k6fuipm9rblso` (`challenge_id`),
  CONSTRAINT `FK7065d68xepw1k6fuipm9rblso` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`challenge_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `challenge_points`
--

DROP TABLE IF EXISTS `challenge_points`;
CREATE TABLE `challenge_points` (
  `challenge_user_count` bigint NOT NULL,
  PRIMARY KEY (`challenge_user_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `challenge_user`
--

DROP TABLE IF EXISTS `challenge_user`;
CREATE TABLE `challenge_user` (
  `challenge_user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `achieved_km` float DEFAULT NULL,
  `date_of_joining` datetime DEFAULT NULL,
  `ride_km` float DEFAULT NULL,
  `challenge_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`challenge_user_id`),
  KEY `FKmq23oqyd3s02abqo1pspisxg8` (`challenge_id`),
  KEY `FKkoths7lx1y4rngxo6fo4v24ah` (`user_id`),
  CONSTRAINT `FKkoths7lx1y4rngxo6fo4v24ah` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKmq23oqyd3s02abqo1pspisxg8` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`challenge_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `city_id` bigint NOT NULL AUTO_INCREMENT,
  `state_id` bigint DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`city_id`),
  KEY `FK6p2u50v8fg2y0js6djc6xanit` (`state_id`),
  CONSTRAINT `FK6p2u50v8fg2y0js6djc6xanit` FOREIGN KEY (`state_id`) REFERENCES `state` (`state_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=719 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `content`
--

DROP TABLE IF EXISTS `content`;
CREATE TABLE `content` (
  `content_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `comment_count` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `excerpt` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `like_count` int DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `tags` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `views` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`content_id`),
  KEY `FK8ha3wmua0lw7fp1km9t8deutx` (`user_id`),
  CONSTRAINT `FK8ha3wmua0lw7fp1km9t8deutx` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `content_point`
--

DROP TABLE IF EXISTS `content_point`;

CREATE TABLE `content_point` (
  `user_count_per_day` bigint NOT NULL,
  PRIMARY KEY (`user_count_per_day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `content_rating`
--

DROP TABLE IF EXISTS `content_rating`;

CREATE TABLE `content_rating` (
  `rating_id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `is_like` bit(1) DEFAULT NULL,
  `rating_star` float DEFAULT NULL,
  `content_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`rating_id`),
  KEY `FK2bof3w4qpk0e2w0mqmw72yatj` (`content_id`),
  KEY `FK2p1yc4lq0q9oocbn6so1iull7` (`user_id`),
  CONSTRAINT `FK2bof3w4qpk0e2w0mqmw72yatj` FOREIGN KEY (`content_id`) REFERENCES `content` (`content_id`) ON DELETE CASCADE,
  CONSTRAINT `FK2p1yc4lq0q9oocbn6so1iull7` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `cycle`
--

DROP TABLE IF EXISTS `cycle`;
CREATE TABLE `cycle` (
  `cycle_id` bigint NOT NULL AUTO_INCREMENT,
  `age` varchar(255) DEFAULT NULL,
  `brakes` varchar(255) DEFAULT NULL,
  `chain` varchar(255) DEFAULT NULL,
  `cog_set` varchar(255) DEFAULT NULL,
  `crank` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `fork` varchar(255) DEFAULT NULL,
  `frame` varchar(255) DEFAULT NULL,
  `front_derailleur` varchar(255) DEFAULT NULL,
  `gears` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `grips` varchar(255) DEFAULT NULL,
  `hand_set` varchar(255) DEFAULT NULL,
  `handle_bar` varchar(255) DEFAULT NULL,
  `hubs` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `pedals` varchar(255) DEFAULT NULL,
  `rear_derailleur` varchar(255) DEFAULT NULL,
  `rims` varchar(255) DEFAULT NULL,
  `saddle` varchar(255) DEFAULT NULL,
  `shifters` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `stem` varchar(255) DEFAULT NULL,
  `tires` varchar(255) DEFAULT NULL,
  `tittle` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`cycle_id`),
  KEY `FKs4g87ukrybeilat8jbt3mtnke` (`category_id`),
  CONSTRAINT `FKs4g87ukrybeilat8jbt3mtnke` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `discover_people`
--

DROP TABLE IF EXISTS `discover_people`;
CREATE TABLE `discover_people` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKc42v3p5sba9lflqdruu4o81a7` (`user_id`),
  CONSTRAINT `FKc42v3p5sba9lflqdruu4o81a7` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `ecomm_add_to_cart`
--

DROP TABLE IF EXISTS `ecomm_add_to_cart`;
CREATE TABLE `ecomm_add_to_cart` (
  `cart_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint DEFAULT NULL,
  `variant_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`cart_id`),
  KEY `FKslukcfqhq8c2j5rsfb9jsm57t` (`user_id`),
  KEY `FKeqr1otr7jbitsj0ffeb6edo37` (`variant_id`),
  CONSTRAINT `FKeqr1otr7jbitsj0ffeb6edo37` FOREIGN KEY (`variant_id`) REFERENCES `ecomm_variant` (`variant_id`),
  CONSTRAINT `FKslukcfqhq8c2j5rsfb9jsm57t` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_address_details`
--

DROP TABLE IF EXISTS `ecomm_address_details`;
CREATE TABLE `ecomm_address_details` (
  `address_details_id` bigint NOT NULL AUTO_INCREMENT,
  `address_type` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `door_no` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `land_mark` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `pin_code` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`address_details_id`),
  KEY `FKq6gx3hkeb2xy4bt5xldl4jht8` (`user_id`),
  CONSTRAINT `FKq6gx3hkeb2xy4bt5xldl4jht8` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_order_items`
--

DROP TABLE IF EXISTS `ecomm_order_items`;
CREATE TABLE `ecomm_order_items` (
  `order_items_id` bigint NOT NULL AUTO_INCREMENT,
  `item_coupon_amount` double DEFAULT NULL,
  `item_margin_amount` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `sub_total` double DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `variant_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int DEFAULT '1',
  `status_date` datetime DEFAULT NULL,
  `item_status` bigint DEFAULT NULL,
  `confirmed_on` datetime DEFAULT NULL,
  `delivered_on` datetime DEFAULT NULL,
  `placed_on` datetime DEFAULT NULL,
  `porcessed_on` datetime DEFAULT NULL,
  `reject_reason` varchar(255) DEFAULT NULL,
  `rejected_on` datetime DEFAULT NULL,
  `shipped_on` datetime DEFAULT NULL,
  `courier_company` varchar(255) DEFAULT NULL,
  `tracking_id` varchar(255) DEFAULT NULL,
  `tracking_link` varchar(255) DEFAULT NULL,
  `product_model_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`order_items_id`),
  KEY `FKfje6yeyi09fllaiueiwtik4ug` (`order_id`),
  KEY `FKjv4euuk3561pn01rtyggo46mj` (`variant_id`),
  KEY `FKtgh3pqrjfm0k1kery9jkf9xum` (`item_status`),
  CONSTRAINT `FKfje6yeyi09fllaiueiwtik4ug` FOREIGN KEY (`order_id`) REFERENCES `ecomm_orders` (`order_id`),
  CONSTRAINT `FKjv4euuk3561pn01rtyggo46mj` FOREIGN KEY (`variant_id`) REFERENCES `ecomm_variant` (`variant_id`),
  CONSTRAINT `FKtgh3pqrjfm0k1kery9jkf9xum` FOREIGN KEY (`item_status`) REFERENCES `static_param` (`static_param_id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_order_tracking`
--

DROP TABLE IF EXISTS `ecomm_order_tracking`;
CREATE TABLE `ecomm_order_tracking` (
  `order_tracking_id` bigint NOT NULL AUTO_INCREMENT,
  `order_status` bigint NOT NULL,
  `order_id` bigint NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`order_tracking_id`),
  KEY `FKcl5e1af9k6t2t6boekhqxg4g2` (`order_id`),
  KEY `FK7xyixiftlx7xyf9rg7yu6d81u` (`order_status`),
  CONSTRAINT `FK7xyixiftlx7xyf9rg7yu6d81u` FOREIGN KEY (`order_status`) REFERENCES `static_param` (`static_param_id`),
  CONSTRAINT `FKcl5e1af9k6t2t6boekhqxg4g2` FOREIGN KEY (`order_id`) REFERENCES `ecomm_orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_orders`
--

DROP TABLE IF EXISTS `ecomm_orders`;
CREATE TABLE `ecomm_orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `coupon_amount` double DEFAULT NULL,
  `coupon_applied` int DEFAULT NULL,
  `coupon_code` varchar(255) DEFAULT NULL,
  `coupon_type` varchar(255) DEFAULT NULL,
  `coupon_type_value` double DEFAULT NULL,
  `margin` double DEFAULT NULL,
  `order_no` varchar(255) DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `total_margin` double DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `discount` double DEFAULT NULL,
  `discount_amount` double DEFAULT NULL,
  `tax_amount` double DEFAULT NULL,
  `tax_percentage` double DEFAULT NULL,
  `payment_method` varchar(45) DEFAULT NULL,
  `address_type` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `door_no` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `land_mark` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `pin_code` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `street` varchar(255) DEFAULT NULL,
  `payment_deducted_amount` double DEFAULT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `payment_transaction_id` varchar(255) DEFAULT NULL,
  `tax_type` varchar(45) DEFAULT NULL,
  `pay_method_id` bigint DEFAULT NULL,
  `order_status` int DEFAULT NULL,
  `delivered_on` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKm7r6t85jovc3u9gx741iw552a` (`user_id`),
  CONSTRAINT `FKm7r6t85jovc3u9gx741iw552a` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `ecomm_product`
--

DROP TABLE IF EXISTS `ecomm_product`;
CREATE TABLE `ecomm_product` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `vid` bigint DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  KEY `ecomm_product_static_param_fk` (`category_id`),
  CONSTRAINT `ecomm_product_static_param_fk` FOREIGN KEY (`category_id`) REFERENCES `static_param` (`static_param_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_review`
--

DROP TABLE IF EXISTS `ecomm_review`;
CREATE TABLE `ecomm_review` (
  `review_id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `rating_star` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `variant_id` bigint DEFAULT NULL,
  PRIMARY KEY (`review_id`),
  KEY `FKbniubfyeyctqoowt5v0teerrl` (`user_id`),
  KEY `FK9jius6ume4lq60athupj139a9` (`variant_id`),
  CONSTRAINT `FK9jius6ume4lq60athupj139a9` FOREIGN KEY (`variant_id`) REFERENCES `ecomm_variant` (`variant_id`) ON DELETE CASCADE,
  CONSTRAINT `FKbniubfyeyctqoowt5v0teerrl` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_taxonomy`
--

DROP TABLE IF EXISTS `ecomm_taxonomy`;
CREATE TABLE `ecomm_taxonomy` (
  `taxonomy_id` bigint NOT NULL AUTO_INCREMENT,
  `taxonomy_name` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `taxonomy_term_id` bigint DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `vid` bigint DEFAULT NULL,
  PRIMARY KEY (`taxonomy_id`),
  KEY `ecomm_taxonomy_static_param_fk` (`category_id`),
  KEY `FKds3eeruhpp3lhmype26h3s2b2` (`taxonomy_term_id`),
  CONSTRAINT `ecomm_taxonomy_static_param_fk` FOREIGN KEY (`category_id`) REFERENCES `static_param` (`static_param_id`),
  CONSTRAINT `FKds3eeruhpp3lhmype26h3s2b2` FOREIGN KEY (`taxonomy_term_id`) REFERENCES `ecomm_taxonomy_term` (`term_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_taxonomy_relationship`
--

DROP TABLE IF EXISTS `ecomm_taxonomy_relationship`;
CREATE TABLE `ecomm_taxonomy_relationship` (
  `taxonomy_relationship_id` bigint NOT NULL AUTO_INCREMENT,
  `category_id` bigint DEFAULT NULL,
  `variant_id` bigint DEFAULT NULL,
  `taxonomy_id` bigint NOT NULL,
  `taxonomy_term_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`taxonomy_relationship_id`),
  KEY `FKf1gdbehedyydxu1v0hdndr311` (`variant_id`),
  KEY `FKrpkry7aa3acwi08x838w9a80x` (`taxonomy_term_id`),
  KEY `ecomm_taxonomy_relationship_ecomm_taxonomy_fk` (`taxonomy_id`),
  KEY `FKm8kig5jupodtqss4m1dqkqtfr` (`category_id`),
  CONSTRAINT `ecomm_taxonomy_relationship_ecomm_taxonomy_fk` FOREIGN KEY (`taxonomy_id`) REFERENCES `ecomm_taxonomy` (`taxonomy_id`),
  CONSTRAINT `FKf1gdbehedyydxu1v0hdndr311` FOREIGN KEY (`variant_id`) REFERENCES `ecomm_variant` (`variant_id`),
  CONSTRAINT `FKm8kig5jupodtqss4m1dqkqtfr` FOREIGN KEY (`category_id`) REFERENCES `static_param` (`static_param_id`),
  CONSTRAINT `FKrpkry7aa3acwi08x838w9a80x` FOREIGN KEY (`taxonomy_term_id`) REFERENCES `ecomm_taxonomy_term` (`term_id`)
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `ecomm_taxonomy_term`
--

DROP TABLE IF EXISTS `ecomm_taxonomy_term`;
CREATE TABLE `ecomm_taxonomy_term` (
  `term_id` bigint NOT NULL AUTO_INCREMENT,
  `term_name` varchar(255) DEFAULT NULL,
  `taxonomy_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `image_url` varchar(255) DEFAULT NULL,
  `tid` bigint DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`term_id`),
  KEY `FKq4733eyvdqi9hxswutkkis5sa` (`taxonomy_id`),
  CONSTRAINT `FKq4733eyvdqi9hxswutkkis5sa` FOREIGN KEY (`taxonomy_id`) REFERENCES `ecomm_taxonomy` (`taxonomy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_variant`
--

DROP TABLE IF EXISTS `ecomm_variant`;
CREATE TABLE `ecomm_variant` (
  `variant_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(2555) DEFAULT NULL,
  `variant_name` varchar(255) DEFAULT NULL,
  `variant_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  `price` double DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `discount_amount` double DEFAULT NULL,
  `discount_percentage` double DEFAULT NULL,
  `image1` varchar(255) DEFAULT NULL,
  `image2` varchar(255) DEFAULT NULL,
  `image3` varchar(255) DEFAULT NULL,
  `image4` varchar(255) DEFAULT NULL,
  `image5` varchar(255) DEFAULT NULL,
  `product_url` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  `color_term_id` bigint DEFAULT NULL,
  `size_term_id` bigint DEFAULT NULL,
  `product_type_term_id` bigint DEFAULT NULL,
  PRIMARY KEY (`variant_id`),
  KEY `FKiq3s33makd8g3dvrdvahbgkbw` (`product_id`),
  KEY `FKivp3eq9ym57lehxtb0luurl3h` (`color_term_id`),
  KEY `FKsf4i026vsfmsjh1knthfjlqta` (`size_term_id`),
  KEY `FKt28nn9rjsm3aj5hk72ehar8u2` (`product_type_term_id`),
  CONSTRAINT `FKiq3s33makd8g3dvrdvahbgkbw` FOREIGN KEY (`product_id`) REFERENCES `ecomm_product` (`product_id`),
  CONSTRAINT `FKivp3eq9ym57lehxtb0luurl3h` FOREIGN KEY (`color_term_id`) REFERENCES `ecomm_taxonomy_term` (`term_id`),
  CONSTRAINT `FKsf4i026vsfmsjh1knthfjlqta` FOREIGN KEY (`size_term_id`) REFERENCES `ecomm_taxonomy_term` (`term_id`),
  CONSTRAINT `FKt28nn9rjsm3aj5hk72ehar8u2` FOREIGN KEY (`product_type_term_id`) REFERENCES `ecomm_taxonomy_term` (`term_id`)
) ENGINE=InnoDB AUTO_INCREMENT=139 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `ecomm_variant_spec`
--

DROP TABLE IF EXISTS `ecomm_variant_spec`;
CREATE TABLE `ecomm_variant_spec` (
  `variant_spec_id` bigint NOT NULL AUTO_INCREMENT,
  `spec_value` varchar(255) DEFAULT NULL,
  `spec_key` bigint NOT NULL,
  `product_id` bigint NOT NULL,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`variant_spec_id`),
  KEY `ecomm_variant_desc_static_param_fk` (`spec_key`),
  KEY `FK1w4cj0a8dyufo50o1dxfvjx34_idx` (`product_id`),
  CONSTRAINT `ecomm_variant_desc_static_param_fk` FOREIGN KEY (`spec_key`) REFERENCES `static_param` (`static_param_id`),
  CONSTRAINT `FKmxugwevk9fy6ae2bwm6drmrba` FOREIGN KEY (`product_id`) REFERENCES `ecomm_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=699 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecomm_wish_list`
--

DROP TABLE IF EXISTS `ecomm_wish_list`;
CREATE TABLE `ecomm_wish_list` (
  `ecommerce_wish_list_id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `user_id` bigint DEFAULT NULL,
  `variant_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  PRIMARY KEY (`ecommerce_wish_list_id`),
  KEY `FKivx7hjob487j6lpd6oxlde6j4` (`user_id`),
  KEY `FKq434sxatde5km503edqfcq0dx` (`variant_id`),
  CONSTRAINT `FKivx7hjob487j6lpd6oxlde6j4` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKq434sxatde5km503edqfcq0dx` FOREIGN KEY (`variant_id`) REFERENCES `ecomm_variant` (`variant_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ecommerce_wish_list`
--

DROP TABLE IF EXISTS `ecommerce_wish_list`;
CREATE TABLE `ecommerce_wish_list` (
  `ecommerce_wish_list_id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `accessories_id` bigint DEFAULT NULL,
  `cycle_variant_id` bigint DEFAULT NULL,
  `fitness_equipment_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`ecommerce_wish_list_id`),
  KEY `FKo2nrvyysp631w15wxuk1xn1aa` (`accessories_id`),
  KEY `FKlyy35l37d1mu5sh97skubmlr4` (`fitness_equipment_id`),
  KEY `FKnmvnmj2o783s5ovnbhts2437d` (`user_id`),
  CONSTRAINT `FKlyy35l37d1mu5sh97skubmlr4` FOREIGN KEY (`fitness_equipment_id`) REFERENCES `fitness_equipment` (`fitness_equipment_id`) ON DELETE CASCADE,
  CONSTRAINT `FKnmvnmj2o783s5ovnbhts2437d` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKo2nrvyysp631w15wxuk1xn1aa` FOREIGN KEY (`accessories_id`) REFERENCES `accessories` (`accessories_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `account_holder_name` varchar(255) DEFAULT NULL,
  `account_number` varchar(255) DEFAULT NULL,
  `age_allowed` varchar(255) DEFAULT NULL,
  `charges` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `distance_in_kms` float DEFAULT NULL,
  `eligibility` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `event_name` varchar(255) DEFAULT NULL,
  `gender_allowed` varchar(255) DEFAULT NULL,
  `ifsc_code` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `no_of_members` int DEFAULT NULL,
  `organizer_name` varchar(255) DEFAULT NULL,
  `organizer_number` varchar(255) DEFAULT NULL,
  `payment_method` varchar(255) DEFAULT NULL,
  `privacy` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `reject_reason` varchar(255) DEFAULT NULL,
  `rewards` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `upi_id` varchar(255) DEFAULT NULL,
  `event_type_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgxoo7ftgbsrwr4i27wb9ylu1` (`event_type_id`),
  KEY `FK31rxexkqqbeymnpw4d3bf9vsy` (`user_id`),
  CONSTRAINT `FK31rxexkqqbeymnpw4d3bf9vsy` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKgxoo7ftgbsrwr4i27wb9ylu1` FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`event_type_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `event_count`
--

DROP TABLE IF EXISTS `event_count`;
CREATE TABLE `event_count` (
  `event_id` bigint NOT NULL,
  `joined` bigint DEFAULT NULL,
  PRIMARY KEY (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `event_group`
--

DROP TABLE IF EXISTS `event_group`;
CREATE TABLE `event_group` (
  `event_group_id` bigint NOT NULL AUTO_INCREMENT,
  `status` int NOT NULL DEFAULT '1',
  `id` bigint DEFAULT NULL,
  `user_group_id` bigint NOT NULL,
  PRIMARY KEY (`event_group_id`),
  KEY `FK20l3t5hk00mrsaccg7qyelcvm` (`id`),
  KEY `FK7f31752u441ay0of2t70wtox0` (`user_group_id`),
  CONSTRAINT `FK20l3t5hk00mrsaccg7qyelcvm` FOREIGN KEY (`id`) REFERENCES `event` (`id`) ON DELETE CASCADE,
  CONSTRAINT `FK7f31752u441ay0of2t70wtox0` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`user_group_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `event_point`
--

DROP TABLE IF EXISTS `event_point`;
CREATE TABLE `event_point` (
  `event_user_count` bigint NOT NULL,
  PRIMARY KEY (`event_user_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `event_type`
--

DROP TABLE IF EXISTS `event_type`;
CREATE TABLE `event_type` (
  `event_type_id` bigint NOT NULL AUTO_INCREMENT,
  `event_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_type_id`),
  UNIQUE KEY `UK_bl9qsp0ojx2jcdtoff4jv6uc6` (`event_type_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `event_user`
--

DROP TABLE IF EXISTS `event_user`;
CREATE TABLE `event_user` (
  `event_user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `date_of_joining` datetime DEFAULT NULL,
  `event_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`event_user_id`),
  KEY `FKtc58o1e7bpugjcxuqr8l05l12` (`event_id`),
  KEY `FK45uvxaov8fbham8l63a7jkbyr` (`user_id`),
  CONSTRAINT `FK45uvxaov8fbham8l63a7jkbyr` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKtc58o1e7bpugjcxuqr8l05l12` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `fitness_equipment`
--

DROP TABLE IF EXISTS `fitness_equipment`;
CREATE TABLE `fitness_equipment` (
  `fitness_equipment_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `sku` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `warranty` varchar(255) DEFAULT NULL,
  `website` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`fitness_equipment_id`),
  UNIQUE KEY `UK_lsx91g4x75wolqt8dacoupc37` (`sku`),
  KEY `FK8ie8alo69u6xl5iddrb41i08e` (`category_id`),
  CONSTRAINT `FK8ie8alo69u6xl5iddrb41i08e` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `fitness_equipment_variations`
--

DROP TABLE IF EXISTS `fitness_equipment_variations`;
CREATE TABLE `fitness_equipment_variations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `sku` varchar(255) DEFAULT NULL,
  `price` float NOT NULL,
  `stock` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `variations_title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_eys6ajw09hv1upjk75luffwy5` (`sku`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `leader_board_friends_view`
--

DROP TABLE IF EXISTS `leader_board_friends_view`;
CREATE TABLE `leader_board_friends_view` (
  `avg_distance` float NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`avg_distance`),
  KEY `FKlkcy9cuypm27fhqw00eu4kp0` (`user_id`),
  CONSTRAINT `FKlkcy9cuypm27fhqw00eu4kp0` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `manage_dealer`
--

DROP TABLE IF EXISTS `manage_dealer`;
CREATE TABLE `manage_dealer` (
  `shop_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `address_line1` varchar(255) DEFAULT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `charge` int DEFAULT NULL,
  `dealer_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `land_mark` varchar(255) DEFAULT NULL,
  `phone_number` bigint DEFAULT NULL,
  `pickup_drop` bit(1) DEFAULT NULL,
  `shop_name` varchar(255) DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  `pincode_id` bigint DEFAULT NULL,
  `state_id` bigint DEFAULT NULL,
  PRIMARY KEY (`shop_id`),
  UNIQUE KEY `UK_sha5avucsalqo6xjbjiy3y1sv` (`shop_name`),
  KEY `FKtebcvrlho2y09bx807fyqnl0g` (`city_id`),
  KEY `FKa347w45vdpo9pmsepsv1lo2ux` (`pincode_id`),
  KEY `FKsq2p4srh15m8hqxdsc866loxq` (`state_id`),
  CONSTRAINT `FKa347w45vdpo9pmsepsv1lo2ux` FOREIGN KEY (`pincode_id`) REFERENCES `pincode` (`pincode_id`) ON DELETE CASCADE,
  CONSTRAINT `FKsq2p4srh15m8hqxdsc866loxq` FOREIGN KEY (`state_id`) REFERENCES `state` (`state_id`) ON DELETE CASCADE,
  CONSTRAINT `FKtebcvrlho2y09bx807fyqnl0g` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `my_cycle`
--

DROP TABLE IF EXISTS `my_cycle`;
CREATE TABLE `my_cycle` (
  `my_cycle_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `image` varchar(255) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`my_cycle_id`),
  KEY `FK5avryc9w7ojqig7i3sfycy9sh` (`brand_id`),
  KEY `FKjs4w3fkk4ydd9hvy8gchd45sn` (`user_id`),
  CONSTRAINT `FK5avryc9w7ojqig7i3sfycy9sh` FOREIGN KEY (`brand_id`) REFERENCES `bike_brand` (`brand_id`) ON DELETE CASCADE,
  CONSTRAINT `FKjs4w3fkk4ydd9hvy8gchd45sn` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `my_earn_points`
--

DROP TABLE IF EXISTS `my_earn_points`;
CREATE TABLE `my_earn_points` (
  `earn_point` int NOT NULL,
  PRIMARY KEY (`earn_point`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `my_profile`
--

DROP TABLE IF EXISTS `my_profile`;
CREATE TABLE `my_profile` (
  `distance` float NOT NULL,
  `ride_time` varchar(255) DEFAULT NULL,
  `speed` float DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`distance`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `notification_alert`
--

DROP TABLE IF EXISTS `notification_alert`;
CREATE TABLE `notification_alert` (
  `notification_id` bigint NOT NULL AUTO_INCREMENT,
  `body` varchar(255) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `data` json DEFAULT NULL,
  `msg_read` int DEFAULT '0',
  `module_name` varchar(255) DEFAULT NULL,
  `page` varchar(255) DEFAULT NULL,
  `receiver` int DEFAULT NULL,
  `sender` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `user_type` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`notification_id`),
  KEY `FKaf7nnr8pn7ssh27ij2jqlnmp7` (`user_id`),
  CONSTRAINT `FKaf7nnr8pn7ssh27ij2jqlnmp7` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items` (
  `order_items_id` bigint NOT NULL AUTO_INCREMENT,
  `approve_status` int NOT NULL DEFAULT '1',
  `item_coupon_amount` double DEFAULT NULL,
  `item_margin_amount` double DEFAULT NULL,
  `order_status` int NOT NULL DEFAULT '1',
  `price` double DEFAULT NULL,
  `product_code` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `sub_total` double DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `order_no` bigint DEFAULT NULL,
  PRIMARY KEY (`order_items_id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKnyudaq78s2hecm5bqxa7kf7nu` (`order_no`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE,
  CONSTRAINT `FKnyudaq78s2hecm5bqxa7kf7nu` FOREIGN KEY (`order_no`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `order_point_count`
--

DROP TABLE IF EXISTS `order_point_count`;

CREATE TABLE `order_point_count` (
  `order_user_count` bigint NOT NULL,
  PRIMARY KEY (`order_user_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `coupon_amount` double DEFAULT NULL,
  `coupon_applied` int DEFAULT NULL,
  `coupon_code` varchar(255) DEFAULT NULL,
  `coupon_type` varchar(255) DEFAULT NULL,
  `coupon_type_value` double DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `margin` double DEFAULT NULL,
  `order_no` varchar(255) DEFAULT NULL,
  `order_status` int NOT NULL DEFAULT '1',
  `payment_method` int DEFAULT NULL,
  `total_amount` double DEFAULT NULL,
  `total_margin` double DEFAULT NULL,
  `accessories_id` bigint DEFAULT NULL,
  `cycle_variant_id` bigint DEFAULT NULL,
  `fitness_equipment_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `customer_id` varchar(255) DEFAULT NULL,
  `date_ordered` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FKdj11ag7ja9twoi8lcg7cqw96j` (`accessories_id`),
  KEY `FKb45xaio6s1wifua1vfkshhb8p` (`fitness_equipment_id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKb45xaio6s1wifua1vfkshhb8p` FOREIGN KEY (`fitness_equipment_id`) REFERENCES `fitness_equipment` (`fitness_equipment_id`) ON DELETE CASCADE,
  CONSTRAINT `FKdj11ag7ja9twoi8lcg7cqw96j` FOREIGN KEY (`accessories_id`) REFERENCES `accessories` (`accessories_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `pincode`
--

DROP TABLE IF EXISTS `pincode`;
CREATE TABLE `pincode` (
  `pincode_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  PRIMARY KEY (`pincode_id`),
  KEY `FKinj0k01xb3inhipwng0sagpnx` (`city_id`),
  CONSTRAINT `FKinj0k01xb3inhipwng0sagpnx` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `point_earn_history`
--

DROP TABLE IF EXISTS `point_earn_history`;
CREATE TABLE `point_earn_history` (
  `point_earn_history_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `earn_date` date NOT NULL,
  `module` varchar(100) NOT NULL,
  `moduleSlug` varchar(100) NOT NULL,
  `earn_point` int NOT NULL,
  `updated_on` datetime NOT NULL,
  PRIMARY KEY (`point_earn_history_id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `points_configuration`
--

DROP TABLE IF EXISTS `points_configuration`;
CREATE TABLE `points_configuration` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nick_name` varchar(255) DEFAULT NULL,
  `range_from` float DEFAULT NULL,
  `range_to` float DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `points_earned`
--

DROP TABLE IF EXISTS `points_earned`;
CREATE TABLE `points_earned` (
  `points_earned_id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `earned_description` varchar(255) DEFAULT NULL,
  `earned_points` int DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `range_from_points` int DEFAULT NULL,
  `range_to_points` int DEFAULT NULL,
  `user_id` bigint NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`points_earned_id`),
  KEY `FKma8nnym6ckrgqxh9cxce2y325` (`user_id`),
  CONSTRAINT `FKma8nnym6ckrgqxh9cxce2y325` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `points_earned_history`
--

DROP TABLE IF EXISTS `points_earned_history`;
CREATE TABLE `points_earned_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `earn_date` datetime DEFAULT NULL,
  `earn_point` int DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `module_slug` varchar(255) DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK64pkyb6qs7b1ov2f7xac86j2l` (`user_id`),
  CONSTRAINT `FK64pkyb6qs7b1ov2f7xac86j2l` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=313 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `referal_and_earn`
--

DROP TABLE IF EXISTS `referal_and_earn`;
CREATE TABLE `referal_and_earn` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `code` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbofgt6dm0o040yr1glsw2pwvn` (`user_id`),
  CONSTRAINT `FKbofgt6dm0o040yr1glsw2pwvn` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `referal_code`
--

DROP TABLE IF EXISTS `referal_code`;
CREATE TABLE `referal_code` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `code` varchar(255) DEFAULT NULL,
  `points` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkf0fkendy51if5mqepl30wev` (`user_id`),
  CONSTRAINT `FKkf0fkendy51if5mqepl30wev` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `register_bike`
--

DROP TABLE IF EXISTS `register_bike`;
CREATE TABLE `register_bike` (
  `register_bike_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `address` varchar(255) DEFAULT NULL,
  `age` varchar(255) DEFAULT NULL,
  `contact_number` varchar(255) DEFAULT NULL,
  `customer_name` varchar(255) DEFAULT NULL,
  `date_of_purchase` datetime DEFAULT NULL,
  `e_mail` varchar(255) DEFAULT NULL,
  `frame_serial_no_image` varchar(255) DEFAULT NULL,
  `frame_serial_number` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `invoice_image` varchar(255) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `model_id` bigint DEFAULT NULL,
  `city_id` bigint DEFAULT NULL,
  `pincode_id` bigint DEFAULT NULL,
  `state_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `pincode` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`register_bike_id`),
  KEY `FKhtd4qm3ihxlrt22rkxrc5oa9g` (`brand_id`),
  KEY `FK7qla4e27ailqmrvb1fkcip0ob` (`model_id`),
  KEY `FKrn5kr9i4cj5d0xlgx1f0q1b4v` (`city_id`),
  KEY `FKas42xm2jd6iuyk5x9yghsgweo` (`pincode_id`),
  KEY `FK6mnhnttdci2v65qvioh2np0uv` (`state_id`),
  KEY `FKpg2434dd4p1dg8qoxfeelrapo` (`user_id`),
  CONSTRAINT `FK6mnhnttdci2v65qvioh2np0uv` FOREIGN KEY (`state_id`) REFERENCES `state` (`state_id`) ON DELETE CASCADE,
  CONSTRAINT `FK7qla4e27ailqmrvb1fkcip0ob` FOREIGN KEY (`model_id`) REFERENCES `bike_model` (`model_id`) ON DELETE CASCADE,
  CONSTRAINT `FKas42xm2jd6iuyk5x9yghsgweo` FOREIGN KEY (`pincode_id`) REFERENCES `pincode` (`pincode_id`) ON DELETE CASCADE,
  CONSTRAINT `FKhtd4qm3ihxlrt22rkxrc5oa9g` FOREIGN KEY (`brand_id`) REFERENCES `bike_brand` (`brand_id`) ON DELETE CASCADE,
  CONSTRAINT `FKpg2434dd4p1dg8qoxfeelrapo` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKrn5kr9i4cj5d0xlgx1f0q1b4v` FOREIGN KEY (`city_id`) REFERENCES `city` (`city_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `ride_activity`
--

DROP TABLE IF EXISTS `ride_activity`;
CREATE TABLE `ride_activity` (
  `ride_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `avg_distance` float DEFAULT NULL,
  `avg_speed` float DEFAULT NULL,
  `avg_time` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `destination_lat` varchar(255) DEFAULT NULL,
  `destination_long` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `privacy` varchar(255) DEFAULT NULL,
  `rate` varchar(255) DEFAULT NULL,
  `source_lat` varchar(255) DEFAULT NULL,
  `source_long` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`ride_id`),
  KEY `FKfw08wenny0jtu375t1rcsmmny` (`user_id`),
  CONSTRAINT `FKfw08wenny0jtu375t1rcsmmny` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `ride_activity_comments`
--

DROP TABLE IF EXISTS `ride_activity_comments`;
CREATE TABLE `ride_activity_comments` (
  `ride_activity_comments_id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `is_like` bit(1) DEFAULT NULL,
  `ride_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `created_by` bigint DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  PRIMARY KEY (`ride_activity_comments_id`),
  KEY `FKefxvhqst8lgdro9m189vwdfct` (`ride_id`),
  KEY `FKhnuohgxhqjyj7c1hnnqigws2t` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ride_activity_points`
--

DROP TABLE IF EXISTS `ride_activity_points`;
CREATE TABLE `ride_activity_points` (
  `average_distance` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`average_distance`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `ride_type`
--

DROP TABLE IF EXISTS `ride_type`;
CREATE TABLE `ride_type` (
  `ride_type_id` bigint NOT NULL AUTO_INCREMENT,
  `ride_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ride_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `role_id` bigint NOT NULL,
  `role_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `state`
--

DROP TABLE IF EXISTS `state`;
CREATE TABLE `state` (
  `state_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`state_id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `static_param`
--

DROP TABLE IF EXISTS `static_param`;
CREATE TABLE `static_param` (
  `static_param_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `value` varchar(255) NOT NULL,
  `key` varchar(100) NOT NULL,
  `display_order` bigint DEFAULT NULL,
  PRIMARY KEY (`static_param_id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb3;


--
-- Table structure for table `stock`
--

DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (
  `stock_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `quantity` int DEFAULT NULL,
  `bike_rental_product_id` bigint DEFAULT NULL,
  `store_id` bigint NOT NULL,
  PRIMARY KEY (`stock_id`),
  KEY `FKoe129eaqt98a2ylpsprh8wscd` (`bike_rental_product_id`),
  KEY `FK74x0dn5v5koqj4fthonqt1m27` (`store_id`),
  CONSTRAINT `FK74x0dn5v5koqj4fthonqt1m27` FOREIGN KEY (`store_id`) REFERENCES `store_detail` (`store_id`) ON DELETE CASCADE,
  CONSTRAINT `FKoe129eaqt98a2ylpsprh8wscd` FOREIGN KEY (`bike_rental_product_id`) REFERENCES `bike_rental_management` (`bike_rental_product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `stock_view`
--

DROP TABLE IF EXISTS `stock_view`;
CREATE TABLE `stock_view` (
  `available_quantity` int NOT NULL,
  `booked_quantity` int DEFAULT NULL,
  `stock_id` bigint DEFAULT NULL,
  PRIMARY KEY (`available_quantity`),
  KEY `FKeabunjj1sqk30qfskm4cji86o` (`stock_id`),
  CONSTRAINT `FKeabunjj1sqk30qfskm4cji86o` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `store_detail`
--

DROP TABLE IF EXISTS `store_detail`;
CREATE TABLE `store_detail` (
  `store_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `address` varchar(255) DEFAULT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `source_lat` varchar(255) DEFAULT NULL,
  `source_long` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`store_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `suggested_for_you`
--

DROP TABLE IF EXISTS `suggested_for_you`;
CREATE TABLE `suggested_for_you` (
  `who` bigint NOT NULL,
  `whom` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`who`),
  KEY `FKqvwo5rc2yp4jd1ceveaabw0qt` (`user_id`),
  CONSTRAINT `FKqvwo5rc2yp4jd1ceveaabw0qt` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `taxonomy`
--

DROP TABLE IF EXISTS `taxonomy`;
CREATE TABLE `taxonomy` (
  `taxonomy_id` bigint NOT NULL AUTO_INCREMENT,
  `taxonomy_name` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`taxonomy_id`),
  KEY `FKnhrgpp8q5jw9ss527yagyfpfp` (`category_id`),
  CONSTRAINT `FKnhrgpp8q5jw9ss527yagyfpfp` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `taxonomy_relationship`
--

DROP TABLE IF EXISTS `taxonomy_relationship`;
CREATE TABLE `taxonomy_relationship` (
  `taxonomy_relationship_id` bigint NOT NULL AUTO_INCREMENT,
  `accessories_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  `cycle_id` bigint DEFAULT NULL,
  `fitness_equipment_id` bigint DEFAULT NULL,
  PRIMARY KEY (`taxonomy_relationship_id`),
  KEY `FKl3i7aogs5jafict0m1lsv8deo` (`accessories_id`),
  KEY `FKkskggmnruqpmxbh7hpwqc7hkb` (`category_id`),
  KEY `FKgnb7kyhm6kshclttx97hv3oyn` (`cycle_id`),
  KEY `FKt5wmct7yfd50ow9hmubpoqe6f` (`fitness_equipment_id`),
  CONSTRAINT `FKgnb7kyhm6kshclttx97hv3oyn` FOREIGN KEY (`cycle_id`) REFERENCES `cycle` (`cycle_id`) ON DELETE CASCADE,
  CONSTRAINT `FKkskggmnruqpmxbh7hpwqc7hkb` FOREIGN KEY (`category_id`) REFERENCES `category` (`category_id`) ON DELETE CASCADE,
  CONSTRAINT `FKl3i7aogs5jafict0m1lsv8deo` FOREIGN KEY (`accessories_id`) REFERENCES `accessories` (`accessories_id`) ON DELETE CASCADE,
  CONSTRAINT `FKt5wmct7yfd50ow9hmubpoqe6f` FOREIGN KEY (`fitness_equipment_id`) REFERENCES `fitness_equipment` (`fitness_equipment_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `taxonomy_term`
--

DROP TABLE IF EXISTS `taxonomy_term`;
CREATE TABLE `taxonomy_term` (
  `term_id` bigint NOT NULL AUTO_INCREMENT,
  `term_name` varchar(255) DEFAULT NULL,
  `texonomy_id` bigint DEFAULT NULL,
  PRIMARY KEY (`term_id`),
  KEY `FKs43aq8hbb5jq2i0fv71dsh9br` (`texonomy_id`),
  CONSTRAINT `FKs43aq8hbb5jq2i0fv71dsh9br` FOREIGN KEY (`texonomy_id`) REFERENCES `taxonomy` (`taxonomy_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `user_follow`
--

DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_on` datetime DEFAULT NULL,
  `following_user_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs7ai258xpfs5bhoh7cug8kiwv` (`following_user_id`),
  KEY `FKpt785jr8exenf2qqlsx709gcg` (`user_id`),
  CONSTRAINT `FKpt785jr8exenf2qqlsx709gcg` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `FKs7ai258xpfs5bhoh7cug8kiwv` FOREIGN KEY (`following_user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=230 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group` (
  `user_group_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `description` varchar(255) DEFAULT NULL,
  `facebook_url` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_group_id`),
  KEY `FK7k9ade3lqbo483u9vuryxmm34` (`user_id`),
  CONSTRAINT `FK7k9ade3lqbo483u9vuryxmm34` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `user_group_member`
--

DROP TABLE IF EXISTS `user_group_member`;
CREATE TABLE `user_group_member` (
  `group_member_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `user_id` bigint NOT NULL,
  `user_group_id` bigint NOT NULL,
  PRIMARY KEY (`group_member_id`),
  KEY `FKjbhg45atfwht2ji7xu241m4qp` (`user_id`),
  KEY `FKdx0s19tepv2djy8v9n6citn1s` (`user_group_id`),
  CONSTRAINT `FKdx0s19tepv2djy8v9n6citn1s` FOREIGN KEY (`user_group_id`) REFERENCES `user_group` (`user_group_id`) ON DELETE CASCADE,
  CONSTRAINT `FKjbhg45atfwht2ji7xu241m4qp` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `user_preference`
--

DROP TABLE IF EXISTS `user_preference`;

CREATE TABLE `user_preference` (
  `user_prference_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `data` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`user_prference_id`),
  KEY `FK7mgxw6j2m7uvuk3svr9vsar8p` (`user_id`),
  CONSTRAINT `FK7mgxw6j2m7uvuk3svr9vsar8p` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



--
-- Table structure for table `user_summary_count_view`
--

DROP TABLE IF EXISTS `user_summary_count_view`;
CREATE TABLE `user_summary_count_view` (
  `challenge_count` bigint DEFAULT NULL,
  `content_count` bigint DEFAULT NULL,
  `event_count` bigint DEFAULT NULL,
  `followers` bigint DEFAULT NULL,
  `followings` bigint DEFAULT NULL,
  `order_count` bigint DEFAULT NULL,
  `rental_count` bigint DEFAULT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FKglh5igguu68iu5038x4rbqkit` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `bio` varchar(255) DEFAULT NULL,
  `birth_date` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `height` float DEFAULT NULL,
  `height_unit` varchar(255) DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `photo_url` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) NOT NULL,
  `weight` float DEFAULT NULL,
  `weight_unit` varchar(255) DEFAULT NULL,
  `role_id` bigint NOT NULL,
  `emergency_contact1` varchar(255) DEFAULT NULL,
  `emergency_contact2` varchar(255) DEFAULT NULL,
  `fcn_token` varchar(255) DEFAULT NULL,
  `fcmtoken` varchar(255) DEFAULT NULL,
  `calories` float DEFAULT NULL,
  `calories_unit` varchar(255) DEFAULT NULL,
  `steps` varchar(255) DEFAULT NULL,
  `heart_beat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_k8d0f2n7n88w1a16yhua64onx` (`user_name`),
  UNIQUE KEY `UKlvscb2d1b3ak05udeo7oxfyl5` (`user_name`,`email`,`mobile`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_63cf888pmqtt5tipcne79xsbm` (`mobile`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=191 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


--
-- Table structure for table `wish_list`
--

DROP TABLE IF EXISTS `wish_list`;

CREATE TABLE `wish_list` (
  `wish_list_id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` bigint DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_by` bigint DEFAULT NULL,
  `updated_on` datetime DEFAULT NULL,
  `status` int NOT NULL DEFAULT '1',
  `bike_rental_product_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`wish_list_id`),
  KEY `FKanjh7kxdgicgbehsvenxxhvc9` (`bike_rental_product_id`),
  KEY `FKit8ap20bpapw291y78egje6f3` (`user_id`),
  CONSTRAINT `FKanjh7kxdgicgbehsvenxxhvc9` FOREIGN KEY (`bike_rental_product_id`) REFERENCES `bike_rental_management` (`bike_rental_product_id`) ON DELETE CASCADE,
  CONSTRAINT `FKit8ap20bpapw291y78egje6f3` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

