package com.waw.core;

import com.waw.core.discount.FixDiscountPolicy;
import com.waw.core.member.MemberService;
import com.waw.core.member.MemberServiceImpl;
import com.waw.core.member.MemoryMemberRepository;
import com.waw.core.order.OrderService;
import com.waw.core.order.OrderServiceImpl;

public class AppConfig {

    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }

    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
}
