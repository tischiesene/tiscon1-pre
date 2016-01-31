package tiscon1.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 音楽情報格納用Model。
 *
 * @author fujiwara
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Music extends Item {
    private String artist;
    private String album;
}
