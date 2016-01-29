package tiscon1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 映画情報格納用Model。
 *
 * @author fujiwara
 */
@Data
@NoArgsConstructor
public class Movie extends Item {
    private String summary;
}
