package com.waw.core.member;

public interface MemberRepository {

    void save(Member member);

    Member findById(Long memberId);
}
