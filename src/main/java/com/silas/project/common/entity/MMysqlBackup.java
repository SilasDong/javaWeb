package com.silas.project.common.entity;

import java.io.Serializable;

import java.util.Date;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2018-09-14
 */
@TableName("m_mysql_backup")
public class MMysqlBackup extends Model<MMysqlBackup> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.UUID)
	private String id;
    /**
     * 文件大小
     */
	private Long size;
    /**
     * 文件名称
     */
	private String fileName;
    /**
     * 创建时间
     */
	private Date createTime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "MMysqlBackup{" +
			", id=" + id +
			", size=" + size +
			", fileName=" + fileName +
			", createTime=" + createTime +
			"}";
	}
}
