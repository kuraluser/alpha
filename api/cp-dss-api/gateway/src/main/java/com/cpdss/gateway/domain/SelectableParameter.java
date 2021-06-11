package com.cpdss.gateway.domain;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SelectableParameter {
	private String name;
	private List<Parameter> values;

}
