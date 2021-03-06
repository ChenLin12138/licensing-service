package com.chenlin.licenseservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

//告诉Spring这是一个JPA类
@Entity
//model到表的映射
@Table(name = "licenses")
public class License {
	//Id表示该字段为主键
	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
//	@GenericGenerator(name = "uuid", strategy = "uuid2")
	//将该字段映射到特定的数据库表中的列
	@Column(name = "license_id", nullable = false)
	private String licenseId;

	@Column(name = "organization_id", nullable = false)
	private String organizationId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "license_type", nullable = false)
	private String licenseType;

	@Column(name = "license_max", nullable = false)
	private Integer licenseMax;

	@Column(name = "license_allocated", nullable = false)
	private Integer licenseAllocated;

	@Column(name = "comment")
	private String comment;
	
	@Transient
	private String organizationName;

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(String licenseType) {
		this.licenseType = licenseType;
	}

	public Integer getLicenseMax() {
		return licenseMax;
	}

	public void setLicenseMax(Integer licenseMax) {
		this.licenseMax = licenseMax;
	}

	public Integer getLicenseAllocated() {
		return licenseAllocated;
	}

	public void setLicenseAllocated(Integer licenseAllocated) {
		this.licenseAllocated = licenseAllocated;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public License withId(String id) {
		this.setLicenseId(id);
		return this;
	}

	public License withOrganizationId(String organizationId) {
		this.setOrganizationId(organizationId);
		return this;
	}

	public License withProductName(String productName) {
		this.setProductName(productName);
		return this;
	}

	public License withLicenseType(String licenseType) {
		this.setLicenseType(licenseType);
		return this;
	}

	public License withLicenseMax(Integer licenseMax) {
		this.setLicenseMax(licenseMax);
		return this;
	}

	public License withLicenseAllocated(Integer licenseAllocated) {
		this.setLicenseAllocated(licenseAllocated);
		return this;
	}

	public License withComment(String comment) {
		this.setComment(comment);
		return this;
	}
	
	public License withOrganizationName(String organizationName) {
		this.setOrganizationName(organizationName);
		return this;
	}
}
