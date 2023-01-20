package fop.service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import fop.model.InvoiceForm;
import fop.model.OrderItem;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXMLFileJava {

    public static final String xmlFilePath = "src/main/resources/xml/Invoice.xml";


    public void createXML(InvoiceForm invoiceForm) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("invoice");
            document.appendChild(root);

            Element customerName = document.createElement("customerName");
            customerName.appendChild(document.createTextNode(invoiceForm.getCustomerName()));
            root.appendChild(customerName);

            Element orderId = document.createElement("orderId");
            orderId.appendChild(document.createTextNode(invoiceForm.getOrderId().toString()));
            root.appendChild(orderId);

            Element companyName = document.createElement("companyName");
            companyName.appendChild(document.createTextNode("Increff"));
            root.appendChild(companyName);

            Element building = document.createElement("building");
            building.appendChild(document.createTextNode("Enzyme"));
            root.appendChild(building);

            Element street = document.createElement("street");
            street.appendChild(document.createTextNode("5th main street"));
            root.appendChild(street);

            Element city = document.createElement("city");
            city.appendChild(document.createTextNode("Bangaluru,"));
            root.appendChild(city);

            Element invoiceDate = document.createElement("invoiceDate");
            invoiceDate.appendChild(document.createTextNode(invoiceForm.getPlaceDate().substring(0, 10)));
            root.appendChild(invoiceDate);

            Element invoiceTime = document.createElement("invoiceTime");
            invoiceTime.appendChild(document.createTextNode(invoiceForm.getPlaceDate().substring(11, 19)));
            root.appendChild(invoiceTime);

            System.out.println(invoiceForm.getPlaceDate());

            Element orderItems = document.createElement("orderItems");
            root.appendChild(orderItems);
            Double amount = 0.0;
            int index = 1;
            // order item element
            for (OrderItem o : invoiceForm.getOrderItemList()){
                Element orderItem = document.createElement("orderItem");

                orderItems.appendChild(orderItem);

//                serial number
                Element sn = document.createElement("sn");
                sn.appendChild(document.createTextNode(String.valueOf(index)));
                orderItem.appendChild(sn);

                // set an attribute to staff element
                Element id = document.createElement("id");
                id.appendChild(document.createTextNode(o.getOrderItemId().toString()));
                orderItem.appendChild(id);

                // firstname element
                Element productName = document.createElement("productName");
                productName.appendChild(document.createTextNode(o.getProductName()));
                orderItem.appendChild(productName);

                // lastname element
                Element quantity = document.createElement("quantity");
                quantity.appendChild(document.createTextNode(o.getQuantity().toString()));
                orderItem.appendChild(quantity);

                Element sellingPrice = document.createElement("sellingPrice");
                sellingPrice.appendChild(document.createTextNode(o.getSellingPrice().toString()));
                orderItem.appendChild(sellingPrice);

                Element total = document.createElement("total");
                total.appendChild(document.createTextNode(String.valueOf(o.getSellingPrice() * o.getQuantity())));
                orderItem.appendChild(total);

                amount += o.getSellingPrice() * o.getQuantity();
                index++;
            }

            Element totalAmount = document.createElement("totalAmount");
            totalAmount.appendChild(document.createTextNode(String.valueOf(amount)));
            root.appendChild(totalAmount);
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(xmlFilePath));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }
}