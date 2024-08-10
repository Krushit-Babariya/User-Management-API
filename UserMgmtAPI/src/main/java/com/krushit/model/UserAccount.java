package com.krushit.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccount {
	private String name;
	private String email;
	private Long mobileNo;
	private String genger = "Male";
	private LocalDate dob = LocalDate.now();
	private Long aadharNo;
}
