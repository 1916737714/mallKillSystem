package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;
import lombok.Data;


import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
public class SecKill {


    private Integer secKillId;

    @NotNull(message = "商品id不能为空")
    private Integer goodsId;

    @NotNull(message = "秒杀价不能为空")
    @DecimalMin("0")
    private BigDecimal secKillPrice;

    @NotNull(message = "秒杀开始时间不能为空")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @NotNull(message = "秒杀结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private Integer limitNum;

//    @Min(value = 1,message = "秒杀商品库存必须大于0")
    private Integer secKillStock;
    

}
