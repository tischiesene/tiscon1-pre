package tiscon1.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * 音楽情報格納用Model。
 * todo 形なんとかする。
 * @author fujiwara
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Music implements Serializable {
    @NonNull
    private String id;

    private String title;

    private String image;

    private String artist;

    private String album;

    private String price;

    private String genre;

    private String releaseDate;
}
