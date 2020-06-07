package com.score.domain.dto.request;

import org.springframework.util.Assert;

import java.util.Objects;

// Not using constraint validators due to ObjectMapper fails to map json between types and ignores
// some validations
public class PaginatorRequestDTO {

  private Integer page = 1;
  private Integer size = 10;

  public PaginatorRequestDTO(Integer page, Integer size) {
    this.page = (page != null) ? page : 1;
    this.size = (size != null) ? size : 10;
    Assert.isTrue(this.page > 0, "page must be positive");
    Assert.isTrue(this.size > 0, "size must be positive");
    Assert.isTrue(this.size < 20_000, "size must be lesser then 20 000");
  }

  public Integer getPage() {
    return page;
  }

  public Integer getSize() {
    return size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PaginatorRequestDTO that = (PaginatorRequestDTO) o;
    return Objects.equals(page, that.page) && Objects.equals(size, that.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, size);
  }

  @Override
  public String toString() {
    return "PaginatorRequestDTO{" + "page=" + page + ", size=" + size + '}';
  }
}
