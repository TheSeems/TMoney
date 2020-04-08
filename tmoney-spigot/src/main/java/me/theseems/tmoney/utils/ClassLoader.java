package me.theseems.tmoney.utils;

import com.google.common.base.Suppliers;
import me.theseems.tmoney.TMoneyPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.function.Supplier;

public class ClassLoader {
  private final URLClassLoader classLoader;
  private final Supplier<Method> method;

  public ClassLoader() throws IllegalStateException {
    java.lang.ClassLoader classLoader = TMoneyPlugin.class.getClassLoader();
    if (classLoader instanceof URLClassLoader) {
      this.classLoader = (URLClassLoader) classLoader;
    } else {
      throw new IllegalStateException("Cannot use no class loader other than URLClassLoader");
    }

    this.method =
        Suppliers.memoize(
            () -> {
              try {
                Method addUrlMethod = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                addUrlMethod.setAccessible(true);
                return addUrlMethod;
              } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
              }
            });
  }

  public void put(File file) {
    try {
      this.method.get().invoke(this.classLoader, file.toURI().toURL());
    } catch (IllegalAccessException | InvocationTargetException | MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }
}
