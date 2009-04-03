package com.bizo.xml.simple;

import java.io.InputStream;
import java.util.LinkedList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

public final class SimpleXmlParser<T> {
  private final Consumer<T> c;
  private final Class<T> cl;

  public SimpleXmlParser(final Consumer<T> c, final Class<T> cl) {
    this.c = c;
    this.cl = cl;
  }

  public void parse(final InputStream in) throws Exception {
    final XMLStreamReader r = XMLInputFactory.newInstance().createXMLStreamReader(in);

    final Model<T> model = new Model(cl);
    T bean = cl.newInstance();

    final LinkedList<String> seen = new LinkedList<String>();

    boolean inBean = false;

    while (r.hasNext()) {
      final int next = r.next();
      String name;
      switch (next) {
      case XMLStreamConstants.START_ELEMENT:
        name = r.getLocalName();
        if (model.getBeanName().equals(name)) {
          inBean = true;
        } else if (inBean) {
          seen.add(name);
        }

        break;
      case XMLStreamConstants.CHARACTERS:
        if (inBean) {
          if (model.need(seen)) {
            model.set(seen, bean, r.getText());
          }
        }
        break;

      case XMLStreamConstants.END_ELEMENT:
        name = r.getLocalName();
        if (model.getBeanName().equals(name)) {
          this.c.handle(bean);
          bean = this.cl.newInstance();
          seen.clear();
          inBean = false;
        } else if (inBean) {
          if (seen.size() > 0) {
            seen.removeLast();
          }
        }

        break;
      }
    }
    
    r.close();
  }
}
