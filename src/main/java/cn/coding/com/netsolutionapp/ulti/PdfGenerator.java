package cn.coding.com.netsolutionapp.ulti;

import cn.coding.com.netsolutionapp.model.Product;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PdfGenerator {

    public void generate(List<Product> productList, HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4.rotate(), 10f, 10f, 30f, 0f );
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontTitle.setSize(11);

        Paragraph paragraph1 = new Paragraph("Net_Solution Products Lists", fontTitle);
        paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph1);

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{1,1,1,1,1,1,1,1,1,1});
        table.setSpacingBefore(3);

        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.DARK_GRAY);
        cell.setPadding(2);

        Font font = FontFactory.getFont(FontFactory.COURIER);
        font.setColor(CMYKColor.WHITE);
        font.setSize(12.5f);

        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Item", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Quantity", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Model", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Categories", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("COD", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Invoice_No", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Profit", font));
        table.addCell(cell);

        for (Product product: productList) {
            table.addCell(String.valueOf(product.getId()));
            table.addCell(product.getItem());
            table.addCell(String.valueOf(product.getQuantity()));
            table.addCell(product.getModel());
            table.addCell(product.getCategories());
            table.addCell(String.valueOf(product.getCOD()));
            table.addCell(product.getInvoiceNo());
            table.addCell(String.valueOf(product.getDate()));
            table.addCell(String.valueOf(product.getAmount()));
            table.addCell(String.valueOf(product.getProfit()));
        }
        document.add(table);
        document.close();
    }
}
