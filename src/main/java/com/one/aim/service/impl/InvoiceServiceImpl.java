package com.one.aim.service.impl;


import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.one.aim.service.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Value("${invoice.template.path}")
    private String templatePath;

    private final ResourceLoader resourceLoader;

    public InvoiceServiceImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public String downloadInvoice() throws Exception {

        Resource resource = resourceLoader.getResource("classpath:" + templatePath);
        String htmlContent = Files.readString(resource.getFile().toPath());

        String productRows =
                        """
                                            <tr>
                                                <td style="padding: 8px; border: 1px solid #ddd;">Wireless Mouse</td>
                                                <td style="padding: 8px; border: 1px solid #ddd;">1</td>
                                                <td style="padding: 8px; border: 1px solid #ddd;">$25.00</td>
                                                <td style="padding: 8px; border: 1px solid #ddd;">$25.00</td>
                                            </tr>
                                        """;
        // htmlContent = htmlContent.replace("${productRows}", productRows);

        // 2. Replace placeholders with dynamic values
        htmlContent = htmlContent.replace("${orderNumber}", "123-4567890-1234567")
                        .replace("${orderDate}", "May 25, 2025")
                        .replace("${invoiceDate}", "May 25, 2025")
                        .replace("${invoiceNumber}", "INV-00012345")
                        .replace("${billingName}", "John Doe")
                        .replace("${billingAddress}", "1234 Elm Street<br/>Springfield, IL 62704")
                        .replace("${shippingName}", "John Doe")
                        .replace("${shippingAddress}", "1234 Elm Street<br/>Springfield, IL 62704")
                        .replace("${productRows}", productRows).replace("${subtotal}", "$25.00")
                        .replace("${tax}", "$2.50").replace("${grandTotal}", "$27.50");
        return htmlContent;
    }

}

