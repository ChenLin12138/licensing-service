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

INSERT INTO `default`.licenses
(license_id, organization_id, product_name, license_type, license_max, license_allocated, comment)
VALUES('320db380-e373-4a65-9f25-c83115d76f1e', '35690f33-71b1-4996-83f8-93b8ca411848', 'VIVOX21', 'Phone', 12, 1, 'comment example');
