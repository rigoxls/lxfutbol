package com.lxfutbol.transformSoap.config;

import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import feign.jaxb.JAXBContextFactory;
import feign.soap.SOAPDecoder;
import org.springframework.context.annotation.Bean;
import org.w3c.dom.Document;

import com.lxfutbol.transformSoap.dto.TransportResult;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static javax.xml.soap.SOAPConstants.DEFAULT_SOAP_PROTOCOL;


public class TransportClientConfiguration  {

    private Charset charsetEncoding = StandardCharsets.UTF_8;
    private String soapProtocol = DEFAULT_SOAP_PROTOCOL;

    private static final JAXBContextFactory jaxbContextFactory = new JAXBContextFactory.Builder()
            .withMarshallerJAXBEncoding("UTF-8")
            .build();

    @Bean
    public Encoder feignEncoder() {
        return (object, bodyType, template) -> {
            if (!(bodyType instanceof Class)) {
                throw new UnsupportedOperationException(
                        "SOAP only supports encoding raw types. Found " + bodyType);
            }
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                Marshaller marshaller = jaxbContextFactory.createMarshaller((Class<?>) bodyType);
                marshaller.marshal(object, document);
                SOAPMessage soapMessage = MessageFactory.newInstance(soapProtocol).createMessage();
                SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
                soapEnvelope.addNamespaceDeclaration("typ", "http://services.aa.com/types/");
                soapMessage.setProperty(SOAPMessage.CHARACTER_SET_ENCODING, charsetEncoding.displayName());
                soapMessage.getSOAPBody().addDocument(document);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                soapMessage.writeTo(bos);
                template.body(new String(bos.toByteArray()).replaceAll("searchFlightElement", "typ:searchFlightElement"));
            } catch (SOAPException | JAXBException | ParserConfigurationException | IOException | TransformerFactoryConfigurationError e) {
                throw new EncodeException(e.toString(), e);
            }
        };
    }

    @Bean
    public Decoder feignDecoder() {
        return new SOAPDecoder(jaxbContextFactory);    
       }
    
	}
	
  
