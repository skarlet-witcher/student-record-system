package cc.orangejuice.srs.auth.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response implements Serializable {
    private static final long serialVersionUID = 4956562018365579081L;

    public static final Integer STATUS_SUCCESS = 0;
    public static final Integer STATUS_FAILED = -1;

    @Builder.Default
    private Integer status = 0;

    @Builder.Default
    private String msg = "";

    private Object data;

    public void failed(String msg) {
        this.setStatus(STATUS_FAILED);
        this.setMsg(msg);
    }
}