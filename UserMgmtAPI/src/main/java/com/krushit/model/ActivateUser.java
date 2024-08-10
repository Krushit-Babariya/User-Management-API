package com.krushit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivateUser {
	private String email;
	private String tempPass;
	private String password;
	private String conformPass;
}
