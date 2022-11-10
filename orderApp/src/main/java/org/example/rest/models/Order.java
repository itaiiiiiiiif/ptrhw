package org.example.rest.models;

import io.vertx.core.json.JsonObject;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Order {

  public long _id;
  public String name;
  public Date date;

  public Order() {
  }

  public Order(long _id, String name, Date date) {
    this._id = _id;
    this.name = name;
    this.date = date;
  }

  public Order(JsonObject jsonObject) {
    this._id = jsonObject.getLong("_id");
    this.name = jsonObject.getString("name");
    this.date = new Date(jsonObject.getString("date"));
  }
}
