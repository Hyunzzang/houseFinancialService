package com.hyunzzang.financial.house.domain.fund;

import com.hyunzzang.financial.house.common.converter.YearAttributeConverter;
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
                        columnNames={"year","month","inst_type"}
                )
        }
)
public class HouseFund {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Convert(converter = YearAttributeConverter.class)
    @Column(name = "year")
    private Year year;

    @Column(name = "month", columnDefinition = "smallint")
    @Enumerated
    private Month month;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inst_type")
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
