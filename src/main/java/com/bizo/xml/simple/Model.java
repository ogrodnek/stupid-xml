package com.bizo.xml.simple;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class Model<T> {
  private final Map<List<String>, Field> m = new HashMap<List<String>, Field>();

  private final String beanName;

  public Model(final Class<T> c) {
    this.beanName = c.getAnnotation(XmlPath.class).path();

    for (final Field f : c.getDeclaredFields()) {
      final XmlPath p = f.getAnnotation(XmlPath.class);
      if (p != null) {
        final String[] parts = p.path().split("/");
        this.m.put(Arrays.asList(parts), f);
        if (!f.isAccessible()) {
          f.setAccessible(true);
        }
      }
    }
  }

  public boolean need(final List<String> paths) {
    return this.m.containsKey(paths);
  }

  public void set(final List<String> paths, final T bean, final String data) throws Exception {
    final Field f = this.m.get(paths);

    if (f == null) {
      throw new IllegalArgumentException("Not assignable");
    }

    if (List.class.isAssignableFrom(f.getType())) {
      List l = (List) f.get(bean);
      if (l == null) {
        l = new ArrayList();
        f.set(bean, l);
      }
      l.add(data);
      return;
    }

    f.set(bean, data);
  }

  public String getBeanName() {
    return this.beanName;
  }
}
