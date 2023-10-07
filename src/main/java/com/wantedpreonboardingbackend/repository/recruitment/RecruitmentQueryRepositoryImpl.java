package com.wantedpreonboardingbackend.repository.recruitment;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wantedpreonboardingbackend.domain.Recruitment;
import com.wantedpreonboardingbackend.dto.recruitment.RecruitmentSearchCond;
import jakarta.persistence.EntityManager;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.wantedpreonboardingbackend.domain.QCompany.company;
import static com.wantedpreonboardingbackend.domain.QRecruitment.recruitment;

public class RecruitmentQueryRepositoryImpl implements RecruitmentQueryRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public RecruitmentQueryRepositoryImpl(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<Recruitment> search(RecruitmentSearchCond searchCond) {
        return query
                .selectFrom(recruitment)
                .join(recruitment.company, company).fetchJoin()
                .where(
                        idEq(searchCond.getRecruitmentId()),
                        companyNameContains(searchCond.getCompanyName()),
                        countryContains(searchCond.getCountry()),
                        regionContains(searchCond.getRegion()),
                        positionContains(searchCond.getPosition()),
                        rewardGoe(searchCond.getReward()),
                        techStackContains(searchCond.getTechStack()),
                        detailContains(searchCond.getDetail())
                )
                .fetch();
    }

    private BooleanExpression idEq(Long id) {
        return id != null ? recruitment.id.eq(id) : null;
    }

    private BooleanExpression companyNameContains(String companyName) {
        return StringUtils.hasText(companyName) ? company.name.contains(companyName) : null;
    }

    private BooleanExpression countryContains(String country) {
        return StringUtils.hasText(country) ? recruitment.country.contains(country) : null;
    }

    private BooleanExpression regionContains(String region) {
        return StringUtils.hasText(region) ? recruitment.region.contains(region) : null;
    }

    private BooleanExpression positionContains(String position) {
        return StringUtils.hasText(position) ? recruitment.position.contains(position) : null;
    }

    private BooleanExpression rewardGoe(Integer reward) {
        return reward != null ? recruitment.reward.goe(reward) : null;
    }

    private BooleanExpression techStackContains(String techStack) {
        return StringUtils.hasText(techStack) ? recruitment.techStack.contains(techStack) : null;
    }

    private BooleanExpression detailContains(String detail) {
        return StringUtils.hasText(detail) ? recruitment.detail.contains(detail) : null;
    }

}
