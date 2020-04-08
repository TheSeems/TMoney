package me.theseems.tmoney;

public class JDBCConfig {
  private String url;
  private String user;
  private String password;
  private String prefix;

  public JDBCConfig(String url, String user, String password, String prefix) {
    this.url = url;
    this.user = user;
    this.password = password;
    this.prefix = prefix;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPrefix() {
    return prefix == null ? "" : prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }
}
