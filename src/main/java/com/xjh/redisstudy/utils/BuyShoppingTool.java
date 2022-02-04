package com.xjh.redisstudy.utils;

import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author xjh
 * @date 2022/2/2 13:05
 * redis秒杀工具
 */

public class BuyShoppingTool {


    @Resource
    private RedisTemplate<String, String> redisTemplate;


    /**
     *
     * @param uid 用户Id
     * @param prodid 商品Id
     * @return
     */
    public boolean doSecKill(String uid, String prodid){
        //1.uid和prodid非空判断
        if (uid == null || prodid == null){
            return false;
        }

        //2.连接redis

        //3.拼接库存
        //3.1 库存key
        String shopkey = "sk:" + prodid + ":qt";

        //3.2 秒杀成功用户key
        String userKey = "sk:" + prodid + ":user";

        //4.获取库存,如果库存为null,表明秒杀还没有开始
        String shopping = redisTemplate.opsForValue().get(shopkey);
        if (shopping == null){
            System.out.println("秒杀活动尚未开始,请等待");
            redisTemplate.killClient("192.168.171.129",6379);
            return false;
        }

        //5.判断用户是否重复秒杀操作,这是set操作
        Boolean member = redisTemplate.opsForSet().isMember(userKey, uid);
        if (Boolean.TRUE.equals(member)){
            System.out.println("已经秒杀成功了,不能重复秒杀");
            redisTemplate.killClient("192.168.171.129",6379);
            return false;
        }

        //6.判断如果商品数量,库存数量小于1,秒杀结束
        if (Integer.parseInt(shopping) <= 0){
            System.out.println("商品数量为0,秒杀活动已经结束");
            redisTemplate.killClient("192.168.171.129",6379);
            return false;
        }

        //7. 秒杀过程
        //7.1 库存-1
        redisTemplate.opsForValue().decrement(shopkey);

        //7.2 把秒杀成功用户添加到清单里面
        redisTemplate.opsForSet().add(userKey,uid);
        System.out.println("秒杀成功了....");
        redisTemplate.killClient("192.168.171.129",6379);
        return true;
    }
}
