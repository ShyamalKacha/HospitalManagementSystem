package com.hospital.service;

import com.hospital.model.*;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfReportService {

    public byte[] generatePrescriptionReport(Prescription prescription) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            document.add(new Paragraph("HOSPITAL MANAGEMENT SYSTEM")
                .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("PRESCRIPTION REPORT")
                .setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Patient: " + prescription.getPatient().getName())
                .setFontSize(12));
            document.add(new Paragraph("Doctor: Dr. " + prescription.getDoctor().getName())
                .setFontSize(12));
            document.add(new Paragraph("Specialty: " + prescription.getDoctor().getSpecialty())
                .setFontSize(12));
            document.add(new Paragraph("Date: " + prescription.getDate())
                .setFontSize(12));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("MEDICINES")
                .setBold().setFontSize(12));
            document.add(new Paragraph(prescription.getMedicines())
                .setFontSize(11));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("DOSAGE")
                .setBold().setFontSize(12));
            document.add(new Paragraph(prescription.getDosage())
                .setFontSize(11));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("INSTRUCTIONS")
                .setBold().setFontSize(12));
            document.add(new Paragraph(prescription.getInstructions())
                .setFontSize(11));
            document.add(new Paragraph("\n\n"));
            
            document.add(new Paragraph("=====================================")
                .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("This is a computer-generated prescription.")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return outputStream.toByteArray();
    }

    public byte[] generateMedicalRecordReport(MedicalRecord record) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            document.add(new Paragraph("HOSPITAL MANAGEMENT SYSTEM")
                .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("MEDICAL RECORD REPORT")
                .setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Patient: " + record.getPatient().getName())
                .setFontSize(12));
            document.add(new Paragraph("Doctor: Dr. " + record.getDoctor().getName())
                .setFontSize(12));
            document.add(new Paragraph("Specialty: " + record.getDoctor().getSpecialty())
                .setFontSize(12));
            document.add(new Paragraph("Date: " + record.getDate())
                .setFontSize(12));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("DIAGNOSIS")
                .setBold().setFontSize(12));
            document.add(new Paragraph(record.getDiagnosis())
                .setFontSize(11));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("NOTES")
                .setBold().setFontSize(12));
            document.add(new Paragraph(record.getNotes() != null ? record.getNotes() : "No additional notes")
                .setFontSize(11));
            document.add(new Paragraph("\n\n"));
            
            document.add(new Paragraph("=====================================")
                .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("This is a computer-generated medical record.")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return outputStream.toByteArray();
    }

    public byte[] generatePatientPrescriptionsReport(Patient patient, List<Prescription> prescriptions) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            document.add(new Paragraph("HOSPITAL MANAGEMENT SYSTEM")
                .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("PATIENT PRESCRIPTIONS REPORT")
                .setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Patient: " + patient.getName())
                .setFontSize(12));
            document.add(new Paragraph("Age: " + patient.getAge() + " | Gender: " + patient.getGender())
                .setFontSize(12));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Total Prescriptions: " + prescriptions.size())
                .setBold().setFontSize(12));
            document.add(new Paragraph("\n"));
            
            for (Prescription rx : prescriptions) {
                document.add(new Paragraph("---").setFontSize(10));
                document.add(new Paragraph("Date: " + rx.getDate())
                    .setFontSize(11));
                document.add(new Paragraph("Doctor: Dr. " + rx.getDoctor().getName())
                    .setFontSize(11));
                document.add(new Paragraph("Medicines: " + rx.getMedicines())
                    .setFontSize(11));
                document.add(new Paragraph("Dosage: " + rx.getDosage())
                    .setFontSize(11));
                document.add(new Paragraph("Instructions: " + rx.getInstructions())
                    .setFontSize(11));
                document.add(new Paragraph("\n"));
            }
            
            document.add(new Paragraph("=====================================")
                .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Generated by Hospital Management System")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return outputStream.toByteArray();
    }

    public byte[] generatePatientMedicalRecordsReport(Patient patient, List<MedicalRecord> records) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            document.add(new Paragraph("HOSPITAL MANAGEMENT SYSTEM")
                .setBold().setFontSize(18).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("PATIENT MEDICAL RECORDS REPORT")
                .setBold().setFontSize(14).setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Patient: " + patient.getName())
                .setFontSize(12));
            document.add(new Paragraph("Age: " + patient.getAge() + " | Gender: " + patient.getGender())
                .setFontSize(12));
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Total Records: " + records.size())
                .setBold().setFontSize(12));
            document.add(new Paragraph("\n"));
            
            for (MedicalRecord record : records) {
                document.add(new Paragraph("---").setFontSize(10));
                document.add(new Paragraph("Date: " + record.getDate())
                    .setFontSize(11));
                document.add(new Paragraph("Doctor: Dr. " + record.getDoctor().getName())
                    .setFontSize(11));
                document.add(new Paragraph("Diagnosis: " + record.getDiagnosis())
                    .setFontSize(11));
                document.add(new Paragraph("Notes: " + (record.getNotes() != null ? record.getNotes() : "N/A"))
                    .setFontSize(11));
                document.add(new Paragraph("\n"));
            }
            
            document.add(new Paragraph("=====================================")
                .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Generated by Hospital Management System")
                .setFontSize(10).setTextAlignment(TextAlignment.CENTER));
            
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return outputStream.toByteArray();
    }
}