CREATE  VIEW `badge_view` AS select 1 AS `total_badges`;

CREATE  VIEW `bike_rental_booked_qty_view` AS select `bike_booking`.`store_id` AS `store_id`,`bike_booking`.`bike_rental_product_id` AS `bike_rental_product_id`,sum(`bike_booking`.`quantity`) AS `booked_quantity` from `bike_booking` where (`bike_booking`.`status` in (0,1)) group by `bike_booking`.`store_id`,`bike_booking`.`bike_rental_product_id`;

CREATE  VIEW `challenge_member_count` AS select 1 AS `challenge_id`,1 AS `joined`;

CREATE  VIEW `event_member_count` AS select 1 AS `event_id`,1 AS `joined`;

CREATE  VIEW `leader_board_view` AS select 1 AS `user_id`,1 AS `created_by`,1 AS `created_on`,1 AS `updated_by`,1 AS `updated_on`,1 AS `status`,1 AS `bio`,1 AS `birth_date`,1 AS `email`,1 AS `first_name`,1 AS `gender`,1 AS `height`,1 AS `height_unit`,1 AS `last_login`,1 AS `last_name`,1 AS `mobile`,1 AS `password`,1 AS `photo_url`,1 AS `token`,1 AS `user_name`,1 AS `weight`,1 AS `weight_unit`,1 AS `role_id`,1 AS `avg_distance`;

CREATE  VIEW `my_challeges` AS select `c`.`challenge_id` AS `challenge_id`,`c`.`created_by` AS `created_by`,`c`.`created_on` AS `created_on`,`c`.`updated_by` AS `updated_by`,`c`.`updated_on` AS `updated_on`,`c`.`status` AS `status`,`c`.`age_allowed` AS `age_allowed`,`c`.`description` AS `description`,`c`.`duration` AS `duration`,`c`.`from_date` AS `from_date`,`c`.`gender_allowed` AS `gender_allowed`,`c`.`image` AS `image`,`c`.`name` AS `name`,`c`.`to_date` AS `to_date`,`c`.`total_kilometers` AS `total_kilometers`,`c`.`total_members` AS `total_members`,`c`.`ride_type_id` AS `ride_type_id`,`c`.`user_id` AS `user_id` from (`overall_challenge` `o` left join `challenge` `c` on((`o`.`challenge_id` = `c`.`challenge_id`))) group by `o`.`challenge_id`,`o`.`user_id` order by `o`.`challenge_id` desc;

CREATE  VIEW `my_group` AS select `g`.`user_group_id` AS `user_group_id`,`g`.`created_by` AS `created_by`,`g`.`created_on` AS `created_on`,`g`.`updated_by` AS `updated_by`,`g`.`updated_on` AS `updated_on`,`g`.`status` AS `status`,`g`.`description` AS `description`,`g`.`facebook_url` AS `facebook_url`,`g`.`group_name` AS `group_name`,`g`.`image` AS `image`,`g`.`type` AS `type`,`g`.`user_id` AS `user_id` from (`overall_user_group` `o` left join `user_group` `g` on((`o`.`user_group_id` = `g`.`user_group_id`))) group by `o`.`user_group_id`,`o`.`user_id` order by `o`.`user_group_id` desc;

CREATE  VIEW `overall_challenge` AS select `c`.`challenge_id` AS `challenge_id`,`c`.`user_id` AS `user_id` from `challenge` `c` union select `cum`.`challenge_id` AS `challenge_id`,`cum`.`user_id` AS `user_id` from `challenge_user` `cum`;

CREATE  VIEW `overall_user_group` AS select `u`.`user_group_id` AS `user_group_id`,`u`.`user_id` AS `user_id` from `user_group` `u` union select `m`.`user_group_id` AS `user_group_id`,`m`.`user_id` AS `user_id` from `user_group_member` `m`;

CREATE  VIEW `point_overall_earn_user_view` AS select `h`.`user_id` AS `user_id`,sum(`h`.`earn_point`) AS `earn_point`,(select `points_configuration`.`id` from `points_configuration` where (((`points_configuration`.`range_from` >= `h`.`earn_point`) and (`points_configuration`.`range_to` <= `h`.`earn_point`)) or ((`points_configuration`.`range_from` <= `h`.`earn_point`) and (`points_configuration`.`range_to` >= `h`.`earn_point`))) limit 1) AS `points_configuration_id` from `point_earn_history` `h` group by `h`.`user_id`;

CREATE  VIEW `ride_acitivity_count_view` AS select `l`.`ride_id` AS `ride_id`,(select count(`ride_activity_comments`.`is_like`) from `ride_activity_comments` where ((`ride_activity_comments`.`is_like` = 1) and (`ride_activity_comments`.`ride_id` = `l`.`ride_id`)) group by `ride_activity_comments`.`ride_id`) AS `like_count`,(select count(`ride_activity_comments`.`comment`) from `ride_activity_comments` where ((`ride_activity_comments`.`comment` is not null) and (`ride_activity_comments`.`ride_id` = `l`.`ride_id`)) group by `ride_activity_comments`.`ride_id`) AS `comment_count` from `ride_activity_comments` `l` group by `l`.`ride_id`;

CREATE  VIEW `this_week_avg` AS select 1 AS `distance`,1 AS `speed`,1 AS `ride_time`,1 AS `user_id`;

CREATE  VIEW `user_challenge_count_view` AS select `challenge`.`user_id` AS `user_id`,count(`challenge`.`challenge_id`) AS `challenge_count` from `challenge` group by `challenge`.`user_id`;

CREATE  VIEW `user_content_count_view` AS select `content`.`user_id` AS `user_id`,count(`content`.`content_id`) AS `content_count` from `content` group by `content`.`user_id`;

CREATE  VIEW `user_event_count_view` AS select `event`.`user_id` AS `user_id`,count(`event`.`id`) AS `event_count` from `event` group by `event`.`user_id`;

CREATE  VIEW `user_follow_following_count` AS select 1 AS `user_id`,1 AS `followers`,1 AS `followings`;

CREATE  VIEW `user_followers_count` AS select `user_follow`.`following_user_id` AS `user_id`,count(`user_follow`.`user_id`) AS `followers` from `user_follow` group by `user_follow`.`following_user_id`;

CREATE  VIEW `user_followings_count` AS select `user_follow`.`user_id` AS `user_id`,count(`user_follow`.`following_user_id`) AS `followings` from `user_follow` group by `user_follow`.`user_id`;

CREATE  VIEW `user_rental_count_view` AS select `bike_service`.`user_id` AS `user_id`,count(`bike_service`.`service_id`) AS `rental_count` from `bike_service` group by `bike_service`.`user_id`;
