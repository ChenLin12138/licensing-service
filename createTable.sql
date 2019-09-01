CREATE TABLE `licenses` (
  `license_id` char(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `organization_id` char(36) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `product_name` char(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `license_type` char(10) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `license_max` int(11) NOT NULL,
  `license_allocated` int(11) NOT NULL,
  `comment` char(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`license_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='licenses';