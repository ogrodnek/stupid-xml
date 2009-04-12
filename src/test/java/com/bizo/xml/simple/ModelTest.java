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

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;


public final class ModelTest {
  @Test
  public void test() throws Exception {
    Model<Company> m = new Model<Company>(Company.class);
    
    Assert.assertEquals("company", m.getBeanName());
    Assert.assertTrue(m.need(Lists.newArrayList("name")));
    Assert.assertTrue(m.need(Lists.newArrayList("headquarters", "address", "state")));
    Assert.assertFalse(m.need(Lists.newArrayList("headquarters", "address", "city")));
    
    final Company c = new Company();
    
    m.set(Lists.newArrayList("headquarters", "address", "state"), c, "CA");
    
    Assert.assertEquals("CA", c.getState());
    
    m.set(Lists.newArrayList("domains", "value"), c, "abc.com");
    m.set(Lists.newArrayList("domains", "value"), c, "def.net");
    
    Assert.assertEquals(2, c.getDomains().size());
    Assert.assertEquals(Lists.newArrayList("abc.com", "def.net"), c.getDomains());
  }
  
  @Test(expected=RuntimeException.class)
  public void testUnsupported() throws Exception {
    Model<Company> m = new Model<Company>(Company.class);
    m.set(Lists.newArrayList("a", "b"), new Company(), "test");
  }
}
