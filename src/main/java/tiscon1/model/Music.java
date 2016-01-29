package tiscon1.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音楽情報格納用Model。
 *
 * @author fujiwara
 */
@Data
@NoArgsConstructor
public class Music extends Item {
    private String artist;
    private String album;
}
