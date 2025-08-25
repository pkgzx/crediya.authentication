package com.creadiya.authentication.model.user;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.creadiya.authentication.model.role.Role;
import com.creadiya.authentication.model.typeidentification.TypeIdentification;

public class User {
  private UUID id;
  private String name;
  private String lastName;
  private TypeIdentification typeIdentification;
  private String identification;
  private String password;
  private String email;
  private String phone;
  private String address;
  private LocalDate birthday;
  private Role role;
  private BaseSalary baseSalary;

  public User() {
  }

  private User(Builder builder) {
    setId(builder.id);
    setName(builder.name);
    setLastName(builder.lastName);
    typeIdentification = builder.typeIdentification;
    setIdentification(builder.identification);
    setPassword(builder.password);
    setEmail(builder.email);
    setPhone(builder.phone);
    setAddress(builder.address);
    setBirthday(builder.birthday);
    setRole(builder.role);
    setBaseSalary(builder.baseSalary);
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public TypeIdentification getTypeIdentification() {
    return typeIdentification;
  }

  public void setTypeIdentifaction(TypeIdentification typeIdentification) {
    this.typeIdentification = typeIdentification;
  }

  public String getIdentification() {
    return identification;
  }

  public void setIdentification(String identification) {
    this.identification = identification;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public BaseSalary getBaseSalary() {
    return baseSalary;
  }

  public void setBaseSalary(BaseSalary baseSalary) {
    this.baseSalary = baseSalary;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(getId(), user.getId()) && Objects.equals(getName(), user.getName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getTypeIdentification(), user.getTypeIdentification()) && Objects.equals(getIdentification(), user.getIdentification()) && Objects.equals(getPassword(), user.getPassword()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPhone(), user.getPhone()) && Objects.equals(getAddress(), user.getAddress()) && Objects.equals(getBirthday(), user.getBirthday()) && Objects.equals(getRole(), user.getRole()) && Objects.equals(getBaseSalary(), user.getBaseSalary());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId(), getName(), getLastName(), getTypeIdentification(), getIdentification(), getPassword(), getEmail(), getPhone(), getAddress(), getBirthday(), getRole(), getBaseSalary());
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + getId() +
      ", name='" + getName() + '\'' +
      ", lastName='" + getLastName() + '\'' +
      ", typeIdentification=" + getTypeIdentification() +
      ", identification='" + getIdentification() + '\'' +
      ", password='" + getPassword() + '\'' +
      ", email='" + getEmail() + '\'' +
      ", phone='" + getPhone() + '\'' +
      ", address='" + getAddress() + '\'' +
      ", birthday=" + getBirthday() +
      ", role=" + getRole() +
      ", baseSalary=" + getBaseSalary() +
      '}';
  }


  /**
   * {@code User} builder static inner class.
   */
  public static final class Builder {
    private UUID id;
    private String name;
    private String lastName;
    private TypeIdentification typeIdentification;
    private String identification;
    private String password;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthday;
    private Role role;
    private BaseSalary baseSalary;

    public Builder() {
    }

    /**
     * Sets the {@code id} and returns a reference to this Builder enabling method chaining.
     *
     * @param id the {@code id} to set
     * @return a reference to this Builder
     */
    public Builder id(UUID id) {
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
     * Sets the {@code lastName} and returns a reference to this Builder enabling method chaining.
     *
     * @param lastName the {@code lastName} to set
     * @return a reference to this Builder
     */
    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    /**
     * Sets the {@code typeIdentification} and returns a reference to this Builder enabling method chaining.
     *
     * @param typeIdentification the {@code typeIdentification} to set
     * @return a reference to this Builder
     */
    public Builder typeIdentification(TypeIdentification typeIdentification) {
      this.typeIdentification = typeIdentification;
      return this;
    }

    /**
     * Sets the {@code identification} and returns a reference to this Builder enabling method chaining.
     *
     * @param identification the {@code identification} to set
     * @return a reference to this Builder
     */
    public Builder identification(String identification) {
      this.identification = identification;
      return this;
    }

    /**
     * Sets the {@code password} and returns a reference to this Builder enabling method chaining.
     *
     * @param password the {@code password} to set
     * @return a reference to this Builder
     */
    public Builder password(String password) {
      this.password = password;
      return this;
    }

    /**
     * Sets the {@code email} and returns a reference to this Builder enabling method chaining.
     *
     * @param email the {@code email} to set
     * @return a reference to this Builder
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * Sets the {@code phone} and returns a reference to this Builder enabling method chaining.
     *
     * @param phone the {@code phone} to set
     * @return a reference to this Builder
     */
    public Builder phone(String phone) {
      this.phone = phone;
      return this;
    }

    /**
     * Sets the {@code address} and returns a reference to this Builder enabling method chaining.
     *
     * @param address the {@code address} to set
     * @return a reference to this Builder
     */
    public Builder address(String address) {
      this.address = address;
      return this;
    }

    /**
     * Sets the {@code birthday} and returns a reference to this Builder enabling method chaining.
     *
     * @param birthday the {@code birthday} to set
     * @return a reference to this Builder
     */
    public Builder birthday(LocalDate birthday) {
      this.birthday = birthday;
      return this;
    }

    /**
     * Sets the {@code role} and returns a reference to this Builder enabling method chaining.
     *
     * @param role the {@code role} to set
     * @return a reference to this Builder
     */
    public Builder role(Role role) {
      this.role = role;
      return this;
    }

    /**
     * Sets the {@code baseSalary} and returns a reference to this Builder enabling method chaining.
     *
     * @param baseSalary the {@code baseSalary} to set
     * @return a reference to this Builder
     */
    public Builder baseSalary(BaseSalary baseSalary) {
      this.baseSalary = baseSalary;
      return this;
    }

    /**
     * Returns a {@code User} built from the parameters previously set.
     *
     * @return a {@code User} built with parameters of this {@code User.Builder}
     */
    public User build() {
      return new User(this);
    }
  }
}
