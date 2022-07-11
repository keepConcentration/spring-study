package com.waw.core;

import com.waw.core.member.Grade;
import com.waw.core.member.Member;
import com.waw.core.member.MemberService;
import com.waw.core.member.MemberServiceImpl;
import com.waw.core.order.Order;
import com.waw.core.order.OrderService;
import com.waw.core.order.OrderServiceImpl;

public class OrderApp {
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        Member member = new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order);

    }
}
