package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_group_master_dtls database table.
 * 
 */
@Entity
@Table(name="user_group_master_dtls")
@NamedQuery(name="UserGroupMasterDtl.findAll", query="SELECT u FROM UserGroupMasterDtl u")
public class UserGroupMasterDtl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1515592633888749872L;

	@Id
	@Column(name="USER_GROUP_MASTER_DTLS_ID")
	private int userGroupMasterDtlsId;

	@Column(name="CMPNY_INFO_ID")
	private String cmpnyInfoId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_TS")
	private Timestamp createdTs;

	@Column(name="GROUP_DTLS_ID")
	private int groupDtlsId;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Column(name="MODIFIED_TS")
	private Timestamp modifiedTs;

	@Column(name="TRACK_ID")
	private String trackId;

	public UserGroupMasterDtl() {
	}

	public int getUserGroupMasterDtlsId() {
		return this.userGroupMasterDtlsId;
	}

	public void setUserGroupMasterDtlsId(int userGroupMasterDtlsId) {
		this.userGroupMasterDtlsId = userGroupMasterDtlsId;
	}

	public String getCmpnyInfoId() {
		return this.cmpnyInfoId;
	}

	public void setCmpnyInfoId(String cmpnyInfoId) {
		this.cmpnyInfoId = cmpnyInfoId;
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

	public int getGroupDtlsId() {
		return this.groupDtlsId;
	}

	public void setGroupDtlsId(int groupDtlsId) {
		this.groupDtlsId = groupDtlsId;
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

	public String getTrackId() {
		return this.trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

}