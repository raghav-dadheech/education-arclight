package com.study.app;

import java.time.LocalDate;
import java.time.Period;

public class Util {

	public static int calculateAge(final LocalDate birthDate) {
        if (birthDate != null) {
        	final LocalDate currentDate = LocalDate.now();
            return Period.between(birthDate, currentDate).getYears();
        } else {
            return 0;
        }
	}
}
