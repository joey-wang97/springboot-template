package wang.joye.springboottemplate.data.param;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * @author 汪继友
 * @since 2020/6/15
 */
@ApiModel("分页参数")
@Data
public class PageParam {
    private long size = 10;
    private long current = 1;
    private int aa = 1;
    private List<OrderItem> orders;

    public <T> Page<T> toPage() {
        return new Page<>(current, size);
    }

    public long getOffset() {
        return (this.current - 1) * this.size;
    }
}
