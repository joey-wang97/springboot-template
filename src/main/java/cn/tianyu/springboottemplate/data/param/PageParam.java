package cn.tianyu.springboottemplate.data.param;

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
public class PageParam<T> {
    private long size = 10;
    private long current = 1;
    private List<OrderItem> orders;

    public Page<T> toPage() {
        return new Page<T>(current, size);
    }

    public long getOffset() {
        return (this.current - 1) * this.size;
    }
}
