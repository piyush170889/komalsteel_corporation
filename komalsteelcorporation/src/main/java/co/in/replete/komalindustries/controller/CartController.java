package co.in.replete.komalindustries.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.in.replete.komalindustries.beans.BaseWrapper;
import co.in.replete.komalindustries.beans.CartDetailRequest;
import co.in.replete.komalindustries.beans.ItemsDetailsRequest;
import co.in.replete.komalindustries.constants.KomalIndustriesConstants;
import co.in.replete.komalindustries.dao.CartDAO;
import co.in.replete.komalindustries.service.CartService;
import co.in.replete.komalindustries.utils.MessageUtility;

@RestController
public class CartController {

	@Autowired
	CartService cartService;
	
	@Autowired
	CartDAO cartDAO;
	
	/**
	 * Description : Get's the list of order's for the userMasterId specified and the status of order(COMPLETE,NOT_COMPLETE,ALL)
	 * 
	 * @return {@link BaseWrapper}
	 * @throws {@link
	 *             Exception}
	 */
	@RequestMapping(value = "/cartdetails/{trackId}/{pageNum}", method=RequestMethod.GET, produces=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper getOrdersList(@PathVariable("trackId") String trackId, @PathVariable("pageNum") int pageNum) throws Exception {
		
		return cartService.getOrdersList(trackId, pageNum);
	}
	
	/**
	 * Description :
	 * 
	 * @return {@link BaseWrapper}
	 * @throws {@link
	 *             Exception}
	 */
	@RequestMapping(value = "/cartdetails/{trackid}", method = RequestMethod.POST, consumes=KomalIndustriesConstants.APPLICATION_JSON)
	public BaseWrapper saveOrder(@Valid @RequestBody CartDetailRequest request, @PathVariable("trackid") String trackId/*, HttpServletResponse servletResponse*/) throws Exception {
		return cartService.saveOrder(request, trackId/*, servletResponse*/);
	}
	
	/**
	 * Description :
	 * 
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@RequestMapping(value="/getitemlist", method=RequestMethod.POST)
	public BaseWrapper getItemsList(@RequestBody ItemsDetailsRequest request) throws Exception {
		
		return cartService.getItemsList(request);
	}
	
	/**
	 * Description :
	 * 
	 * @return {@link BaseWrapper}
	 * @throws {@link Exception}
	 */
	@RequestMapping(value="/searchItems/{keyword}", method=RequestMethod.GET)
	public BaseWrapper searchItems(@PathVariable("keyword") String keyword) throws Exception {
		
		return cartService.searchItems(keyword);
	}
}
