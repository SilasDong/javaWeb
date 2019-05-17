package com.silas.project.main.config;

/**
 * @author silas
 */
public class ProjectConfigParams {
	private Integer saveMysqlDays;
	private Boolean mysqlBackup;
	/**
	 * 网站名称
	 */
	private String siteName;
	/**
	 * 网站域名
	 */
	private String siteUrl;

	private String allowedOrigin;
	private String allowedMethod;
	private String allowedHeader;
	private Boolean allowedCredentials;

	public Integer getSaveMysqlDays() {
		return saveMysqlDays;
	}

	public void setSaveMysqlDays(Integer saveMysqlDays) {
		this.saveMysqlDays = saveMysqlDays;
	}

	public Boolean getMysqlBackup() {
		return mysqlBackup;
	}

	public void setMysqlBackup(Boolean mysqlBackup) {
		this.mysqlBackup = mysqlBackup;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getAllowedOrigin() {
		return allowedOrigin;
	}

	public void setAllowedOrigin(String allowedOrigin) {
		this.allowedOrigin = allowedOrigin;
	}

	public String getAllowedMethod() {
		return allowedMethod;
	}

	public void setAllowedMethod(String allowedMethod) {
		this.allowedMethod = allowedMethod;
	}

	public String getAllowedHeader() {
		return allowedHeader;
	}

	public void setAllowedHeader(String allowedHeader) {
		this.allowedHeader = allowedHeader;
	}

	public Boolean getAllowedCredentials() {
		return allowedCredentials;
	}

	public void setAllowedCredentials(Boolean allowedCredentials) {
		this.allowedCredentials = allowedCredentials;
	}

	public String getSiteUrl() {
		return siteUrl;
	}

	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
}
