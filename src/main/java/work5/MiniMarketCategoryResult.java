package work5;

import lombok.Data;

import java.util.List;

@Data
public class MiniMarketCategoryResult {
    private Integer id;
    private String title;
    private List<MiniMarketProductsResult> products;

}