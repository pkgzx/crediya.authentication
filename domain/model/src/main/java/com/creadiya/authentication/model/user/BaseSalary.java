package com.creadiya.authentication.model.user;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public class BaseSalary {
    private Currency currency;
    private BigDecimal value;

  public BaseSalary(Currency currency, BigDecimal value) {
    this.currency = currency;
    this.value = value;
  }

  public BaseSalary() {
  }

  private BaseSalary(Builder builder) {
    setCurrency(builder.currency);
    setValue(builder.value);
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = currency;
  }

  public BigDecimal getValue() {
    return value;
  }

  public void setValue(BigDecimal value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    BaseSalary that = (BaseSalary) o;
    return Objects.equals(getCurrency(), that.getCurrency()) && Objects.equals(getValue(), that.getValue());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getCurrency(), getValue());
  }

  @Override
  public String toString() {
    return "BaseSalary{" +
      "currency=" + getCurrency() +
      ", value=" + getValue() +
      '}';
  }


  /**
   * {@code BaseSalary} builder static inner class.
   */
  public static final class Builder {
    private Currency currency;
    private BigDecimal value;

    public Builder() {

    }

    /**
     * Sets the {@code currency} and returns a reference to this Builder enabling method chaining.
     *
     * @param currency the {@code currency} to set
     * @return a reference to this Builder
     */
    public Builder currency(Currency currency) {
      this.currency = currency;
      return this;
    }

    /**
     * Sets the {@code value} and returns a reference to this Builder enabling method chaining.
     *
     * @param value the {@code value} to set
     * @return a reference to this Builder
     */
    public Builder value(BigDecimal value) {
      this.value = value;
      return this;
    }

    /**
     * Returns a {@code BaseSalary} built from the parameters previously set.
     *
     * @return a {@code BaseSalary} built with parameters of this {@code BaseSalary.Builder}
     */
    public BaseSalary build() {
      return new BaseSalary(this);
    }
  }
}
