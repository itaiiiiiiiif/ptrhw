package org.example.rest.models;

public class User {

  public long _id;
  public String name;

  public User() {
    this._id = 0L;
    this.name = "";
  }

  public User(long _id, String name) {
    this._id = _id;
    this.name = name;
  }

  public long get_id() {
    return _id;
  }

  public void set_id(long _id) {
    this._id = _id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
