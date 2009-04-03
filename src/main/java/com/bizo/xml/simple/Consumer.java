package com.bizo.xml.simple;

public interface Consumer<T> {
  void handle(T o);
}
