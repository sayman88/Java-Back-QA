package work3;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Amounts {
    private Double sourceAmount;
    private String sourceUnit;
    private Double targetAmount;
    private String targetUnit;
    private String answer;

}