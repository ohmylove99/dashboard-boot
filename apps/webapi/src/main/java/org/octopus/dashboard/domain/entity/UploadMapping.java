package org.octopus.dashboard.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the db_task database table.
 * 
 */
@Entity
@Table(name = "db_upload_mapping")
public class UploadMapping implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	@Column(name = "table_name")
	private String tableName;

	@Column(name = "table_id")
	private Long tableId;

	private Upload upload;

	public UploadMapping() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "upload_id")
	public Upload getUpload() {
		return this.upload;
	}

	public void setUpload(Upload upload) {
		this.upload = upload;
	}

}