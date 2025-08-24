package com.creadiya.authentication.model.typeIdentifaction;


import java.util.Objects;

public class TypeIdentifaction {
    private Long id;
    private String name;

  public TypeIdentifaction(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public TypeIdentifaction() {
  }

  private TypeIdentifaction(Builder builder) {
    setId(builder.id);
    setName(builder.name);
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    TypeIdentifaction that = (TypeIdentifaction) o;
    return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName());
  }

  @Override
  public String toString() {
    return "TypeIdentifaction{" +
      "id=" + getId() +
      ", name='" + getName() + '\'' +
      '}';
  }


  /**
   * {@code TypeIdentifaction} builder static inner class.
   */
  public static final class Builder {
    private Long id;
    private String name;

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
     * Returns a {@code TypeIdentifaction} built from the parameters previously set.
     *
     * @return a {@code TypeIdentifaction} built with parameters of this {@code TypeIdentifaction.Builder}
     */
    public TypeIdentifaction build() {
      return new TypeIdentifaction(this);
    }
  }
}
