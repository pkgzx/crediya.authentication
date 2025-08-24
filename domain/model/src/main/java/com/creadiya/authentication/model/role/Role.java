package com.creadiya.authentication.model.role;

import java.util.List;
import java.util.Objects;

import com.creadiya.authentication.model.permission.Permission;

public class Role {
  private Long id;
  private String name;
  private String description;
  private List<Permission> permissions;

  public Role(Long id, String name, String description, List<Permission> permissions) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.permissions = permissions;
  }

  public Role() {
  }

  private Role(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setDescription(builder.description);
    setPermissions(builder.permissions);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<Permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<Permission> permissions) {
    this.permissions = permissions;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Role role = (Role) o;
    return Objects.equals(getId(), role.getId()) && Objects.equals(getName(), role.getName()) && Objects.equals(getDescription(), role.getDescription()) && Objects.equals(getPermissions(), role.getPermissions());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getDescription(), getPermissions());
  }

  @Override
  public String toString() {
    return "Role{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", description='" + description + '\'' +
      ", permissions=" + permissions +
      '}';
  }


  /**
   * {@code Role} builder static inner class.
   */
  public static final class Builder {
    private Long id;
    private String name;
    private String description;
    private List<Permission> permissions;

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
     * Sets the {@code name} and returns a reference to this Builder enabling method chaining.
     *
     * @param name the {@code name} to set
     * @return a reference to this Builder
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }

    /**
     * Sets the {@code description} and returns a reference to this Builder enabling method chaining.
     *
     * @param description the {@code description} to set
     * @return a reference to this Builder
     */
    public Builder description(String description) {
      this.description = description;
      return this;
    }

    /**
     * Sets the {@code permissions} and returns a reference to this Builder enabling method chaining.
     *
     * @param permissions the {@code permissions} to set
     * @return a reference to this Builder
     */
    public Builder permissions(List<Permission> permissions) {
      this.permissions = permissions;
      return this;
    }

    /**
     * Returns a {@code Role} built from the parameters previously set.
     *
     * @return a {@code Role} built with parameters of this {@code Role.Builder}
     */
    public Role build() {
      return new Role(this);
    }
  }
}
