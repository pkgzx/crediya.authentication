package com.creadiya.authentication.model.permission;


import java.util.Objects;

public class Permission {
  private Long id;
  private String resource;
  private String action;

  public Permission(Long id, String resource, String action) {
    this.id = id;
    this.resource = resource;
    this.action = action;
  }

  public Permission() {
  }

  private Permission(Builder builder) {
    setId(builder.id);
    setResource(builder.resource);
    setAction(builder.action);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getResource() {
    return resource;
  }

  public void setResource(String resource) {
    this.resource = resource;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Permission that = (Permission) o;
    return Objects.equals(getId(), that.getId()) && Objects.equals(getResource(), that.getResource()) && Objects.equals(getAction(), that.getAction());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getResource(), getAction());
  }

  @Override
  public String toString() {
    return "Permission{" +
      "id=" + id +
      ", resource='" + resource + '\'' +
      ", action='" + action + '\'' +
      '}';
  }


  /**
   * {@code Permission} builder static inner class.
   */
  public static final class Builder {
    private Long id;
    private String resource;
    private String action;

    public Builder() {
    }

    /**
     * Sets the {@code id} and returns a reference to this Builder enabling method chaining.
     *
     * @param id the {@code id} to set
     * @return a reference to this Builder
     */
    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    /**
     * Sets the {@code resource} and returns a reference to this Builder enabling method chaining.
     *
     * @param resource the {@code resource} to set
     * @return a reference to this Builder
     */
    public Builder resource(String resource) {
      this.resource = resource;
      return this;
    }

    /**
     * Sets the {@code action} and returns a reference to this Builder enabling method chaining.
     *
     * @param action the {@code action} to set
     * @return a reference to this Builder
     */
    public Builder action(String action) {
      this.action = action;
      return this;
    }

    /**
     * Returns a {@code Permission} built from the parameters previously set.
     *
     * @return a {@code Permission} built with parameters of this {@code Permission.Builder}
     */
    public Permission build() {
      return new Permission(this);
    }
  }
}
