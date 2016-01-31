package tiscon1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 映画情報格納用Model。
 *
 * @author fujiwara
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Movie extends Item {
    private String summary;
}
