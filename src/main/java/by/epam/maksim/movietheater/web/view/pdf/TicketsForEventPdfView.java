package by.epam.maksim.movietheater.web.view.pdf;

import by.epam.maksim.movietheater.entity.Ticket;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Map;

public class TicketsForEventPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfWriter pdfWriter,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        @SuppressWarnings("unchecked") Collection<Ticket> tickets = (Collection<Ticket>) map.get("tickets");

        PdfPTable pdfTable = new PdfPTable(5);
        pdfTable.addCell("Event");
        pdfTable.addCell("Date");
        pdfTable.addCell("Seat");
        pdfTable.addCell("User");
        pdfTable.addCell("Price");
        tickets.forEach(t -> {
            pdfTable.addCell(String.valueOf(t.getEvent().getName()));
            pdfTable.addCell(String.valueOf(t.getSeanceDateTime()));
            pdfTable.addCell(String.valueOf(t.getSeat()));
            pdfTable.addCell(t.getUser().getFirstName() + " " + t.getUser().getLastName());
            pdfTable.addCell(String.valueOf(t.getSellingPrice()));
        });
        document.add(pdfTable);
    }

}