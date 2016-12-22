package co.in.replete.komalindustries.beans;

import java.util.List;


public class ItemDetailsResponse extends BaseWrapper{
	
	private List<ItemDetailsTO> itemsList;
	
	public ItemDetailsResponse(){}
	
	public ItemDetailsResponse(List<ItemDetailsTO> itemsList){
		this.itemsList = itemsList;
	}

	public List<ItemDetailsTO> getItemsList() {
		return itemsList;
	}

	public void setItemsList(List<ItemDetailsTO> itemsList) {
		this.itemsList = itemsList;
	}
	
}
