package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the master_data database table.
 * 
 */
@Entity
@Table(name="master_data")
@NamedQuery(name="MasterData.findAll", query="SELECT m FROM MasterData m")
public class MasterData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4761497897745471388L;

	@Id
	@Column(name="MASTER_DATA_ID")
	private int masterDataId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	private String description;

	@Column(name="GROUP_DESC")
	private String groupDesc;

	@Column(name="IS_ACTIVE")
	private String isActive;

	@Column(name="MASTER_CODE")
	private String masterCode;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	@Column(name="SUB_GROUP_DESC")
	private String subGroupDesc;

	public MasterData() {
	}

	public int getMasterDataId() {
		return this.masterDataId;
	}

	public void setMasterDataId(int masterDataId) {
		this.masterDataId = masterDataId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTs() {
		return this.createdTs;
	}

	public void setCreatedTs(Timestamp createdTs) {
		this.createdTs = createdTs;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getMasterCode() {
		return this.masterCode;
	}

	public void setMasterCode(String masterCode) {
		this.masterCode = masterCode;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Timestamp getModifiedTs() {
		return this.modifiedTs;
	}

	public void setModifiedTs(Timestamp modifiedTs) {
		this.modifiedTs = modifiedTs;
	}

	public String getSubGroupDesc() {
		return this.subGroupDesc;
	}

	public void setSubGroupDesc(String subGroupDesc) {
		this.subGroupDesc = subGroupDesc;
	}

}