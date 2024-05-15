package za.co.vodacom.cvm.client.wigroup.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.annotation.Generated;
import java.util.Objects;

/**
 * Categories
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-05-10T13:34:34.750589900+02:00[Africa/Johannesburg]")
public class Categories {

  @JsonProperty("name")
  private String name;

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("rank")
  private Integer rank;

  public Categories name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
  */

  @Schema(name = "name", required = false)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Categories id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */

  @Schema(name = "id", required = false)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Categories rank(Integer rank) {
    this.rank = rank;
    return this;
  }

  /**
   * Get rank
   * @return rank
  */

  @Schema(name = "rank", required = false)
  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Categories categories = (Categories) o;
    return Objects.equals(this.name, categories.name) &&
        Objects.equals(this.id, categories.id) &&
        Objects.equals(this.rank, categories.rank);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, id, rank);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Categories {\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    rank: ").append(toIndentedString(rank)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

