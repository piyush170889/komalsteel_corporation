package co.in.replete.komalindustries.beans.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The persistent class for the  configuration database table.
 * 
 */
@Entity
@Table(name="configuration")
public class AppConfiguration implements Serializable {
	 
	    /**
	 * 
	 */
	private static final long serialVersionUID = -7998707340213408502L;

		@Id
	    @Column(name="CONFIG_NAME")
		private String configName;
	   
		@NotNull(message="error.servicetax.required")
		@NotEmpty(message="error.servicetax.required")
		@Column(name="CONFIG_VAL")
		private String configVal;

		@Column(name="CONFIG_DESC")
		private String configDesc;

		@Column(name="CRAETED_TS")
		private Date createdTs;

		@Column(name="MODIFIED_TS")
		private Date modifiedTs;
   
		public AppConfiguration(){}

		public String getConfigName() {
			return configName;
		}

		public void setConfigName(String configName) {
			this.configName = configName;
		}

		public String getConfigVal() {
			return configVal;
		}

		public void setConfigVal(String configVal) {
			this.configVal = configVal;
		}

		public String getConfigDesc() {
			return configDesc;
		}

		public void setConfigDesc(String configDesc) {
			this.configDesc = configDesc;
		}

		public Date getCreatedTs() {
			return createdTs;
		}

		public void setCreatedTs(Date createdTs) {
			this.createdTs = createdTs;
		}

		public Date getModifiedTs() {
			return modifiedTs;
		}

		public void setModifiedTs(Date modifiedTs) {
			this.modifiedTs = modifiedTs;
		}

		
		
}
