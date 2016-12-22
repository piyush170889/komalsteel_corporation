package co.in.replete.komalindustries.beans.entity;

public class CategoryMaster {

	private int id;
	
	private String name;
	
	private String catDesc;
	
	private int parantId;
	
	private String url;
	
	public CategoryMaster() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

	public int getParantId() {
		return parantId;
	}

	public void setParantId(int parantId) {
		this.parantId = parantId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
