package indi.yuluo.xojbackgroundmanagmentsystem.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: yuluo
 * @CreateTime: 2022-08-28  10:26
 * @Description: 在插入和删除时自动填充更新时间和
 */

@Slf4j
@Component
public class MPMeatObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // log.info("公共字段自动填充【insert】");
        // log.info(metaObject.toString());
        // metaObject.setValue("gmtCreate", LocalDateTime.now());
        // metaObject.setValue("gmtModified", LocalDateTime.now());

        // metaObject.setValue("author", BaseContext.getCurrent());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // metaObject.setValue("author", BaseContext.getCurrent());
        // metaObject.setValue("gmtModified", LocalDateTime.now());
    }

}
