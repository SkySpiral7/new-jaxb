package com.example.mindemo;

import com.example.xml.example.MyRequest;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class XmlSerializationUtilTest {

  private final XmlSerializationUtil testObject = new XmlSerializationUtil();

  @Test
  void convertXmlToJaxb() {
    final String xml =
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
            + "<ns1:MyRequest xmlns:ns1=\"http://example.com/xml/example\">\n"
            + "   <FieldName1>hi</FieldName1>\n"
            + "</ns1:MyRequest>\n";

    final MyRequest request = testObject.convertXmlToJaxb(xml);

    assertNotNull(request);
  }

  @Test
  void xml_equals_via_round_trip() {
    final MyRequest request1 = new MyRequest();
    request1.setFieldName1("hi");

    final String xml = testObject.convertJaxbToXml(request1);
    final MyRequest request2 = testObject.convertXmlToJaxb(xml);

    assertEquals(request1, request2);
  }

  private static class XmlSerializationUtil {

    private final Jaxb2Marshaller marshaller;

    public XmlSerializationUtil() {
      this.marshaller = new Jaxb2Marshaller();
      marshaller.setPackagesToScan("com.example");
    }

    public String convertJaxbToXml(final Object obj) {
      final StringWriter writer = new StringWriter();
      marshaller.marshal(obj, new StreamResult(writer));

      return (writer.toString().replace("><", (">" + System.getProperty("line.separator") + "<")));
      // although still not formatted the end lines make it much easier to read
    }

    public <T> T convertXmlToJaxb(final String xml) {
      StreamSource source = new StreamSource(new StringReader(xml));
      @SuppressWarnings("unchecked")
      T result = (T) marshaller.unmarshal(source);
      return result;
    }
  }
}
