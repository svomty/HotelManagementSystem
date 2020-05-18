package com.svintsitski.hotel_management_system.model.report;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.svintsitski.hotel_management_system.model.Config;
import com.svintsitski.hotel_management_system.model.database.Apartment;
import com.svintsitski.hotel_management_system.model.database.ApartmentType;
import com.svintsitski.hotel_management_system.model.database.Reservation;
import com.svintsitski.hotel_management_system.service.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;


public class ReservationPdfGeneration {

    public String DEST;
    private static final String DIRECTORY = "results/";
    Reservation reservation;
    Apartment apartment;
    ApartmentType apartmentType;
    int fontSize = 9;
    int fontSize2 = 7;

    @Autowired
    private ReservationServiceImpl reservationService;

    public ReservationPdfGeneration(Reservation reservation, Apartment apartment, ApartmentType apartmentType) {

        this.reservation = reservation;
        this.apartment = apartment;
        this.apartmentType = apartmentType;

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

        PdfFont Vinnytsia = PdfFontFactory.createFont(
                "src/main/webapp/WEB-INF/static/fonts/Vinnytsia.ttf",
                "CP1251", true);

        PdfFont pixel = PdfFontFactory.createFont("src/main/webapp/WEB-INF/static/fonts/pixel.ttf",
                "CP1251", true);

        document.setFont(Vinnytsia);
        document.setFontSize(fontSize2 + 1);
        //Add paragraph to the content
        Config config = Config.getInstance();

        document.add(
                new Paragraph()
                        .add(new Text("------\tГОСТИНИЦА\t" + config.getName().toUpperCase() + "\t------").setFont(pixel).setFontSize(8))
        );

        document.setFontSize(fontSize);
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

        Date date1 = reservation.getArrival_date();
        Date date2 = reservation.getDeparture_date();

        long milliseconds = date2.getTime() - date1.getTime();
        int days = (int) (milliseconds / (24 * 60 * 60 * 1000));

        document.setFont(pixel);
        document.setFontSize(fontSize2);

        document.add(
                new Paragraph("")
                        .add(new Text("Кол-во\tдней:\t" + days + "\n"))
                        .add(new Text("Стоимость\tсуток:\t" + apartmentType.getPrice() + " BYN\n"))
                        .add(new Text("Количество\tмест:\t" + apartmentType.getPlaces_number() + "\n"))
                        .add(new Text("\nИТОГО\tК\tОПЛАТЕ:\t" + days * apartmentType.getPrice() * apartmentType.getPlaces_number() + " BYN\n").setFontSize(fontSize))
        );

        document.setFont(Vinnytsia);
        document.setFontSize(fontSize2 + 1);

        document.add(
                new Paragraph("\nНАШИ КОНТАКТЫ\n").setFontSize(fontSize + 3)
                        .add(new Text("Адрес: " + config.getAddress() + "\n").setFontSize(fontSize2 + 2))
                        .add(new Text("Телефон: " + config.getPhone() + "\n").setFontSize(fontSize2 + 2))
                        .add(new Text("Email: " + config.getEmail() + "\n").setFontSize(fontSize2 + 2))
                        .setTextAlignment(TextAlignment.RIGHT)
                        .setHorizontalAlignment(HorizontalAlignment.RIGHT)
        );

        document.close();

        openPdf();
    }

    public void openPdf() throws IOException {
        File pdfFile = new File(DEST);
        if (pdfFile.exists()) {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            }
        }
    }
}