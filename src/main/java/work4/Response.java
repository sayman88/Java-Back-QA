package work4;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response {

    private String aisle;

    private Double cost;

    static Long id;

    private Long ingredientId;

    private Measures measures;

    private String name;

    private Boolean pantryItem;

    private List<Object> usageRecipeIds;

    private List<Object> usages;
}