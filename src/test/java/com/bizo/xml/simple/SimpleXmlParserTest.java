/**
 * Copyright 2009 Bizo, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bizo.xml.simple;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

public final class SimpleXmlParserTest {
  
  @Test
  public void test() throws Exception {
    final CollectingConsumer consumer = new CollectingConsumer();
    final SimpleXmlParser<Company> parser = new SimpleXmlParser<Company>(consumer, Company.class);
    parser.parse(getInputStream("3_companies.xml"));

    final List<Company> companies = consumer.getCompanies();
    
    Assert.assertEquals(3, companies.size());

    checkCompany(companies.get(0), "IBM", Lists.newArrayList("ibm.com"), "NY");
    checkCompany(companies.get(1), "Microsoft", Lists.newArrayList("microsoft.com", "msn.com"), "WA");
    checkCompany(companies.get(2), "Amazon", Lists.newArrayList("amazon.com", "amazonaws.com"), "WA");
  }
  
  private void checkCompany(final Company c, final String name, final List<String> domains, final String state) {
    Assert.assertEquals(name, c.getName());
    Assert.assertEquals(domains, c.getDomains());
    Assert.assertEquals(state, c.getState());
  }
  
  private InputStream getInputStream(final String name) throws Exception {
    final URL url = SimpleXmlParser.class.getClassLoader().getResource(name);
    
    return url.openStream();
  }
}
