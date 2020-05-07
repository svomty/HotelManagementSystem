package com.svintsitski.hotel_management_system;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class FirstPdf {

    public String DEST;
    private static final String DIRECTORY = "results/";
    Reservation reservation;

    @Autowired
    private ReservationServiceImpl reservationService;

    public FirstPdf(Reservation reservation) {
        this.reservation = reservation;
        DEST = DIRECTORY + reservation.getId() + ".pdf";
    }

    public void createPdf() throws IOException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        //Initialize PDF writer
        FileOutputStream fos = new FileOutputStream(DEST);
        PdfWriter writer = new PdfWriter(fos);

        //Initialize PDF writer
        PdfDocument pdf = new PdfDocument(writer);

        //Initialize document and font
        Document document = new Document(pdf, PageSize.A6);
        PdfFont russian = PdfFontFactory.createFont(
                "src/main/webapp/WEB-INF/static/fonts/18028.ttf",
                "CP1251", true);
        document.setFont(russian);
        document.setFontSize(11);
        //Add paragraph to the content
        Config config = Config.getInstance();

        document.add(
                new Paragraph("****************************************")
                        .add(new Text("ГОСТИНИЦА " + config.getName().toUpperCase()).setFontSize(8))
                        .add(new Text("****************************************"))
        );

        document.setFontSize(9);
        Table table = new Table(2, false);
        table.addCell("ID бронирования");
        table.addCell("" + reservation.getId());
        table.addCell("UUID");
        table.addCell(reservation.getUUID());
        table.addCell("Дата приезда");
        table.addCell("" + reservation.getArrival_date());
        table.addCell("Дата выезда");
        table.addCell("" + reservation.getDeparture_date());
        table.addCell("Ваше ФИО");
        table.addCell(reservation.getFull_name());
        table.addCell("Ваш телефон");
        table.addCell(reservation.getCustomer_phone());
        table.addCell("Номер апартамента, в который Вас заселят");
        table.addCell("" + reservation.getApartment_id());

        document.add(table);

        Paragraph preface = new Paragraph("НАШИ КОНТАКТЫ");
        preface.setTextAlignment(TextAlignment.CENTER);

        document.add(
                new Paragraph("НАШИ КОНТАКТЫ\n").setFontSize(12)
                        .add(new Text("Адрес: " + config.getAddress() + "\n").setFontSize(9))
                        .add(new Text("Телефон: " + config.getPhone() + "\n").setFontSize(9))
                        .add(new Text("Email: " + config.getEmail() + "\n").setFontSize(9))
        );

        //Close document
        document.close();

        openPdf();
    }

    public void openPdf() throws IOException {
        File pdfFile = new File(DEST);
        if (pdfFile.exists()) {

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {
                System.out.println("Awt Desktop is not supported!");
            }

        } else {
            System.out.println("File is not exists!");
        }

        System.out.println("Done");
    }
}