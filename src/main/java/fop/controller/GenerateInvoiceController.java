package fop.controller;

import fop.model.InvoiceForm;
import fop.service.GenerateInvoiceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api
@RestController
public class GenerateInvoiceController {

    @Autowired
    private GenerateInvoiceService service;

    @ApiOperation(value = "Generate Invoice")
    @RequestMapping(path = "/api/invoice", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPDF(@RequestBody InvoiceForm form) throws IOException {

        service.generateInvoice(form);

        Path pdfPath = Paths.get("./src/main/resources/pdf/invoice.pdf");

        byte[] contents = Files.readAllBytes(pdfPath);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = "output.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

}
