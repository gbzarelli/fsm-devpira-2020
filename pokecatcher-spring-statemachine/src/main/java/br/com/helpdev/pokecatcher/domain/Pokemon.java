package br.com.helpdev.pokecatcher.domain;

import java.io.Serializable;

public class Pokemon implements Serializable {

  private final Integer id;
  private final String name;
  private final Integer level;

  public Pokemon(final Integer id, final String name, final Integer level) {
    this.id = id;
    this.name = name;
    this.level = level;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getLevel() {
    return level;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    return id.equals(((Pokemon) o).id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Pokemon{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", level=" + level +
        '}';
  }
}
