package work5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MiniMarketProductsResult {
    private Long id;
    private String title;
    private Integer price;
    private String categoryTitle;
}