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

import java.util.List;

/**
 * Test model.
 * 
 * @author larry
 */
@XmlPath(path="company")
public final class Company {
  @XmlPath(path="name")
  private String name;
  
  @XmlPath(path="domains/value")  
  private List<String> domains;
  
  @XmlPath(path="headquarters/address/state")
  private String state;

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public List<String> getDomains() {
    return this.domains;
  }

  public void setDomains(final List<String> domains) {
    this.domains = domains;
  }

  public String getState() {
    return this.state;
  }

  public void setState(final String state) {
    this.state = state;
  }
}
