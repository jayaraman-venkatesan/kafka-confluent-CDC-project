package com.notification.model;

public class EventData {

  private int id;
  private String status;
  private int execution_time;
  private String username;
  private String error_desc;
  private String error_code;
  private String flow_id;
  private String event_source_data;
  private boolean __deleted;

  // Default constructor
  public EventData() {}

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public int getExecution_time() {
    return execution_time;
  }

  public void setExecution_time(int execution_time) {
    this.execution_time = execution_time;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getError_desc() {
    return error_desc;
  }

  public void setError_desc(String error_desc) {
    this.error_desc = error_desc;
  }

  public String getError_code() {
    return error_code;
  }

  public void setError_code(String error_code) {
    this.error_code = error_code;
  }

  public String getFlow_id() {
    return flow_id;
  }

  public void setFlow_id(String flow_id) {
    this.flow_id = flow_id;
  }

  public String getEvent_source_data() {
    return event_source_data;
  }

  public void setEvent_source_data(String event_source_data) {
    this.event_source_data = event_source_data;
  }

  public boolean isDeleted() {
    return __deleted;
  }

  public void setDeleted(boolean deleted) {
    this.__deleted = deleted;
  }

}
