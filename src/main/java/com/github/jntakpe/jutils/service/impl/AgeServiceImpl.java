package com.github.jntakpe.jutils.service.impl;

import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.jutils.domain.Age;
import com.github.jntakpe.jutils.service.AgeService;
import org.joda.time.*;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Implémentation des opérations sur l'age
 *
 * @author jntakpe
 */
@Service
public class AgeServiceImpl implements AgeService {

    @Override
    public Age calcAge(Date birthdate) {
        DateTime birthdateDT = new DateTime(birthdate);
        DateTime now = DateTime.now();
        if (now.isBefore(birthdateDT))
            throw new BusinessException(BusinessCode.FUTURE_BIRTHDATE);
        Interval interval = new Interval(birthdateDT, now);
        Age age = new Age();
        age.setMonth(Months.monthsIn(interval).getMonths());
        age.setWeek(Weeks.weeksIn(interval).getWeeks());
        age.setDay(Days.daysIn(interval).getDays());
        age.setHour(Hours.hoursIn(interval).getHours());
        age.setMinute(Minutes.minutesIn(interval).getMinutes());
        age.setSecond(interval.toDuration().getStandardSeconds());
        return age;
    }
}
