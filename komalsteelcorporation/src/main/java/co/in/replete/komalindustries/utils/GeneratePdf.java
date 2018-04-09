package co.in.replete.komalindustries.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import co.in.replete.komalindustries.beans.Invoice;
import co.in.replete.komalindustries.beans.TaxDescription;
import co.in.replete.komalindustries.beans.Transaction;

@Component
public class GeneratePdf {

	@Autowired
	private CommonUtility commonUtility;
	
	static Font infoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10,Font.BOLD);
	static Font defFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);
	static Font invoiceDetailsFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,Font.BOLD);
	final static String INVOICE_HEADER = "PROFORMA INVOICE";
	
	public int genearatePDFOutsideMaharashtra(Invoice invoice, String pdfFilePath) throws FileNotFoundException, DocumentException
	{
		DateFormat dfddMMMMYYYY = new SimpleDateFormat("dd MMMM yyyy HH:mm");
		
		File file=new File(pdfFilePath);
		OutputStream file1 = new FileOutputStream(file);
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, file1);
		document.open();
		
		// Heading
		PdfPTable heading = new PdfPTable(1);
		heading.setWidthPercentage(100);
		Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.BOLD);
		PdfPCell headingCell = new PdfPCell(new Phrase(INVOICE_HEADER,headingFont));
		headingCell.setBorder(Rectangle.NO_BORDER);
		headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		heading.addCell(headingCell);
		document.add(heading);
		
		
		
		//Table Layout Properties
		PdfPTable invoiceDetails = new PdfPTable(3);
		invoiceDetails.setSpacingBefore(10f);
		invoiceDetails.setWidthPercentage(100);
		float[] iIcolumnWidths = {58f, 21f,21f};
		invoiceDetails.setWidths(iIcolumnWidths);
		PdfPCell invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		
		//Company Address
		invoiceDetailsCell.addElement(new Phrase(invoice.getCompanyName(),invoiceDetailsFont));
		invoiceDetailsCell.addElement(new Phrase("VAISHALI IND. ESTATE, ’B’ WING, BASEMENT,",defFont));
		invoiceDetailsCell.addElement(new Phrase("GALA NO. 12, MHATREWADI, BALKRISHNA TAWADE ROAD,",defFont));
		invoiceDetailsCell.addElement(new Phrase("DAHISAR (W), MUMBAI - 400 068",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getCompanyGstinUin(),defFont));
		invoiceDetailsCell.addElement(new Phrase("E-Mail : "+invoice.getCompanyEmail(),defFont));
		invoiceDetailsCell.setRowspan(3);
		invoiceDetails.addCell(invoiceDetailsCell);
		
		
		//Invoice no
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Order No.",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getInvoiceNo(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Invoice Date
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Dated",defFont));
//		invoiceDetailsCell.addElement(new Phrase(invoice.getInvoiceDate(),infoFont));
		invoiceDetailsCell.addElement(new Phrase(dfddMMMMYYYY.format(Calendar.getInstance().getTime()),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Delivery Note
		
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Mark :" ,defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getMode_TermsPayment(),infoFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getMark(),infoFont));
		invoiceDetailsCell.setColspan(2);
		//invoiceDetailsCell.setRowspan(2);
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Terms Of Delivery
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Terms of Delivery",defFont));
		invoiceDetailsCell.addElement(new Phrase("",infoFont));	
		invoiceDetailsCell.setColspan(2);
		//invoiceDetailsCell.setRowspan(2);
		invoiceDetails.addCell(invoiceDetailsCell);	
		
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Mark :",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getDispDocumentNo(),infoFont));
		invoiceDetailsCell.setColspan(2);
		invoiceDetailsCell.setRowspan(4);
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Delivery Note",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getDeliveryNote(),infoFont));
		//invoiceDetailsCell.setColspan(2);
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		/*//Mode/Terms of Payment
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Mode/Terms of Payment",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getMode_TermsPayment(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);

		//Supplier’s Ref.
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Supplier’s Ref.",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getSupplierRef(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);

		//Other Reference(s)
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Other Reference(s)",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getOtherRef(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		//Buyers Information
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Buyer",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerName(),invoiceDetailsFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerStreetAddress(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerCity(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerDistrict(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerState(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerGstinUin(),defFont));
		invoiceDetailsCell.setRowspan(3);
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Despatch Document No.
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Dispatch Document No.: ",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getDispDocumentNo(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Delivery Note Date
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Delivery Note Date",defFont));
//		invoiceDetailsCell.addElement(new Phrase(invoice.getDeliveryNoteDate(),infoFont));
		invoiceDetailsCell.addElement(new Phrase(dfddMMMMYYYY.format(Calendar.getInstance().getTime()),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		
		//Despatched through
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Dispatched through",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getDispatchedThrough(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Destination
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Destination",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getDestination(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		/*//Terms Of Delivery
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Terms of Delivery",defFont));
		invoiceDetailsCell.addElement(new Phrase("",infoFont));	
		invoiceDetailsCell.setColspan(2);
		invoiceDetails.addCell(invoiceDetailsCell);	*/
		
		//Add Invoice Details Table
		document.add(invoiceDetails);
		
		
		//Transaction table
		PdfPTable transTable = new PdfPTable(8);
		transTable.setWidthPercentage(100);
		float[] columnWidths2 = {3f, 34f, 10f, 10f, 10f,10f,4f,19f};
		transTable.setWidths(columnWidths2);
		
		//Row For Transaction table heading
		transTable.addCell(insertCell("Sr.",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Description of Goods",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("HSN/SAC",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("GST Rate",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Quantity",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Rate",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Per",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Amount",defFont,Element.ALIGN_CENTER));
		
		
		
		
		//Row For Transactions
		//TODO: Add dynamic transaction load from database(For each loop)
		int count=1;
		double subTotal=0;
		for(Transaction transaction:invoice.getTransactionList())
		{
		transTable.addCell(insertCell(String.valueOf(count),defFont,Element.ALIGN_RIGHT));
		transTable.addCell(insertCell(transaction.getDescriptioOFGoods(),infoFont,Element.ALIGN_LEFT));
		transTable.addCell(insertCell(transaction.getHsnSac(),defFont,Element.ALIGN_RIGHT));
		transTable.addCell(insertCell(Integer.toString(Math.round(transaction.getGstRate()))+"%",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(transaction.getQuantity()+"Pc.",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(null==transaction.getRate() ? "" :commonUtility.roundUpToTwoDecimal(transaction.getRate(), 2).toString(),defFont,Element.ALIGN_RIGHT));
		transTable.addCell(insertCell(transaction.getPer(),defFont,Element.ALIGN_CENTER));
		String totalAmount = (null==transaction.getAmount()) ? "0" : commonUtility.roundUpToTwoDecimal(transaction.getAmount(), 2);
		transTable.addCell(insertCell(totalAmount, infoFont, Element.ALIGN_RIGHT));
		count++;
		subTotal+=Double.parseDouble(totalAmount);
		}
		
		//Row For Sub Total Print
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Sub Total",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		/*PdfPCell subTotalCell = insertCell("Sub Total",infoFont,Element.ALIGN_CENTER);
		subTotalCell.setColspan(2);
		transTable.addCell(subTotalCell);*/
		transTable.addCell(insertCell(commonUtility.roundUpToTwoDecimal((float)subTotal, 2),infoFont,Element.ALIGN_RIGHT));
		
		//Row Fro IGST Print
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Output IGST",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(invoice.getiGstAmount(), 2)),infoFont,Element.ALIGN_RIGHT));
		
		
		//Rounded Value
		double totalChargableAmount = invoice.getTotalChargableAmount();
		String rfValue = commonUtility.roundUpToTwoDecimal((float) (Math.ceil(totalChargableAmount) - totalChargableAmount), 2);
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("RF", infoFont, Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		/*PdfPCell subTotalCell = insertCell("Sub Total",infoFont,Element.ALIGN_CENTER);
		subTotalCell.setColspan(2);
		transTable.addCell(subTotalCell);*/
		transTable.addCell(insertCell(String.valueOf(rfValue),infoFont,Element.ALIGN_RIGHT));
		
		// Transaction total Amount printing
		//TODO: Add your Total amount of All transaction
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Total (Round Off)",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(String.valueOf(invoice.getTotalQuantity())+"Pc.",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Rs."+String.valueOf(commonUtility.roundUpToTwoDecimal((float)Math.round(invoice.getTotalChargableAmount()), 2)),infoFont,Element.ALIGN_RIGHT));
		
		
		//Print amount in words
		int amountInWords = (int)(Math.round(invoice.getTotalChargableAmount()));
		PdfPCell amountWordCell=new PdfPCell();
		amountWordCell.setPaddingBottom(5);
		amountWordCell.setColspan(8);
		amountWordCell.addElement(new Phrase("Amount Chargeable (in words)",defFont));
		amountWordCell.addElement(new Phrase(WordUtils.capitalize(commonUtility.doGetWords(amountInWords)),infoFont));
		transTable.addCell(amountWordCell);
		document.add(transTable);
		
		//Table for tax valuation 
		PdfPTable taxTable = new PdfPTable(4);
		taxTable.setWidthPercentage(100);
		float[] columnWidths3 = {60f, 16f, 10f, 14f};
		taxTable.setWidths(columnWidths3);

		//Headers for tax valuation table
		PdfPCell cell=insertCell("HSN/SAC",defFont,Element.ALIGN_CENTER);
		cell.setRowspan(2);
		taxTable.addCell(cell);
		cell=insertCell("Taxable Value",defFont,Element.ALIGN_CENTER);
		cell.setRowspan(2);
		taxTable.addCell(cell);
		cell=insertCell("IGST",defFont,Element.ALIGN_CENTER);
		cell.setColspan(2);
		taxTable.addCell(cell);
		taxTable.addCell(insertCell("Rate",defFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell("Amount",defFont,Element.ALIGN_CENTER));
		
		
		// Transaction entries for tax valuation table(Static entries)
		for(TaxDescription taxDescription:invoice.getTaxDescription())
		{
		taxTable.addCell(insertCell(taxDescription.getHsnSac(),defFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(taxDescription.getTaxableValue(), 2)),defFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell(Math.round(taxDescription.getiGstRate())+"%",defFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(taxDescription.getiGsttaxAmount(), 2)),defFont,Element.ALIGN_RIGHT));
		}
		
		// Total Tax amount printing
		taxTable.addCell(insertCell("Total",infoFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(invoice.getTotalTaxableValue(), 2)),infoFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell("",infoFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(invoice.getTotalTaxAmount(), 2)),infoFont,Element.ALIGN_RIGHT));
		
		//Tax Amount in words
		/*Paragraph p = new Paragraph();
		Chunk textPhrase = new Chunk("Tax Amount (in words): ", defFont);
		Chunk amountWord = new Chunk(WordUtils.capitalize(commonUtility.doGetWords((int)invoice.getTotalTaxableValue())), infoFont);
		p.add(textPhrase);
		p.add(amountWord);
		PdfPCell taxInWordCell=new PdfPCell(p);
		taxInWordCell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		taxInWordCell.setPaddingTop(5);
		taxInWordCell.setPaddingBottom(5);
		taxInWordCell.setColspan(4);
		taxTable.addCell(taxInWordCell);*/
		
		//Company Bank details
		PdfPCell bankInfocell=new PdfPCell();
		bankInfocell=insertCell("Company’s Bank Details", defFont, Element.ALIGN_LEFT);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(4);
		bankInfocell.setPaddingTop(30f);
		bankInfocell.setPaddingBottom(5);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		
		Paragraph p1 = new Paragraph();
		Chunk bankNameTitle = new Chunk("Bank Name              :", defFont);
		Chunk bankName = new Chunk(invoice.getCompanyBankName(), infoFont);
		p1.add(bankNameTitle);
		p1.add(bankName);
		bankInfocell=new PdfPCell(p1);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(4);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		
		
		Paragraph p2 = new Paragraph();
		Chunk acNoTitle = new Chunk("A/c No                      :", defFont);
		Chunk acNo = new Chunk(invoice.getCompanyAcno(), infoFont);
		p2.add(acNoTitle);
		p2.add(acNo);
		bankInfocell=new PdfPCell(p2);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(4);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		
		Paragraph p3 = new Paragraph();
		Chunk branchIfscTitle = new Chunk("Branch & IFS Code  :", defFont);
		Chunk branchIfsc = new Chunk(invoice.getCompanyBranchIfscCode(), infoFont);
		p3.add(branchIfscTitle);
		p3.add(branchIfsc);
		bankInfocell=new PdfPCell(p3);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(4);
		bankInfocell.setPaddingBottom(5);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		document.add(taxTable);
		
		// Table For Signature and PAN Number
		PdfPTable signPanTable = new PdfPTable(2);
		signPanTable.setWidthPercentage(100);
		float[] columnWidths4 = {50f, 50f};
		signPanTable.setWidths(columnWidths4);
		
		//Compnies PAN
		Paragraph p4 = new Paragraph();
		Chunk panTitle = new Chunk("Company’s PAN          :", defFont);
		Chunk panNo = new Chunk("           "+invoice.getCompanyPanNo(), infoFont);
		p4.add(panTitle);
		p4.add(panNo);
		PdfPCell panCell=new PdfPCell(p4);
		panCell.setBorder(PdfPCell.LEFT | Rectangle.BOTTOM);
		panCell.setPaddingBottom(5);
		panCell.setPaddingTop(25f);
		panCell.setRowspan(2);
		signPanTable.addCell(panCell);
		

		
		// Authority Signature
		PdfPCell authorityCell=new PdfPCell();
		authorityCell=insertCell("for Komal Trading Corporation", infoFont, Element.ALIGN_RIGHT);
		authorityCell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT | Rectangle.TOP );
		authorityCell.setPaddingRight(30f);
		authorityCell.setPaddingBottom(5);
		signPanTable.addCell(authorityCell);
		
		authorityCell=new PdfPCell();
		authorityCell=insertCell("Authorised Signatory", defFont, Element.ALIGN_RIGHT);
		authorityCell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
		authorityCell.setPaddingTop(20f);
		authorityCell.setPaddingRight(20f);
		authorityCell.setPaddingBottom(5);
		signPanTable.addCell(authorityCell);	
		document.add(signPanTable);
		
		//Subject To Mumbai Jurisdiction
		PdfPTable footer = new PdfPTable(1);
		heading.setWidthPercentage(100);
		PdfPCell footerCell = new PdfPCell(new Phrase("Subject To Mumbai Jurisdiction",defFont));
		footerCell.setBorder(Rectangle.NO_BORDER);
		authorityCell.setPaddingTop(20f);
		footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		footer.addCell(footerCell);
		document.add(footer);
		
		document.close();
		
		return 1;
	}

	
	public int genearatePDFForMaharashtra(Invoice invoice, String pdfFilePath) throws FileNotFoundException, DocumentException
	{
		DateFormat dfddMMMMYYYY = new SimpleDateFormat("dd MMMM yyyy HH:mm");
		
		File file=new File(pdfFilePath);
		OutputStream file1 = new FileOutputStream(file);
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, file1);
		document.open();
		
		// Heading
		PdfPTable heading = new PdfPTable(1);
		heading.setWidthPercentage(100);
		Font headingFont = new Font(Font.FontFamily.TIMES_ROMAN, 14,Font.BOLD);
		PdfPCell headingCell = new PdfPCell(new Phrase(INVOICE_HEADER,headingFont));
		headingCell.setBorder(Rectangle.NO_BORDER);
		headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		heading.addCell(headingCell);
		document.add(heading);
		
		
		
		//Table Layout Properties
		PdfPTable invoiceDetails = new PdfPTable(3);
		invoiceDetails.setSpacingBefore(10f);
		invoiceDetails.setWidthPercentage(100);
		float[] iIcolumnWidths = {58f, 21f,21f};
		invoiceDetails.setWidths(iIcolumnWidths);
		PdfPCell invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		
		//Company Address
		invoiceDetailsCell.addElement(new Phrase(invoice.getCompanyName(),invoiceDetailsFont));
		invoiceDetailsCell.addElement(new Phrase("VAISHALI IND. ESTATE, ’B’ WING, BASEMENT,",defFont));
		invoiceDetailsCell.addElement(new Phrase("GALA NO. 12, MHATREWADI, BALKRISHNA TAWADE ROAD,",defFont));
		invoiceDetailsCell.addElement(new Phrase("DAHISAR (W), MUMBAI - 400 068",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getCompanyGstinUin(),defFont));
		invoiceDetailsCell.addElement(new Phrase("E-Mail : "+invoice.getCompanyEmail(),defFont));
		invoiceDetailsCell.setRowspan(3);
		invoiceDetails.addCell(invoiceDetailsCell);
		
		
		//Invoice no
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Order No.",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getInvoiceNo(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Invoice Date
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Dated",defFont));
//		invoiceDetailsCell.addElement(new Phrase(invoice.getInvoiceDate(),infoFont));
		invoiceDetailsCell.addElement(new Phrase(dfddMMMMYYYY.format(Calendar.getInstance().getTime()),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Delivery Note
		
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Mark. :",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getDispDocumentNo(),infoFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getMark(),infoFont));
		invoiceDetailsCell.setColspan(2);
		invoiceDetailsCell.setRowspan(4);
		invoiceDetails.addCell(invoiceDetailsCell);
		
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Delivery Note",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getDeliveryNote(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		//Mode/Terms of Payment
		
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Despatch Document No.:",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getDispDocumentNo(),infoFont));
		invoiceDetailsCell.setColspan(2);
		invoiceDetailsCell.setRowspan(1);
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Mode/Terms of Payment",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getMode_TermsPayment(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);*/

		//Supplier’s Ref.
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Despatch Document No.:",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getDispDocumentNo(),infoFont));
		invoiceDetailsCell.setColspan(2);
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Supplier’s Ref.",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getSupplierRef(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);*/

		//Other Reference(s)
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Despatch Document No.:",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getDispDocumentNo(),infoFont));
		invoiceDetailsCell.setColspan(2);
		invoiceDetails.addCell(invoiceDetailsCell);
		*/
		/*invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		//invoiceDetailsCell.addElement(new Phrase("Other Reference(s)",defFont));
		//invoiceDetailsCell.addElement(new Phrase(invoice.getOtherRef(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		//Buyers Information
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Buyer",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerName(),invoiceDetailsFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerStreetAddress(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerCity(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerDistrict(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerState(),defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getBuyerGstinUin(),defFont));
		invoiceDetailsCell.setRowspan(3);
		invoiceDetails.addCell(invoiceDetailsCell);
		
		
		
		//Despatch Document No.
/*		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Despatch Document No.:",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getDispDocumentNo(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Delivery Note Date
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Delivery Note Date",defFont));
		invoiceDetailsCell.addElement(new Phrase(dfddMMMMYYYY.format(Calendar.getInstance().getTime()),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);*/
		
		
		//Despatched through
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Dispatched through",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getDispatchedThrough(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		
		//Despatched through
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Destination",defFont));
		invoiceDetailsCell.addElement(new Phrase(invoice.getDestination(),infoFont));
		invoiceDetails.addCell(invoiceDetailsCell);
		
		//Terms Of Delivery
		invoiceDetailsCell = new PdfPCell();
		invoiceDetailsCell.setPaddingBottom(5f);
		invoiceDetailsCell.addElement(new Phrase("Terms of Delivery",defFont));
		invoiceDetailsCell.addElement(new Phrase("",infoFont));	
		invoiceDetailsCell.setColspan(2);
		invoiceDetails.addCell(invoiceDetailsCell);	
		
		//Add Invoice Details Table
		document.add(invoiceDetails);
		
		
		//Transaction table

		PdfPTable transTable = new PdfPTable(8);
		transTable.setWidthPercentage(100);
		float[] columnWidths2 = {3f, 34f, 10f, 10f, 10f,10f,4f,19f};
		transTable.setWidths(columnWidths2);
		
		//Row For Transaction table heading
		transTable.addCell(insertCell("Sr.",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Description of Goods",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("HSN/SAC",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("GST Rate",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Quantity",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Rate",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Per",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Amount",defFont,Element.ALIGN_CENTER));
		
		//Row For Transactions
		//TODO: Add dynamic transaction load from database(For each loop)
		int count=1;
		double subTotal=0;
		for(Transaction transaction:invoice.getTransactionList())
		{
		transTable.addCell(insertCell(String.valueOf(count),defFont,Element.ALIGN_RIGHT));
		transTable.addCell(insertCell(transaction.getDescriptioOFGoods(),infoFont,Element.ALIGN_LEFT));
		transTable.addCell(insertCell(transaction.getHsnSac(),defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(Integer.toString(Math.round(transaction.getGstRate()))+"%",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(transaction.getQuantity()+"Pc.",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(null==transaction.getRate() ? "" : commonUtility.roundUpToTwoDecimal(transaction.getRate(), 2).toString(),defFont,Element.ALIGN_RIGHT));
		transTable.addCell(insertCell(transaction.getPer(),defFont,Element.ALIGN_CENTER));
		String totalAmount = null==transaction.getAmount() ? "0" : commonUtility.roundUpToTwoDecimal(transaction.getAmount(), 2);
		transTable.addCell(insertCell(totalAmount,infoFont,Element.ALIGN_RIGHT));
		count++;
		subTotal+=Double.parseDouble(totalAmount);
		}
		
		//Row For Sub Total Print
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Sub Total",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		/*PdfPCell subTotalCell = insertCell("Sub Total",infoFont,Element.ALIGN_CENTER);
		subTotalCell.setColspan(2);
		transTable.addCell(subTotalCell);*/
		transTable.addCell(insertCell(commonUtility.roundUpToTwoDecimal((float)subTotal, 2),infoFont,Element.ALIGN_RIGHT));
		
		
		//Row For CGST Print
		String outputCgst = commonUtility.roundUpToTwoDecimal(invoice.getcGstAmount(), 2);
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Output CGST",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(String.valueOf(outputCgst),infoFont,Element.ALIGN_RIGHT));
		
		
		//Row For SGST Print
		String outputSgst = commonUtility.roundUpToTwoDecimal(invoice.getsGstAmount(), 2);
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Output SGST",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(String.valueOf(outputSgst),infoFont,Element.ALIGN_RIGHT));
				
		//Rounded Value
		double totalChargableAmount = invoice.getTotalChargableAmount();
		String rfValue = commonUtility.roundUpToTwoDecimal((float) (Math.ceil(totalChargableAmount) - totalChargableAmount), 2);
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("RF", infoFont, Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		/*PdfPCell subTotalCell = insertCell("Sub Total",infoFont,Element.ALIGN_CENTER);
		subTotalCell.setColspan(2);
		transTable.addCell(subTotalCell);*/
		transTable.addCell(insertCell(String.valueOf(rfValue),infoFont,Element.ALIGN_RIGHT));
		
		// Transaction total Amount printing
		//TODO: Add your Total amount of All transaction
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Total (Round Off)",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell(String.valueOf(invoice.getTotalQuantity())+"Pc.",infoFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("",defFont,Element.ALIGN_CENTER));
		transTable.addCell(insertCell("Rs."+String.valueOf(commonUtility.roundUpToTwoDecimal((float) Math.round(invoice.getTotalChargableAmount()), 2)),infoFont,Element.ALIGN_RIGHT));
		
		
		//Print amount in words
		int amountInWords = (int)Math.round(invoice.getTotalChargableAmount());
		PdfPCell amountWordCell=new PdfPCell();
		amountWordCell.setPaddingBottom(5);
		amountWordCell.setColspan(8);
		amountWordCell.addElement(new Phrase("Amount Chargeable (in words)",defFont));
		amountWordCell.addElement(new Phrase(WordUtils.capitalize(commonUtility.doGetWords(amountInWords)),infoFont));
		transTable.addCell(amountWordCell);
		document.add(transTable);
		
		//Table for tax valuation 
		PdfPTable taxTable = new PdfPTable(6);
		taxTable.setWidthPercentage(100);
		float[] columnWidths3 = {36f,16f,10f,14f,10f,14f};
		taxTable.setWidths(columnWidths3);

		//Headers for tax valuation table
		PdfPCell cell=insertCell("HSN/SAC",defFont,Element.ALIGN_CENTER);
		cell.setRowspan(2);
		taxTable.addCell(cell);
		cell=insertCell("Taxable Value",defFont,Element.ALIGN_CENTER);
		cell.setRowspan(2);
		taxTable.addCell(cell);
		cell=insertCell("CGST",defFont,Element.ALIGN_CENTER);
		cell.setColspan(2);
		taxTable.addCell(cell);
		cell=insertCell("SGST",defFont,Element.ALIGN_CENTER);
		cell.setColspan(2);
		taxTable.addCell(cell);
		taxTable.addCell(insertCell("Rate",defFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell("Amount",defFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell("Rate",defFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell("Amount",defFont,Element.ALIGN_CENTER));
		
		
		// Transaction entries for tax valuation table(Static entries)
		for(TaxDescription taxDescription:invoice.getTaxDescription())
		{
		taxTable.addCell(insertCell(taxDescription.getHsnSac(),defFont,Element.ALIGN_LEFT));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(taxDescription.getTaxableValue(), 2)),defFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell(Math.round(taxDescription.getcGstRate())+"%",defFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(taxDescription.getcGsttaxAmount(), 2)),defFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell(Math.round(taxDescription.getsGstRate())+"%",defFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(taxDescription.getsGsttaxAmount(), 2)),defFont,Element.ALIGN_RIGHT));
		}
		
		// Total Tax amount printing
		taxTable.addCell(insertCell("Total",infoFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(invoice.getTotalTaxableValue(), 2)),infoFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell("",infoFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(invoice.getcGsttotalTaxAmount(), 2)),infoFont,Element.ALIGN_RIGHT));
		taxTable.addCell(insertCell("",infoFont,Element.ALIGN_CENTER));
		taxTable.addCell(insertCell(String.valueOf(commonUtility.roundUpToTwoDecimal(invoice.getsGsttotalTaxAmount(), 2)),infoFont,Element.ALIGN_RIGHT));
		
		//Tax Amount in words
		/*Paragraph p = new Paragraph();
		Chunk textPhrase = new Chunk("Tax Amount (in words): ", defFont);
		//TODO: Added total in words
		Chunk amountWord = new Chunk(WordUtils.capitalize(commonUtility.doGetWords((int)invoice.getTotalTaxableValue())), infoFont);
		p.add(textPhrase);
		p.add(amountWord);
		PdfPCell taxInWordCell=new PdfPCell(p);
		taxInWordCell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		taxInWordCell.setPaddingTop(5);
		taxInWordCell.setPaddingBottom(5);
		taxInWordCell.setColspan(6);
		taxTable.addCell(taxInWordCell);*/
		
		//Company Bank details
		PdfPCell bankInfocell=new PdfPCell();
		bankInfocell=insertCell("Company’s Bank Details", defFont, Element.ALIGN_LEFT);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(6);
		bankInfocell.setPaddingTop(30f);
		bankInfocell.setPaddingBottom(5);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		
		Paragraph p1 = new Paragraph();
		Chunk bankNameTitle = new Chunk("Bank Name              :", defFont);
		Chunk bankName = new Chunk(invoice.getCompanyBankName(), infoFont);
		p1.add(bankNameTitle);
		p1.add(bankName);
		bankInfocell=new PdfPCell(p1);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(6);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		
		
		Paragraph p2 = new Paragraph();
		Chunk acNoTitle = new Chunk("A/c No                      :", defFont);
		Chunk acNo = new Chunk(invoice.getCompanyAcno(), infoFont);
		p2.add(acNoTitle);
		p2.add(acNo);
		bankInfocell=new PdfPCell(p2);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(6);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		
		Paragraph p3 = new Paragraph();
		Chunk branchIfscTitle = new Chunk("Branch & IFS Code  :", defFont);
		Chunk branchIfsc = new Chunk(invoice.getCompanyBranchIfscCode(), infoFont);
		p3.add(branchIfscTitle);
		p3.add(branchIfsc);
		bankInfocell=new PdfPCell(p3);
		bankInfocell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT);
		bankInfocell.setColspan(6);
		bankInfocell.setPaddingBottom(5);
		bankInfocell.setPaddingLeft(261f);
		taxTable.addCell(bankInfocell);
		document.add(taxTable);
		
		// Table For Signature and PAN Number
		PdfPTable signPanTable = new PdfPTable(2);
		signPanTable.setWidthPercentage(100);
		float[] columnWidths4 = {50f, 50f};
		signPanTable.setWidths(columnWidths4);
		
		//Compnies PAN
		Paragraph p4 = new Paragraph();
		Chunk panTitle = new Chunk("Company’s PAN          :", defFont);
		Chunk panNo = new Chunk("           "+invoice.getCompanyPanNo(), infoFont);
		p4.add(panTitle);
		p4.add(panNo);
		PdfPCell panCell=new PdfPCell(p4);
		panCell.setBorder(PdfPCell.LEFT | Rectangle.BOTTOM);
		panCell.setPaddingBottom(5);
		panCell.setPaddingTop(25f);
		panCell.setRowspan(2);
		signPanTable.addCell(panCell);
		

		
		// Authority Signature
		PdfPCell authorityCell=new PdfPCell();
		authorityCell=insertCell("for Komal Trading Corporation", infoFont, Element.ALIGN_RIGHT);
		authorityCell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT | Rectangle.TOP );
		authorityCell.setPaddingRight(30f);
		authorityCell.setPaddingBottom(5);
		signPanTable.addCell(authorityCell);
		
		authorityCell=new PdfPCell();
		authorityCell=insertCell("Authorised Signatory", defFont, Element.ALIGN_RIGHT);
		authorityCell.setBorder(PdfPCell.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
		authorityCell.setPaddingTop(20f);
		authorityCell.setPaddingRight(20f);
		authorityCell.setPaddingBottom(5);
		signPanTable.addCell(authorityCell);	
		document.add(signPanTable);
		
		//Subject To Mumbai Jurisdiction
		PdfPTable footer = new PdfPTable(1);
		heading.setWidthPercentage(100);
		PdfPCell footerCell = new PdfPCell(new Phrase("Subject To Mumbai Jurisdiction",defFont));
		footerCell.setBorder(Rectangle.NO_BORDER);
		authorityCell.setPaddingTop(20f);
		footerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		footer.addCell(footerCell);
		document.add(footer);
		
		document.close();
		
		return 1;
	}
	
	public static PdfPCell insertCell(String text,Font font,int elementAlign)
	{
		PdfPCell cell=new PdfPCell();
		cell.setPaddingBottom(5);
		cell = new PdfPCell(new Phrase(text,font));
		cell.setHorizontalAlignment(elementAlign);
		return cell;
	}
	
	public float getGSTRate(float amount,float gstRate)
	{
		float gstTaxAmount=0.0f;
		return gstTaxAmount;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		
		//genearatePDF("KDHA/0108/17-18");

	}

}
