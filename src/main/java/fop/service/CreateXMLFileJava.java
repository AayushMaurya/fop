package fop.service;

import java.io.File;
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

    public static final String xmlFilePath = "D:\\java_projects\\fop\\src\\main\\resources\\xml" +
            "\\Invoice.xml";


    public void createXML(InvoiceForm invoiceForm) {

        try {

            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

            Document document = documentBuilder.newDocument();

            // root element
            Element root = document.createElement("invoice");
            document.appendChild(root);

            Element customer_name = document.createElement("customer_name");
            customer_name.appendChild(document.createTextNode(invoiceForm.getCustomerName()));
            root.appendChild(customer_name);

            Element order_id = document.createElement("order_id");
            order_id.appendChild(document.createTextNode(invoiceForm.getOrderId().toString()));
            root.appendChild(order_id);

            // order item element
            for (OrderItem o : invoiceForm.getOrderItemList()){
                Element order_item = document.createElement("order_item");

                root.appendChild(order_item);

                // set an attribute to staff element
                Element id = document.createElement("id");
                id.appendChild(document.createTextNode("1"));
                order_item.appendChild(id);

                // firstname element
                Element ProductId = document.createElement("product_name");
                ProductId.appendChild(document.createTextNode(o.getProductName()));
                order_item.appendChild(ProductId);

                // lastname element
                Element quantity = document.createElement("quantity");
                quantity.appendChild(document.createTextNode(o.getQuantity().toString()));
                order_item.appendChild(quantity);

                Element sellingPrice = document.createElement("selling_price");
                sellingPrice.appendChild(document.createTextNode(o.getSellingPrice().toString()));
                order_item.appendChild(sellingPrice);

            }
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