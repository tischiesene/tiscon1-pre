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
@RequiredArgsConstructor
public class Movie implements Serializable {
    @NonNull
    private String id;

    private String title;

    private String image;

    private String summary;

    private String price;

    private String genre;

    private String releaseDate;
}
