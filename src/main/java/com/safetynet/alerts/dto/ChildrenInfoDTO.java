package com.safetynet.alerts.dto;

import java.util.List;

public class ChildrenInfoDTO {

	List<ChildInfoDTO> listOfChildren;

	public ChildrenInfoDTO() {
	}

	public ChildrenInfoDTO(List<ChildInfoDTO> listOfChildren) {
		this.listOfChildren = listOfChildren;
	}

	public List<ChildInfoDTO> getListOfChildren() {
		return listOfChildren;
	}

	public void setListOfChildren(List<ChildInfoDTO> listOfChildren) {
		this.listOfChildren = listOfChildren;
	}

}
