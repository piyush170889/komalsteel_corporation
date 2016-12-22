package co.in.replete.komalindustries.webcontroller.beans;

public class CategoryMasterTO {

	private int id;
	
	private String name;
	
	private int parentId;
	
	private String url;
	
	private String catDesc;
	
	private String parantNm;
	
	public CategoryMasterTO(){}

	public String getParantNm() {
		return parantNm;
	}

	public void setParantNm(String parantNm) {
		this.parantNm = parantNm;
	}

	public String getCatDesc() {
		return catDesc;
	}

	public void setCatDesc(String catDesc) {
		this.catDesc = catDesc;
	}

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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
