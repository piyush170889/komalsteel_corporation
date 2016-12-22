package co.in.replete.komalindustries.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import co.in.replete.komalindustries.beans.CartDetailsTO;
import co.in.replete.komalindustries.beans.CartItemDetailsListTO;
import co.in.replete.komalindustries.beans.CartItemDtlsTO;
import co.in.replete.komalindustries.beans.ItemDetailsTO;
import co.in.replete.komalindustries.beans.ItemOrderDetailsTO;
import co.in.replete.komalindustries.beans.NewCartDetailsTO;
import co.in.replete.komalindustries.beans.UserDetailsTO;
import co.in.replete.komalindustries.beans.entity.CartDlvryDtl;
import co.in.replete.komalindustries.beans.entity.CartDtl;
import co.in.replete.komalindustries.beans.entity.CartItemDtl;
import co.in.replete.komalindustries.beans.entity.InvoiceDtl;
import co.in.replete.komalindustries.beans.entity.ItemMasterDtl;
import co.in.replete.komalindustries.beans.entity.ItemsInventoryDtl;
import co.in.replete.komalindustries.beans.entity.OfferDtl;
import co.in.replete.komalindustries.beans.entity.PaymentDtl;
import co.in.replete.komalindustries.beans.entity.ShippingAddressDetail;
import co.in.replete.komalindustries.webcontroller.beans.WCartItemTO;
import co.in.replete.komalindustries.webcontroller.beans.WOrderDetailsTO;
import co.in.replete.komalindustries.webcontroller.beans.WUserCartDetails;
import co.in.replete.komalindustries.webcontroller.beans.WUserOrderInfo;

public interface CartDAO {

   List<CartDetailsTO> selectCartDetaisByOrderStatus(String userMasterId, String orderStatus);
	
	public int insertAddress(ShippingAddressDetail shippingAddressDetail);

	public int insertCartDeliveryDtls(CartDlvryDtl cartDlvryDtl);
	
	public int insertInvoiceDetails(InvoiceDtl invoiceDetails);
	
	public OfferDtl selectOfferDetails(String offerDtlsId);
	
	public String insertPaymentDetails(PaymentDtl paymentDtl);

	public int insertCartDetails(CartDtl cartDetail);
	
	public int[] insertCartItemDetails(List<CartItemDtl> cartItemDtlList);
	
	public List<ItemDetailsTO> selectItemsListByFilter(String sql, String[] filterValues);

	public List<ItemDetailsTO> selectItemsDetails(String keyword);

	ItemsInventoryDtl selectItemInventoryDtl(String itemMasterDtlsId);

	void updateItemInventoryDetails(Float avlQty, Float bookedQty, int itemInventoryDetailsId);

	List<NewCartDetailsTO> selectCartDetaisByPageNum(String trackId, int pageNum,int maxRecord) throws DataAccessException;

	int selectOrderCount(String trackId) throws DataAccessException;

	List<ItemOrderDetailsTO> selectItemDetails(String trackId) throws Exception;

	UserDetailsTO selectUserDetails(String trackId);

	List<CartItemDetailsListTO> selectCartItemDtls(int cartDtlsId);
	
	List<WUserOrderInfo> selectUserOrderInfo(String id);
	
    List<WUserCartDetails> selectUserCartDetails(int invoiceId);	
    
    List<WOrderDetailsTO> selectOrderDetails();
    
    List<WCartItemTO> selectCartItemDetails(int orderId);

	List<CartItemDtlsTO> selectOrderItemDetailsByCartId(int orderDetailsId);

	List<WOrderDetailsTO> searchOrders(String searchBy, String searchDateRange) throws DataAccessException, ParseException;

	UserDetailsTO selectUserUserDetailsByTrackId(String trackId);
}
