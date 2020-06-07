package com.score.domain.dto;

import java.io.Serializable;
import java.util.Objects;

public class ErrorDTO implements Serializable {

  protected static final long serialVersionUID = 1L;

  private Integer code;
  private String message;

  public ErrorDTO() {}

  public ErrorDTO(Integer code, String message) {
    this.code = code;
    this.message = message;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorDTO errorDTO = (ErrorDTO) o;
    return Objects.equals(code, errorDTO.code) && Objects.equals(message, errorDTO.message);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message);
  }

  @Override
  public String toString() {
    return "ErrorDTO{" + "code=" + code + ", message='" + message + '\'' + '}';
  }
}
