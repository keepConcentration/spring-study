package com.waw.core;

import com.waw.core.discount.DiscountPolicy;
import com.waw.core.discount.FixDiscountPolicy;
import com.waw.core.discount.RateDiscountPolicy;
import com.waw.core.member.MemberRepository;
import com.waw.core.member.MemberService;
import com.waw.core.member.MemberServiceImpl;
import com.waw.core.member.MemoryMemberRepository;
import com.waw.core.order.OrderService;
import com.waw.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
