package com.hexin.common.html2pdf;
/** 
 * @ClassName: TableHeader 
 * @Description: 页面页脚设置 
 * @author duyaxing
 * @date 2015-12-30 上午11:06:57
 *  
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HeaderFooter extends PdfPageEventHelper {

    private int headType;
    private int footType;
    private String imagePath;
    private String headerContent;
    private static final Logger log = LoggerFactory.getLogger(HeaderFooter.class);
    /**
     * 页眉页脚类型：FOOT_TYPE_1:去掉页脚  ;FOOT_TYPE_2:页脚正常显示;FOOT_TYPE_3:保留页脚 只留页码  HEAD_TYPE_1:页眉 和信贷图标去掉
     */
    public static final int FOOT_TYPE_1 = 1;
    public static final int FOOT_TYPE_2 = 2;
    public static final int FOOT_TYPE_3 = 3;

	public static final int HEAD_TYPE_BLANK = 1;
	public static final int HEAD_TYPE_HX = 2;
	public static final int HEAD_TYPE_CAR = 3;
	public static final int HEAD_TYPE_HXD = 4;

    public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	//	@Autowired CeAgreementDownloadWS ceAgreementDownloadWS;
    /** The template with the total number of pages. */
    PdfTemplate total;
    BaseFont bf;
    public HeaderFooter(int headType, int footType, String headerContent, String imagePath) {
        this.headType = headType;
        this.footType = footType;
        this.headerContent = headerContent;
        this.imagePath = imagePath;
    }





    /**
     * Creates the PdfTemplate that will hold the total number of pages.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
	public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);
    }


    /**
     * Adds a header to every page
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
	public void onEndPage(PdfWriter writer, Document document) {

		String picPath = imagePath + "common/static/images/";
		switch (headType) {
		case HEAD_TYPE_BLANK:
			picPath += "logo-blank.png";
			break;
		case HEAD_TYPE_CAR:
			picPath += "logo-car.png";
			break;
		case HEAD_TYPE_HXD:
			picPath += "logo-hxd.png";
			break;
		default:
			picPath += "logo-hx.png";
			break;
		}

		PdfPTable headTable = new PdfPTable(1);
        try {
			Image img = Image.getInstance(picPath);
			img.setAlignment(Image.LEFT | Image.TEXTWRAP);
			img.setBorder(Image.BOX);
			img.setBorderWidth(1);
			img.setBorderColor(BaseColor.WHITE);
			img.scaleToFit(47, 23);

        	bf= BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        	headTable.setWidths(new int[]{18});
        	headTable.setTotalWidth(527);
        	headTable.setLockedWidth(true);
        	headTable.getDefaultCell().setFixedHeight(45);
        	headTable.getDefaultCell().setBorder(Rectangle.BOTTOM);
        	headTable.addCell(img);

            PdfContentByte pdfContentByte = writer.getDirectContent();
            pdfContentByte.saveState();
            String header = headerContent;
            float headTextBase = document.top() +8;
            pdfContentByte.beginText();
            pdfContentByte.setFontAndSize(bf, 11);
            pdfContentByte.setTextMatrix(document.right()-155, headTextBase);
            pdfContentByte.showText(header);
            pdfContentByte.endText();
            pdfContentByte.restoreState();

        	headTable.writeSelectedRows(0, -1, 34, 830, writer.getDirectContent());

            if (footType == FOOT_TYPE_2) {
                PdfPTable footTable = new PdfPTable(1);
                footTable.setWidths(new int[]{24});
                footTable.setTotalWidth(527);
                footTable.setLockedWidth(true);
                footTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                footTable.getDefaultCell().setBorder(Rectangle.TOP);
                PdfPCell cell = new PdfPCell();
                cell.setBorder(Rectangle.UNDEFINED);
                footTable.addCell(new Phrase());
                footTable.addCell(cell);

                PdfContentByte pcb = writer.getDirectContent();
                pcb.saveState();
                String text = "和信电子商务有限公司 ( " + writer.getPageNumber() + "/";
                float textBase = document.bottom() -26;
                pcb.beginText();
                pcb.setFontAndSize(bf, 10);
                pcb.setTextMatrix(document.right()-133, textBase);
                pcb.showText(text);
                pcb.endText();
                pcb.addTemplate(total, document.right()-10, textBase);
                pcb.restoreState();

                footTable.writeSelectedRows(0, -1, 34, 55, writer.getDirectContent());
            }else if(footType == FOOT_TYPE_3){
            	 PdfPTable footTable = new PdfPTable(1);
                 footTable.setWidths(new int[]{24});
                 footTable.setTotalWidth(527);
                 footTable.setLockedWidth(true);
                 footTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                 footTable.getDefaultCell().setBorder(Rectangle.TOP);
                 PdfPCell cell = new PdfPCell();
                 cell.setBorder(Rectangle.UNDEFINED);
                 footTable.addCell(new Phrase());
                 footTable.addCell(cell);

                 PdfContentByte pcb = writer.getDirectContent();
                 pcb.saveState();
                 String text =writer.getPageNumber() + "/";
                 float textBase = document.bottom() -26;
                 pcb.beginText();
                 pcb.setFontAndSize(bf, 10);
                 pcb.setTextMatrix(document.right()-28, textBase);
                 pcb.showText(text);
                 pcb.endText();
                 pcb.addTemplate(total, document.right()-10, textBase);
                 pcb.restoreState();

                 footTable.writeSelectedRows(0, -1, 34, 55, writer.getDirectContent());
            }


        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills out the total number of pages before the document is closed.
     * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
     *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
     */
    @Override
	public void onCloseDocument(PdfWriter writer, Document document) {
    	
    	total.beginText();  
        total.setFontAndSize(bf, 10);  
        total.setTextMatrix(0, 0);  
        total.showText(String.valueOf(writer.getPageNumber() - 1)+(footType == FOOT_TYPE_3?"":")"));  
        total.endText(); 
    }
}