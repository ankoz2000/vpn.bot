package bot.telegram.vpn.services.vpn.outline.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessKey {
    private int id;
    private String name;
    private String password;
    private int port;
    private String method;
    private DataLimit dataLimit;
    private String accessUrl;
}