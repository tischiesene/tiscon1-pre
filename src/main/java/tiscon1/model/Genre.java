package tiscon1.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author kawasima
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Genre implements Serializable {
    @NonNull
    private String id;

    @NonNull
    private String name;

    @NonNull
    private String url;
}
