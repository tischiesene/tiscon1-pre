package tiscon1.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 映画情報格納用Model。
 * todo 形なんとかする。
 * @author fujiwara
 */
@Data
@NoArgsConstructor
public class Movie extends Item {
    private String summary;
}
