package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.domain.institution.Institution;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.Month;
import java.time.Year;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name="house_fund",
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"year","month","institution_id"}
                )
        }
)
public class HouseFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "year")
    private Year year;

    @Column(name = "month")
    private Month month;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @Column
    private Long amount;


    @Builder
    public HouseFund(Integer year, Integer month, Institution institution, Long amount) {
        Assert.notNull(institution, "institution가 Null 입니다.");

        this.year = Year.of(year);
        this.month = Month.of(month);
        this.institution = institution;
        this.amount = amount;
    }
}
